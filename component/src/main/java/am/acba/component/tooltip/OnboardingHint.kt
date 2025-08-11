package am.acba.component.tooltip

import am.acba.component.databinding.OnboardingHintLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getDisplayHeight
import am.acba.component.extensions.getDisplayWidth
import am.acba.component.extensions.getStatusBarHeight
import am.acba.component.extensions.inflater
import am.acba.component.extensions.log
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams

@SuppressLint("ViewConstructor")
class OnboardingHint(
    context: Context,
    private val contentAndViews: List<Pair<TooltipModel, View>>,
    @StringRes private val lastButtonTextResource: Int? = null,
    private val lastButtonText: String? = null,
    private val onLastButtonClick: () -> Unit = {},
    private val onCloseButtonClick: () -> Unit = {}
) : FrameLayout(context, null, 0) {

    private val binding by lazy { OnboardingHintLayoutBinding.inflate(context.inflater(), this, false) }
    private var currentPosition = 0
    private var onFinish: (() -> Unit)? = null
    private var scrollView: ScrollView? = null

    init {
        addView(binding.root)
        binding.tooltip.setBackwardClickListener {
            currentPosition--
            handleScrollPositionAndChangeTargetView()
        }
        binding.tooltip.setForwardClickListener {
            currentPosition++
            handleScrollPositionAndChangeTargetView()
        }
        binding.tooltip.setSkipClickListener {
            onFinish?.invoke()
            onLastButtonClick.invoke()
        }
        binding.tooltip.setCloseTooltipClickListener {
            onFinish?.invoke()
            onCloseButtonClick.invoke()
        }
        if (contentAndViews.isNotEmpty()) {
            setTargetViews()
        }
    }

    //Added to return tooltip current position on forward click
    fun setForwardClickListener(onClick: (Int) -> Unit) {
        binding.tooltip.setForwardClickListener {
            currentPosition++
            handleScrollPositionAndChangeTargetView()
            onClick(currentPosition)
        }
    }

    private fun handleScrollPositionAndChangeTargetView() {
        val view = contentAndViews[currentPosition].second
        if (!view.isViewFullyVisible()) {
            scrollView?.smoothScrollTo(0, view.y.toInt())
            Handler(Looper.getMainLooper()).postDelayed({
                changeTargetView()
            }, 250)
        } else {
            changeTargetView()
        }
    }

    private fun View.isViewFullyVisible(): Boolean {
        val localVisibleRect = Rect()
        getLocalVisibleRect(localVisibleRect)
        return localVisibleRect.top == 0 && localVisibleRect.bottom == height
    }

    private fun setTooltipCountAndText() {
        if (contentAndViews.size > 1)
            binding.tooltip.setTooltipCount(
                (currentPosition + 1),
                contentAndViews.size
            )
    }

    private fun setButtonTitle(@StringRes title: Int) {
        binding.tooltip.setButtonTitle(title)
    }

    private fun setButtonTitle(title: String) {
        binding.tooltip.setButtonTitle(title)
    }

    private fun setTargetViews() {
        val globalListener = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val view = contentAndViews.first().second
                binding.clipView.clipForView(view)
                var height = binding.infoContainer.height
                if (contentAndViews.first().first.imageUrl.isNotEmpty() || contentAndViews.first().first.localImage != null || contentAndViews.first().first.lottieAnimationName.isNotEmpty()) {
                    height += 100.dpToPx()
                }
                val coordinates = calculateNewXYCoordinatesForInfoContainer(view, height)
                binding.infoContainer.x = coordinates.first
                binding.infoContainer.y = coordinates.second
                binding.tooltip.setForwardVisibility(contentAndViews.size > 1)
                binding.tooltip.setSkipVisibility(contentAndViews.size == 1)
                binding.tooltip.setTooltip(contentAndViews[0].first)
                lastButtonTextResource?.let(::setButtonTitle) ?: run {
                    lastButtonText?.let(::setButtonTitle)
                }
                setTooltipCountAndText()
            }
        }
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(globalListener)
    }


    private fun changeTargetView() {
        binding.tooltip.setTooltip(contentAndViews[currentPosition].first)
        var height: Int
        binding.root.viewTreeObserver
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    height = binding.infoContainer.height
                    height.log("info height by observer")
                    val view = contentAndViews[currentPosition].second
                    val coordinates = calculateNewXYCoordinatesForInfoContainer(view, height)
                    binding.infoContainer.animate()
                        .y(coordinates.second)
                        .setInterpolator(LinearInterpolator())
                        .setDuration(300)
                        .start()
                    binding.infoContainer.animate()
                        .x(coordinates.first)
                        .setInterpolator(LinearInterpolator())
                        .setDuration(300)
                        .withStartAction {
                            binding.clipView.clipForView(view)
                        }
                        .start()
                }
            })

        binding.tooltip.setSkipVisibility(contentAndViews.size - 1 == currentPosition)
        binding.tooltip.setForwardVisibility(contentAndViews.size - 1 != currentPosition)
        binding.tooltip.setBackwardVisibility(currentPosition != 0)
        binding.root.invalidate()
        setTooltipCountAndText()
    }

    private fun calculateNewXYCoordinatesForInfoContainer(
        view: View,
        height: Int
    ): Pair<Float, Float> {
        val displayHeight = context.getDisplayHeight()
        val location = intArrayOf(-1, -1)
        view.getLocationOnScreen(location)
        val viewX = location[0].toFloat().log("Coordinates", "Location X ->")
        val viewY = location[1].toFloat().log("Coordinates", "Location Y ->")
        binding.infoContainer.height.toString().log("info height")

        return if ((displayHeight - viewY - view.height - 16.dpToPx() - context.getStatusBarHeight()) < height) {
            val x = calculateXCoordinateOfView(view)
            setAnchorPosition(
                false,
                viewX - x + view.width / 2 - binding.anchor.width / 2
            )

            Pair(x, viewY - view.height - 12.dpToPx())
        } else {
            val x = calculateXCoordinateOfView(view)
            setAnchorPosition(
                true,
                viewX - x + view.width / 2 - binding.anchor.width / 2
            )

            Pair(x, viewY + view.height + 12.dpToPx())
        }

    }

    private fun setAnchorPosition(isTopAnchor: Boolean, newAnchorX: Float) {
        binding.anchor.updateLayoutParams<ConstraintLayout.LayoutParams> {
            if (isTopAnchor) {
                binding.anchor.rotation = 180f
                topToBottom = ConstraintLayout.LayoutParams.UNSET
                bottomToTop = binding.tooltip.id
            } else {
                binding.anchor.rotation = 0f
                bottomToTop = ConstraintLayout.LayoutParams.UNSET
                topToBottom = binding.tooltip.id
            }
            startToStart = binding.infoContainer.id
            endToEnd = binding.infoContainer.id
        }
        binding.anchor.x = newAnchorX
    }

    private fun calculateXCoordinateOfView(view: View): Float {
        val location = intArrayOf(-1, -1)
        view.getLocationOnScreen(location)
        val viewX = location[0].toFloat().log("Coordinates", "Location X ->")
        val displayWidth = context.getDisplayWidth()
        return when {
            (viewX + view.width / 2) < displayWidth / 3 -> 8.dpToPx().toFloat()
            (viewX + view.width / 2) < displayWidth * 2 / 3 -> displayWidth / 2 - (binding.infoContainer.width / 2).toFloat()
            else -> displayWidth - binding.infoContainer.width.toFloat() - 8.dpToPx()
        }
    }

    fun handleSkip(onFinish: () -> Unit) {
        this.onFinish = onFinish
    }

    fun scrollView(scrollView: ScrollView) {
        this.scrollView = scrollView
    }

    fun setCloseButtonVisibility(isVisible: Boolean) {
        binding.tooltip.setCloseButtonVisibility(isVisible)
    }
}
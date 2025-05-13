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
    private val onLastButtonClick: () -> Unit = {}
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
        binding.tooltip.setCloseTooltipClickListener { onFinish?.invoke() }
        if (contentAndViews.isNotEmpty()) {
            binding.tooltip.setTooltip(contentAndViews[0].first)
            setTargetViews()
            lastButtonTextResource?.let(::setButtonTitle) ?: run {
                lastButtonText?.let(::setButtonTitle)
            }
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

    private fun setTooltipCountAndText(tooltipListSize: Int) {
        if (this.contentAndViews.size > 1)
            binding.tooltip.setTooltipCount(
                (currentPosition + 1),
                tooltipListSize
            )
    }

    private fun setButtonTitle(@StringRes title: Int) {
        binding.tooltip.setButtonTitle(title)
    }

    private fun setButtonTitle(title: String) {
        binding.tooltip.setButtonTitle(title)
    }

    private fun setTargetViews() {
        binding.root.viewTreeObserver
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    currentPosition = 0
                    val view = contentAndViews.first().second
                    binding.clipView.clipForView(view)
                    val height = binding.root.height
                    val coordinates = calculateNewXYCoordinatesForInfoContainer(view, height)
                    binding.clParent.x = coordinates.first
                    binding.clParent.y = coordinates.second
                    binding.tooltip.setForwardVisibility(contentAndViews.size > 1)
                    binding.tooltip.setSkipVisibility(contentAndViews.size == 1)
                }
            })
        setTooltipCountAndText(contentAndViews.size)
    }


    private fun changeTargetView() {
        binding.tooltip.setTooltip(contentAndViews[currentPosition].first)
        var height: Int
        binding.root.viewTreeObserver
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    height = binding.root.height
                    height.log("info height by observer")
                    val view = contentAndViews[currentPosition].second
                    val coordinates = calculateNewXYCoordinatesForInfoContainer(view, height)
                    binding.clParent.animate()
                        .y(coordinates.second)
                        .setInterpolator(LinearInterpolator())
                        .setDuration(300)
                        .start()
                    binding.clParent.animate()
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
        binding.root.height.toString().log("info height")

        return if ((displayHeight - viewY - view.height - 16.dpToPx() - context.getStatusBarHeight()) < height) {
            val x = calculateXCoordinateOfView(view)
            setAnchorPosition(
                false,
                viewX - x + view.width / 2 - binding.anchor.width / 2
            )

            Pair(x, viewY - height - 48.dpToPx() + context.getStatusBarHeight())
        } else {
            val x = calculateXCoordinateOfView(view)
            setAnchorPosition(
                true,
                viewX - x + view.width / 2 - binding.anchor.width / 2
            )

            Pair(x, viewY + view.height - 16.dpToPx() + context.getStatusBarHeight())
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
            startToStart = binding.clParent.id
            endToEnd = binding.clParent.id
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
            (viewX + view.width / 2) < displayWidth * 2 / 3 -> displayWidth / 2 - (binding.root.width / 2).toFloat()
            else -> displayWidth - binding.root.width.toFloat() - 8.dpToPx()
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
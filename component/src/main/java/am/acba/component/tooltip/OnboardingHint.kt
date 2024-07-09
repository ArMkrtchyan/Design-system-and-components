package am.acba.component.tooltip

import am.acba.component.databinding.OnboardingHintLayoutBinding
import am.acba.component.databinding.OnboardingInfoLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getDisplayHeight
import am.acba.component.extensions.getDisplayWidth
import am.acba.component.extensions.inflater
import am.acba.component.extensions.log
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams


class OnboardingHint @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {


    private val onboardingHintBinding by lazy {
        OnboardingHintLayoutBinding.inflate(
            context.inflater(),
            this,
            false
        )
    }

    private val onboardingInfoBinding by lazy {
        OnboardingInfoLayoutBinding.inflate(
            context.inflater(),
            this,
            false
        )
    }
    private val targetViews: MutableList<View> = arrayListOf()
    private var currentPosition = 0
    private var onFinish: (() -> Unit)? = null
    private var tooltipList: List<TooltipModel> = ArrayList()


    init {
        addView(onboardingHintBinding.root)
        onboardingHintBinding.infoContainer.background = null
        onboardingHintBinding.infoContainer.addView(onboardingInfoBinding.root)
        onboardingInfoBinding.tooltip.setForwardClickListener { changeTargetView() }
        onboardingInfoBinding.tooltip.setBackwardClickListener { changeTargetView(false) }
        onboardingInfoBinding.tooltip.setSkipClickListener { onFinish?.invoke() }
        onboardingInfoBinding.tooltip.setCloseTooltipClickListener { onFinish?.invoke() }
    }

    private fun setTooltipCountAndText() {
        if (tooltipList.size > 1)
            onboardingInfoBinding.tooltip.setTooltipCount(
                (currentPosition + 1),
                tooltipList.size
            )
    }

    fun setOnBoardingList(list: List<TooltipModel>) {
        this.tooltipList = list
        if (tooltipList.isNotEmpty()) {
            onboardingInfoBinding.tooltip.setTooltip(tooltipList.get(0))
        }
        setTooltipCountAndText()
    }

    fun setButtonTitle(@StringRes title: Int) {
        onboardingInfoBinding.tooltip.setButtonTitle(title)
    }

    fun setTargetViews(views: List<View>) {
        targetViews.clear()
        targetViews.addAll(views)
        onboardingInfoBinding.root.getViewTreeObserver()
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    onboardingInfoBinding.root.getViewTreeObserver()
                        .removeOnGlobalLayoutListener(this)
                    if (targetViews.isNotEmpty()) {
                        currentPosition = 0
                        val view = targetViews.first()
                        onboardingHintBinding.clipView.clipForView(view)
                        val coordinates = calculateNewXYCoordinatesForInfoContainer(view)
                        onboardingHintBinding.infoContainer.x = coordinates.first
                        onboardingHintBinding.infoContainer.y = coordinates.second
                    }
                    onboardingInfoBinding.tooltip.setForwardVisibility(targetViews.size > 1)
                    onboardingInfoBinding.tooltip.setSkipVisibility(targetViews.size == 1)
                }
            })
    }

    private fun changeTargetView(isNext: Boolean = true) {
        if (isNext) {
            currentPosition++
        } else {
            currentPosition--
        }
        onboardingInfoBinding.tooltip.setTooltip(tooltipList.get(currentPosition))
        setTooltipCountAndText()

        onboardingInfoBinding.tooltip.setSkipVisibility(targetViews.size - 1 == currentPosition)
        onboardingInfoBinding.tooltip.setForwardVisibility(targetViews.size - 1 != currentPosition)
        onboardingInfoBinding.tooltip.setBackwardVisibility(currentPosition != 0)
        val view = targetViews[currentPosition]
        val coordinates = calculateNewXYCoordinatesForInfoContainer(view)
        onboardingHintBinding.infoContainer.animate()
            .y(coordinates.second)
            .setInterpolator(LinearInterpolator())
            .setDuration(390)
            .start()
        onboardingHintBinding.infoContainer.animate()
            .x(coordinates.first)
            .setInterpolator(LinearInterpolator())
            .setDuration(390)
            .withStartAction { onboardingHintBinding.clipView.clipForView(view) }
            .start()

    }

    private fun calculateNewXYCoordinatesForInfoContainer(view: View): Pair<Float, Float> {
        val displayHeight = context.getDisplayHeight()
        return if ((displayHeight - view.y + view.height - 32.dpToPx()) < onboardingInfoBinding.root.height.dpToPx()) {
            val x = calculateXCoordinateOfView(view)
            setAnchorPosition(false, view, x)
            Pair(x, view.y - onboardingInfoBinding.root.height - 32.dpToPx())
        } else {
            val x = calculateXCoordinateOfView(view)
            setAnchorPosition(true, view, x)
            Pair(x, view.y + view.height + 32.dpToPx())

        }

    }

    private fun setAnchorPosition(isTopAnchor: Boolean, view: View, x: Float) {
        onboardingInfoBinding.anchor.updateLayoutParams<ConstraintLayout.LayoutParams> {
            if (isTopAnchor) {
                onboardingInfoBinding.anchor.rotation = 180f
                topToBottom = ConstraintLayout.LayoutParams.UNSET
                bottomToTop = onboardingInfoBinding.tooltip.id
            } else {
                onboardingInfoBinding.anchor.rotation = 0f
                bottomToTop = ConstraintLayout.LayoutParams.UNSET
                topToBottom = onboardingInfoBinding.tooltip.id
            }
            startToStart = onboardingInfoBinding.clParent.id
            endToEnd = onboardingInfoBinding.clParent.id
        }

        onboardingInfoBinding.anchor.x =
            view.x - x + view.width / 2 - onboardingInfoBinding.anchor.width / 2
    }

    private fun calculateXCoordinateOfView(view: View): Float {
        val displayWidth = context.getDisplayWidth()
        return when {
            (view.x + view.width / 2) < displayWidth / 3 -> 8.dpToPx().toFloat()
            (view.x + view.width / 2) < displayWidth * 2 / 3 -> displayWidth / 2 - (onboardingInfoBinding.root.width / 2).toFloat()
            else -> displayWidth - onboardingInfoBinding.root.width.toFloat() - 8.dpToPx()
        }
    }

    fun handleSkip(onFinish: () -> Unit) {
        this.onFinish = onFinish
    }
}
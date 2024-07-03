package am.acba.component.tooltip

import am.acba.component.databinding.OnboardingHintLayoutBinding
import am.acba.component.databinding.OnboardingInfoLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getDisplayHeight
import am.acba.component.extensions.getDisplayWidth
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.annotation.StringRes

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
        if (views.isNotEmpty()) {
            currentPosition = 0
            val view = views.first()
            onboardingHintBinding.clipView.clipForView(view)
            val coordinates = calculateNewXYCoordinatesForInfoContainer(view)
            onboardingHintBinding.infoContainer.x = coordinates.first
            onboardingHintBinding.infoContainer.y = coordinates.second
        }
        onboardingInfoBinding.tooltip.setForwardVisibility(views.size > 1)
        onboardingInfoBinding.tooltip.setSkipVisibility(targetViews.size == 1)
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
        val displayWidth = context.getDisplayWidth()
        val displayHeight = context.getDisplayHeight()
        return Pair(view.x, view.y + view.height + 32.dpToPx())
    }

    fun handleSkip(onFinish: () -> Unit) {
        this.onFinish = onFinish
    }
}
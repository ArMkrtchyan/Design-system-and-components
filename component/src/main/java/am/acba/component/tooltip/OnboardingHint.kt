package am.acba.component.tooltip

import am.acba.component.databinding.OnboardingHintLayoutBinding
import am.acba.component.databinding.OnboardingInfoLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getDisplayHeight
import am.acba.component.extensions.getDisplayWidth
import am.acba.component.extensions.inflater
import am.acba.component.extensions.log
import android.app.Activity
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
    context: Context,
    attrs: AttributeSet? = null,
    activity: Activity,
    tooltipList: List<TooltipModel>,
    viewList: List<View>
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
    private var tooltipList: MutableList<TooltipModel> = ArrayList()
    private var sizeOfTooltipToShow = 0


    init {
        if (tooltipList.isNotEmpty() && viewList.isNotEmpty()) {
            addView(onboardingHintBinding.root)
            onboardingHintBinding.infoContainer.background = null
            onboardingHintBinding.infoContainer.addView(onboardingInfoBinding.root)
            onboardingInfoBinding.tooltip.setBackwardClickListener {
                changeTargetView(
                    false,
                    activity
                )
            }
            onboardingInfoBinding.tooltip.setForwardClickListener {
                changeTargetView(
                    true,
                    activity
                )
            }
            onboardingInfoBinding.tooltip.setSkipClickListener { onFinish?.invoke() }
            onboardingInfoBinding.tooltip.setCloseTooltipClickListener { onFinish?.invoke() }
        } else {
            log("Tooltip error", "Tooltips size and views size are not the same")
        }
    }

    private fun setTooltipCountAndText(tooltipListSize: Int) {
        if (this.tooltipList.size > 1)
            onboardingInfoBinding.tooltip.setTooltipCount(
                (currentPosition + 1),
                tooltipListSize
            )
    }

    fun setTooltipList(list: MutableList<TooltipModel>) {
        this.tooltipList = list
        if (tooltipList.isNotEmpty()) {
            onboardingInfoBinding.tooltip.setTooltip(tooltipList.get(0))
        }
    }

    fun setCancelable(isCancelable: Boolean) {
        if (isCancelable) {
            onboardingHintBinding.root.setOnClickListener {
                onboardingHintBinding.root.removeAllViews()
            }
        }
    }

    fun setButtonTitle(@StringRes title: Int) {
        onboardingInfoBinding.tooltip.setButtonTitle(title)
    }

    fun setTargetViews(views: List<View>, activity: Activity) {
        targetViews.clear()
        targetViews.addAll(views)
        onboardingInfoBinding.root.getViewTreeObserver()
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    onboardingInfoBinding.root.getViewTreeObserver()
                        .removeOnGlobalLayoutListener(this)
                    checkListsEquality()
                    if (targetViews.isNotEmpty()) {
                        currentPosition = 0
                        val view = targetViews.first()
                        onboardingHintBinding.clipView.clipForView(view, activity)
                        val height = onboardingInfoBinding.root.height
                        val coordinates = calculateNewXYCoordinatesForInfoContainer(view, height)
                        onboardingHintBinding.infoContainer.x = coordinates.first
                        onboardingHintBinding.infoContainer.y = coordinates.second
                    }
                    onboardingInfoBinding.tooltip.setForwardVisibility(targetViews.size > 1)
                    onboardingInfoBinding.tooltip.setSkipVisibility(targetViews.size == 1)
                }
            })
    }

    private fun checkListsEquality() {
        if (tooltipList.size > targetViews.size) {
            val differCount = tooltipList.size - targetViews.size
            sizeOfTooltipToShow = tooltipList.dropLast(differCount).toMutableList().size
            setTooltipCountAndText(sizeOfTooltipToShow)
        } else if (tooltipList.size < targetViews.size) {
            val differCount = targetViews.size - tooltipList.size
            sizeOfTooltipToShow = targetViews.dropLast(differCount).toMutableList().size
            setTooltipCountAndText(sizeOfTooltipToShow)
        } else {
            sizeOfTooltipToShow = targetViews.size
            setTooltipCountAndText(tooltipList.size)
        }
    }

    private fun changeTargetView(isNext: Boolean = true, activity: Activity) {
        if (isNext) {
            currentPosition++
        } else {
            currentPosition--
        }
        onboardingInfoBinding.tooltip.setTooltip(tooltipList.get(currentPosition))
        checkListsEquality()
        var height: Int
        onboardingInfoBinding.root.getViewTreeObserver()
            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    onboardingInfoBinding.root.getViewTreeObserver()
                        .removeOnGlobalLayoutListener(this)
                    height = onboardingInfoBinding.root.height
                    height.log("info height by observer")
                    val view = targetViews[currentPosition]
                    val coordinates = calculateNewXYCoordinatesForInfoContainer(view, height)
                    onboardingHintBinding.infoContainer.animate()
                        .y(coordinates.second)
                        .setInterpolator(LinearInterpolator())
                        .setDuration(300)
                        .start()
                    onboardingHintBinding.infoContainer.animate()
                        .x(coordinates.first)
                        .setInterpolator(LinearInterpolator())
                        .setDuration(300)
                        .withStartAction {
                            onboardingHintBinding.clipView.clipForView(
                                view,
                                activity
                            )
                        }
                        .start()
                }
            })

        onboardingInfoBinding.tooltip.setSkipVisibility(sizeOfTooltipToShow - 1 == currentPosition)
        onboardingInfoBinding.tooltip.setForwardVisibility(sizeOfTooltipToShow - 1 != currentPosition)
        onboardingInfoBinding.tooltip.setBackwardVisibility(currentPosition != 0)
        onboardingInfoBinding.root.invalidate()

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
        onboardingInfoBinding.root.height.toString().log("info height")

        return if ((displayHeight - viewY - view.height - 16.dpToPx()) < height) {
            val x = calculateXCoordinateOfView(view)
            setAnchorPosition(
                false,
                viewX - x + view.width / 2 - onboardingInfoBinding.anchor.width / 2
            )

            Pair(x, viewY - height - 48.dpToPx())

        } else {
            val x = calculateXCoordinateOfView(view)
            setAnchorPosition(
                true,
                viewX - x + view.width / 2 - onboardingInfoBinding.anchor.width / 2
            )
            Pair(x, viewY + view.height - 16.dpToPx())
        }

    }

    private fun setAnchorPosition(isTopAnchor: Boolean, newAnchorX: Float) {
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
        onboardingInfoBinding.anchor.x = newAnchorX
    }

    private fun calculateXCoordinateOfView(view: View): Float {
        val location = intArrayOf(-1, -1)
        view.getLocationOnScreen(location)
        val viewX = location[0].toFloat().log("Coordinates", "Location X ->")
        val displayWidth = context.getDisplayWidth()
        return when {
            (viewX + view.width / 2) < displayWidth / 3 -> 8.dpToPx().toFloat()
            (viewX + view.width / 2) < displayWidth * 2 / 3 -> displayWidth / 2 - (onboardingInfoBinding.root.width / 2).toFloat()
            else -> displayWidth - onboardingInfoBinding.root.width.toFloat() - 8.dpToPx()
        }
    }

    fun handleSkip(onFinish: () -> Unit) {
        this.onFinish = onFinish
    }
}
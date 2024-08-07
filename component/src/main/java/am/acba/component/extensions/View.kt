package am.acba.component.extensions

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.View
import android.view.View.MeasureSpec
import android.view.animation.AccelerateInterpolator
import androidx.core.app.FrameMetricsAggregator

fun View.animateRotation(
    target: Float,
    duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
    interpolator: TimeInterpolator? = AccelerateInterpolator(),
    startAction: (View.() -> Unit)? = null,
    endAction: (View.() -> Unit)? = null,
) {
    startAction?.invoke(this)
    animate().rotation(target).setDuration(duration).setInterpolator(interpolator).withEndAction {
        endAction?.invoke(this)
    }.start()
}

fun View.expandHeight(duration: Long = 0) {
    this.measure(
        MeasureSpec.makeMeasureSpec(this.rootView.width, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(this.rootView.height, MeasureSpec.AT_MOST)
    )
    val targetHeight = this.measuredHeight

    val heightAnimator = ValueAnimator.ofInt(0, targetHeight)
    heightAnimator.addUpdateListener { animation ->
        this.layoutParams.height = animation.animatedValue as Int
        this.requestLayout()
    }
    heightAnimator.duration = if (duration > 0) duration else FrameMetricsAggregator.ANIMATION_DURATION.toLong()
    heightAnimator.start()
}

fun View.collapseHeight(duration: Long = 0) {
    val initialHeight = this.measuredHeight
    val heightAnimator = ValueAnimator.ofInt(0, initialHeight)
    heightAnimator.addUpdateListener { animation ->
        val animatedValue = animation.animatedValue as Int
        this.layoutParams.height = initialHeight - animatedValue
        this.requestLayout()
    }
    heightAnimator.duration = if (duration > 0) duration else FrameMetricsAggregator.ANIMATION_DURATION.toLong()
    heightAnimator.start()
}

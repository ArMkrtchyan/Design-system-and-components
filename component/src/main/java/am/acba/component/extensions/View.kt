package am.acba.component.extensions

import android.animation.TimeInterpolator
import android.view.View
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
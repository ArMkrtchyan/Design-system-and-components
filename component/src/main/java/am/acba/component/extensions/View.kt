package am.acba.component.extensions

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.MeasureSpec
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.app.FrameMetricsAggregator
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.ShimmerFrameLayout

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

fun ImageView.load(
    url: String,
    onResourceReady: ((drawable: Drawable?, exception: GlideException?) -> Unit)? = null,
    timeout: Int = 60000,
) {
    Glide.with(context)
        .load(url)
        .timeout(timeout)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
            ): Boolean {
                onResourceReady?.invoke(null, e)
                return true
            }

            override fun onResourceReady(
                resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
            ): Boolean {
                setImageDrawable(resource)
                onResourceReady?.invoke(resource, null)
                return false
            }
        })
        .into(this)
}

fun ImageView.load(
    url: String,
    shimmer: ShimmerFrameLayout? = null,
    @DrawableRes errorIcon: Int = 0,
    isCircle: Boolean = false,
    timeout: Int = 60000,
    onResourceReady: ((drawable: Drawable?, exception: GlideException?) -> Unit)? = null
) {
    shimmer?.isVisible = true
    shimmer?.startShimmer()
    Glide.with(context)
        .load(url)
        .timeout(timeout)
        .apply(if (isCircle) RequestOptions().circleCrop() else RequestOptions().dontTransform())
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
            ): Boolean {
                shimmer?.stopShimmer()
                shimmer?.isVisible = false
                setImageResource(errorIcon)
                onResourceReady?.invoke(null, e)
                return true
            }

            override fun onResourceReady(
                resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
            ): Boolean {
                shimmer?.stopShimmer()
                shimmer?.isVisible = false
                setImageDrawable(resource)
                onResourceReady?.invoke(resource, null)
                return false
            }
        })
        .into(this)
}
package am.acba.component.extensions

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.app.FrameMetricsAggregator
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
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

fun View.animateXRotation(
    target: Float,
    duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
    interpolator: TimeInterpolator? = AccelerateInterpolator(),
    startAction: (View.() -> Unit)? = null,
    endAction: (View.() -> Unit)? = null,
) {
    startAction?.invoke(this)
    animate().rotationX(target).setDuration(duration).setInterpolator(interpolator).withEndAction {
        endAction?.invoke(this)
    }.start()
}

fun View.expandHeightTo(duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(), height: Int) {
    val heightAnimator = ValueAnimator.ofInt(0, height)
    heightAnimator.addUpdateListener { animation ->
        this.layoutParams.height = animation.animatedValue as Int
        this.requestLayout()
    }
    heightAnimator.duration = duration
    heightAnimator.start()
}


fun View.expandHeight(duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong()) {
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
    heightAnimator.duration = duration
    heightAnimator.start()
}

fun View.expandHeightFromTo(duration: Long = 0, from: Int, to: Int) {
    val heightAnimator = ValueAnimator.ofInt(from, to)
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
        updateLayoutParams<ViewGroup.LayoutParams> {
            height = initialHeight - animation.getAnimatedValue().toString().toInt()
            invalidate()
        }
    }
    heightAnimator.duration = if (duration > 0) duration else FrameMetricsAggregator.ANIMATION_DURATION.toLong()
    heightAnimator.start()
}

fun View.collapseWidth(duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong()) {
    val initialWidth = this.measuredWidth
    val widthAnimator = ValueAnimator.ofInt(0, initialWidth)
    widthAnimator.addUpdateListener { animation ->
        updateLayoutParams<ViewGroup.LayoutParams> {
            width = initialWidth - animation.getAnimatedValue().toString().toInt()
            invalidate()
        }
    }
    widthAnimator.duration = duration
    widthAnimator.start()
}

fun View.expandWidth(duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong()) {
    this.measure(
        MeasureSpec.makeMeasureSpec(this.rootView.width, MeasureSpec.AT_MOST),
        MeasureSpec.makeMeasureSpec(this.rootView.height, MeasureSpec.EXACTLY)
    )
    val targetWidth = this.measuredWidth
    val widthAnimator = ValueAnimator.ofInt(0, targetWidth)
    widthAnimator.addUpdateListener { animation ->
        updateLayoutParams<ViewGroup.LayoutParams> {
            width = animation.getAnimatedValue().toString().toInt()
            invalidate()
        }
    }
    widthAnimator.duration = duration
    widthAnimator.start()
}

fun View.expandWidthTo(duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(), widthTo: Int) {
    this.measure(
        MeasureSpec.makeMeasureSpec(this.rootView.width, MeasureSpec.AT_MOST),
        MeasureSpec.makeMeasureSpec(this.rootView.height, MeasureSpec.EXACTLY)
    )
    val widthAnimator = ValueAnimator.ofInt(0, widthTo)
    widthAnimator.addUpdateListener { animation ->
        updateLayoutParams<ViewGroup.LayoutParams> {
            width = animation.getAnimatedValue().toString().toInt()
            invalidate()
        }
    }
    widthAnimator.duration = duration
    widthAnimator.start()
}

fun View.animateHeightFromTo(duration: Long = 250, from: Float, to: Float) {
    val animator = ValueAnimator.ofFloat(from, to);
    animator.setDuration(duration);

    animator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
        override fun onAnimationUpdate(animation: ValueAnimator) {
            val value = animation.getAnimatedValue().toString().toFloat()
            updateLayoutParams<ViewGroup.LayoutParams> {
                height = value.toInt()
                invalidate()
            }
        }
    })
    animator.start()
}

fun View.animateAlpha(
    target: Float,
    duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
    startAction: (View.() -> Unit)? = null,
    endAction: (View.() -> Unit)? = null,
) {
    startAction?.invoke(this)
    animate().alpha(target).setDuration(duration).withEndAction {
        endAction?.invoke(this)
    }.start()
}

fun TextView.animateTextSize(
    startSize: Float,
    endSize: Float,
    duration: Long = FrameMetricsAggregator.ANIMATION_DURATION.toLong(),
) {
    val animator = ValueAnimator.ofFloat(startSize, endSize);
    animator.setDuration(duration);

    animator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
        override fun onAnimationUpdate(animation: ValueAnimator) {
            val value = animation.getAnimatedValue().toString().toFloat()
            setTextSize(TypedValue.COMPLEX_UNIT_SP, value)
            postInvalidate()
        }
    })
    animator.start()
}

fun View.shakeViewHorizontally(duration: Long) {
    val shakeXAnimator = ObjectAnimator.ofFloat(this, "translationX", 0f, 10f, -10f, 8f, -8f, 5f, -5f, 0f)
    shakeXAnimator.duration = duration
    shakeXAnimator.start()
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

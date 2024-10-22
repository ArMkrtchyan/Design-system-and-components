package am.acba.component.progressBar

import am.acba.component.databinding.SegmentMediaViewLayoutBinding
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import com.airbnb.lottie.SimpleColorFilter

class SegmentMediaView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs, 0) {
    private val binding: SegmentMediaViewLayoutBinding = SegmentMediaViewLayoutBinding.inflate(LayoutInflater.from(context), this, true)


    fun setMediaAnimation(animation: String, color:Int) {
        binding.lavMedia.setAnimation(animation)
        binding.lavMedia.playAnimation()
        binding.lavMedia.colorFilter = SimpleColorFilter(color)
    }

    fun setImageView(resource: Int) {
        binding.ivMedia.setImageResource(resource)
    }

    fun showLottieView() {
        animateViewSize(binding.lavMedia, 14.dpToPx(), 14.dpToPx(), 200)
        animateViewSize(binding.ivMedia, 0.dpToPx(), 0.dpToPx(), 200)
        binding.lavMedia.playAnimation()
    }

    fun resizeImageView(size:Int = 8) {
        animateViewSize(binding.ivMedia, size.dpToPx(), size.dpToPx(), 200)
        animateViewSize(binding.lavMedia, 0.dpToPx(), 0.dpToPx(), 200)
    }

    private fun animateViewSize(view: View, targetWidth: Int, targetHeight: Int, duration: Long = 300) {
        val initialWidth = view.width
        val initialHeight = view.height

        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = duration
        valueAnimator.interpolator = DecelerateInterpolator()

        valueAnimator.addUpdateListener { animator ->
            val progress = animator.animatedValue as Float
            val newWidth = (initialWidth + (targetWidth - initialWidth) * progress).toInt()
            val newHeight = (initialHeight + (targetHeight - initialHeight) * progress).toInt()

            val layoutParams = view.layoutParams
            layoutParams.width = newWidth
            layoutParams.height = newHeight
            view.layoutParams = layoutParams
        }

        valueAnimator.start()
    }

    fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()

}
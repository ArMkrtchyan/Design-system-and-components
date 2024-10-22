package am.acba.component.progressBar

import am.acba.component.databinding.CardShippingSegmentLayoutBinding
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat


class ShippingProgressSegment @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs, 0) {
    private val binding: CardShippingSegmentLayoutBinding = CardShippingSegmentLayoutBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        setLayoutWeight()
    }

    fun startProgress(progressValue: Int = 100, isAnimated: Boolean = true) {
        if (isAnimated) {
            val animator = ObjectAnimator.ofInt(binding.progress, "progress", binding.progress.progress, progressValue)
            animator.duration = 500
            animator.start()
            showLottieView()
        } else binding.progress.progress = progressValue
    }

    fun getProgress() = binding.progress.progress

    fun setSegmentImage(icon: Int) {
        binding.segmentMediaView.setImageView(icon)
    }

    fun setTrackThickness(trackThickness: Int) {
        binding.progress.trackThickness = trackThickness
    }

    fun showLottieView() {
        binding.segmentMediaView.showLottieView()
    }

    fun showImageView() {
        binding.segmentMediaView.resizeImageView()
    }

    private fun setLayoutWeight(weight: Float = 1f) {
        val layoutParams = this.layoutParams as? LinearLayoutCompat.LayoutParams
            ?: LinearLayoutCompat.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.weight = weight
        this.layoutParams = layoutParams
        addView(binding.root)
    }

    fun setMediaAnimation(animation: String?, color: Int) {
        if (animation != null) {
            binding.segmentMediaView.setMediaAnimation(animation, color)
        }
    }
}
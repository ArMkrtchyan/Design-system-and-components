package am.acba.component.progressBar

import am.acba.component.R
import am.acba.component.databinding.CardProgressLayoutBinding
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat

class ShippingProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayoutCompat(context, attrs, 0) {
    private val binding: CardProgressLayoutBinding = CardProgressLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    private var segmentCount: Int
    private var trackThickness: Int
    private var mediaAnimation: String? = null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ShippingProgressBar).apply {
            try {
                segmentCount = getInt(R.styleable.ShippingProgressBar_shippingStepCount, 4)
                trackThickness = getDimensionPixelOffset(R.styleable.ShippingProgressBar_shippingTrackThickness, -1)
            } finally {
                recycle()
            }
        }
        addProgressBar(segmentCount)

    }

    private fun addProgressBar(count: Int) {
        binding.progress.weightSum = segmentCount.toFloat()
        for (i in 0 until count) {
            val shippingProgressSegment = ShippingProgressSegment(context)
            if (trackThickness > 0) shippingProgressSegment.setTrackThickness(trackThickness)
            shippingProgressSegment.setSegmentImage(R.drawable.ic_dot)
            shippingProgressSegment.setMediaAnimation("check_test.json", R.attr.contentBrand)
            shippingProgressSegment.showImageView()
            binding.progress.addView(shippingProgressSegment)
        }
        binding.lastStep.resizeImageView()
        binding.lastStep.setImageView(R.drawable.ic_dot)
        binding.lastStep.setMediaAnimation("check_test.json", R.attr.contentBrand)

    }

    fun setMediaAnimation(animation: String) {
        mediaAnimation = animation
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun setProgress(index: Int, progressWithAnimate: Boolean = true) {
        val delayBetweenAnimations = 500L
        val handler = Handler(Looper.getMainLooper())

        for (i in 0..binding.progress.childCount) {
            val child = binding.progress.getChildAt(i)
            if (child is ShippingProgressSegment) {
                if (i < index) {
                    if (progressWithAnimate && child.getProgress() == 0) {
                        handler.postDelayed({
                            startProgressImageAnimation(child.x + child.width - binding.image.width / 2)
                            child.startProgress()
                            child.showLottieView()
                            if (i == segmentCount -1) handler.postDelayed({binding.lastStep.showLottieView()}, 500)
                        }, i * delayBetweenAnimations)
                    } else child.startProgress(100, false)
                }
            }
        }
    }

    private fun startProgressImageAnimation(value: Float) {
        ObjectAnimator.ofFloat(binding.image, "translationX", value).apply {
            duration = 500
            start()
        }
    }
}
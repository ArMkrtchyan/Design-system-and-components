package am.acba.component.progressBar

import am.acba.component.R
import am.acba.component.databinding.ProgressIndicatorBinding
import am.acba.component.databinding.SegmentedProgressLayoutBinding
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.progressindicator.LinearProgressIndicator

class SegmentedProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayoutCompat(context, attrs, 0) {

    private val binding: SegmentedProgressLayoutBinding = SegmentedProgressLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    private var segmentCount: Int
    private var trackThickness: Int

    init {
        context.obtainStyledAttributes(attrs, R.styleable.SegmentedProgressBar).apply {
            try {
                segmentCount = getInt(R.styleable.SegmentedProgressBar_segmentCount, 4)
                trackThickness = getDimensionPixelOffset(R.styleable.SegmentedProgressBar_trackThickness, -1)
            } finally {
                recycle()
            }
        }
        binding.parent.weightSum = segmentCount.toFloat()
        addProgressBar(segmentCount, trackThickness)
    }

    private fun addProgressBar(count: Int, trackThickness: Int) {
        for (i in 0 until count) {
            val progressLayout = ProgressIndicatorBinding.inflate(LayoutInflater.from(context), binding.parent, false)
            if (trackThickness > 0) progressLayout.lpLimitProgressBar.trackThickness = trackThickness
            binding.parent.addView(progressLayout.root)
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun setProgress(index: Int, progressWithAnimate: Boolean = true) {
        val delayBetweenAnimations = 500L
        val handler = Handler(Looper.getMainLooper())

        for (i in 0 until binding.parent.childCount) {
            val child = binding.parent.getChildAt(i)
            if (child is LinearProgressIndicator) {
                if (i < index) {
                    if (progressWithAnimate) {
                        handler.postDelayed({
                            val animator = ObjectAnimator.ofInt(child, "progress", child.progress, 100)
                            animator.duration = 500
                            animator.start()
                        }, i * delayBetweenAnimations)
                    } else child.progress = 100

                } else {
                    child.progress = 0
                }
            }
        }
    }
}

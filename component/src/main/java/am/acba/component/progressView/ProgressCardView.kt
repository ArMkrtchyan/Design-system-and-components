package am.acba.component.progressView

import am.acba.component.R
import am.acba.component.databinding.ViewProgressCardBinding
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

@SuppressLint("CustomViewStyleable")
class ProgressCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, 0) {

    private val binding by lazy { ViewProgressCardBinding.inflate(context.inflater(), this, false) }
    private var title: String
    private var subTitle: String
    private var currentStep = 1

    init {
        addView(binding.root)
        context.obtainStyledAttributes(attrs, R.styleable.ProgressCardView).apply {
            try {
                title = getString(R.styleable.ProgressCardView_ProgressViewTitleText) ?: ""
                subTitle = getString(R.styleable.ProgressCardView_ProgressViewSubTitleText) ?: ""
            } finally {
                recycle()
            }
        }
    }

    fun nextStep() {
        if (currentStep <= 3) {
            currentStep++
            updateStepUI(currentStep)
        } else {
            currentStep = 1
            nextStep()
        }
    }

    private fun updateStepUI(step: Int) {
        when (step) {
            1 -> {
                binding.step1.imageTintList = context.getColorStateListFromAttr(R.attr.contentBrand)
                binding.step2.imageTintList = context.getColorStateListFromAttr(R.attr.borderBrandTonal1Disable)
                binding.step3.imageTintList = context.getColorStateListFromAttr(R.attr.borderBrandTonal1Disable)
                animateProgressBar(33)
            }

            2 -> {
                binding.step1.imageTintList = context.getColorStateListFromAttr(R.attr.contentBrand)
                binding.step2.imageTintList = context.getColorStateListFromAttr(R.attr.contentBrand)
                binding.step3.imageTintList = context.getColorStateListFromAttr(R.attr.borderBrandTonal1Disable)
                animateProgressBar(66)
            }

            3 -> {
                binding.step1.imageTintList = context.getColorStateListFromAttr(R.attr.contentBrand)
                binding.step2.imageTintList = context.getColorStateListFromAttr(R.attr.contentBrand)
                binding.step3.imageTintList = context.getColorStateListFromAttr(R.attr.contentBrand)
                animateProgressBar(100)
            }
        }
    }

    private fun animateProgressBar(progress: Int) {
        val animator = ObjectAnimator.ofInt(binding.stepProgressBar, "progress", binding.stepProgressBar.progress, progress)
        animator.duration = 500 // 0.5 second animation
        animator.start()
    }
}



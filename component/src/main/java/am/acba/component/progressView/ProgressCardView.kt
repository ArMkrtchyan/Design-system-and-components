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
    private var progressViewStepCount: Int
    private var progressImageVisibility: Boolean

    init {
        addView(binding.root)
        context.obtainStyledAttributes(attrs, R.styleable.ProgressCardView).apply {
            try {
                title = getString(R.styleable.ProgressCardView_ProgressViewTitleText) ?: ""
                subTitle = getString(R.styleable.ProgressCardView_ProgressViewSubTitleText) ?: ""
                progressViewStepCount = getInt(R.styleable.ProgressCardView_ProgressViewStepCount, 0)
                progressImageVisibility = getBoolean(R.styleable.ProgressCardView_progressImageVisibility, true)
            } finally {
                recycle()
            }
            binding.shippingProgress.addProgressBar(progressViewStepCount)
            binding.title.text = title
            binding.subtitle.text = subTitle
            progressImageVisibility(progressImageVisibility)
        }
    }

    fun setProgress(index: Int, progressWithAnimate: Boolean = true) {
        binding.shippingProgress.setProgress(index, progressWithAnimate)
    }

    private fun progressImageVisibility(visible: Boolean) {
        binding.shippingProgress.progressImageVisibility(visible)
    }
}



package am.acba.component.progressView

import am.acba.component.R
import am.acba.component.databinding.AccordionViewBinding
import am.acba.component.databinding.PrimaryProgressViewLayoutBinding
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class PrimaryProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, 0) {
    private val binding by lazy { PrimaryProgressViewLayoutBinding.inflate(context.inflater(), this, false) }
    private var topLeftText: String
    private var topRightText: String
    private var bottomLeftText: String
    private var bottomRightText: String
    private var progressMaxValue: Int

    init {
        addView(binding.root)
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryProgressView).apply {
            try {
                topLeftText = getString(R.styleable.PrimaryProgressView_topLeftText) ?: ""
                topRightText = getString(R.styleable.PrimaryProgressView_topRightText) ?: ""
                bottomLeftText = getString(R.styleable.PrimaryProgressView_bottomLeftText) ?: ""
                bottomRightText = getString(R.styleable.PrimaryProgressView_bottomRightText) ?: ""
                progressMaxValue = getInteger(R.styleable.PrimaryProgressView_progress_maxValue, 0)


                binding.lpLimitProgressBar.max = progressMaxValue
                if (topLeftText.isNotEmpty()) {
                    binding.tvTopLeft.visibility = VISIBLE
                    binding.tvTopLeft.text = topLeftText
                }
                if (topRightText.isNotEmpty()) {
                    binding.tvTopRight.visibility = VISIBLE
                    binding.tvTopRight.text = topRightText
                }
                if (bottomLeftText.isNotEmpty()) {
                    binding.tvBottomLeft.visibility = VISIBLE
                    binding.tvBottomLeft.text = bottomLeftText

                }
                if (bottomRightText.isNotEmpty()) {
                    binding.tvBottomRight.visibility = VISIBLE
                    binding.tvBottomRight.text = bottomRightText
                }
            } finally {
                recycle()
            }
        }
    }

    fun setProgress(progress: Int) {
        binding.lpLimitProgressBar.progress = progress
    }
}


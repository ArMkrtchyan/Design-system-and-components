package am.acba.component.divider

import am.acba.component.R
import am.acba.component.databinding.DividerWithTextLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getDisplayWidth
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams

class PrimaryDivider : FrameLayout {
    private val binding by lazy {
        DividerWithTextLayoutBinding.inflate(
            context.inflater(),
            this,
            false
        )
    }

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryDivider).apply {
            addView(binding.root)
            try {
                val dividerText = getString(R.styleable.PrimaryDivider_android_text)

                binding.dividerText.text = dividerText
                binding.dividerText.isVisible = !dividerText.isNullOrEmpty()
                if (!isInEditMode) {
                    val dividerTextMaxWidth =
                        getFloat(R.styleable.PrimaryDivider_dividerTextMaxWidth, 0.8f)
                    binding.dividerText.maxWidth =
                        (context.getDisplayWidth() * dividerTextMaxWidth).toInt()
                }
                if (hasValue(R.styleable.PrimaryDivider_dividerTextAppearance)) {
                    val textStyle = getResourceId(
                        R.styleable.PrimaryDivider_dividerTextAppearance,
                        R.style.Small_Regular
                    )
                    binding.dividerText.setTextAppearance(textStyle)
                }
                val textColor = getColorStateList(R.styleable.PrimaryDivider_dividerTextColor)
                textColor?.let { binding.dividerText.setTextColor(getColorStateList(R.styleable.PrimaryDivider_dividerTextColor)) }
                val thickness =
                    getDimensionPixelOffset(R.styleable.PrimaryDivider_dividerThickness, -1)
                if (thickness != -1) {
                    binding.divider.updateLayoutParams<LayoutParams> { height = thickness }
                }


                if (hasValue(R.styleable.PrimaryDivider_dividerTint)) {
                    binding.divider.backgroundTintList =
                        getColorStateList(R.styleable.PrimaryDivider_dividerTint)
                }

                if (hasValue(R.styleable.PrimaryDivider_dividerBackground)) {
                    binding.divider.background =
                        getDrawable(R.styleable.PrimaryDivider_dividerBackground)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
            requestLayout()
        }
    }

    fun setText(text: String) {
        binding.dividerText.text = text
        binding.dividerText.isVisible = text.isNotEmpty()
    }
}
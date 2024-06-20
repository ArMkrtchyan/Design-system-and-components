package am.acba.component.input

import am.acba.component.R
import am.acba.component.databinding.PrimaryDropdownBinding
import am.acba.component.extensions.inflater
import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.widget.FrameLayout

class PrimaryDropDown @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs, 0) {
    private val binding by lazy { PrimaryDropdownBinding.inflate(context.inflater(), this, false) }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryDropDown).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            addView(binding.root, layoutParams)
            try {
                val text = getString(R.styleable.PrimaryDropDown_android_text)
                binding.inputDropDown.editText?.setText(text)
                binding.inputDropDown.hint = getString(R.styleable.PrimaryDropDown_android_hint)
            } finally {
                recycle()
            }
        }
    }

    fun setText(text: String) {
        binding.inputDropDown.editText?.setText(text)
    }

    fun setHint(hint: String) {
        binding.inputDropDown.hint = hint
    }
}
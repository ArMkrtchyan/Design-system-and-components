package am.acba.component.input

import am.acba.component.R
import am.acba.component.databinding.PrimaryDropdownBinding
import am.acba.component.extensions.inflater
import android.content.Context
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
        binding.root.setOnClickListener {
            binding.inputDropDown.editText?.requestFocus()
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {

        binding.frame.setOnClickListener {
            l?.onClick(it)
        }
    }

    fun setText(text: String) {
        binding.inputDropDown.editText?.setText(text)
    }

    fun setHint(hint: String) {
        binding.inputDropDown.hint = hint
    }

    fun addFocus() {
        binding.inputDropDown.editText?.setBackgroundResource(R.drawable.background_dropdown_focused)
        binding.inputDropDown.setEndIconDrawable(R.drawable.ic_up)
    }

    fun removeFocus() {
        binding.inputDropDown.editText?.setBackgroundResource(R.drawable.background_primary_input)
        binding.inputDropDown.setEndIconDrawable(R.drawable.ic_down)
    }
}
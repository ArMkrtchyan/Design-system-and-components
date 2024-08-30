package am.acba.component.input

import am.acba.component.R
import am.acba.component.databinding.PrimaryDropdownBinding
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.DrawableRes

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
                binding.inputDropDown.setStartIconTintList(getColorStateList(R.styleable.PrimaryDropDown_dropDownStartIconTint))
                binding.inputDropDown.setEndIconTintList(
                    getColorStateList(R.styleable.PrimaryDropDown_dropDownEndIconTint) ?: context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
                )
                setStartIcon(getDrawable(R.styleable.PrimaryDropDown_dropDownStartIcon))
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

    fun setStartIcon(drawable: Drawable?) {
        binding.inputDropDown.startIconDrawable = drawable
    }

    fun setStartIcon(@DrawableRes drawableRes: Int) {
        binding.inputDropDown.setStartIconDrawable(drawableRes)
    }

    fun setText(text: String) {
        binding.inputDropDown.editText?.setText(text)
    }

    fun getText(): String {
        return binding.inputDropDown.editText?.text.toString()
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
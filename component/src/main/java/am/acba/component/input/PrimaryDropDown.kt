package am.acba.component.input

import am.acba.component.R
import am.acba.component.databinding.PrimaryDropdownBinding
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.extensions.parcelable
import am.acba.component.extensions.restoreChildViewStates
import am.acba.component.extensions.saveChildViewStates
import am.acba.component.extensions.sparseParcelableArray
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.widget.FrameLayout
import androidx.annotation.DrawableRes

open class PrimaryDropDown @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs, 0) {
    protected val binding by lazy { PrimaryDropdownBinding.inflate(context.inflater(), this, false) }
    private var isEnabled = true

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
                    getColorStateList(R.styleable.PrimaryDropDown_dropDownEndIconTint)
                        ?: context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
                )
                isEnabled = getBoolean(R.styleable.PrimaryDropDown_isDropDownEnabled, true)
                setDropDownEnabled(isEnabled)
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

    fun loadStartIcon(url: String) {
        binding.inputDropDown.loadStartIcon(url)
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

    fun setDropDownEnabled(isEnabled: Boolean) {
        binding.parent.alpha = if (isEnabled) 1F else 0.5F
        binding.parent.isFocusable = isEnabled
        binding.parent.isClickable = isEnabled
        binding.frame.isFocusable = isEnabled
        binding.frame.isClickable = isEnabled
        binding.inputDropDown.isFocusable = isEnabled
        binding.inputDropDown.isClickable = isEnabled
    }

    companion object {
        const val SUPER_STATE_KEY = "SUPER_STATE_KEY"
        const val SPARSE_STATE_KEY = "SPARSE_STATE_KEY"
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState())
            putSparseParcelableArray(SPARSE_STATE_KEY, saveChildViewStates())
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var newState = state
        if (newState is Bundle) {
            val childrenState = newState.sparseParcelableArray<Parcelable>(SPARSE_STATE_KEY)
            childrenState?.let { restoreChildViewStates(it) }
            newState = newState.parcelable(SUPER_STATE_KEY)
        }
        super.onRestoreInstanceState(newState)
    }
}
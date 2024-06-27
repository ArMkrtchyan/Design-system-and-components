package am.acba.component.checkbox

import am.acba.component.R
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.checkbox.MaterialCheckBox

class PrimaryCheckbox : MaterialCheckBox {
    constructor(context: Context) : super(context, null, R.attr.primaryCheckboxStyle)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryCheckboxStyle) {
        init(attrs, R.attr.primaryCheckboxStyle)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, R.attr.primaryCheckboxStyle) {
        init(attrs, R.attr.primaryCheckboxStyle)
    }

    private fun init(attrs: AttributeSet, defStyleAttr: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryCheckbox, defStyleAttr, R.style.CheckBox_Primary).apply {
            checkStatedCheck()
            recycle()
        }
    }

    override fun setCheckedState(checkedState: Int) {
        super.setCheckedState(checkedState)
        checkStatedCheck()
    }

    private fun checkStatedCheck() {
        when (checkedState) {
            STATE_UNCHECKED -> {
                setButtonIconDrawableResource(R.drawable.checkbox_button_icon)
            }

            STATE_INDETERMINATE -> {
                setButtonIconDrawableResource(R.drawable.checkbox_button_indeterminated_icon)
            }

            STATE_CHECKED -> {
                setButtonIconDrawableResource(R.drawable.checkbox_button_icon)
            }
        }
    }
}
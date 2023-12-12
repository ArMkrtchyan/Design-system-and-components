package am.acba.component.checkbox

import am.acba.component.R
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.checkbox.MaterialCheckBox

class PrimaryCheckbox : MaterialCheckBox {
    constructor(context: Context) : super(context, null, R.attr.primaryCheckboxStyle)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryCheckboxStyle) {

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, R.attr.primaryCheckboxStyle) {

    }
}
package am.acba.component.radiobutton

import am.acba.component.R
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.radiobutton.MaterialRadioButton

class PrimaryRadioButton : MaterialRadioButton {
    constructor(context: Context) : super(context, null, R.attr.primaryRadioButtonStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryRadioButtonStyle) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }
}
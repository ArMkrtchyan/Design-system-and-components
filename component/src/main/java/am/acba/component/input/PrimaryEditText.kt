package am.acba.component.input

import am.acba.component.R
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class PrimaryEditText : AppCompatEditText {
    constructor(context: Context) : super(context, null, R.attr.inputEditTextStyle)

    constructor(context: Context, attrs: AttributeSet) : super(
        context, attrs, R.attr.inputEditTextStyle
    ) {

    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, R.attr.inputEditTextStyle) {

    }
}
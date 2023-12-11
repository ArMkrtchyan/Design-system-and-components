package am.acba.component.input

import am.acba.component.R
import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputEditText

class PrimaryEditText : AppCompatEditText {
    constructor(context: Context) : super(context, null, R.attr.inputEditTextStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.inputEditTextStyle)

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, R.attr.inputEditTextStyle) {

    }
}
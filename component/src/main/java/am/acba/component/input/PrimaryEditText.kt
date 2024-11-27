package am.acba.component.input

import android.content.Context
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.textfield.TextInputEditText

class PrimaryEditText : TextInputEditText {
    private var disableDot = false
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        if (disableDot && selStart == selEnd) {
            if (selStart < (text?.length ?: 1)) {
                keyListener = DigitsKeyListener.getInstance("0123456789")
            } else {
                keyListener = DigitsKeyListener.getInstance("0123456789.")
            }
        }
        Log.d("SelectionChanged", "selStart: $selStart, selEnd: $selEnd")
        super.onSelectionChanged(selStart, selEnd)
    }

    fun setDisableDot(isDotDisabled: Boolean) {
        disableDot = isDotDisabled
    }
}

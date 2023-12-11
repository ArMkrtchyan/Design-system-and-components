package am.acba.component.input

import am.acba.component.R
import am.acba.component.extensions.getColorStateListFromAttr
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout

class PrimaryInput : TextInputLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryInput).apply {
            recycle()
        }
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    override fun setErrorEnabled(enabled: Boolean) {
        super.setErrorEnabled(enabled)
        if (enabled) {
            editText?.background = ContextCompat.getDrawable(context, R.drawable.background_error_input)
            val errorTextView: TextView? = findViewById(com.google.android.material.R.id.textinput_error)
            val errorIcon = ContextCompat.getDrawable(context, R.drawable.ic_attention_18)
            errorTextView?.setCompoundDrawablesRelativeWithIntrinsicBounds(errorIcon, null, null, null)
            errorTextView?.compoundDrawablePadding = 8
            errorTextView?.compoundDrawableTintList = context.getColorStateListFromAttr(R.attr.statusError)
        } else {
            editText?.background = ContextCompat.getDrawable(context, R.drawable.background_primary_input)
            val errorTextView: TextView? = findViewById(com.google.android.material.R.id.textinput_error)
            errorTextView?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
        }
    }
}
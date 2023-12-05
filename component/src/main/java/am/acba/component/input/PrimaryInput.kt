package am.acba.component.input

import am.acba.component.R
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updateMarginsRelative
import androidx.core.view.updatePadding
import androidx.core.view.updatePaddingRelative
import com.google.android.material.textfield.TextInputLayout

class PrimaryInput : TextInputLayout {
    private val edittext: PrimaryEditText

    constructor(context: Context) : super(context) {
        edittext = PrimaryEditText(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        edittext = PrimaryEditText(context)
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        edittext = PrimaryEditText(context)
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryInput).apply {
            edittext.layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, 180)
            //      addView(edittext)
            recycle()
        }
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    override fun setErrorEnabled(enabled: Boolean) {
        super.setErrorEnabled(enabled)
        if (enabled) {
            editText?.background = ContextCompat.getDrawable(
                context,
                R.drawable.background_error_input
            )
            val errorTextView: TextView? =
                findViewById(com.google.android.material.R.id.textinput_error)
            errorTextView?.setCompoundDrawablesRelativeWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_error_default
                ),
                null,
                null,
                null
            )
            errorTextView?.compoundDrawablePadding = 8
            errorTextView?.compoundDrawableTintList =
                ContextCompat.getColorStateList(context, R.color.Red_500)
        } else {
            editText?.background = ContextCompat.getDrawable(
                context,
                R.drawable.background_primary_input
            )
            val errorTextView: TextView? =
                findViewById(com.google.android.material.R.id.textinput_error)
            errorTextView?.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                null,
                null
            )
        }
    }
}
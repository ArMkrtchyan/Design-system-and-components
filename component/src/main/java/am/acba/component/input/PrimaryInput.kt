package am.acba.component.input

import am.acba.component.R
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorStateListFromAttr
import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updateMarginsRelative
import com.google.android.material.textfield.TextInputLayout

open class PrimaryInput : TextInputLayout {
    private var textMaxLength = -1
    private var cornerStyle = -1
    private var hasDropDown = false

    constructor(context: Context) : super(context, null, R.attr.primaryInputStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryInputStyle) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryInput).apply {
            try {
                if (hasValueOrEmpty(R.styleable.PrimaryInput_textMaxLength))
                    textMaxLength = getInt(R.styleable.PrimaryInput_textMaxLength, -1)
                cornerStyle = getInt(R.styleable.PrimaryInput_cornerStyle, 0)
                hasDropDown = getBoolean(R.styleable.PrimaryInput_hasDropDown, false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val layoutStyle = if (cornerStyle == 1) R.layout.text_input_edittext_left_corner_layout else R.layout.text_input_edittext_layout
            addView(LayoutInflater.from(context).inflate(layoutStyle, this@PrimaryInput, false))
            if (textMaxLength != -1) setMaxLength(textMaxLength)
            if (hasDropDown) {
                val endIcon = findViewById<ImageButton>(com.google.android.material.R.id.text_input_end_icon)
                editText?.isEnabled = false
                endIcon.setImageResource(R.drawable.ic_down)
                endIcon.imageTintList = context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
            }
            updateEndIconBackgroundState()
            updateStartIconBackgroundState()
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
            errorTextView?.compoundDrawablePadding = 4.dpToPx()
            errorTextView?.compoundDrawableTintList = context.getColorStateListFromAttr(R.attr.borderDanger)
        } else {
            editText?.background = ContextCompat.getDrawable(context, R.drawable.background_primary_input)
            val errorTextView: TextView? = findViewById(com.google.android.material.R.id.textinput_error)
            errorTextView?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
        }
    }

    fun setMaxLength(maxLength: Int) {
        textMaxLength = maxLength
        val fArray = arrayOfNulls<InputFilter>(1)
        fArray[0] = LengthFilter(maxLength)
        editText?.filters = fArray
    }

    private fun updateEndIconBackgroundState() {
        val endIcon = findViewById<ImageButton>(com.google.android.material.R.id.text_input_end_icon)
        endIcon.background = ContextCompat.getDrawable(context, R.drawable.background_ghost_brand_cycle)
        endIcon.updateLayoutParams<FrameLayout.LayoutParams> {
            updateMarginsRelative(0, 0, 0, 0)
            updateMargins(0, 0, 0, 0)
        }
    }

    private fun updateStartIconBackgroundState() {
        val startIcon = findViewById<ImageButton>(com.google.android.material.R.id.text_input_start_icon)
        startIcon.background = ContextCompat.getDrawable(context, R.drawable.background_ghost_brand_cycle)
        startIcon.updateLayoutParams<LayoutParams> {
            updateMarginsRelative(0, 0, 0, 0)
            updateMargins(0, 0, 0, 0)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        if (l != null) {
            editText?.isFocusable = false
            editText?.isClickable = true
        }
        editText?.setOnClickListener(l)
    }
}
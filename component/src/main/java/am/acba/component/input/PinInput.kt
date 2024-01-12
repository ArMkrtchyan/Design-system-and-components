package am.acba.component.input

import am.acba.component.R
import am.acba.component.databinding.WidgetPinInputBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class PinInput : FrameLayout {

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs
    ) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }
    private val binding by lazy { WidgetPinInputBinding.inflate(context.inflater(), this, false) }

    private fun init(attrs: AttributeSet) {

        context.obtainStyledAttributes(attrs, R.styleable.PinInput).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            addView(binding.root, layoutParams)
            recycle()
            invalidate()

        }
    }

    private fun setUiPinAsterisk(pinCount: String) {
        when (pinCount.length) {
            1 -> {
                binding.box1.text = "*"
                binding.box2.text = ""
                binding.box3.text = ""
                binding.box4.text = ""
            }

            2 -> {
                binding.box1.text = "*"
                binding.box2.text = "*"
                binding.box3.text = ""
                binding.box4.text = ""
            }

            3 -> {
                binding.box1.text = "*"
                binding.box2.text = "*"
                binding.box3.text = "*"
                binding.box4.text = ""
            }

            4 -> {
                binding.box1.text = "*"
                binding.box2.text = "*"
                binding.box3.text = "*"
                binding.box4.text = "*"
            }
        }
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    fun setError(errorEnabled: Boolean, message: String = "") {
        val errorTextView: TextView = binding.errorText
        if (errorEnabled) {

            binding.box1.background =
                ContextCompat.getDrawable(context, R.drawable.background_error_input)
            binding.box1.background =
                ContextCompat.getDrawable(context, R.drawable.background_error_input)
            binding.box2.background =
                ContextCompat.getDrawable(context, R.drawable.background_error_input)
            binding.box3.background =
                ContextCompat.getDrawable(context, R.drawable.background_error_input)
            binding.box4.background =
                ContextCompat.getDrawable(context, R.drawable.background_error_input)
            binding.errorText.text = message
            val errorIcon = ContextCompat.getDrawable(context, R.drawable.ic_attention_18)
            errorTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                errorIcon,
                null,
                null,
                null
            )
            errorTextView.compoundDrawablePadding = 4.dpToPx()
            errorTextView.compoundDrawableTintList =
                context.getColorStateListFromAttr(R.attr.borderDanger)
            errorTextView.animate().alpha(1.0f).setDuration(200)
        } else {
            binding.box1.background =
                ContextCompat.getDrawable(context, R.drawable.background_pin_input)
            binding.box2.background =
                ContextCompat.getDrawable(context, R.drawable.background_pin_input)
            binding.box3.background =
                ContextCompat.getDrawable(context, R.drawable.background_pin_input)
            binding.box4.background =
                ContextCompat.getDrawable(context, R.drawable.background_pin_input)
            errorTextView.animate().alpha(0.0f).setDuration(200)

        }
    }

}
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

    private val binding by lazy { WidgetPinInputBinding.inflate(context.inflater(), this, false) }
    private val errorTextView: TextView = binding.errorText
    private val pinItemBackground =
        ContextCompat.getDrawable(context, R.drawable.background_pin_input)
    private val pinItemErrorBackground =
        ContextCompat.getDrawable(context, R.drawable.background_error_input)
    private val errorIcon = ContextCompat.getDrawable(context, R.drawable.ic_attention_18)

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


    @SuppressLint("UseCompatTextViewDrawableApis")
    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PinInput).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            addView(binding.root, layoutParams)
            recycle()
            invalidate()
        }
        errorTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
            errorIcon,
            null,
            null,
            null
        )
        errorTextView.compoundDrawablePadding = 4.dpToPx()
        errorTextView.compoundDrawableTintList =
            context.getColorStateListFromAttr(R.attr.borderDanger)
    }

    fun setUiPinCount(pinCount: String) {
        when (pinCount.length) {
            0 -> clearPinInput()
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

    fun addErrorState(message: String = "") {
        binding.errorText.text = message
        binding.box1.background = pinItemErrorBackground
        binding.box2.background = pinItemErrorBackground
        binding.box3.background = pinItemErrorBackground
        binding.box4.background = pinItemErrorBackground
        errorTextView.animate().alpha(1.0f).setDuration(200)
    }

    fun removeErrorState() {
        binding.errorText.text = ""
        binding.box1.background = pinItemBackground
        binding.box2.background = pinItemBackground
        binding.box3.background = pinItemBackground
        binding.box4.background = pinItemBackground
        errorTextView.animate().alpha(0.0f).setDuration(200)
    }

    fun clearPinInput() {
        binding.box1.text = ""
        binding.box2.text = ""
        binding.box3.text = ""
        binding.box4.text = ""
    }
}

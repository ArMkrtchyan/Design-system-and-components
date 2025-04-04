package am.acba.component.input

import am.acba.component.R
import am.acba.component.databinding.WidgetPinInputBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged

class PinInput : FrameLayout {

    private val binding by lazy { WidgetPinInputBinding.inflate(context.inflater(), this, false) }
    private val errorTextView: TextView = binding.errorText
    private val pinItemBackground =
        ContextCompat.getDrawable(context, R.drawable.background_pin_input)
    private val pinItemErrorBackground =
        ContextCompat.getDrawable(context, R.drawable.background_error_input)
    private val errorIcon = ContextCompat.getDrawable(context, R.drawable.ic_attention_18)
    private var showPinCode = false
    private val enteredPin = StringBuilder()

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
            showPinCode = getBoolean(R.styleable.PinInput_showPinCode, false)
            setInputType(getInteger(R.styleable.PinInput_pinType, 0), getInteger(R.styleable.PinInput_pinLength, 4))
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

    fun isPinShown(): Boolean {
        return showPinCode
    }

    fun switchPinVisibility() {
        showPinCode = !showPinCode
        if (showPinCode) {
            showPin()
        } else {
            hidePin()
        }
    }

    fun setPinShow(show: Boolean) {
        showPinCode = show
        if (showPinCode) {
            showPin()
        } else {
            hidePin()
        }
    }

    private fun showPin() {
        drawDigitsOnPinView(enteredPin.toString().map { it.toString() })
    }

    private fun hidePin() {
        drawDigitsOnPinView(enteredPin.toString().map { it.toString() })
    }

    private fun setInputType(inputType: Int, pinCount: Int) {
        when (inputType) {
            0 -> setupNumericPinInput(pinCount)
            1 -> setupGemaltoPinInput()
        }
    }

    private fun setupNumericPinInput(pinCount: Int) {
        initEditText(pinCount)
        initTextChangeListener()
    }

    private fun initEditText(pinCount: Int) {
        when (pinCount) {
            4 -> {
                binding.box5.visibility = View.GONE
                binding.box6.visibility = View.GONE
            }

            6 -> {
                binding.box5.visibility = View.VISIBLE
                binding.box6.visibility = View.VISIBLE
            }
        }
        val fArray = arrayOfNulls<InputFilter>(1)
        fArray[0] = LengthFilter(pinCount)
        binding.numericPinInput.filters = fArray
    }

    private fun setupGemaltoPinInput() {
        binding.numericPinInput.visibility = View.GONE
    }

    private fun initTextChangeListener() {
        binding.numericPinInput.doOnTextChanged { text, _, _, _ ->
            initInputTextWithPinView(text)
        }
    }

    private fun initInputTextWithPinView(text: CharSequence?) {
        enteredPin.clear().append(text)
        drawDigitsOnPinView(enteredPin.map { it.toString() })
    }

    private fun drawDigitsOnPinView(digits: List<String>) {
        listOf(
            binding.box1, binding.box2, binding.box3,
            binding.box4, binding.box5, binding.box6
        ).forEachIndexed { index, textView ->
            textView.text = if (index < digits.size) {
                if (showPinCode) digits[index] else "*"
            } else ""
        }
    }

    fun setUpUIPinCountForGemalto(count: Int) {
        listOf(
            binding.box1, binding.box2, binding.box3,
            binding.box4, binding.box5, binding.box6
        ).forEachIndexed { index, textView ->
            textView.text = if (index < count) "*" else ""
        }
    }

    fun addErrorState(message: String = "") {
        binding.errorText.text = message
        listOf(
            binding.box1, binding.box2, binding.box3,
            binding.box4, binding.box5, binding.box6
        ).forEach { it.background = pinItemErrorBackground }
        errorTextView.animate().alpha(1.0f).setDuration(200)
    }

    fun removeErrorState() {
        binding.errorText.text = ""
        listOf(
            binding.box1, binding.box2, binding.box3,
            binding.box4, binding.box5, binding.box6
        ).forEach { it.background = pinItemBackground }
        errorTextView.animate().alpha(0.0f).setDuration(200)
    }

    fun clearPinInput() {
        listOf(
            binding.box1, binding.box2, binding.box3,
            binding.box4, binding.box5, binding.box6
        ).forEach { it.text = "" }
    }
}

package am.acba.component.input

import am.acba.component.R
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.load
import am.acba.component.extensions.numberDeFormatting
import am.acba.component.extensions.numberFormatting
import am.acba.component.extensions.numberFormattingWithOutDot
import am.acba.component.extensions.shakeViewHorizontally
import am.acba.component.extensions.vibrate
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updateMarginsRelative
import com.google.android.material.textfield.TextInputLayout
import java.text.NumberFormat
import java.util.Locale

open class PrimaryInput : TextInputLayout {

    var enableErrorAnimation = false
    private val TYPE_NUMBER = 1
    private val AMOUNT_FORMATTING = 2
    private val TYPE_EMAIL = 3

    private var textMaxLength = -1
    private var currencyInputMaxLength = -1
    private var cornerStyle = -1
    private var hasDropDown = false
    private var inputType = -1
    private var formattingWithDot = false
    private var validateAfterInput = false
    private var isKeyboardActionClicked = false
    private var isFirstFocusable = true
    private var isDotDisabled = false
    private var cleanString = ""
    private var parsed = 0.0
    private var formatter = ""
    private var current = ""

    private var onOtherActionButtonClick: ((Int) -> Unit)? = null
    private var onDoneButtonClick: (() -> Unit)? = null
    private var mAction: ((Boolean) -> Unit?)? = null

    constructor(context: Context) : super(context, null, R.attr.primaryInputStyle)

    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs,
        R.attr.primaryInputStyle
    ) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryInput).apply {
            try {
                if (hasValueOrEmpty(R.styleable.PrimaryInput_textMaxLength))
                    textMaxLength = getInt(R.styleable.PrimaryInput_textMaxLength, -1)
                cornerStyle = getInt(R.styleable.PrimaryInput_cornerStyle, -1)
                hasDropDown = getBoolean(R.styleable.PrimaryInput_hasDropDown, false)
                isDotDisabled = getBoolean(R.styleable.PrimaryInput_isDotDisabledInMiddleOfText, false)
                inputType = getInt(R.styleable.PrimaryInput_inputType, -1)
                formattingWithDot = getBoolean(R.styleable.PrimaryInput_formattingWithDots, false)
                enableErrorAnimation = getBoolean(R.styleable.PrimaryInput_enableErrorAnimation, false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val layoutStyle =
                if (cornerStyle == 1) R.layout.text_input_edittext_left_corner_layout else R.layout.text_input_edittext_layout
            addView(LayoutInflater.from(context).inflate(layoutStyle, this@PrimaryInput, false))
            val primaryEditText = findViewById<PrimaryEditText>(R.id.primaryEditText)
            primaryEditText.setDisableDot(isDotDisabled = isDotDisabled)
            val text = getString(R.styleable.PrimaryInput_android_text)
            if (!text.isNullOrEmpty()) {
                isHintAnimationEnabled = false
                postDelayed({ isHintAnimationEnabled = true }, 800)
            }
            setText(text)
            isHintAnimationEnabled = true
            if (textMaxLength != -1) setMaxLength(textMaxLength)
            if (hasDropDown) {
                val endIcon =
                    findViewById<ImageButton>(com.google.android.material.R.id.text_input_end_icon)
                editText?.isEnabled = false
                endIcon.setImageResource(R.drawable.ic_down)
                endIcon.imageTintList =
                    context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
            }
            when (inputType) {
                TYPE_NUMBER -> setInputTypeForNumber()
                AMOUNT_FORMATTING -> amountFormattingWhileTyping()
                TYPE_EMAIL -> setInputTypeForEmail()
            }

            updateEndIconBackgroundState()
            updateStartIconBackgroundState()
            suffixTextView.translationY = -8.dpToPx().toFloat()
            suffixTextView.updateLayoutParams<LayoutParams> {
                height = LayoutParams.MATCH_PARENT

            }
            suffixTextView.gravity = Gravity.CENTER_VERTICAL
            val suffixParent = suffixTextView.parent as View
            suffixParent.viewTreeObserver.addOnGlobalLayoutListener {
                if (!suffixText.isNullOrEmpty()) {
                    (suffixTextView.parent as View).isVisible = true
                    suffixTextView.isVisible = true
                }
            }
            editText?.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        val imm = getSystemService(context, InputMethodManager::class.java)
                        imm?.hideSoftInputFromWindow(editText?.windowToken, 0)
                        editText?.clearFocus()
                        setErrorAnimation()

                        onDoneButtonClick?.invoke()
                        return true
                    } else {
                        onOtherActionButtonClick?.invoke(actionId)
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            isKeyboardActionClicked = true
                            setErrorAnimation()
                        }
                    }
                    return false
                }
            })
            recycle()
        }
    }

    fun onKeyboardDoneButtonClick(onDoneButtonClick: () -> Unit) {
        this.onDoneButtonClick = onDoneButtonClick
    }

    fun onKeyboardOtherActionButtonClick(onActionButtonClick: (Int) -> Unit) {
        this.onOtherActionButtonClick = onActionButtonClick
    }

    fun setInputTypeForNumber() {
        editText?.inputType = InputType.TYPE_CLASS_NUMBER
    }

    fun setInputTypeForEmail() {
        editText?.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }

    fun setInputTypeDefault() {
        editText?.inputType = InputType.TYPE_CLASS_TEXT
    }

    fun setAmountText(amount: String) {
        editText?.setText((if (formattingWithDot) amount.numberFormatting() else amount.numberFormattingWithOutDot()))
    }

    private fun amountFormattingWhileTyping() {
        editText?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        val currencyTextWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty() || s.toString() == current) {
                    return
                }

                editText?.removeTextChangedListener(this)
                val amount = s.toString()
                val cleanString = amount.replace(",", "")

                if (amount.contains(".")) {
                    when (amount.split(".")[1].length) {

                        1 -> {
                            val parsed = cleanString.toDoubleOrNull() ?: 0.0
                            val formatter = NumberFormat.getInstance(Locale.ENGLISH)
                            formatter.maximumFractionDigits = 2
                            formatter.minimumFractionDigits = 1
                            formatter.format(parsed)

                            current = formatter.format(parsed)
                            editText?.setText(current)
                            editText?.setSelection(current.length)
                        }

                        2 -> {
                            val parsed = cleanString.toDoubleOrNull() ?: 0.0
                            val formatter = NumberFormat.getInstance(Locale.ENGLISH)
                            formatter.maximumFractionDigits = 2
                            formatter.minimumFractionDigits = 2
                            formatter.format(parsed)

                            current = formatter.format(parsed)
                            editText?.setText(current)
                            editText?.setSelection(current.length)
                        }

                        else -> {
                            if (amount.split(".")[1].isNotEmpty()) {
                                val parts = amount.split(".")
                                val limitedAmount = "${parts[0]}.${parts[1].take(2)}".replace(",", "")

                                val parsed = limitedAmount.toDoubleOrNull() ?: 0.0
                                val formatter = NumberFormat.getInstance(Locale.ENGLISH).apply {
                                    maximumFractionDigits = 2
                                    minimumFractionDigits = 0
                                }

                                current = formatter.format(parsed)
                                editText?.setText(current)
                            }
                        }
                    }
                } else {
                    parsed = cleanString.toDoubleOrNull() ?: 0.0
                    formatter = NumberFormat.getInstance(Locale.ENGLISH).format(parsed)

                    current = formatter.format(parsed)
                    editText?.setText(current)
                    val length = editText?.text?.length ?: 0
                    editText?.setSelection(length.coerceAtMost(current.length))
                }
                editText?.addTextChangedListener(this)
            }
        }
        editText?.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            amountTextFormattingWhileTyping(hasFocus, currencyTextWatcher)
            mAction?.invoke(hasFocus)
        }
    }


    private fun amountTextFormattingWhileTyping(isFocusable: Boolean, currencyTextWatcher: TextWatcher? = null) {
        val text = editText?.text?.toString()?.trim() ?: ""
        if (isFocusable) {
            setMaxLength(currencyInputMaxLength)
            if (text.contains(".") && text.split(".")[1].toDouble() == 0.00) {
                cleanString = text.replace(",", "")
                parsed = cleanString.toDoubleOrNull() ?: 0.0
                formatter = NumberFormat.getInstance(Locale.ENGLISH).format(parsed)
                current = formatter.format(parsed)

                editText?.setText(current)
            }
            editText?.addTextChangedListener(currencyTextWatcher)
        } else {
            val formattedText = text.replace(",", "").numberFormatting()
            setMaxLength(formattedText.length)
            editText?.setText(formattedText)
            editText?.removeTextChangedListener(currencyTextWatcher)
        }
    }

    fun setMaxLength(maxLength: Int) {
        textMaxLength = maxLength
        val fArray = arrayOfNulls<InputFilter>(1)
        fArray[0] = LengthFilter(maxLength)
        editText?.filters = fArray
    }

    fun setMaxLengthForFormattedText(maxLength: Int) {
        currencyInputMaxLength = maxLength
        setMaxLength(currencyInputMaxLength)
    }

    fun onFocusChangeListener(action: ((Boolean) -> Unit)? = null) {
        mAction = action
    }

    fun getDeFormatedStringAmount(): String {
        val amountText = editText?.text?.toString()?.trim() ?: ""
        return if (amountText.isEmpty()) amountText else amountText.numberDeFormatting()
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    override fun setErrorEnabled(enabled: Boolean) {
        super.setErrorEnabled(enabled)

        val tvError: TextView? = findViewById(com.google.android.material.R.id.textinput_error)
        val backgroundRes: Int
        var errorIcon: Drawable? = null
        val errorTextColorRes: ColorStateList

        if (enabled) {
            backgroundRes = R.drawable.background_error_input
            errorTextColorRes = context.getColorStateListFromAttr(R.attr.contentDangerTonal1)

            tvError?.isVisible = error != null
            error?.let {
                errorIcon = ContextCompat.getDrawable(context, R.drawable.ic_attention_18)
                initErrorView(tvError)
            }
        } else {
            backgroundRes = R.drawable.background_primary_input
            errorTextColorRes = context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
        }
        editText?.background = ContextCompat.getDrawable(context, backgroundRes)
        tvError?.setCompoundDrawablesRelativeWithIntrinsicBounds(errorIcon, null, null, null)

        hintTextColor = errorTextColorRes
        defaultHintTextColor = errorTextColorRes
    }

    private fun initErrorView(tvError: TextView?) {
        val errorTint = R.attr.borderDanger
        tvError?.compoundDrawableTintList = context.getColorStateListFromAttr(errorTint)
        tvError?.compoundDrawablePadding = 4.dpToPx()
    }

    fun validateAfterFocusChange(errorMessage: String?, isValid: Boolean = true) {
        if (editText?.hasFocus() == false) {
            val isNotEmpty = editText?.text?.isNotEmpty() == true
            isErrorEnabled = !isValid && isNotEmpty
            validateAfterInput = isNotEmpty
            if (isErrorEnabled) {
                isFirstFocusable = false
                if (isKeyboardActionClicked) setErrorAnimation()
                error = errorMessage
            }
        }
    }

    fun validateAfterTextChange(errorMessage: String?, isValid: Boolean = true) {
        if (validateAfterInput && !isFirstFocusable) {
            if (editText?.text?.isEmpty() == true) {
                isErrorEnabled = false
                validateAfterInput = false
                return
            }
            isErrorEnabled = !isValid && editText?.text?.isNotEmpty() == true

            if (isErrorEnabled) error = errorMessage
        }
    }


    private fun updateEndIconBackgroundState() {
        val endIcon =
            findViewById<ImageButton>(com.google.android.material.R.id.text_input_end_icon)
        endIcon.background =
            ContextCompat.getDrawable(context, R.drawable.background_ghost_brand_cycle)
        endIcon.updateLayoutParams<FrameLayout.LayoutParams> {
            updateMarginsRelative(0, 0, 0, 0)
            updateMargins(0, 0, 0, 0)
        }
        endIcon.isVisible = endIconDrawable != null
        editText?.setPadding(
            editText?.paddingLeft ?: 0,
            editText?.paddingTop ?: 0,
            if (endIconDrawable != null) 48.dpToPx() else editText?.paddingRight ?: 0,
            editText?.paddingBottom ?: 0
        )
    }

    private fun updateStartIconBackgroundState() {
        val startIcon =
            findViewById<ImageButton>(com.google.android.material.R.id.text_input_start_icon)
        startIcon.background =
            ContextCompat.getDrawable(context, R.drawable.background_ghost_brand_cycle)
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

    fun setText(text: String?) {
        editText?.setText(text)
        if (isInEditMode) {
            isHintAnimationEnabled = false
        }
    }

    fun setText(@StringRes resId: Int) {
        editText?.setText(resId)
    }

    fun setInputExpandedHintEnabled(isExpandedHintEnabled: Boolean) {
        this.isExpandedHintEnabled = isExpandedHintEnabled
    }

    fun loadStartIcon(url: String) {
        val startIcon =
            findViewById<ImageButton>(com.google.android.material.R.id.text_input_start_icon)
        setStartIconDrawable(R.drawable.empty_resource)
        startIcon.load(url)
    }

    private fun setErrorAnimation() {
        if (enableErrorAnimation && isErrorEnabled) {
            context.vibrate(VIBRATION_AMPLITUDE)
            shakeViewHorizontally(SHAKE_AMPLITUDE)
        }
    }

    companion object {
        const val SHAKE_AMPLITUDE = 500L
        const val VIBRATION_AMPLITUDE = 80L
    }
}
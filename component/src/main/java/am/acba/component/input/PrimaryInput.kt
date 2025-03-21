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
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
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

open class PrimaryInput : TextInputLayout {

    var enableErrorAnimation = false

    private var textMaxLength = -1
    private var cornerStyle = -1
    private var hasDropDown = false
    private var inputType = -1
    private var formattingWithDot = false
    private var validateAfterInput = false
    private var isKeyboardActionClicked = false

    private var onOtherActionButtonClick: ((Int) -> Unit)? = null
    private var onDoneButtonClick: (() -> Unit)? = null

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
                inputType = getInt(R.styleable.PrimaryInput_inputType, -1)
                formattingWithDot = getBoolean(R.styleable.PrimaryInput_formattingWithDots, false)
                enableErrorAnimation = getBoolean(R.styleable.PrimaryInput_enableErrorAnimation, false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val layoutStyle =
                if (cornerStyle == 1) R.layout.text_input_edittext_left_corner_layout else R.layout.text_input_edittext_layout
            addView(LayoutInflater.from(context).inflate(layoutStyle, this@PrimaryInput, false))
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
                0 -> setInputTypeForAmount()
                1 -> setInputTypeForNumber()
            }
//            editText?.hideSoftInput()
//            editText?.let { rootView.addKeyboardVisibilityListener(it) }
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

    fun setInputTypeForAmount() {
        editText?.inputType = InputType.TYPE_CLASS_NUMBER
        editText?.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            amountTextFormatting(hasFocus)
        }

    }

    fun setInputTypeForNumber() {
        editText?.inputType = InputType.TYPE_CLASS_NUMBER
    }

    fun setInputTypeForText() {
        editText?.inputType = InputType.TYPE_CLASS_TEXT
    }

    fun setAmountText(amount: String) {
        editText?.setText((if (formattingWithDot) amount.numberFormatting() else amount.numberFormattingWithOutDot()))
    }

    private fun amountTextFormatting(isFocusable: Boolean) {
        if (isFocusable) {
            editText?.setText(getDeFormatedStringAmount())
            setMaxLength(textMaxLength)
        } else {
            val text = editText?.text?.toString()?.trim() ?: ""
            val formattedText = if (!formattingWithDot) text.numberFormattingWithOutDot() else text.numberFormatting()
            setMaxLength(formattedText.length)
            editText?.setText(formattedText)
        }
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
                if (isKeyboardActionClicked) setErrorAnimation()
                error = errorMessage
            }
        }
    }

    fun validateAfterTextChange(errorMessage: String?, isValid: Boolean = true) {
        if (validateAfterInput) {
            if (editText?.text?.isEmpty() == true) {
                isErrorEnabled = false
                validateAfterInput = false
                return
            }
            isErrorEnabled = !isValid && editText?.text?.isNotEmpty() == true

            if (isErrorEnabled) error = errorMessage
        }
    }

    fun setMaxLength(maxLength: Int) {
        textMaxLength = maxLength
        val fArray = arrayOfNulls<InputFilter>(1)
        fArray[0] = LengthFilter(maxLength)
        editText?.filters = fArray
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
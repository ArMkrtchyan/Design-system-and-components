package am.acba.component.currencyInput

import am.acba.component.R
import am.acba.component.databinding.CurrencyInputBinding
import am.acba.component.dialog.CountryBottomSheetDialog
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.extensions.numberDeFormatting
import am.acba.component.extensions.numberFormatting
import am.acba.component.extensions.numberFormattingWithOutDot
import am.acba.component.extensions.shakeViewHorizontally
import am.acba.component.extensions.vibrate
import am.acba.component.input.PrimaryInput.Companion.SHAKE_AMPLITUDE
import am.acba.component.input.PrimaryInput.Companion.VIBRATION_AMPLITUDE
import am.acba.component.phoneNumberInput.CountryModel
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@SuppressLint("CustomViewStyleable")
class CurrencyInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, 0) {

    private val binding by lazy { CurrencyInputBinding.inflate(context.inflater(), this, false) }
    private var errorText: String
    private var hintText: String
    private var helpText: String
    private var maxLength: Int
    private var maxAmount: Double
    private var minAmount: Double
    private var isKeyboardActionClicked = false
    private var isValidAmount: Boolean = true
    private var formattingWithOutDot = false
    private var isFirstFocusable = true
    private var bottomSheetTitle = ""
    private var currency: String = ""
        get() = binding.currency.text.toString()

    private lateinit var currencyList: MutableList<CountryModel>

    var enableErrorAnimation = false

    init {
        addView(binding.root)
        context.obtainStyledAttributes(attrs, R.styleable.CurrencyInput).apply {
            try {
                errorText = getString(R.styleable.CurrencyInput_currencyInputErrorText) ?: ""
                hintText = getString(R.styleable.CurrencyInput_currencyInputHintText) ?: ""
                helpText = getString(R.styleable.CurrencyInput_currencyInputHelpText) ?: ""
                maxLength = getInt(R.styleable.CurrencyInput_currencyInputMaxLength, 13)
                maxAmount =
                    getFloat(R.styleable.CurrencyInput_currencyInputMaxAmount, 0f).toDouble()
                minAmount =
                    getFloat(R.styleable.CurrencyInput_currencyInputMinAmount, 0F).toDouble()
                formattingWithOutDot =
                    getBoolean(R.styleable.CurrencyInput_formattingWithOutDot, false)
                enableErrorAnimation =
                    getBoolean(R.styleable.CurrencyInput_enableErrorAnimation, false)
                bottomSheetTitle = getString(R.styleable.CurrencyInput_currencyInputBottomSheetTitle) ?: "Select currency"
            } finally {
                recycle()
            }

            binding.amount.editText?.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty()) {
                    isValidAmount = true
                    isFirstFocusable = true
                    setValidState()
                } else if (!isFirstFocusable) validateAmount()
            }

        }
        setMaxLength(maxLength)
        setHelpText(helpText)
        setErrorText(errorText)
        initKeyboardListeners()
        binding.currencyLayout.setOnClickListener { currencyIconClick() }
        setupFirstUi()
        setupCurrenciesList()
        setupBackgroundsByFocusChange()
    }

    private fun initKeyboardListeners() {
        binding.amount.apply {
            onKeyboardDoneButtonClick { setErrorAnimation() }
            onKeyboardOtherActionButtonClick { actionId ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    isKeyboardActionClicked = true
                }
            }
        }
    }

    fun setOnCurrencyClickListener(onClickListener: View.OnClickListener?) {
        binding.currencyLayout.setOnClickListener(onClickListener)
    }

    fun setMaxLength(maxLength: Int) {
        binding.amount.setMaxLengthForFormattedText(maxLength)
    }

    fun setImeOptions(imeOptions: Int) {
        binding.amount.editText?.imeOptions = imeOptions
    }

    fun loadCurrencyIcon(@DrawableRes resId: Int) {
        Glide.with(context).asBitmap()
            .load(resId)
            .apply(
                RequestOptions.circleCropTransform()
                    .override(22.dpToPx(), 22.dpToPx())
            )
            .into(binding.currencyFlag)
    }

    fun loadCurrencyIcon(drawable: Drawable) {
        Glide.with(context).asBitmap()
            .load(drawable)
            .apply(
                RequestOptions.circleCropTransform()
                    .override(22.dpToPx(), 22.dpToPx())
            )
            .into(binding.currencyFlag)
    }

    fun loadCurrencyIcon(iconUrl: String) {
        Glide.with(context)
            .load(iconUrl)
            .apply(
                RequestOptions.circleCropTransform()
            )
            .into(binding.currencyFlag)
    }

    fun setCurrencyText(title: String) {
        binding.currency.text = title
    }

    fun fixCurrency() {
        binding.icArrow.isVisible = false
        binding.currencyLayout.isClickable = false
    }

    private fun onKeyboardHidden() {
        binding.amount.editText?.clearFocus()
    }

    override fun setEnabled(isEnable: Boolean) {
        binding.amount.isEnabled = isEnable
        binding.currencyLayout.isEnabled = isEnable
        binding.currency.setTextColor(context.getColorFromAttr(if (isEnable) R.attr.contentPrimary else R.attr.contentPrimaryTonal1Disable))
        binding.icArrow.imageTintList =
            context.getColorStateListFromAttr(if (isEnable) R.attr.contentPrimary else R.attr.contentPrimaryTonal1Disable)
        binding.amount.defaultHintTextColor =
            context.getColorStateListFromAttr(if (isEnable) R.attr.contentPrimaryTonal1 else R.attr.contentPrimaryTonal1Disable)
        binding.amount.editText?.setTextColor(context.getColorStateListFromAttr(if (isEnable) R.attr.contentPrimaryTonal1 else R.attr.contentPrimaryTonal1Disable))
        binding.currencyFlag.alpha = if (isEnable) 1f else 0.4f
        binding.helpText.setTextColor(context.getColorStateListFromAttr(if (isEnable) R.attr.contentPrimaryTonal1 else R.attr.contentPrimaryTonal1Disable))
    }

    private fun setupFirstUi() {
        binding.helpText.isVisible = helpText.isNotEmpty()
        binding.helpText.text = helpText
        binding.helpText.setTextColor(context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1))
        binding.amount.hint = hintText
        binding.amount.hintTextColor = context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
        binding.amount.editText?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        if (formattingWithOutDot) {
            binding.amount.editText?.keyListener = DigitsKeyListener.getInstance("0123456789,")
        } else {
            binding.amount.editText?.keyListener = DigitsKeyListener.getInstance("0123456789,.")
        }
    }

    private fun setupBackgroundsByFocusChange() {
        binding.amount.onFocusChangeListener { isFocusable ->
            this.isFocusable = isFocusable
            if (isFocusable) {
                setupBackgroundByFocusable()
            } else {
                validateAmount()
            }
            mAction?.invoke(isFocusable)
        }
    }

    fun validateAmount() {
        val amount = getFloatAmount()
        val text = binding.amount.editText?.text ?: ""
        val isBelowMin = minAmount != 0.0 && amount < minAmount
        val isAboveMax = maxAmount != 0.0 && amount > maxAmount
        if (text.isEmpty()) isFirstFocusable = true
        isValidAmount = text.isEmpty() || !(isBelowMin || isAboveMax)

        if (isValidAmount) {
            setValidState()
        } else {
            setErrorState()
        }
        if (isKeyboardActionClicked) setErrorAnimation()
    }

    private fun setupCurrenciesList() {
        currencyList = arrayListOf()
        currencyList.add(
            CountryModel(
                currency = "AMD",
                name = "AMD",
                flagResId = R.drawable.ic_flag_am
            )
        )
        currencyList.add(
            CountryModel(
                currency = "USD",
                name = "USD",
                flagResId = R.drawable.ic_flag_usa
            )
        )
        currencyList.add(
            CountryModel(
                currency = "EUR",
                name = "EUR",
                flagResId = R.drawable.ic_flag_eur
            )
        )
        currencyList.add(
            CountryModel(
                currency = "RUB",
                name = "RUB",
                flagResId = R.drawable.ic_flag_rus
            )
        )
        currencyList.add(
            CountryModel(
                currency = "GBP",
                name = "GBP",
                flagResId = R.drawable.ic_flag_gb
            )
        )
        currencyList.add(
            CountryModel(
                currency = "CHF",
                name = "CHF",
                flagResId = R.drawable.ic_flag_sw
            )
        )
        currencyList.add(
            CountryModel(
                currency = "GEL",
                name = "GEL",
                flagResId = R.drawable.ic_flag_gel
            )
        )
    }

    private fun currencyIconClick() {
        val bundle = Bundle()
        bundle.putBoolean("needToSavActionsOnDB", false)
        bundle.putBoolean("isSearchInputVisible", false)
        bundle.putInt("bottomSheetType", 2)
        bundle.putString("title", bottomSheetTitle)
        bundle.putParcelableArrayList("CountriesList", currencyList as ArrayList)
        CountryBottomSheetDialog.show(getFragmentManager(), bundle, ::selectCurrency, arrayListOf())
    }

    private fun getFragmentManager(): FragmentManager {
        try {
            val fragmentActivity = context as? FragmentActivity
            fragmentActivity?.let {
                val fragmentManager = it.supportFragmentManager
                return fragmentManager
            }
        } catch (e: ClassCastException) {
            Log.d(TAG, "Can't get the fragment manager with this")
        }
        return getFragmentManager()
    }

    fun setValidState() {
        binding.helpText.setTextColor(context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1))
        binding.amount.hintTextColor =
            context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
        binding.amount.defaultHintTextColor =
            context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
        binding.helpText.text = helpText
        binding.icError.isVisible = false
        binding.helpText.isVisible = helpText.isNotEmpty()
        binding.amount.hintTextColor =
            context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
        binding.currencyLayout.background = ContextCompat.getDrawable(
            context, if (isFocusable) R.drawable.background_primary_input_right_border
            else R.drawable.background_primary_input_right_corner
        )
        binding.amount.editText?.background = ContextCompat.getDrawable(
            context, if (isFocusable) R.drawable.background_primary_input_left_border
            else R.drawable.background_primary_input_left_corner
        )
    }

    fun setErrorState() {
        isFirstFocusable = false
        binding.helpText.setTextColor(context.getColorStateListFromAttr(R.attr.contentDangerTonal1))
        binding.amount.hintTextColor = context.getColorStateListFromAttr(R.attr.contentDangerTonal1)
        binding.amount.defaultHintTextColor =
            context.getColorStateListFromAttr(R.attr.contentDangerTonal1)
        binding.helpText.isVisible = errorText.isNotEmpty()
        binding.helpText.text = errorText
        binding.icError.isVisible = true
        binding.amount.editText?.background = ContextCompat.getDrawable(
            context,
            R.drawable.background_primary_input_error_left_border
        )
        binding.currencyLayout.background = ContextCompat.getDrawable(
            context,
            R.drawable.background_primary_input_error_right_border
        )
    }

    private fun setupBackgroundByFocusable() {
        binding.currencyLayout.background = ContextCompat.getDrawable(
            context,
            if (isValidAmount) R.drawable.background_primary_input_right_border else R.drawable.background_primary_input_error_right_border
        )
        binding.amount.editText?.background = ContextCompat.getDrawable(
            context,
            if (isValidAmount) R.drawable.background_primary_input_left_border else R.drawable.background_primary_input_error_left_border
        )
    }


    @SuppressLint("SetTextI18n")
    private fun selectCurrency(countryModel: CountryModel) {
        binding.currency.text = countryModel.name
        currency = countryModel.name ?: ""
        Glide.with(context).asBitmap()
            .load(countryModel.flagResId)
            .apply(
                RequestOptions.circleCropTransform()
                    .override(22.dpToPx(), 22.dpToPx())
            )
            .into(binding.currencyFlag)
    }


    fun setAmountText(amount: String) {
        this.currency = currency
        val amountFormatting = if (formattingWithOutDot) amount.numberFormattingWithOutDot() else amount.numberFormatting()
        binding.amount.editText?.setText(amountFormatting)
        validateAmount()
    }

    fun getFloatAmount(): Float {
        val amountText = binding.amount.editText?.text?.toString()?.trim() ?: ""
        return if (amountText.isEmpty()) 0.0F else {
            if (amountText.numberDeFormatting().isEmpty()) 0.0F
            else amountText.numberDeFormatting().toFloat()
        }
    }

    fun getLongAmount(): Long {
        val amountText = binding.amount.editText?.text?.toString()?.trim() ?: ""
        return if (amountText.isEmpty()) 0 else amountText.toLong()
    }

    fun getDeFormatedStringAmount(): String {
        val amountText = binding.amount.editText?.text?.toString()?.trim() ?: ""
        return if (amountText.isEmpty()) amountText else amountText.numberDeFormatting()

    }

    fun onFocusChangeListener(action: ((Boolean) -> Unit)? = null) {
        mAction = action
    }

    private var mAction: ((Boolean) -> Unit?)? = null
    fun isValidAmount(): Boolean = isValidAmount
    fun getEditText(): EditText? = binding.amount.editText


    fun getFormatedStringAmount(): String {
        return binding.amount.editText?.text?.toString()?.trim() ?: ""
    }

    fun setHintText(hintText: String) {
        this.hintText = hintText
        binding.amount.hint = hintText
    }

    fun setMaxAmount(amount: Double) {
        maxAmount = amount
    }

    fun setMinAmount(amount: Double) {
        minAmount = amount
    }

    fun setErrorText(text: String) {
        errorText = text
        binding.helpText.isVisible = errorText.isNotEmpty()
        binding.helpText.text = errorText
    }

    fun setHelpText(text: String) {
        helpText = text
        binding.helpText.isVisible = helpText.isNotEmpty()
        binding.helpText.text = helpText
    }

    fun setCurrency(currency: String) {
        currencyList.forEach { if (it.currency == currency.uppercase()) selectCurrency(it) }
    }

    fun setCurrencyList(currencies: List<String>) {
        val currencySet = currencies.map { it.uppercase() }.toSet()
        val filteredCurrencyList = currencyList.filter { it.currency in currencySet }

        if (filteredCurrencyList.isNotEmpty()) {
            currencyList.clear()
            currencyList.addAll(filteredCurrencyList)

            binding.icArrow.isVisible = filteredCurrencyList.size > 1
            binding.currencyLayout.setOnClickListener {
                if (filteredCurrencyList.size > 1) currencyIconClick() else it.setOnClickListener(
                    null
                )
            }
            selectCurrency(filteredCurrencyList[0])
        }
    }

    private fun setErrorAnimation() {
        if (enableErrorAnimation && !isValidAmount) {
            isKeyboardActionClicked = false
            context.vibrate(VIBRATION_AMPLITUDE)
            shakeViewHorizontally(SHAKE_AMPLITUDE)
        }
    }
}
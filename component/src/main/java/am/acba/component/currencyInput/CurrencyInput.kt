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
import am.acba.component.phoneNumberInput.CountryModel
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
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
    private var maxAmount: Int
    private var minAmount: Int
    private var isValidAmount: Boolean = true
    private var formattingWithOutDot = false
    private var currency: String = ""
        get() = binding.currency.text.toString()

    private lateinit var currencyList: MutableList<CountryModel>

    init {
        addView(binding.root)
        context.obtainStyledAttributes(attrs, R.styleable.CurrencyInputInput).apply {
            try {
                errorText = getString(R.styleable.CurrencyInputInput_currencyInputErrorText) ?: ""
                hintText = getString(R.styleable.CurrencyInputInput_currencyInputHintText) ?: ""
                helpText = getString(R.styleable.CurrencyInputInput_currencyInputHelpText) ?: ""
                maxAmount = getInt(R.styleable.CurrencyInputInput_currencyInputMaxAmount, 0)
                minAmount = getInt(R.styleable.CurrencyInputInput_currencyInputMinAmount, 0)
                formattingWithOutDot = getBoolean(R.styleable.CurrencyInputInput_formattingWithOutDot, false)
            } finally {
                recycle()
            }
        }
        setHelpText(helpText)
        setErrorText(errorText)
        binding.currencyLayout.setOnClickListener { currencyIconClick() }
        setupFirstUi()
        setupCurrenciesList()
        setupBackgroundsByFocusChange()
        setupEditTextFocusActions()
    }

    private fun setupEditTextFocusActions() {
        binding.amount.editText?.setOnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(context, InputMethodManager::class.java)
                imm?.hideSoftInputFromWindow(textView.windowToken, 0)
                true
            } else {
                false
            }
        }
        binding.rootConstraint.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight < screenHeight * 0.15) { // Assuming the keyboard takes more than 15% of the screen height
                onKeyboardHidden()
            }
        }
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
        binding.amount.hint = hintText
        binding.amount.hintTextColor = context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
        binding.amount.editText?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

    private fun setupBackgroundsByFocusChange() {
        binding.amount.editText?.setOnFocusChangeListener { _, isFocusable ->
            this.isFocusable = isFocusable
            mAction?.invoke(isFocusable)
            amountTextFormatting(isFocusable)
            val amountText = binding.amount.editText?.text ?: ""
            if (isFocusable) {
                setupBackgroundByFocusable()
            } else if (amountText.isEmpty()) {
                isValidAmount = true
                setValidState()
            } else {
                validateAmount()
            }
        }
    }

    private fun amountTextFormatting(isFocusable: Boolean) {
        if (isFocusable) {
            binding.amount.editText?.setText(getDeFormatedStringAmount())
        } else {
            val text = binding.amount.editText?.text?.toString()?.trim() ?: ""
            binding.amount.editText?.setText(
                if (text.length >= 15) text else
                    (if (formattingWithOutDot) text.numberFormattingWithOutDot() else text.numberFormatting())
            )
        }
    }

    private fun validateAmount() {
        when {
            minAmount != 0 && getFloatAmount() < minAmount -> {
                setErrorState()
                isValidAmount = false
            }

            maxAmount != 0 && getFloatAmount() > maxAmount -> {
                setErrorState()
                isValidAmount = false
            }

            else -> {
                isValidAmount = true
                setValidState()
            }
        }
    }

    private fun setupCurrenciesList() {
        currencyList = arrayListOf()
        currencyList.add(CountryModel(currency = "AMD", name = "AMD", flagResId = R.drawable.ic_am_flag))
        currencyList.add(CountryModel(currency = "USD", name = "USD", flagResId = R.drawable.flag_usa))
        currencyList.add(CountryModel(currency = "EUR", name = "EUR", flagResId = R.drawable.eur_flag))
        currencyList.add(CountryModel(currency = "RUB", name = "RUB", flagResId = R.drawable.flag_russian))
        currencyList.add(CountryModel(currency = "GBP", name = "GBP", flagResId = R.drawable.gb_flag))
        currencyList.add(CountryModel(currency = "CHF", name = "CHF", flagResId = R.drawable.sw_flag))
        currencyList.add(CountryModel(currency = "GEL", name = "GEL", flagResId = R.drawable.gel_flag))
    }

    private fun currencyIconClick() {
        val bundle = Bundle()
        bundle.putBoolean("needToSavActionsOnDB", false)
        bundle.putBoolean("isSearchInputVisible", false)
        bundle.putString("bottomSheetTitle", "Currency")
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

    private fun setValidState() {
        binding.helpText.setTextColor(context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1))
        binding.amount.hintTextColor = context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
        binding.amount.defaultHintTextColor = context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
        binding.helpText.text = helpText
        binding.icError.isVisible = false
        binding.helpText.isVisible = helpText.isNotEmpty()
        binding.amount.hintTextColor = context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
        binding.currencyLayout.background = ContextCompat.getDrawable(
            context, if (isFocusable) R.drawable.background_primary_input_right_border
            else R.drawable.background_primary_input_right_corner
        )
        binding.amount.editText?.background = ContextCompat.getDrawable(
            context, if (isFocusable) R.drawable.background_primary_input_left_border
            else R.drawable.background_primary_input_left_corner
        )
    }

    private fun setErrorState() {
        binding.helpText.setTextColor(context.getColorStateListFromAttr(R.attr.contentDangerTonal1))
        binding.amount.hintTextColor = context.getColorStateListFromAttr(R.attr.contentDangerTonal1)
        binding.amount.defaultHintTextColor = context.getColorStateListFromAttr(R.attr.contentDangerTonal1)
        binding.helpText.isVisible = errorText.isNotEmpty()
        binding.helpText.text = errorText
        binding.icError.isVisible = true
        binding.amount.editText?.background = ContextCompat.getDrawable(context, R.drawable.background_primary_input_error_left_border)
        binding.currencyLayout.background = ContextCompat.getDrawable(context, R.drawable.background_primary_input_error_right_border)
    }

    private fun setupBackgroundByFocusable() {
        binding.currencyLayout.background = ContextCompat.getDrawable(
            context,
            if (isValidAmount) R.drawable.background_primary_input_right_border else R.drawable.background_primary_input_error_right_border
        )
        binding.amount.editText?.background = ContextCompat.getDrawable(
            context, if (isValidAmount) R.drawable.background_primary_input_left_border else R.drawable.background_primary_input_error_left_border
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
        binding.amount.editText?.setText((if (formattingWithOutDot) amount.numberFormattingWithOutDot() else amount.numberFormatting()))
        validateAmount()
    }

    fun getFloatAmount(): Float {
        val amountText = binding.amount.editText?.text?.toString()?.trim() ?: ""
        return if (amountText.isEmpty()) 0.0F else amountText.numberDeFormatting().toFloat()
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

    fun setMaxAmount(amount: Int) {
        maxAmount = amount
    }

    fun setMinAmount(amount: Int) {
        minAmount = amount
    }

    fun setErrorText(text: String) {
        errorText = text
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
        val currencyListByUsage: MutableList<CountryModel> = mutableListOf()
        currencyList.forEach { currency ->
            if (currencies.contains(currency.currency)) {
                currencyListByUsage.add(currency)
            }
        }
        if (currencyListByUsage.isNotEmpty()) {
            currencyList.clear()
            currencyList.addAll(currencyListByUsage)
            if (currencyListByUsage.size > 1) {
                binding.icArrow.visibility = VISIBLE
            } else {
                binding.icArrow.visibility = INVISIBLE
                binding.currencyLayout.setOnClickListener(null)
            }
        }
    }
}
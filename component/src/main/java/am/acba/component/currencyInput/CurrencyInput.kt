package am.acba.component.currencyInput

import am.acba.component.R
import am.acba.component.databinding.CurrencyInputBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.extensions.numberDeFormatting
import am.acba.component.extensions.numberFormatting
import am.acba.component.dialog.CountryBottomSheetDialog
import am.acba.component.phoneNumberInput.CountryModel
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
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
    private var isMoreThanMax = false
    private var isLessThanMin = false
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
            } finally {
                recycle()
            }
        }
        binding.currencyLayout.setOnClickListener { currencyIconClick() }
        setupFirstUi()
        setupCurrenciesList()
        setupBackgroundsByFocusChange()
    }

    private fun setupFirstUi() {
        binding.helpText.isVisible = helpText.isNotEmpty()
        binding.helpText.text = helpText
        binding.amount.hint = hintText
        binding.amount.hintTextColor = context.getColorStateListFromAttr(
            if (!isLessThanMin && !isMoreThanMax) R.attr.contentPrimaryTonal1
            else R.attr.contentDangerTonal1
        )
        binding.amount.editText?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

    private fun setupBackgroundsByFocusChange() {
        binding.amount.editText?.setOnFocusChangeListener { _, isFocusable ->
            this.isFocusable = isFocusable
            amountTextFormatting(isFocusable)
            val amountText = binding.amount.editText?.text ?: ""
            if (isFocusable) {
                setupBackgroundByFocusable()
            } else if (amountText.isEmpty()) {
                setValidState()
            } else {
                isMoreThanMax()
                isLessThanMin()
            }
        }
    }


    private fun amountTextFormatting(isFocusable: Boolean) {
        if (isFocusable) {
            binding.amount.editText?.setText(getDeFormatedStringAmount())
        } else {
            val text = binding.amount.editText?.text?.toString()?.trim() ?: ""
            binding.amount.editText?.setText(if (text.length >= 15) text else text.numberFormatting())
        }
    }

    private fun isMoreThanMax() {
        if (maxAmount != 0 && !isLessThanMin) {
            isMoreThanMax = getFloatAmount() > maxAmount
            if (isMoreThanMax) {
                setErrorState()
            } else {
                setValidState()
            }
        }

    }

    fun isValidAmount(): Boolean {
        return !isLessThanMin && !isMoreThanMax
    }

    private fun isLessThanMin() {
        if (minAmount != 0 && !isMoreThanMax) {
            isLessThanMin = getFloatAmount() < minAmount
            if (isLessThanMin) {
                setErrorState()
            } else {
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
        isLessThanMin = false
        isMoreThanMax = false
        binding.icError.isVisible = false
        binding.helpText.isVisible = helpText.isNotEmpty() == true
        binding.amount.hintTextColor = context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
        binding.currencyLayout.background = ContextCompat.getDrawable(
            context, if (isFocusable) R.drawable.background_primary_input_right_border else R.drawable.background_primary_input_right_corner
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
        binding.helpText.text = errorText
        binding.icError.isVisible = true
        binding.amount.editText?.background = ContextCompat.getDrawable(context, R.drawable.background_primary_input_error_left_border)
        binding.currencyLayout.background = ContextCompat.getDrawable(context, R.drawable.background_primary_input_error_right_border)
    }

    private fun setupBackgroundByFocusable() {
        binding.currencyLayout.background = ContextCompat.getDrawable(
            context, if (!isLessThanMin && !isMoreThanMax) R.drawable.background_primary_input_right_border
            else R.drawable.background_primary_input_error_right_border
        )
        binding.amount.editText?.background = ContextCompat.getDrawable(
            context, if (!isLessThanMin && !isMoreThanMax) R.drawable.background_primary_input_left_border
            else R.drawable.background_primary_input_error_left_border
        )
    }


    @SuppressLint("SetTextI18n")
    private fun selectCurrency(countryModel: CountryModel) {
        binding.currency.text = countryModel.name
        currency = countryModel.name ?: ""
        Glide.with(context).asBitmap().load(countryModel.flagResId).apply(RequestOptions.circleCropTransform().override(22.dpToPx(), 22.dpToPx()))
            .into(binding.currencyFlag)
    }

    fun setCurrency(currency: String) {
        currencyList.forEach { if (it.currency == currency) selectCurrency(it) }
    }

    fun setAmountText(amount: String) {
        this.currency = currency
        binding.amount.editText?.setText(amount.numberFormatting())
    }

    fun getFloatAmount(): Float {
        val amountText = binding.amount.editText?.text?.toString()?.trim() ?: ""
        return if (amountText.isEmpty()) 0.0F else amountText.numberDeFormatting().toFloat()
    }

    fun getDeFormatedStringAmount(): String {
        val amountText = binding.amount.editText?.text?.toString()?.trim() ?: ""
        return if (amountText.isEmpty()) amountText else amountText.numberDeFormatting()

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

    fun getFormatedStringAmount(): String {
        return binding.amount.editText?.text?.toString()?.trim() ?: ""
    }

    fun setHintText(hintText: String) {
        this.hintText = hintText
        binding.amount.hint = hintText
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
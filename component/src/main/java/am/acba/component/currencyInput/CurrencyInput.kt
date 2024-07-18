package am.acba.component.currencyInput

import am.acba.component.R
import am.acba.component.databinding.CurrencyInputBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.extensions.numberDeFormatting
import am.acba.component.extensions.numberFormatting
import am.acba.component.phoneNumberInput.CountryBottomSheetDialog
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
    private var errorText: String? = null
    private var helpText: String? = null
    private var isValidNumber = true
    private var currency: String = ""
        get() = binding.currency.text.toString()

    private lateinit var currencyList: MutableList<CountryModel>

    init {
        addView(binding.root)
        context.obtainStyledAttributes(attrs, R.styleable.CurrencyInputInput).apply {
            try {
                errorText = getString(R.styleable.CurrencyInputInput_currencyInputErrorText)
                helpText = getString(R.styleable.CurrencyInputInput_currencyInputHelpText)
            } finally {
                recycle()
            }
        }
        binding.amount.editText?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        binding.currencyLayout.setOnClickListener { currencyIconClick() }
        setupCurrenciesList()
        setupBackgrounds()
        setupHelpErrorText()
    }

     fun setErrorBackground() {
        binding.amount.isErrorEnabled = true
        binding.icError.isVisible = true
        binding.amount.hintTextColor = context.getColorStateListFromAttr(R.attr.contentDangerTonal1)
        binding.helpText.setTextColor(context.getColorStateListFromAttr(R.attr.contentDangerTonal1))
        binding.amount.editText?.background = ContextCompat.getDrawable(context, R.drawable.background_primary_input_error_left_border)
        binding.currencyLayout.background = ContextCompat.getDrawable(context, R.drawable.background_primary_input_error_right_border)
    }

    private fun setupBackgroundByFocusable() {
        binding.currencyLayout.background = ContextCompat.getDrawable(
            context, if (isFocusable) R.drawable.background_primary_input_right_border else R.drawable.background_primary_input_right_corner
        )
    }

    private fun setupBackgrounds() {
        binding.amount.editText?.setOnFocusChangeListener { _, isFocusable ->
            this.isFocusable = isFocusable
            amountTextFormatting(isFocusable)
            if (isValidNumber) {
                setupBackgroundByFocusable()
            } else {
                setErrorBackground()
            }
        }
    }


    private fun amountTextFormatting(isFocusable: Boolean) {
        if (isFocusable) {
            binding.amount.editText?.setText(getDeFormatedStringAmount())
        } else {
            val text = binding.amount.editText?.text?.toString()?.trim()
            binding.amount.editText?.setText(text?.numberFormatting())
        }
    }


    fun setCurrency(currency: String) {
        currencyList.forEach { if (it.currency == currency) selectCurrency(it) }
    }

    fun setAmountText(amount: String) {
        this.currency = currency
        binding.amount.editText?.setText(amount.numberFormatting())
    }

    fun getDoubleAmount(): Double {
        val amountText = binding.amount.editText?.text?.toString()?.trim() ?: ""
        return if (amountText.isEmpty()) 0.0 else amountText.numberDeFormatting().toDouble()
    }

    fun getDeFormatedStringAmount(): String {
        val amountText = binding.amount.editText?.text?.toString()?.trim() ?: ""
        return if (amountText.isEmpty()) amountText else amountText.numberDeFormatting()

    }

    fun getFormatedStringAmount(): String {
        return binding.amount.editText?.text?.toString()?.trim() ?: ""
    }

    private fun isValidNumber(): Boolean {
        return false
    }

    private fun setupCurrenciesList() {
        currencyList = arrayListOf()
        currencyList.add(CountryModel(currency = "AMD", flagResId = R.drawable.ic_am_flag))
        currencyList.add(CountryModel(currency = "USD", flagResId = R.drawable.flag_usa))
        currencyList.add(CountryModel(currency = "EUR", flagResId = R.drawable.eur_flag))
        currencyList.add(CountryModel(currency = "RUB", flagResId = R.drawable.flag_russian))
        currencyList.add(CountryModel(currency = "GBP", flagResId = R.drawable.gb_flag))
        currencyList.add(CountryModel(currency = "CHF", flagResId = R.drawable.sw_flag))
        currencyList.add(CountryModel(currency = "GEL", flagResId = R.drawable.gel_flag))
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

    private fun setupHelpErrorText() {
        if (isValidNumber) {
            binding.helpText.setTextColor(context.getColorFromAttr(R.attr.contentPrimaryTonal1))
            binding.helpText.text = helpText
            binding.icError.visibility = GONE
            binding.helpText.isVisible = helpText?.isNotEmpty() == true
        } else {
            binding.helpText.text = errorText
            binding.helpText.setTextColor(context.getColorFromAttr(R.attr.contentDangerTonal1))
            binding.icError.visibility = VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun selectCurrency(countryModel: CountryModel) {
        binding.currency.text = countryModel.name
        currency = countryModel.name ?: ""
        Glide.with(context).asBitmap().load(countryModel.flagResId).apply(RequestOptions.circleCropTransform().override(22.dpToPx(), 22.dpToPx()))
            .into(binding.currencyFlag)
    }

}
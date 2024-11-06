package am.acba.components

import am.acba.component.extensions.log
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentCurrencyInputBinding
import androidx.core.widget.doAfterTextChanged

class CurrencyInputFragment : BaseViewBindingFragment<FragmentCurrencyInputBinding>() {
    override val inflate: Inflater<FragmentCurrencyInputBinding>
        get() = FragmentCurrencyInputBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentCurrencyInputBinding.initView() {
        amount.setCurrencyList(arrayListOf("AMD", "USD", "EUR"))
        amount.onFocusChangeListener { fosus-> }
        amount.setHelpText("Amount")
        amount.setErrorText("amount is not valid amount is not valid amount is not valid")
        amount.setHintText("Fill the amount")
        amount.setMaxAmount(999999999999999.0)
        amount.setMinAmount(100.0)
        amount.fixCurrency()
        search.setOnClickListener {
            amount.getDeFormatedStringAmount().log("amountCurrency")
            amount.getFormatedStringAmount().log("amountCurrency")
            amount.getFloatAmount().log("amountCurrency")
            amount.setCurrency("USD")
            amount.setAmountText("5555")
            amount.setHintText("5555")
        }
        val duration = mBinding.duration
        duration.setMaxLength(2)
        duration.setInputTypeForNumber()
        duration.editText?.maxLines = 1
        duration.editText?.doAfterTextChanged {

        }
        duration.editText?.setOnFocusChangeListener { _, hasFocus ->

        }
    }
}
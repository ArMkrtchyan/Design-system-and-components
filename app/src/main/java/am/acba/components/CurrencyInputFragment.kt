package am.acba.components

import am.acba.component.extensions.log
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentCurrencyInputBinding

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
        amount.setMaxAmount(10000)
        amount.setMinAmount(100)
        search.setOnClickListener {
            amount.getDeFormatedStringAmount().log("amountCurrency")
            amount.getFormatedStringAmount().log("amountCurrency")
            amount.getFloatAmount().log("amountCurrency")
            amount.setCurrency("USD")
            amount.setAmountText("5555")
            amount.setHintText("5555")
        }
    }
}
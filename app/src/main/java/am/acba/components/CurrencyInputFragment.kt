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
        amount.enableErrorAnimation = true
        amount.onFocusChangeListener { fosus -> }
        amount.setHelpText("Amount")
        amount.setErrorText("amount is not valid amount is not valid amount is not valid")
        amount.setHintText("Fill the amount")
        amount.setMaxAmount(999999999999999.0)
        amount.setMinAmount(100.0)
        amount.fixCurrency()
        amount.loadCurrencyIcon("https://online1-test.acba.am/Shared/Currencies/EUR.svg")
        //  amount.loadCurrencyIcon("https://online1-test.acba.am/Shared/Banner/Amex%20cashback/Mobile/hy/amexcashback_06.2024.png")
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
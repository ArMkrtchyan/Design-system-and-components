package am.acba.components

import am.acba.component.extensions.log
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.databinding.FragmentCurrencyInputBinding
import am.acba.components.base.Inflater
import androidx.appcompat.widget.Toolbar

class CurrencyInputFragment : BaseViewBindingFragment<FragmentCurrencyInputBinding>() {
    override val inflate: Inflater<FragmentCurrencyInputBinding>
        get() = FragmentCurrencyInputBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentCurrencyInputBinding.initView() {
        search.setOnClickListener {
            amount.getDeFormatedStringAmount().log("amountCurrency")
            amount.getFormatedStringAmount().log("amountCurrency")
            amount.getFloatAmount().log("amountCurrency")
            amount.setCurrency("USD")
            amount.setAmountText("5555")
            amount.isValidAmount()
        }
    }
}
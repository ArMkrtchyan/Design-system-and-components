package am.acba.components

import am.acba.component.R
import am.acba.component.exchange.ExchangeRate
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentExchangeBinding
import androidx.appcompat.widget.Toolbar

class ExchangeFragment : BaseViewBindingFragment<FragmentExchangeBinding>() {
    override val inflate: Inflater<FragmentExchangeBinding>
        get() = FragmentExchangeBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentExchangeBinding.initView() {
        exchangeRates.apply {
            val firstRate = ExchangeRate(R.drawable.flag_usa, "$ 406.00", "$ 410.50")
            val secondRate = ExchangeRate(R.drawable.flag_russian, "₽ 4.30", "₽ 4.72")
            val thirdRate = ExchangeRate(R.drawable.flag_usa, "€ 435.50", "€ 452.00")
            val rates = Triple(firstRate, secondRate, thirdRate)
            setExchangeRates(rates)
        }
    }
}
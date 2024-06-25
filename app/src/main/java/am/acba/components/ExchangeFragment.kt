package am.acba.components

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

    }

}
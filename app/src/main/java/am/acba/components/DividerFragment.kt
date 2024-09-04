package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentDividerBinding

class DividerFragment : BaseViewBindingFragment<FragmentDividerBinding>() {
    override val inflate: Inflater<FragmentDividerBinding>
        get() = FragmentDividerBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentDividerBinding.initView() {

    }

}
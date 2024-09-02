package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentButtonsBinding

class ButtonsFragment : BaseViewBindingFragment<FragmentButtonsBinding>() {
    override val inflate: Inflater<FragmentButtonsBinding>
        get() = FragmentButtonsBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentButtonsBinding.initView() {

    }

}
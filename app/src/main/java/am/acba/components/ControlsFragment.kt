package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentControlsBinding

class ControlsFragment : BaseViewBindingFragment<FragmentControlsBinding>() {
    override val inflate: Inflater<FragmentControlsBinding>
        get() = FragmentControlsBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentControlsBinding.initView() {

    }

}
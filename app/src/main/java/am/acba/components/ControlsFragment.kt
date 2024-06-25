package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentControlsBinding
import androidx.appcompat.widget.Toolbar

class ControlsFragment : BaseViewBindingFragment<FragmentControlsBinding>() {
    override val inflate: Inflater<FragmentControlsBinding>
        get() = FragmentControlsBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentControlsBinding.initView() {

    }

}
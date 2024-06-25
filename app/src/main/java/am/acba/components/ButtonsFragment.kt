package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentButtonsBinding
import androidx.appcompat.widget.Toolbar

class ButtonsFragment : BaseViewBindingFragment<FragmentButtonsBinding>() {
    override val inflate: Inflater<FragmentButtonsBinding>
        get() = FragmentButtonsBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentButtonsBinding.initView() {

    }

}
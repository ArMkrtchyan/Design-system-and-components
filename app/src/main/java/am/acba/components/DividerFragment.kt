package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentDividerBinding
import androidx.appcompat.widget.Toolbar

class DividerFragment : BaseViewBindingFragment<FragmentDividerBinding>() {
    override val inflate: Inflater<FragmentDividerBinding>
        get() = FragmentDividerBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentDividerBinding.initView() {

    }

}
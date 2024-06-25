package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentChipsBinding
import androidx.appcompat.widget.Toolbar

class ChipsFragment : BaseViewBindingFragment<FragmentChipsBinding>() {
    override val inflate: Inflater<FragmentChipsBinding>
        get() = FragmentChipsBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentChipsBinding.initView() {

    }

}
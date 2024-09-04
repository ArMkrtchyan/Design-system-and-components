package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentBannersBinding

class BannersFragment : BaseViewBindingFragment<FragmentBannersBinding>() {
    override val inflate: Inflater<FragmentBannersBinding>
        get() = FragmentBannersBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentBannersBinding.initView() {

    }

}
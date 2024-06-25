package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentBannersBinding
import androidx.appcompat.widget.Toolbar

class BannersFragment : BaseViewBindingFragment<FragmentBannersBinding>() {
    override val inflate: Inflater<FragmentBannersBinding>
        get() = FragmentBannersBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentBannersBinding.initView() {

    }

}
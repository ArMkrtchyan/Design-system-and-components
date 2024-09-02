package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentBadgesBinding

class BadgesFragment : BaseViewBindingFragment<FragmentBadgesBinding>() {
    override val inflate: Inflater<FragmentBadgesBinding>
        get() = FragmentBadgesBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentBadgesBinding.initView() {

    }

}
package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentBadgesBinding
import androidx.appcompat.widget.Toolbar

class BadgesFragment : BaseViewBindingFragment<FragmentBadgesBinding>() {
    override val inflate: Inflater<FragmentBadgesBinding>
        get() = FragmentBadgesBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentBadgesBinding.initView() {

    }

}
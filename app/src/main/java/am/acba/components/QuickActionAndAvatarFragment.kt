package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentQuickActionAndAvatarBinding
import androidx.appcompat.widget.Toolbar

class QuickActionAndAvatarFragment : BaseViewBindingFragment<FragmentQuickActionAndAvatarBinding>() {
    override val inflate: Inflater<FragmentQuickActionAndAvatarBinding>
        get() = FragmentQuickActionAndAvatarBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentQuickActionAndAvatarBinding.initView() {

    }

}
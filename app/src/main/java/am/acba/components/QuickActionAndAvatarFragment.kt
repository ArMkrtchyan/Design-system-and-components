package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentQuickActionAndAvatarBinding
import androidx.core.content.ContextCompat

class QuickActionAndAvatarFragment : BaseViewBindingFragment<FragmentQuickActionAndAvatarBinding>() {
    override val inflate: Inflater<FragmentQuickActionAndAvatarBinding>
        get() = FragmentQuickActionAndAvatarBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentQuickActionAndAvatarBinding.initView() {
        mBinding.quickAction.setIconBackground(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.ic_success_filled))
    }

}
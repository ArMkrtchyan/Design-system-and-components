package am.acba.components

import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentQuickActionAndAvatarBinding
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat

class QuickActionAndAvatarFragment : BaseViewBindingFragment<FragmentQuickActionAndAvatarBinding>() {
    override val inflate: Inflater<FragmentQuickActionAndAvatarBinding>
        get() = FragmentQuickActionAndAvatarBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentQuickActionAndAvatarBinding.initView() {
        mBinding.quickAction.setAnimation("check_test.json", am.acba.component.R.attr.contentWarning)
        mBinding.quickAction.setActionBadgeImage(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.ic_success_filled))
        mBinding.quickAction.setOnActionBadgeClickListener {
            Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
        }
        mBinding.quickAction.setActionBadgeBackground(am.acba.component.R.drawable.background_rounded)
        mBinding.quickAction.setActionBadgeImageTint(context?.getColorStateListFromAttr(am.acba.component.R.attr.contentBrand))
        mBinding.quickAction.setActionBadgeImage(getDrawable(requireContext(), am.acba.component.R.drawable.ic_right))
        mBinding.second.setAvatarCheckedStatus(true)
        mBinding.third.setAvatarCheckedStatus(true)
    }
}
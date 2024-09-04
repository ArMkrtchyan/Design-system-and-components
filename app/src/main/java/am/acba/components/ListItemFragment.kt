package am.acba.components

import am.acba.component.badge.PrimaryBadge
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentListItemBinding

class ListItemFragment : BaseViewBindingFragment<FragmentListItemBinding>() {
    override val inflate: Inflater<FragmentListItemBinding>
        get() = FragmentListItemBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentListItemBinding.initView() {
        listItem.showBadge()


        listItem.badge.setBadgeType(PrimaryBadge.BadgeType.TEXT)
        listItem.badge.setBadgeText("Առաջարկ")
        listItem.badge.setBadgeTextColor(requireContext().getColorFromAttr(am.acba.component.R.attr.contentWarning))
        listItem.badge.setBadgeBackgroundTint(requireContext().getColorStateListFromAttr(am.acba.component.R.attr.backgroundWarning))
    }

}
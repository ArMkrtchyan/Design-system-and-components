package am.acba.components

import am.acba.component.badge.PrimaryBadge
import am.acba.component.button.PrimaryActionTextButton
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.listItem.ListItem2
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentListItem2Binding
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isVisible

class ListItem2Fragment : BaseViewBindingFragment<FragmentListItem2Binding>() {
    override val inflate: Inflater<FragmentListItem2Binding>
        get() = FragmentListItem2Binding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentListItem2Binding.initView() {
        listItem1.apply {
            startComponentType = ListItem2.ListStartComponentType.ICON
            startIconDimensionsType = ListItem2.ListIconDimensionType.WITH_BACKGROUND
            setStartIconDrawable(
                getDrawable(requireContext(), am.acba.component.R.drawable.ic_info)
            )
            setStartIconBackgroundTint(context.getColorStateListFromAttr(am.acba.component.R.attr.backgroundPendingTonal1))
            endComponentType = ListItem2.ListEndComponentType.ICON
            firstEndIconDimensionsType = ListItem2.ListIconDimensionType.WITHOUT_BACKGROUND
            secondEndIconDimensionsType = ListItem2.ListIconDimensionType.WITHOUT_BACKGROUND
            setSecondEndComponentIconDrawable(
                getDrawable(requireContext(), am.acba.component.R.drawable.ic_info)
            )
            getBadgeStartIcon().setBadgeIcon()
        }

        listItem2.apply {
            startComponentType = ListItem2.ListStartComponentType.NONE
            endComponentType = ListItem2.ListEndComponentType.ICON
            secondEndIconDimensionsType = ListItem2.ListIconDimensionType.WITH_BACKGROUND
            setSecondEndComponentIconBackgroundTint(context.getColorStateListFromAttr(am.acba.component.R.attr.backgroundPendingTonal1))
            setSecondEndComponentIconDrawable(
                getDrawable(requireContext(), am.acba.component.R.drawable.ic_info)
            )
        }

        listItem3.apply {
            startIconDimensionsType = ListItem2.ListIconDimensionType.WITHOUT_BACKGROUND
            getBadgeStartIcon().setBadgeIcon()
            getBadgeStartIcon().setStrokeColor(am.acba.component.R.color.BrandGreen_650)
        }

        listItem4.apply {
            setListItemBackgroundColor(context.getColorFromAttr(am.acba.component.R.attr.backgroundTonal1))
        }

        listItem5.apply {
            setSwitchChecked(true)
        }

        listItem6.apply {
            setRadioButtonChecked(true)
        }

        listItem7.apply {
            setCheckboxChecked(true)
            setBody1Text("Supporting line text lorem sdhkjhssdsncjnscnjsdjcbjhbsdfhvbhkdfvhdhbvhd")
        }

        listItem8.apply {
            setSwitchChecked(true)
            getBadgeListItem().setBadgeNew()
            setBody1Text("Supporting line text lorem sdhkjhssdsncjnscnjsdjcbjhbsdfhvbhkdfvhdhbvhd")
        }

        listItem9.apply {
            setRadioButtonChecked(false)
            setBody1Text("Supporting line text lorem sdhkjhssdsncjnscnjsdjcbjhbsdfhvbhkdfvhdhbvhd")
            getBadgeListItem().setBadgeNew()
        }

        listItem11.apply {
            getBadgeListItem().setBadgeNew()
            getBadgeStartIcon().setBadgeIcon()
        }

        listItem15.apply {
            setFirstEndIconDrawable(
                getDrawable(context, am.acba.component.R.drawable.ic_info)
            )
            firstEndIconDimensionsType = ListItem2.ListIconDimensionType.WITH_BACKGROUND
            setFirstEndIconBackgroundTint(context.getColorStateListFromAttr(am.acba.component.R.attr.backgroundPendingTonal1))
            endComponentType = ListItem2.ListEndComponentType.ICON
            setSecondEndComponentIconDrawable(
                getDrawable(context, am.acba.component.R.drawable.ic_info)
            )
        }

        listItem16.apply {
            setListItemBackgroundColor(context.getColorFromAttr(am.acba.component.R.attr.backgroundTonal1))
            avatar.apply {
                setType(PrimaryActionTextButton.ActionButtonType.AVATAR)
                setIcon(am.acba.component.R.drawable.default_avatar)
                setBadgeSize(PrimaryActionTextButton.ActionIconSize.LARGE)
                setBadgeIconVisibility(true, PrimaryActionTextButton.ActionIconSize.XSMALL)
                setActionBadgeImage(
                    getDrawable(requireContext(), am.acba.component.R.drawable.ic_camera)
                )
                setBadgeChecked(true)
                setOnActionBadgeClickListener {
                    Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }

        listItem17.apply {
            setListItemBackgroundColor(context.getColorFromAttr(am.acba.component.R.attr.backgroundTonal1))
            startComponentType = ListItem2.ListStartComponentType.AVATAR
            avatar.apply {
                setType(PrimaryActionTextButton.ActionButtonType.AVATAR)
                setIcon(am.acba.component.R.drawable.default_avatar)
                setOnActionBadgeClickListener {
                    Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
                }
            }
            setDividerVisibility(true)
        }

        listItem18.setDividerText("Divider")
    }

    private fun PrimaryBadge.setBadgeNew() {
        setBadgeType(PrimaryBadge.BadgeType.TEXT)
        setBadgeText("New")
        setBadgeBackgroundTint(context.getColorStateListFromAttr(am.acba.component.R.attr.backgroundWarning))
        isVisible = true
    }

    private fun PrimaryBadge.setBadgeIcon() {
        setBadgeType(PrimaryBadge.BadgeType.DOT)
        setBadgeBackgroundTint(context.getColorStateListFromAttr(am.acba.component.R.attr.backgroundBrand))
        isVisible = true
    }
}
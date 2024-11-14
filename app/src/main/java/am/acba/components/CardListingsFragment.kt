package am.acba.components

import am.acba.component.R
import am.acba.component.cardListing.PrimaryCardListing
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentCardListingsBinding
import android.content.res.ColorStateList
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable

class CardListingsFragment : BaseViewBindingFragment<FragmentCardListingsBinding>() {
    override val inflate: Inflater<FragmentCardListingsBinding>
        get() = FragmentCardListingsBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentCardListingsBinding.initView() {
        cardListing.apply {
            setLayoutBackgroundColor(context.getColorStateListFromAttr(R.attr.backgroundBase))
            setLayoutBorderColor(context.getColorFromAttr(am.acba.component.R.attr.borderNeutral))
            setStartTitleText("MasterCard")
            setStartBodyText("****5464")
            setEndTitleText("հասանելի")
            setEndBodyText("200000")
            setCardCurrencyText("AMD")

            setStartIconType(PrimaryCardListing.IconTypes.LARGE)

            setStartIconBackgroundTint(requireContext().getColorStateListFromAttr(R.attr.backgroundAlternative))
            setStartIconTint(requireContext().getColorStateListFromAttr(R.attr.contentAlternative))
            setStartIcon("https://bcw-media.s3.ap-northeast-1.amazonaws.com/text_to_image_topbanner_mb_1_f66b5f345b.jpg")
            setEndIcon(getDrawable(context, am.acba.component.R.drawable.ic_right))

            showStatus(true)
            setStatusBackgroundColor(context.getColor(am.acba.component.R.color.BlueGrey_100))
            setStatusIcon(getDrawable(context, am.acba.component.R.drawable.ic_info))
            setStatusIconTint(
                ColorStateList.valueOf(context.getColor(am.acba.component.R.color.AztecPurple_700))
            )
            setStatusText("Չբլոկավորված")
            setStatusTextColor(context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1))

            setOnLinkClickListener { Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show() }
        }
    }
}
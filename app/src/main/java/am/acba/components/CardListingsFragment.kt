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
            setStartIcon(getDrawable(context, am.acba.component.R.drawable.ic_info))

            setStartIconBackgroundColor(
                ColorStateList.valueOf(context.getColor(am.acba.component.R.color.BlueGrey_100))
            )
            setStartIconTint(
                ColorStateList.valueOf(context.getColor(am.acba.component.R.color.BlueGrey_950_60))
            )
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
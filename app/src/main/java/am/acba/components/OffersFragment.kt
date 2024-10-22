package am.acba.components

import am.acba.component.R
import am.acba.component.extensions.log
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.databinding.FragmentOffersBinding
import am.acba.components.models.OfferCard
import android.view.LayoutInflater
import android.view.ViewGroup

class OffersFragment : BaseViewBindingFragment<FragmentOffersBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOffersBinding
        get() = FragmentOffersBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentOffersBinding.initView() {
        val list = arrayListOf(
            OfferCard(
                offerCardTitle = "Գյուղ. վարկ",
                offerCardAmount = "200,000.00",
                offerCardCurrency = "AMD",
                offerCardEndDate = "Վերջնաժամկետ - 12/09/2024",
                offerCardBackgroundColorAttr = R.attr.backgroundAlternative6,
                offerCardBadgeBackgroundColorAttr = R.attr.backgroundSuccess,
                offerCardBadgeText = "Նոր"
            )
        )
        loanOffers.submitLoanOffers(list)
        loanOffers.setNewBadgeCount(list.count { it.newBadgeVisibility })
        loanOffers.setOnOfferClick { it.log() }
        loanOffers.setOnSeeAllOfferClick { "See all".log() }
    }
}

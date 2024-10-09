package am.acba.components

import am.acba.component.extensions.log
import am.acba.component.loanComponents.OfferCard
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.databinding.FragmentOffersBinding
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
                newBadgeVisibility = true,
                offerCardBackgroundColorAttr = am.acba.component.R.attr.backgroundAlternative6,
                offerCardBadgeBackgroundColorAttr = am.acba.component.R.attr.backgroundSuccess,
                offerCardBadgeText = "Նոր"
            ),
            OfferCard(
                offerCardTitle = "5G վարկ",
                offerCardAmount = "5,000.00",
                offerCardCurrency = "USD",
                offerCardEndDate = "Վերջնաժամկետ - 20/01/2025",
                newBadgeVisibility = true,
                offerCardBackgroundColorAttr = am.acba.component.R.attr.backgroundAlternative3,
                offerCardBadgeBackgroundColorAttr = am.acba.component.R.attr.backgroundSuccess,
                offerCardBadgeText = "Նոր"
            ),
            OfferCard(
                offerCardTitle = "5G վարկ",
                offerCardAmount = "3,000.00",
                offerCardCurrency = "EUR",
                offerCardEndDate = "Վերջնաժամկետ - 15/12/2024",
                newBadgeVisibility = false,
                offerCardBackgroundColorAttr = am.acba.component.R.attr.backgroundAlternative4
            ),
        )
        loanOffers.submitLoanOffers(list)
        loanOffers.setNewBadgeCount(list.count { it.newBadgeVisibility })
        loanOffers.setOnOfferClick { it.log() }
        loanOffers.setOnSeeAllOfferClick { "See all".log() }
    }
}

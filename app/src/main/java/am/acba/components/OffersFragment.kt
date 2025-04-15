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
    private var isOneItem = true
    private val items = arrayListOf(
        OfferCard(
            offerCardTitle = "Գյուղ. վարկ",
            offerCardAmount = "200,000.00",
            offerCardCurrency = "AMD",
            offerCardEndDate = "Վերջնաժամկետ - 12/09/2024",
            offerCardBackgroundColorAttr = R.attr.backgroundAlternative6,
        )
    )

    private val items2 = arrayListOf(
        OfferCard(
            offerCardId = 1,
            offerCardTitle = "Վարկ 1",
            offerCardAmount = "200,000.00",
            offerCardCurrency = "AMD",
            offerCardEndDate = "Վերջնաժամկետ - 12/09/2024",
            offerCardBackgroundColorAttr = R.attr.backgroundAlternative6,
        ),
        OfferCard(
            offerCardId = 2,
            offerCardTitle = "Վարկ 2",
            offerCardAmount = "400,000.00",
            offerCardCurrency = "AMD",
            offerCardEndDate = "Վերջնաժամկետ - 12/09/2024",
            offerCardBackgroundColorAttr = R.attr.backgroundAlternative6,
        ),
        OfferCard(
            offerCardId = 3,
            offerCardTitle = "Վարկ 3",
            offerCardAmount = "400,000.00",
            offerCardCurrency = "AMD",
            offerCardEndDate = "Վերջնաժամկետ - 12/09/2024",
            offerCardBackgroundColorAttr = R.attr.backgroundAlternative6,
        )
    )
    private val items3 = arrayListOf(
        OfferCard(
            offerCardId = 1,
            offerCardTitle = "Վարկ 1",
            offerCardAmount = "200,000.00",
            offerCardCurrency = "AMD",
            offerCardEndDate = "Վերջնաժամկետ - 12/09/2024",
            offerCardBackgroundColorAttr = R.attr.backgroundAlternative6,
        ),
        OfferCard(
            offerCardId = 2,
            offerCardTitle = "Վարկ 2",
            offerCardAmount = "400,000.00",
            offerCardCurrency = "AMD",
            offerCardEndDate = "Վերջնաժամկետ - 12/09/2024",
            offerCardBackgroundColorAttr = R.attr.backgroundAlternative6,
        ),
    )

    override fun FragmentOffersBinding.initView() {

        loanOffers.submitLoanOffers(items2)
        loanOffers.setOnOfferClick { it.log() }
        loanOffers.setOnSeeAllOfferClick { "See all".log() }
        refresh.setOnClickListener {
            if (isOneItem) {
                loanOffers.submitLoanOffers(items3)
            } else {
                loanOffers.submitLoanOffers(items2)
            }
            isOneItem = !isOneItem
        }
    }
}

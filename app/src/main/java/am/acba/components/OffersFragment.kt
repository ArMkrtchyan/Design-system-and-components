package am.acba.components

import am.acba.component.loanComponents.LoanOfferCard
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
        loanOffers.submitLoanOffers(
            arrayListOf(
                LoanOfferCard(
                    title = "Գյուղ. վարկ",
                    amount = "200,000.00",
                    currency = "AMD",
                    endDate = "Վերջնաժամկետ - 12/09/2024",
                    isNewBadgeVisible = true,
                    backgroundColorAttr = am.acba.component.R.attr.backgroundAlternative6
                ),
                LoanOfferCard(
                    title = "5G վարկ",
                    amount = "5,000.00",
                    currency = "USD",
                    endDate = "Վերջնաժամկետ - 20/01/2025",
                    isNewBadgeVisible = true,
                    backgroundColorAttr = am.acba.component.R.attr.backgroundAlternative3
                ),
                LoanOfferCard(
                    title = "5G վարկ",
                    amount = "3,000.00",
                    currency = "EUR",
                    endDate = "Վերջնաժամկետ - 15/12/2024",
                    isNewBadgeVisible = false,
                    backgroundColorAttr = am.acba.component.R.attr.backgroundAlternative4
                ),
            )
        )
    }
}

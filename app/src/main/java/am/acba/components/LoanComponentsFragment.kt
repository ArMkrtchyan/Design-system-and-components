package am.acba.components

import am.acba.component.loanComponents.LoanCard
import am.acba.component.loanComponents.LoanCardAdditionalInfo
import am.acba.component.loanComponents.LoanOfferCard
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.databinding.FragmentLoanComponentsBinding
import android.view.LayoutInflater
import android.view.ViewGroup

class LoanComponentsFragment : BaseViewBindingFragment<FragmentLoanComponentsBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoanComponentsBinding
        get() = FragmentLoanComponentsBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentLoanComponentsBinding.initView() {
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
        loanCard.setLoanCard(
            LoanCard(
                title = "5G վարկ",
                description = "Վերջ - 12/սեպ/2024",
                nextPaymentDay = "14/09/2024",
                nextPaymentAmount = "36,000.00 AMD",
                loanAdditionalInfo = arrayListOf(
                    LoanCardAdditionalInfo(
                        title = "Սկզբնական գումար",
                        info = "1,500,000.00 AMD",
                    ),
                    LoanCardAdditionalInfo(
                        title = "Ընթացիկ պարտք",
                        info = "1,125,432.00 AMD",
                    ),
                ),
                startIcon = am.acba.component.R.drawable.ic_add
            )
        )
    }

}

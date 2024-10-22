package am.acba.components

import am.acba.component.R
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.databinding.FragmentCardViewBinding
import am.acba.components.models.CardAdditionalInfo
import am.acba.components.models.CardInfoExample
import android.view.LayoutInflater
import android.view.ViewGroup

class CardViewFragment : BaseViewBindingFragment<FragmentCardViewBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardViewBinding
        get() = FragmentCardViewBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentCardViewBinding.initView() {
        loanCardFull.setCardInfo(
            CardInfoExample(
                productTitle = "5G վարկ",
                productDescription = "Վերջ - 12/սեպ/2024",
                productNextPaymentDay = "14/09/2024",
                productNextPaymentAmount = "36,000.00 AMD",
                productNextPaymentDayTitle = "Վճարման օր",
                productNextPaymentAmountTitle = "Վճարման ենթակա գումար",
                productCardAdditionalInfo = arrayListOf(
                    CardAdditionalInfo(
                        additionalInfoTitle = "Սկզբնական գումար",
                        additionalInfo = "1,500,000.00 AMD",
                    ),
                    CardAdditionalInfo(
                        additionalInfoTitle = "Ընթացիկ պարտք",
                        additionalInfo = "1,125,432.00 AMD",
                    ),
                ),
                productStartIcon = R.drawable.ic_add,
                productStartIconTint = R.attr.contentPrimary,
                productBackgroundColorAttr = R.attr.backgroundTonal2,
                productBadgeIcon = R.drawable.ic_warning,
                productBadgeText = "Առկա է ժամկետնանց պարտավորություն",
                productBadgeBackgroundColorAttr = R.attr.backgroundDangerTonal1,
                productBadgeColorAndIconColorAttr = R.attr.contentDangerTonal1,
            )
        )
        loanCardWithoutIcon.setCardInfo(
            CardInfoExample(
                productTitle = "5G վարկ",
                productDescription = "Վերջ - 12/սեպ/2024",
                productNextPaymentDay = "14/09/2024",
                productNextPaymentAmount = "36,000.00 AMD",
                productNextPaymentDayTitle = "Վճարման օր",
                productNextPaymentAmountTitle = "Վճարման ենթակա գումար",
                productCardAdditionalInfo = arrayListOf(
                    CardAdditionalInfo(
                        additionalInfoTitle = "Սկզբնական գումար",
                        additionalInfo = "1,500,000.00 AMD",
                    ),
                    CardAdditionalInfo(
                        additionalInfoTitle = "Ընթացիկ պարտք",
                        additionalInfo = "1,125,432.00 AMD",
                    ),
                ),
                productBackgroundColorAttr = R.attr.backgroundTonal2
            )
        )
        loanCardWithoutRows.setCardInfo(
            CardInfoExample(
                productTitle = "Ավանդի գրավով վարկ",
                productDescription = "Վերջ - 12/սեպ/2024",
                productNextPaymentDay = "14/09/2024",
                productNextPaymentAmount = "36,000.00 AMD",
                productNextPaymentDayTitle = "Վճարման օր",
                productNextPaymentAmountTitle = "Վճարման ենթակա գումար",
                productStartIcon = R.drawable.ic_add,
                productStartIconTint = R.attr.contentPrimary,
                productBackgroundColorAttr = R.attr.backgroundTonal2
            )
        )
        loanCardWithoutSubTitle.setCardInfo(
            CardInfoExample(
                productTitle = "5G վարկ",
                productNextPaymentDay = "14/09/2024",
                productNextPaymentAmount = "36,000.00 AMD",
                productNextPaymentDayTitle = "Վճարման օր",
                productNextPaymentAmountTitle = "Վճարման ենթակա գումար",
                productCardAdditionalInfo = arrayListOf(
                    CardAdditionalInfo(
                        additionalInfoTitle = "Սկզբնական գումար",
                        additionalInfo = "1,500,000.00 AMD",
                    ),
                    CardAdditionalInfo(
                        additionalInfoTitle = "Ընթացիկ պարտք",
                        additionalInfo = "1,125,432.00 AMD",
                    ),
                ),
                productStartIcon = R.drawable.ic_add,
                productStartIconTint = R.attr.contentPrimary,
                productBackgroundColorAttr = R.attr.backgroundTonal2
            )
        )
        loanCardWith1Row.setCardInfo(
            CardInfoExample(
                productTitle = "5G վարկ",
                productDescription = "Վերջ - 12/սեպ/2024",
                productNextPaymentDay = "14/09/2024",
                productNextPaymentAmount = "36,000.00 AMD",
                productNextPaymentDayTitle = "Վճարման օր",
                productNextPaymentAmountTitle = "Վճարման ենթակա գումար",
                productCardAdditionalInfo = arrayListOf(
                    CardAdditionalInfo(
                        additionalInfoTitle = "Սկզբնական գումար",
                        additionalInfo = "1,500,000.00 AMD",
                    )
                ),
                productStartIcon = R.drawable.ic_add,
                productStartIconTint = R.attr.contentPrimary,
                productBackgroundColorAttr = R.attr.backgroundTonal2,
                productBadgeIcon = R.drawable.ic_warning,
                productBadgeText = "Վճարումը 5 օրից",
                productBadgeBackgroundColorAttr = R.attr.backgroundAlternative6,
                productBadgeColorAndIconColorAttr = R.attr.contentAlternative6,
            )
        )
        loanCardWithoutTextContent.setCardInfo(
            CardInfoExample(
                productTitle = "Արագ օվերդրաֆտ",
                productDescription = "Վերջ - 12/սեպ/2024",
                productCardAdditionalInfo = arrayListOf(
                    CardAdditionalInfo(
                        additionalInfoTitle = "Սկզբնական գումար",
                        additionalInfo = "1,500,000.00 AMD",
                    ),
                    CardAdditionalInfo(
                        additionalInfoTitle = "Ընթացիկ պարտք",
                        additionalInfo = "1,125,432.00 AMD",
                    ),
                ),
                productStartIcon = R.drawable.ic_flag_am,
            )
        )
    }
}

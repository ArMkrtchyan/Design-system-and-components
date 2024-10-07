package am.acba.components

import am.acba.component.R
import am.acba.component.productCard.ProductCard
import am.acba.component.productCard.ProductCardAdditionalInfo
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.databinding.FragmentCardViewBinding
import android.view.LayoutInflater
import android.view.ViewGroup

class CardViewFragment : BaseViewBindingFragment<FragmentCardViewBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardViewBinding
        get() = FragmentCardViewBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentCardViewBinding.initView() {
        loanCardFull.setProductCard(
            ProductCard(
                productTitle = "5G վարկ",
                productDescription = "Վերջ - 12/սեպ/2024",
                productNextPaymentDay = "14/09/2024",
                productNextPaymentAmount = "36,000.00 AMD",
                productCardAdditionalInfo = arrayListOf(
                    ProductCardAdditionalInfo(
                        additionalInfoTitle = "Սկզբնական գումար",
                        additionalInfo = "1,500,000.00 AMD",
                    ),
                    ProductCardAdditionalInfo(
                        additionalInfoTitle = "Ընթացիկ պարտք",
                        additionalInfo = "1,125,432.00 AMD",
                    ),
                ),
                productStartIcon = R.drawable.ic_add,
                productBackgroundColorAttr = R.attr.backgroundTonal2,
                productBadgeIcon = R.drawable.ic_warning,
                productBadgeText = "Առկա է ժամկետնանց պարտավորություն",
                productBadgeBackgroundColorAttr = R.attr.backgroundDangerTonal1,
                productBadgeColorAndIconColorAttr = R.attr.contentDangerTonal1,
            )
        )
        loanCardWithoutIcon.setProductCard(
            ProductCard(
                productTitle = "5G վարկ",
                productDescription = "Վերջ - 12/սեպ/2024",
                productNextPaymentDay = "14/09/2024",
                productNextPaymentAmount = "36,000.00 AMD",
                productCardAdditionalInfo = arrayListOf(
                    ProductCardAdditionalInfo(
                        additionalInfoTitle = "Սկզբնական գումար",
                        additionalInfo = "1,500,000.00 AMD",
                    ),
                    ProductCardAdditionalInfo(
                        additionalInfoTitle = "Ընթացիկ պարտք",
                        additionalInfo = "1,125,432.00 AMD",
                    ),
                ),
                productBackgroundColorAttr = R.attr.backgroundTonal2
            )
        )
        loanCardWithoutRows.setProductCard(
            ProductCard(
                productTitle = "5G վարկ",
                productDescription = "Վերջ - 12/սեպ/2024",
                productNextPaymentDay = "14/09/2024",
                productNextPaymentAmount = "36,000.00 AMD",
                productStartIcon = R.drawable.ic_add,
                productBackgroundColorAttr = R.attr.backgroundTonal2
            )
        )
        loanCardWithoutSubTitle.setProductCard(
            ProductCard(
                productTitle = "5G վարկ",
                productNextPaymentDay = "14/09/2024",
                productNextPaymentAmount = "36,000.00 AMD",
                productCardAdditionalInfo = arrayListOf(
                    ProductCardAdditionalInfo(
                        additionalInfoTitle = "Սկզբնական գումար",
                        additionalInfo = "1,500,000.00 AMD",
                    ),
                    ProductCardAdditionalInfo(
                        additionalInfoTitle = "Ընթացիկ պարտք",
                        additionalInfo = "1,125,432.00 AMD",
                    ),
                ),
                productStartIcon = R.drawable.ic_add,
                productBackgroundColorAttr = R.attr.backgroundTonal2
            )
        )
        loanCardWith1Row.setProductCard(
            ProductCard(
                productTitle = "5G վարկ",
                productDescription = "Վերջ - 12/սեպ/2024",
                productNextPaymentDay = "14/09/2024",
                productNextPaymentAmount = "36,000.00 AMD",
                productCardAdditionalInfo = arrayListOf(
                    ProductCardAdditionalInfo(
                        additionalInfoTitle = "Սկզբնական գումար",
                        additionalInfo = "1,500,000.00 AMD",
                    )
                ),
                productStartIcon = R.drawable.ic_add,
                productBackgroundColorAttr = R.attr.backgroundTonal2,
                productBadgeIcon = R.drawable.ic_warning,
                productBadgeText = "Վճարումը 5 օրից",
                productBadgeBackgroundColorAttr = R.attr.backgroundAlternative6,
                productBadgeColorAndIconColorAttr = R.attr.contentAlternative6,
            )
        )
        loanCardWithoutTextContent.setProductCard(
            ProductCard(
                productTitle = "5G վարկ",
                productDescription = "Վերջ - 12/սեպ/2024",
                productCardAdditionalInfo = arrayListOf(
                    ProductCardAdditionalInfo(
                        additionalInfoTitle = "Սկզբնական գումար",
                        additionalInfo = "1,500,000.00 AMD",
                    ),
                    ProductCardAdditionalInfo(
                        additionalInfoTitle = "Ընթացիկ պարտք",
                        additionalInfo = "1,125,432.00 AMD",
                    ),
                ),
                productStartIcon = R.drawable.ic_add,
                productBackgroundColorAttr = R.attr.backgroundTonal2
            )
        )
    }
}

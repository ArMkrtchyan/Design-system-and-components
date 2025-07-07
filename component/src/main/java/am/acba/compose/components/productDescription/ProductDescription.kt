package am.acba.compose.components.productDescription

import am.acba.compose.components.timeLine.ProductDescriptionBadge
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.graphics.Color

internal data class ProductDescription(
    override val title: String = EMPTY_STRING,
    override val subTitle: String = EMPTY_STRING,
    override val mediaImage: String = EMPTY_STRING,
    override val secondTitle: String = EMPTY_STRING,
    override val secondSubTitle: String = EMPTY_STRING,
    override val bullets: List<String> = emptyList(),
    override val badges: List<ProductDescriptionBadge> = emptyList(),
    override val badgeBackgroundColor: Color? = null,
    override val badgeTextColor: Color? = null
) : IProductDescription

interface ProductDescriptionMockDataCreator {
    @Composable
    fun create(): IProductDescription
}

@Composable
@NonRestartableComposable
fun createMockState(productDescriptionMockDataCreator: ProductDescriptionMockDataCreator): IProductDescription {
    return productDescriptionMockDataCreator.create()
}

class MockState(private val type: Int) : ProductDescriptionMockDataCreator {
    @Composable
    override fun create(): IProductDescription {
        return when (type) {

            1 -> ProductDescription(
                title = "Հաշվով իրականացրու",
                subTitle = "Հաշվով իրականացրու ընթացիկ գործառնություններ",
                mediaImage = "https://online1-test.acba.am/Shared/LoanImages/FastOverdraft.png",
                secondTitle = "ԹՎԱՅԻՆ ՔԱՐՏ",
                secondSubTitle = "Հասանելի 7 արժույթagxyagxgaxgaxajhcahjcahjgxahjgcahjchajcahach svcavchjsvhjcshcbshjcbshjcbshjbcsjkbcksbs",
                bullets = listOf(
                    "Տոկոսադրույք՝ 0%",
                    "Տևողություն՝ 4 ամիս",
                    "Գումարի չափ՝ 50.000 կամ 100.000 ՀՀ դրամ",
                    "Միանվագ միջնորդավճար 2.900 կամ 5.800 ՀՀ դրամ"
                ),
                badges = listOf(
                    ProductDescriptionBadge("https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Standard.svg", "-50% տարեկան վճար"),
                    ProductDescriptionBadge("https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Standard.svg", "Բարերար"),
                    ProductDescriptionBadge("https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Standard.svg", "Նոր"),
                ),
            )

            2 -> ProductDescription(
                title = "5G վարկ",
                mediaImage = "https://online1-test.acba.am/Shared/LoanImages/5g.png",
                bullets = listOf(
                    "Մինչև 10 մլն ՀՀ դրամ",
                    "Տևողություն՝ 48 կամ 60 ամիս",
                    "Տոկոսադրույք՝ 18.9%-19.9%",
                    "Առանց գրավի և երաշխավորության"
                ),
            )

            3 -> ProductDescription(
                title = "Ավանդի գրավով վարկ",
                mediaImage = "https://online1-test.acba.am/Shared/LoanImages/DepositLoan.png",
                bullets = listOf(
                    "Նվազագույն գումար՝ 50.000 ՀՀ դրամ",
                    "Տևողություն՝ 1-60 ամիս",
                    "Առավելագույն գումար՝ գրավադրվող գումարի 90%-ի կամ 95%-ի չափով՝ կախված ավանդի արժույթից"
                ),
            )

            4 -> ProductDescription(
                title = "Ավանդի գրավով վարկային գիծ",
                mediaImage = "https://online1-test.acba.am/Shared/LoanImages/DepositCreditLine.png",
                bullets = listOf(
                    "Տոկոսադրույք՝ 14%",
                    "Տևողություն՝ մինչև 60 ամիս",
                    "Առավելագույն գումար՝ գրավադրվող գումարի 75%-95%՝ կախված ավանդի արժույթից"
                ),
            )

            5 -> ProductDescription(
                title = "Արագ օվերդրաֆտ",
                mediaImage = "https://online1-test.acba.am/Shared/LoanImages/FastOverdraft.png",
                bullets = listOf(
                    "Տոկոսադրույք՝ 0%",
                    "Տևողություն՝ 4 ամիս",
                    "Գումարի չափ՝ 50.000 կամ 100.000 ՀՀ դրամ",
                    "Միանվագ միջնորդավճար 2.900 կամ 5.800 ՀՀ դրամ"
                ),
            )

            6 -> ProductDescription(
                title = "Հաշվով իրականացրու",
                subTitle = "Հաշվով իրականացրու ընթացիկ գործառնություններ",
                mediaImage = "https://online1-test.acba.am/Shared/LoanImages/FastOverdraft.png",
            )

            7 -> ProductDescription(
                title = "Հաշվով իրականացրու",
                mediaImage = "https://online1-test.acba.am/Shared/LoanImages/FastOverdraft.png",
                secondTitle = "ԹՎԱՅԻՆ ՔԱՐՏ",
                secondSubTitle = "Հասանելի 7 արժույթagxyagxgaxgaxajhcahjcahjgxahjgcahjchajcahach svcavchjsvhjcshcbshjcbshjcbshjbcsjkbcksbs",
                badges = listOf(
                    ProductDescriptionBadge("https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Partial.svg", "-50% տարեկան վճար"),
                    ProductDescriptionBadge("https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Partial.svg", "Բարերար"),
                    ProductDescriptionBadge("https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Partial.svg", "Նոր"),
                ),
            )

            8 -> ProductDescription(
                title = "Հաշվով իրականացրու",
                mediaImage = "https://online1-test.acba.am/Shared/LoanImages/FastOverdraft.png",
                secondTitle = "ԹՎԱՅԻՆ ՔԱՐՏ",
                badges = listOf(
                    ProductDescriptionBadge("https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Total.svg", "-50% տարեկան վճար"),
                    ProductDescriptionBadge("https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Total.svg", "Բարերար"),
                    ProductDescriptionBadge("https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Total.svg", "Նոր"),
                ),
                badgeBackgroundColor = DigitalTheme.colorScheme.backgroundWarning,
                badgeTextColor = DigitalTheme.colorScheme.contentWarning,
            )

            else -> {
                ProductDescription(
                    title = "Հաշվով իրականացրու",
                    mediaImage = "https://online1-test.acba.am/Shared/LoanImages/FastOverdraft.png",
                    secondTitle = "ԹՎԱՅԻՆ ՔԱՐՏ",
                    secondSubTitle = "Հասանելի 7 արժույթagxyagxgaxgaxajhcahjcahjgxahjgcahjchajcahach svcavchjsvhjcshcbshjcbshjcbshjbcsjkbcksbs",
                )
            }
        }
    }
}

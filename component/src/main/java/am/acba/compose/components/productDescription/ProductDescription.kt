package am.acba.compose.components.productDescription

import am.acba.compose.theme.DigitalTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class ProductDescription(
    override val title: String = "",
    override val subTitle: String = "",
    override val mediaImage: String = "",
    override val secondTitle: String = "",
    override val secondSubTitle: String = "",
    override val bullets: List<String> = emptyList(),
    override val badges: List<Pair<String, String>> = emptyList(),
    override val badgeBackgroundColor: Color? = null,
    override val badgeTextColor: Color? = null
) : IProductDescription {
    companion object {
        @Composable
        fun getMockAllField() = ProductDescription(
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
                "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Standard.svg" to "-50% տարեկան վճար",
                "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Standard.svg" to "Բարերար",
                "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Standard.svg" to "Նոր",
            ),
        )

        fun getLoan1() = ProductDescription(
            title = "5G վարկ",
            mediaImage = "https://online1-test.acba.am/Shared/LoanImages/5g.png",
            bullets = listOf(
                "Մինչև 10 մլն ՀՀ դրամ",
                "Տևողություն՝ 48 կամ 60 ամիս",
                "Տոկոսադրույք՝ 18.9%-19.9%",
                "Առանց գրավի և երաշխավորության"
            ),
        )

        fun getLoan2() = ProductDescription(
            title = "Ավանդի գրավով վարկ",
            mediaImage = "https://online1-test.acba.am/Shared/LoanImages/DepositLoan.png",
            bullets = listOf(
                "Նվազագույն գումար՝ 50.000 ՀՀ դրամ",
                "Տևողություն՝ 1-60 ամիս",
                "Առավելագույն գումար՝ գրավադրվող գումարի 90%-ի կամ 95%-ի չափով՝ կախված ավանդի արժույթից"
            ),
        )

        fun getLoan3() = ProductDescription(
            title = "Ավանդի գրավով վարկային գիծ",
            mediaImage = "https://online1-test.acba.am/Shared/LoanImages/DepositCreditLine.png",
            bullets = listOf(
                "Տոկոսադրույք՝ 14%",
                "Տևողություն՝ մինչև 60 ամիս",
                "Առավելագույն գումար՝ գրավադրվող գումարի 75%-95%՝ կախված ավանդի արժույթից"
            ),
        )

        fun getLoan4() = ProductDescription(
            title = "Արագ օվերդրաֆտ",
            mediaImage = "https://online1-test.acba.am/Shared/LoanImages/FastOverdraft.png",
            bullets = listOf(
                "Տոկոսադրույք՝ 0%",
                "Տևողություն՝ 4 ամիս",
                "Գումարի չափ՝ 50.000 կամ 100.000 ՀՀ դրամ",
                "Միանվագ միջնորդավճար 2.900 կամ 5.800 ՀՀ դրամ"
            ),
        )

        fun getMockTitleSubTitleAndMedia() = ProductDescription(
            title = "Հաշվով իրականացրու",
            subTitle = "Հաշվով իրականացրու ընթացիկ գործառնություններ",
            mediaImage = "https://online1-test.acba.am/Shared/LoanImages/FastOverdraft.png",
        )

        @Composable
        fun getMockState1() = ProductDescription(
            title = "Հաշվով իրականացրու",
            mediaImage = "https://online1-test.acba.am/Shared/LoanImages/FastOverdraft.png",
            secondTitle = "ԹՎԱՅԻՆ ՔԱՐՏ",
            secondSubTitle = "Հասանելի 7 արժույթagxyagxgaxgaxajhcahjcahjgxahjgcahjchajcahach svcavchjsvhjcshcbshjcbshjcbshjbcsjkbcksbs",
            badges = listOf(
                "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Partial.svg" to "-50% տարեկան վճար",
                "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Partial.svg" to "Բարերար",
                "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Partial.svg" to "Նոր",
            ),
        )

        fun getMockState2() = ProductDescription(
            title = "Հաշվով իրականացրու",
            mediaImage = "https://online1-test.acba.am/Shared/LoanImages/FastOverdraft.png",
            secondTitle = "ԹՎԱՅԻՆ ՔԱՐՏ",
            secondSubTitle = "Հասանելի 7 արժույթagxyagxgaxgaxajhcahjcahjgxahjgcahjchajcahach svcavchjsvhjcshcbshjcbshjcbshjbcsjkbcksbs",
        )

        @Composable
        fun getMockState3() = ProductDescription(
            title = "Հաշվով իրականացրու",
            mediaImage = "https://online1-test.acba.am/Shared/LoanImages/FastOverdraft.png",
            secondTitle = "ԹՎԱՅԻՆ ՔԱՐՏ",
            badges = listOf(
                "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Total.svg" to "-50% տարեկան վճար",
                "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Total.svg" to "Բարերար",
                "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Total.svg" to "Նոր",
            ),
            badgeBackgroundColor = DigitalTheme.colorScheme.backgroundWarning,
            badgeTextColor = DigitalTheme.colorScheme.contentWarning,
        )
    }
}
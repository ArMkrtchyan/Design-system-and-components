package am.acba.composeComponents.cardView

import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.productCard.ProductCard
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCardScreen(title: String = "") {
    Box(
        modifier = Modifier
            .background(DigitalTheme.colorScheme.backgroundBase)
            .fillMaxSize()
            .padding(
                bottom = TopAppBarDefaults.windowInsets
                    .only(WindowInsetsSides.Bottom)
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
    ) {
        Column(Modifier.fillMaxSize()) {
            PrimaryToolbar(title = title)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                ProductCard(
                    title = "Ավանդի գրավորվ վարկային",
                    startAvatarBackgroundColor = Color.Transparent,
                    startAvatarImageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType37_1_1.png",
                    startAvatarContentScale = ContentScale.FillWidth,
                    startAvatarImageCornerRadius = 4,
                    bottomRowTitle1 = "Վճարման օր",
                    bottomRowValue1 = "14/սեպ/2024",
                    bottomRowTitle2 = "Վճարման ենթակա գումար",
                    bottomRowValue2 = "40,000.00 AMD",
                ) {}
                VerticalSpacer(20)
                ProductCard(
                    startAvatarIcon = am.acba.component.R.drawable.default_icon,
                    title = "Ավանդի գրավորվ վարկային",
                    description = "Վերջ - 12/սեպ/2024", rowItems = arrayListOf(
                        "Պայմանագրի համար" to "1231456789",
                        "Սկզբնական գումար" to "200,000.00 AMD",
                        "Ընթացիկ պարտք" to "200,000.00 AMD"
                    )
                ) {}
                VerticalSpacer(20)
                ProductCard(
                    startAvatarIcon = am.acba.component.R.drawable.default_icon,
                    title = "Ավանդի գրավորվ վարկային", description = "Վերջ - 12/սեպ/2024",
                    rowItems = arrayListOf(
                        "Պայմանագրի համար" to "1231456789",
                        "Սկզբնական գումար" to "200,000.00 AMD",
                        "Ընթացիկ պարտք" to "200,000.00 AMD"
                    ),
                    bottomRowTitle1 = "Վճարման օր",
                    bottomRowValue1 = "14/սեպ/2024",
                    bottomRowTitle2 = "Վճարման ենթակա գումար",
                    bottomRowValue2 = "40,000.00 AMD",
                    statusTitle = "Առկա է ժամկետնանց պարտավորություն",
                    statusIcon = am.acba.component.R.drawable.ic_warning,
                    statusBackgroundColor = DigitalTheme.colorScheme.backgroundDangerTonal1,
                    statusTextColor = DigitalTheme.colorScheme.contentDangerTonal1,
                    statusIconColor = DigitalTheme.colorScheme.contentDangerTonal1
                ) {}
                VerticalSpacer(20)
                ProductCard(
                    title = "Ավանդի գրավորվ վարկային", description = "Վերջ - 12/սեպ/2024",
                    rowItems = arrayListOf(
                        "Պայմանագրի համար" to "1231456789",
                        "Սկզբնական գումար" to "200,000.00 AMD",
                        "Ընթացիկ պարտք" to "200,000.00 AMD"
                    ),
                    bottomRowTitle1 = "Վճարման օր",
                    bottomRowValue1 = "14/սեպ/2024",
                ) {}
                VerticalSpacer(50)
            }
        }
    }
}

@Composable
@PreviewLightDark
fun ProductCardScreenPreview() {
    DigitalTheme {
        ProductCardScreen()
    }
}
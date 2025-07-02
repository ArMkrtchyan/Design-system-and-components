package am.acba.composeComponents.offers

import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.featureCard.OfferCard
import am.acba.compose.components.featureCard.model.OfferCardItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersScreen(title: String = "") {
    var expanded = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .background(DigitalTheme.colorScheme.backgroundBase)
            .fillMaxSize()
            .padding(
                bottom = TopAppBarDefaults.windowInsets
                    .only(WindowInsetsSides.Bottom)
                    .asPaddingValues()
                    .calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp
            )
    ) {
        Column(Modifier.fillMaxSize()) {
            PrimaryToolbar(title = title)
            val offer = OfferCardItem(
                amount = "10,000,000.00 AMD",
                creditLimitTitle = "վարկային սահմանաչափ",
                expirationDate = "Վերջնաժամկետ 12/09/2024",
                badge = "նոր",
            )

            OfferCard(
                title = "duq uneq nor arajark",
                items = listOf(offer, offer, offer, offer),
                badge = "+1",
                seeAllTitle = "Տեսնել բոլոր առաջարկները",
                isExpanded = expanded.value,
                onClick = {
                    expanded.value = !expanded.value
                },
                onItemClick = {},
                onSeeAllClick = {},
            )
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme
    Column(
        Modifier
            .background(DigitalTheme.colorScheme.backgroundBase)
            .padding(10.dp)
    ) {
        OffersScreen()
    }
}
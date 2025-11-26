package am.acba.composeComponents.stockListItem

import am.acba.component.R
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.stockListItem.StockListItem
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockListItemScreen(title: String = EMPTY_STRING) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
        ) {
            PrimaryToolbar(title = title)
            VerticalSpacer(16.dp)
            StockListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                avatarIcon = R.drawable.ic_stock,
                infoText = "APPL",
                infoTitle = "Apple",

                endTitle = "Քանակ։ 25",
                endValue = "2,122,233.12 USD",
                endPercent = 0.36,
                endPercentText = "+0.36%",
                endPercentIconUrl = "https://online1-test.acba.am/Shared/Invest/Trend-up.svg"
            )
            VerticalSpacer(16.dp)
            StockListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                avatarIcon = R.drawable.ic_stock,
                infoText = "AMACBAS10ER7",
                infoTitle = "Ակբա բաց բաժնետիրական",
                infoSubTitle = "Մարումը՝ 01/03/2025",

                middleTitle = "Շուկ. եկամ",
                middleValue = "8,321%",

                endTitle = "Քանակ։ 25",
                endValue = "2,122,233.12 USD",
                endPercent = 0.0,
                endPercentText = "0.00%"
            )
            VerticalSpacer(16.dp)
            StockListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                avatarImageUrl = "https://online1-test.acba.am/Shared/Invest/stock.svg",
                infoText = "AMACBAS10ER7",
                infoTitle = "Ակբա բաց բաժնետիրական",
                infoSubTitle = "Մարումը՝ 01/03/2025",

                middleTitle = "Շուկ. եկամ",
                middleValue = "8,321%",

                endTitle = "Քանակ։ 25",
                endValue = "2,122,233.12 USD",
                endPercent = -0.5,
                endPercentText = "-0.5%",
                endPercentIconUrl = "https://online1-test.acba.am/Shared/Invest/Trend-down.svg"
            )
        }
    }
}


@Composable
@PreviewLightDark
fun Preview() {
    DigitalTheme {
        StockListItemScreen()
    }
}
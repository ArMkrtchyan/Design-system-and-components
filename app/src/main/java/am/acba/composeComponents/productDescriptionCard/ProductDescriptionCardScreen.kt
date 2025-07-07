package am.acba.composeComponents.productDescriptionCard

import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.productDescription.ProductDescription
import am.acba.compose.components.productDescription.ProductDescriptionCard
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDescriptionCardScreen(title: String = "") {
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
            PrimaryToolbar(title = title, actions = {
                IconButton(onClick = {

                }) {

                }
            })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 40.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                ProductDescriptionCard(productDescription = ProductDescription.getLoan1()) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = ProductDescription.getLoan2()) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = ProductDescription.getLoan3()) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = ProductDescription.getLoan4()) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = ProductDescription.getMockTitleSubTitleAndMedia()) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = ProductDescription.getMockAllField()) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = ProductDescription.getMockState1()) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = ProductDescription.getMockState2()) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = ProductDescription.getMockState3()) {}
            }
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        ProductDescriptionCardScreen()
    }
}
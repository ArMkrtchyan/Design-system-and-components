package am.acba.composeComponents.productDescriptionCard

import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.productDescription.MockState
import am.acba.compose.components.productDescription.ProductDescriptionCard
import am.acba.compose.components.productDescription.createMockState
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
            PrimaryToolbar(title = title)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 40.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                ProductDescriptionCard(productDescription = createMockState(MockState(1))) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = createMockState(MockState(2))) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = createMockState(MockState(3))) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = createMockState(MockState(4))) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = createMockState(MockState(5))) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = createMockState(MockState(6))) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = createMockState(MockState(7))) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = createMockState(MockState(8))) {}
                VerticalSpacer(20)
                ProductDescriptionCard(productDescription = createMockState(MockState(9))) {}
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
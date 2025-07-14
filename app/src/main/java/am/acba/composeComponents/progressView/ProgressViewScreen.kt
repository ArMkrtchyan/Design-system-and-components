package am.acba.composeComponents.progressView

import am.acba.compose.components.PrimaryButton
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.progressComponents.PrimaryProgressBar
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressViewScreen(title: String = "") {

    var targetProgress by remember { mutableFloatStateOf(20000f) }
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
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                PrimaryProgressBar(
                    progress = 25000f,
                    min = 10000f,
                    max = 100000f,
                    modifier = Modifier.fillMaxWidth().scale(scaleX = 1.005f, scaleY = 1.0f),
                )
                PrimaryProgressBar(
                    progress = 15000f,
                    min = 10000f,
                    max = 100000f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    topLeftText = "1,000,000.00 AMD",
                    topRightText = "1,000,000.00 AMD",
                    bottomLeftText = "Սկսած",
                    bottomRightText = "Առավելագույնը` 20,000,000.00 AMD"
                )
                PrimaryProgressBar(
                    progress = targetProgress,
                    min = 10000f,
                    max = 100000f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    progressColor = DigitalTheme.colorScheme.backgroundInfo,
                    bottomLeftText = "Օգտագործած",
                    bottomLeftTextBold = "548,003,065.00 AMD",
                    bottomRightText = "Սկզբնական",
                    bottomRightTextBold = "20,000,000.00 AMD"
                )
                PrimaryButton(
                    onClick = {
                        targetProgress += 10000f
                        if (targetProgress > 100000f) targetProgress = 10000f
                    },
                    text = "change progress state",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        ProgressViewScreen()
    }
}
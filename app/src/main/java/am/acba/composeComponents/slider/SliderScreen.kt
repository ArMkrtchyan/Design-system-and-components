package am.acba.composeComponents.slider

import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.slider.PrimarySlider
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.formatWithPattern
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
import androidx.compose.material3.SliderState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderScreen(title: String = "") {
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
                val sliderState = remember {
                    SliderState(
                        valueRange = 100.5f..100000000.25f,
                        onValueChangeFinished = {},
                        value = 100.5f,
                        steps = 9
                    )
                }
                PrimaryText(text = "${sliderState.value.formatWithPattern(minimumFractionDigits = 2)}$")
                VerticalSpacer(20)
                PrimarySlider(
                    state = sliderState,
                    startSuffix = "AMD",
                    endSuffix = "AMD",
                    minimumFractionDigits = 2
                )
                VerticalSpacer(20)

                val sliderState2 = remember {
                    SliderState(
                        valueRange = 6f..48f,
                        onValueChangeFinished = {},
                        value = 12f,
                        steps = 6
                    )
                }
                PrimaryText(text = "${sliderState2.value.formatWithPattern()} Months")
                VerticalSpacer(20)
                PrimarySlider(
                    state = sliderState2,
                    startSuffix = "Months",
                    endSuffix = "Months",
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun SliderScreenPreview() {
    DigitalTheme {
        SliderScreen()
    }
}
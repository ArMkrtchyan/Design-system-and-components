package am.acba.composeComponents.progressView

import am.acba.compose.components.PrimaryButton
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.progressComponents.ComponentLinearProgressIndicator
import am.acba.compose.components.progressComponents.ComponentSegmentedProgressBar
import am.acba.compose.components.progressComponents.model.ProgressCaption
import am.acba.compose.components.progressComponents.model.ProgressCaptionLine
import am.acba.compose.components.progressComponents.model.ProgressIndicatorType
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
    var segmentProgress by remember { mutableIntStateOf(0) }
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

                ComponentLinearProgressIndicator(
                    progress = 25000f,
                    min = 10000f,
                    max = 100000f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(scaleX = 1.005f, scaleY = 1.0f),
                )
                ComponentLinearProgressIndicator(
                    progress = 15000f,
                    min = 10000f,
                    max = 100000f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    progressCaptionLines = listOf(
                        ProgressCaptionLine(
                            leading = ProgressCaption("Օգտագործած"),
                            trailing = ProgressCaption("Սկզբնական"),
                        ),
                        ProgressCaptionLine(
                            leading = ProgressCaption("548,003,065.00 AMD"),
                            trailing = ProgressCaption("20,000,000.00 AMD"),
                        ),
                    )
                )
                ComponentLinearProgressIndicator(
                    progress = targetProgress,
                    min = 10000f,
                    max = 100000f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    progressColor = DigitalTheme.colorScheme.backgroundInfo,
                    type = ProgressIndicatorType.SECONDARY,
                    progressCaptionLines = listOf(
                        ProgressCaptionLine(
                            leading = ProgressCaption("Օգտագործած"),
                            trailing = ProgressCaption("Սկզբնական"),
                        ),
                        ProgressCaptionLine(
                            leading = ProgressCaption("548,003,065.00 AMD"),
                            trailing = ProgressCaption("20,000,000.00 AMD"),
                        ),
                    )
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

                ComponentSegmentedProgressBar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    segmentCount = 5,
                    progress = segmentProgress,
                )

                PrimaryButton(
                    onClick = {
                        segmentProgress++
                    },
                    text = "+",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 16.dp)
                )

                PrimaryButton(
                    onClick = {
                        segmentProgress--
                    },
                    text = "-",
                    modifier = Modifier
                        .wrapContentSize()
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
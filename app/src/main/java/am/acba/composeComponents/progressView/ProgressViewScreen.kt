package am.acba.composeComponents.progressView

import am.acba.compose.components.PrimaryButton
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.progressComponents.PrimaryProgressBar
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressViewScreen(title: String = "") {

    var targetProgress  by remember { mutableFloatStateOf(0.1f) }
    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
    )
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
                PrimaryProgressBar(
                    progress = 0.1f,
                    modifier = Modifier.fillMaxWidth(),
                    topLeftText = "1,000,000.00 AMD",
                    topRightText = "1,000,000.00 AMD",
                    bottomLeftText = "Սկսած",
                    bottomRightText = "Առավելագույնը` 20,000,000.00 AMD"
                )
                Spacer(modifier = Modifier.height(20.dp))
                PrimaryProgressBar(
                    progress = animatedProgress,
                    modifier = Modifier.fillMaxWidth(),
                    progressColor = DigitalTheme.colorScheme.backgroundInfo,
                    bottomLeftText = "Օգտագործած",
                    bottomLeftTextBold = "548,003,065.00 AMD",
                    bottomRightText = "Սկզբնական",
                    bottomRightTextBold = "20,000,000.00 AMD"
                )
                PrimaryButton(
                    onClick = {
                        targetProgress += 0.1f
                        if (targetProgress > 1f) targetProgress = 0f
                    },
                    text = "change progress state",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
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
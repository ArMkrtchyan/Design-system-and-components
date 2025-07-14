package am.acba.compose.components.progressComponents

import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp


@Composable
fun PrimaryProgressBar(
    modifier: Modifier = Modifier,
    min: Float,
    max: Float,
    progress: Float,
    trackColor: Color = DigitalTheme.colorScheme.backgroundTonal3,
    progressColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    topLeftText: String = "",
    topRightText: String = "",
    bottomLeftText: String = "",
    bottomLeftTextBold: String = "",
    bottomRightText: String = "",
    bottomRightTextBold: String = ""
) {
    val normalizedProgress = ((progress - min) / (max - min)).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = normalizedProgress,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        TopContent(topRightText = topRightText, topLeftText = topLeftText)
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = progressColor,
            trackColor = trackColor,
            drawStopIndicator = {}
        )
        BottomContent(
            bottomLeftText = bottomLeftText,
            bottomLeftTextBold = bottomLeftTextBold,
            bottomRightText = bottomRightText,
            bottomRightTextBold = bottomRightTextBold
        )
    }
}

@Composable
fun TopContent(
    topRightText: String,
    topLeftText: String
) {
    if (topLeftText.isNotEmpty() || topRightText.isNotEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            topLeftText.takeIf { it.isNotEmpty() }?.let {
                PrimaryText(
                    text = it,
                    style = DigitalTheme.typography.body2Bold,
                    color = DigitalTheme.colorScheme.contentPrimary
                )
            }

            topRightText.takeIf { it.isNotEmpty() }?.let {
                PrimaryText(
                    text = it,
                    style = DigitalTheme.typography.body2Bold,
                    color = DigitalTheme.colorScheme.contentPrimary
                )
            }
        }
    }
}

@Composable
fun BottomContent(
    bottomLeftText: String,
    bottomLeftTextBold: String,
    bottomRightText: String,
    bottomRightTextBold: String
) {
    if (bottomLeftText.isNotEmpty() || bottomLeftTextBold.isNotEmpty() ||
        bottomRightText.isNotEmpty() || bottomRightTextBold.isNotEmpty()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                bottomLeftText.takeIf { it.isNotEmpty() }?.let {
                    PrimaryText(
                        text = it,
                        style = DigitalTheme.typography.smallRegular,
                        color = DigitalTheme.colorScheme.contentPrimaryTonal1
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                bottomLeftTextBold.takeIf { it.isNotEmpty() }?.let {
                    PrimaryText(
                        text = it,
                        style = DigitalTheme.typography.smallBold,
                        color = DigitalTheme.colorScheme.contentPrimary
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                bottomRightText.takeIf { it.isNotEmpty() }?.let {
                    PrimaryText(
                        text = it,
                        style = DigitalTheme.typography.smallRegular,
                        color = DigitalTheme.colorScheme.contentPrimaryTonal1
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                bottomRightTextBold.takeIf { it.isNotEmpty() }?.let {
                    PrimaryText(
                        text = it,
                        style = DigitalTheme.typography.smallBold,
                        color = DigitalTheme.colorScheme.contentPrimary
                    )
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
fun PrimaryProgressBarPreview() {
    PrimaryProgressBar(
        progress = 20000f,
        min = 10000f,
        max = 100000f,
        modifier = Modifier.fillMaxWidth(),
        progressColor = DigitalTheme.colorScheme.backgroundInfo,
        bottomLeftText = "Օգտագործած",
        bottomLeftTextBold = "548,003,065.00 AMD",
        bottomRightText = "Սկզբնական",
        bottomRightTextBold = "20,000,000.00 AMD"
    )
}
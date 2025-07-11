package am.acba.compose.components.progressComponents

import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp


@Composable
fun PrimaryProgressBar(
    progress: Float,
    topLeftText: String = "",
    topRightText: String = "",
    bottomLeftText: String = "",
    bottomLeftTextBold: String = "",
    bottomRightText: String = "",
    bottomRightTextBold: String = "",
    trackColor: Color = DigitalTheme.colorScheme.backgroundTonal3,
    progressColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        TopContent(topRightText = topRightText, topLeftText = topLeftText)
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = progressColor,
            trackColor = trackColor,
            drawStopIndicator = {}
        )

        Spacer(modifier = Modifier.height(8.dp))
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
        progress = 0.1f,
        modifier = Modifier.fillMaxWidth(),
        topLeftText = "1,000,000.00 AMD",
        topRightText = "1,000,000.00 AMD",
        bottomLeftText = "Սկսած",
        bottomRightText = "Առավելագույնը` 20,000,000.00 AMD"
    )
    Spacer(modifier = Modifier.height(20.dp))
    PrimaryProgressBar(
        progress = 0.5f,
        modifier = Modifier.fillMaxWidth(),
        progressColor = DigitalTheme.colorScheme.backgroundInfo,
        bottomLeftText = "Օգտագործած",
        bottomLeftTextBold = "548,003,065.00 AMD",
        bottomRightText = "Սկզբնական",
        bottomRightTextBold = "20,000,000.00 AMD"
    )
}
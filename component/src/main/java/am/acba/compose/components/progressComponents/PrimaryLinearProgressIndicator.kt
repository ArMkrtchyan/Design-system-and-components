package am.acba.compose.components.progressComponents

import am.acba.compose.HorizontalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp


@Composable
fun PrimaryLinearProgressIndicator(
    modifier: Modifier = Modifier,
    min: Float,
    max: Float,
    progress: Float,
    trackColor: Color = DigitalTheme.colorScheme.backgroundTonal3,
    progressColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    type: ProgressIndicatorType = ProgressIndicatorType.TOP_AND_BOTTOM_TEXTS,
    content: List<Content> = emptyList(),
) {
    val normalizedProgress = ((progress - min) / (max - min)).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = normalizedProgress,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    val topRow = content.getOrNull(0)
    val bottomRow = content.getOrNull(1)

    Column(
        modifier = modifier,
    ) {
        when (type) {
            ProgressIndicatorType.TOP_AND_BOTTOM_TEXTS -> {
                ProgressTextRow(
                    content = topRow,
                    defaultStyle = DigitalTheme.typography.body2Bold,
                    defaultColor = DigitalTheme.colorScheme.contentPrimary
                )
                HorizontalSpacer(8)
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = progressColor,
                    trackColor = trackColor,
                    drawStopIndicator = {}
                )
                HorizontalSpacer(4)
                ProgressTextRow(
                    content = bottomRow,
                    defaultStyle = DigitalTheme.typography.smallRegular,
                    defaultColor = DigitalTheme.colorScheme.contentPrimaryTonal1
                )
            }

            ProgressIndicatorType.ONLY_BOTTOM_TEXTS -> {
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = progressColor,
                    trackColor = trackColor,
                    drawStopIndicator = {}
                )
                HorizontalSpacer(4)
                ProgressTextRow(
                    content = topRow,
                    defaultStyle = DigitalTheme.typography.smallRegular,
                    defaultColor = DigitalTheme.colorScheme.contentPrimaryTonal1
                )
                ProgressTextRow(
                    content = bottomRow,
                    defaultStyle = DigitalTheme.typography.smallBold,
                    defaultColor = DigitalTheme.colorScheme.contentPrimary
                )
            }
        }
    }
}

@Composable
fun ProgressTextRow(
    content: Content?,
    defaultStyle: TextStyle,
    defaultColor: Color
) {
    val hasLeading = content?.leadingContent?.second?.isNotEmpty() == true
    val hasTrailing = content?.trailingContent?.second?.isNotEmpty() == true

    if (hasLeading || hasTrailing) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            content?.leadingContent
                ?.takeIf { it.second.isNotEmpty() }
                ?.let { (style, text) ->
                    PrimaryText(
                        text = text,
                        style = style.style ?: defaultStyle,
                        color = style.color ?: defaultColor
                    )
                }

            content?.trailingContent
                ?.takeIf { it.second.isNotEmpty() }
                ?.let { (style, text) ->
                    PrimaryText(
                        text = text,
                        style = style.style ?: defaultStyle,
                        color = style.color ?: defaultColor
                    )
                }
        }
    }
}


@Composable
@PreviewLightDark
fun PrimaryProgressBarPreview() {
    /*PrimaryLinearProgressIndicator(
        progress = 20000f,
        min = 10000f,
        max = 100000f,
        modifier = Modifier.fillMaxWidth(),
        progressColor = DigitalTheme.colorScheme.backgroundInfo,
        bottomLeftText = "Օգտագործած",
        bottomLeftTextBold = "548,003,065.00 AMD",
        bottomRightText = "Սկզբնական",
        bottomRightTextBold = "20,000,000.00 AMD"
    )*/
}
package am.acba.compose.components.progressComponents

import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.progressComponents.model.ProgressCaptionLine
import am.acba.compose.components.progressComponents.model.ProgressIndicatorType
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.tripleOf
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun ComponentLinearProgressIndicator(
    modifier: Modifier = Modifier,
    min: Float,
    max: Float,
    progress: Float,
    trackColor: Color = DigitalTheme.colorScheme.backgroundTonal3,
    progressColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    type: ProgressIndicatorType = ProgressIndicatorType.PRIMARY,
    progressCaptionLines: List<ProgressCaptionLine> = emptyList(),
) {
    val normalizedProgress = ((progress - min) / (max - min)).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = normalizedProgress,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    val topCaption = progressCaptionLines.getOrNull(0)
    val bottomCaption = progressCaptionLines.getOrNull(1)

    Column(
        modifier = modifier,
    ) {
        when (type) {
            ProgressIndicatorType.PRIMARY -> {
                ProgressTextRow(
                    progressCaptionLine = topCaption,
                    defaultStyle = DigitalTheme.typography.body2Bold,
                    defaultColor = DigitalTheme.colorScheme.contentPrimary
                )
                VerticalSpacer(8)
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = progressColor,
                    trackColor = trackColor,
                    drawStopIndicator = {}
                )
                VerticalSpacer(4)
                ProgressTextRow(
                    progressCaptionLine = bottomCaption,
                    defaultStyle = DigitalTheme.typography.smallRegular,
                    defaultColor = DigitalTheme.colorScheme.contentPrimaryTonal1
                )
            }

            ProgressIndicatorType.SECONDARY -> {
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = progressColor,
                    trackColor = trackColor,
                    drawStopIndicator = {}
                )
                VerticalSpacer(4)
                ProgressTextRow(
                    progressCaptionLine = topCaption,
                    defaultStyle = DigitalTheme.typography.smallRegular,
                    defaultColor = DigitalTheme.colorScheme.contentPrimaryTonal1
                )
                ProgressTextRow(
                    progressCaptionLine = bottomCaption,
                    defaultStyle = DigitalTheme.typography.smallBold,
                    defaultColor = DigitalTheme.colorScheme.contentPrimary
                )
            }
        }
    }
}

@Composable
fun ProgressTextRow(
    progressCaptionLine: ProgressCaptionLine?,
    defaultStyle: TextStyle,
    defaultColor: Color
) {
    val hasLeading = progressCaptionLine?.leading?.first?.isNotEmpty() == true
    val hasTrailing = progressCaptionLine?.trailing?.first?.isNotEmpty() == true

    if (hasLeading || hasTrailing) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            progressCaptionLine?.leading
                ?.takeIf { !it.first.isNullOrEmpty() }
                ?.let { (text, color, style) ->
                    PrimaryText(
                        text = text.orEmpty(),
                        style = style ?: defaultStyle,
                        color = color ?: defaultColor
                    )
                }

            progressCaptionLine?.trailing
                ?.takeIf { !it.first.isNullOrEmpty() }
                ?.let { (text, color, style) ->
                    PrimaryText(
                        text = text.orEmpty(),
                        style = style ?: defaultStyle,
                        color = color ?: defaultColor
                    )
                }
        }
    }
}


@Composable
@PreviewLightDark
fun PrimaryProgressBarPreview() {
    ComponentLinearProgressIndicator(
        progress = 10000F,
        min = 10000f,
        max = 100000f,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        progressColor = DigitalTheme.colorScheme.backgroundInfo,
        type = ProgressIndicatorType.SECONDARY,
        progressCaptionLines = listOf(
            ProgressCaptionLine(
                leading = tripleOf("Օգտագործած"),
                trailing = tripleOf("Սկզբնական"),
            ),
            ProgressCaptionLine(
                leading = tripleOf("548,003,065.00 AMD"),
                trailing = tripleOf("20,000,000.00 AMD"),
            )
        )
    )
}
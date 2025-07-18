package am.acba.compose.components.progressComponents

import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.progressComponents.model.ProgressCaption
import am.acba.compose.components.progressComponents.model.ProgressCaptionLine
import am.acba.compose.components.progressComponents.model.ProgressIndicatorType
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.safeLet
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
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
            ProgressIndicatorType.PRIMARY -> Primary(animatedProgress, topCaption, bottomCaption, trackColor, progressColor)
            ProgressIndicatorType.SECONDARY -> Secondary(animatedProgress, topCaption, bottomCaption, trackColor, progressColor)
        }
    }
}

@Composable
private fun Primary(
    progress: Float,
    topCaptionLine: ProgressCaptionLine?,
    bottomCaptionLine: ProgressCaptionLine?,
    trackColor: Color,
    progressColor: Color,
) {
    ProgressTextRow(
        progressCaptionLine = topCaptionLine,
        defaultStyle = DigitalTheme.typography.body2Bold,
        defaultColor = DigitalTheme.colorScheme.contentPrimary
    )
    VerticalSpacer(8)
    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp),
        color = progressColor,
        trackColor = trackColor,
        drawStopIndicator = {}
    )
    VerticalSpacer(4)
    ProgressTextRow(
        progressCaptionLine = bottomCaptionLine,
        defaultStyle = DigitalTheme.typography.smallRegular,
        defaultColor = DigitalTheme.colorScheme.contentPrimaryTonal1
    )
}

@Composable
private fun Secondary(
    progress: Float,
    topCaptionLine: ProgressCaptionLine?,
    bottomCaptionLine: ProgressCaptionLine?,
    trackColor: Color,
    progressColor: Color,
) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp),
        color = progressColor,
        trackColor = trackColor,
        drawStopIndicator = {}
    )
    VerticalSpacer(4)
    ProgressTextRow(
        progressCaptionLine = topCaptionLine,
        defaultStyle = DigitalTheme.typography.smallRegular,
        defaultColor = DigitalTheme.colorScheme.contentPrimaryTonal1
    )
    ProgressTextRow(
        progressCaptionLine = bottomCaptionLine,
        defaultStyle = DigitalTheme.typography.smallBold,
        defaultColor = DigitalTheme.colorScheme.contentPrimary
    )
}

@Composable
private fun ProgressTextRow(
    progressCaptionLine: ProgressCaptionLine?,
    defaultStyle: TextStyle,
    defaultColor: Color
) {
    val leading = progressCaptionLine?.leading
    val trailing = progressCaptionLine?.trailing

    val hasLeading = !leading?.value.isNullOrEmpty()
    val hasTrailing = !trailing?.value.isNullOrEmpty()

    if (hasLeading || hasTrailing) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            leading?.let {
                safeLet(leading, leading.value) {leading, value ->
                    CaptionText(
                        modifier = Modifier.weight(1f),
                        text = value,
                        style = it.style ?: defaultStyle,
                        color = it.color ?: defaultColor
                    )
                }
            }

            trailing?.let {
                safeLet(leading, trailing.value) {leading, value ->
                    CaptionText(
                        modifier = Modifier.weight(1f),
                        text = value,
                        style = it.style ?: defaultStyle,
                        color = it.color ?: defaultColor
                    )
                }
            }
        }
    }
}

@Composable
@NonRestartableComposable
private fun CaptionText(
    modifier: Modifier,
    text: String,
    color: Color,
    style: TextStyle
) {
    PrimaryText(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
    )
}

@Composable
@PreviewLightDark
fun PrimaryProgressBarPreview() {
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
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
                        leading = ProgressCaption("Օգտագործած"),
                        trailing = ProgressCaption("Սկզբնական"),
                    ),
                    ProgressCaptionLine(
                        leading = ProgressCaption("548,003,065.00 AMD"),
                        trailing = ProgressCaption("20,000,000.00 AMD"),
                    ),
                )
            )
        }
    }
}
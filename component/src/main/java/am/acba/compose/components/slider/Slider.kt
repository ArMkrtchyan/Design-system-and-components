package am.acba.compose.components.slider

import am.acba.component.extensions.dpToPx
import am.acba.compose.HorizontalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimarySlider(
    state: SliderState,
    modifier: Modifier = Modifier,
    startValue: String = "",
    endValue: String = ""
) {
    Column(modifier = modifier) {
        Box {
            LeftAdditionalTrack()
            RightAdditionalTrack()
            Slider(
                state = state,
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .shadow(3.dp, ShapeTokens.shapeRound)
                            .background(DigitalTheme.colorScheme.contentSecondary, ShapeTokens.shapeRound)
                            .border(2.dp, DigitalTheme.colorScheme.backgroundBrand, ShapeTokens.shapeRound)

                    )
                },
                track = {
                    SliderDefaults.Track(
                        modifier = Modifier.height(7.dp),
                        colors = SliderDefaults.colors(
                            activeTrackColor = DigitalTheme.colorScheme.backgroundBrand,
                            inactiveTrackColor = DigitalTheme.colorScheme.backgroundTonal2,
                        ),
                        sliderState = state,
                        drawStopIndicator = {},
                        drawTick = { _, _ -> },
                        thumbTrackGapSize = 0.dp,
                        trackInsideCornerSize = 0.dp
                    )
                },
            )
        }
        BottomTexts(startValue, endValue)
    }
}

@Composable
@NonRestartableComposable
private fun BoxScope.LeftAdditionalTrack() {
    val color = DigitalTheme.colorScheme.backgroundBrand
    val cornerRadius = CornerRadius(100.dpToPx().toFloat(), 100.dpToPx().toFloat())
    val roundRect = RoundRect(left = 11.5.dpToPx().toFloat(), top = 0f, right = 19.dpToPx().toFloat(), bottom = 7.dpToPx().toFloat(), cornerRadius = cornerRadius)
    Canvas(
        modifier = Modifier
            .width(18.dp)
            .height(7.dp)
            .align(Alignment.CenterStart)
    ) {
        val path = Path()
        path.addRoundRect(roundRect)
        clipPath(path, clipOp = ClipOp.Difference) {
            drawRoundRect(SolidColor(color), cornerRadius = cornerRadius)
        }
    }
}

@Composable
@NonRestartableComposable
private fun BoxScope.RightAdditionalTrack() {
    val color = DigitalTheme.colorScheme.backgroundTonal2
    val cornerRadius = CornerRadius(100.dpToPx().toFloat(), 100.dpToPx().toFloat())
    val roundRect = RoundRect(left = -11.5.dpToPx().toFloat(), top = 0f, right = 6.9.dpToPx().toFloat(), bottom = 7.dpToPx().toFloat(), cornerRadius = cornerRadius)
    Canvas(
        modifier = Modifier
            .width(18.dp)
            .height(7.dp)
            .align(Alignment.CenterEnd)
    ) {
        val path = Path()
        path.addRoundRect(roundRect)
        clipPath(path, clipOp = ClipOp.Difference) {
            drawRoundRect(SolidColor(color), cornerRadius = cornerRadius)
        }
    }
}

@Composable
@NonRestartableComposable
private fun BottomTexts(startValue: String, endValue: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        PrimaryText(
            modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            text = startValue,
            style = DigitalTheme.typography.smallRegular,
            color = DigitalTheme.colorScheme.contentPrimaryTonal1
        )
        HorizontalSpacer(8)
        PrimaryText(
            modifier = Modifier.weight(1f),
            text = endValue,
            textAlign = TextAlign.End,
            style = DigitalTheme.typography.smallRegular,
            color = DigitalTheme.colorScheme.contentPrimaryTonal1
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@PreviewLightDark
fun SliderPreview() {
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            val sliderState = remember {
                SliderState(
                    valueRange = 0f..100f,
                    onValueChangeFinished = {},
                    value = 10f,
                    steps = 9
                )
            }
            PrimarySlider(state = sliderState, startValue = "Start", endValue = "End")
        }
    }
}

package am.acba.compose.components.slider

import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.vibrate
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import am.acba.utils.Constants.EMPTY_STRING
import am.acba.utils.Constants.PATTERN_NUMBER_SEPARATOR
import am.acba.utils.annotations.AcbaScheme
import am.acba.utils.extensions.formatWithPattern
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@AcbaScheme(
    value = "Step calculation",
    examples = ["""
        val step = (state.valueRange.endInclusive - state.valueRange.start)/stepCount - 1
    """]
)
fun PrimarySlider(
    state: SliderState,
    modifier: Modifier = Modifier,
    startSuffix: String = EMPTY_STRING,
    endSuffix: String = EMPTY_STRING,
    pattern: String = PATTERN_NUMBER_SEPARATOR,
    isShowSliderValue: Boolean = true,
    minimumFractionDigits: Int = 0,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    onTouch: (TouchComponent, PointerEvent) -> Unit = { _, _ -> },
) {
    val pressed = remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(state.value) {
        if (pressed.value) {
            context.vibrate(40L)
        }
    }
    Column(modifier = modifier) {
        Box {
            LeftAdditionalTrack()
            RightAdditionalTrack()
            Slider(
                enabled = enabled,
                state = state,
                interactionSource = interactionSource,
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .shadow(4.dp, ShapeTokens.shapeRound)
                            .background(
                                DigitalTheme.colorScheme.backgroundTonal1,
                                ShapeTokens.shapeRound
                            )
                            .border(
                                2.dp,
                                DigitalTheme.colorScheme.backgroundBrand,
                                ShapeTokens.shapeRound
                            )
                            .pointerInput(Unit) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val event = awaitPointerEvent()
                                        onTouch.invoke(TouchComponent.THUMB, event)
                                        when (event.type) {
                                            PointerEventType.Press -> pressed.value = true
                                            PointerEventType.Release -> pressed.value = false
                                        }
                                    }
                                }
                            }
                    )
                },
                track = {
                    SliderDefaults.Track(
                        modifier = Modifier
                            .height(7.dp)
                            .pointerInput(Unit) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val event = awaitPointerEvent()
                                        onTouch.invoke(TouchComponent.TRACK, event)
                                        when (event.type) {
                                            PointerEventType.Press -> pressed.value = true
                                            PointerEventType.Release -> pressed.value = false
                                        }
                                    }
                                }
                            },
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
        Row(modifier = Modifier.fillMaxWidth()) {
            val startText = if (isShowSliderValue) state.valueRange.start.formatWithPattern(
                pattern,
                minimumFractionDigits
            ) else EMPTY_STRING
            val endText = if (isShowSliderValue) state.valueRange.endInclusive.formatWithPattern(
                pattern,
                minimumFractionDigits
            ) else EMPTY_STRING
            BottomText("$startText$startSuffix", Modifier.fillMaxWidth(fraction = 0.5f))
            HorizontalSpacer(8.dp)
            BottomText("$endText$endSuffix", Modifier.weight(1f), textAlign = TextAlign.End)
        }
    }
}

@Composable
@NonRestartableComposable
private fun BoxScope.LeftAdditionalTrack() {
    val color = DigitalTheme.colorScheme.backgroundBrand
    val cornerRadius = CornerRadius(100.dpToPx().toFloat(), 100.dpToPx().toFloat())
    val roundRect = RoundRect(
        left = 11.5.dpToPx().toFloat(),
        top = 0f,
        right = 19.dpToPx().toFloat(),
        bottom = 7.dpToPx().toFloat(),
        cornerRadius = cornerRadius
    )
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
    val roundRect = RoundRect(
        left = -11.5.dpToPx().toFloat(),
        top = 0f,
        right = 6.9.dpToPx().toFloat(),
        bottom = 7.dpToPx().toFloat(),
        cornerRadius = cornerRadius
    )
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
private fun BottomText(value: String, modifier: Modifier, textAlign: TextAlign = TextAlign.Start) {
    PrimaryText(
        modifier = modifier,
        text = value,
        textAlign = textAlign,
        style = DigitalTheme.typography.smallRegular,
        color = DigitalTheme.colorScheme.contentPrimaryTonal1
    )
}

enum class TouchComponent {
    THUMB,
    TRACK;
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
                    valueRange = 6f..48f,
                    onValueChangeFinished = {},
                    value = 12f,
                    steps = 1
                )
            }
            PrimarySlider(
                state = sliderState,
                startSuffix = "Months",
                endSuffix = "Months"
            )
        }
    }
}

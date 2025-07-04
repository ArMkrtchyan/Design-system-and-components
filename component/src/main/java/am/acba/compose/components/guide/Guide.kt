package am.acba.compose.components.guide

import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getDisplayHeight
import am.acba.component.extensions.getDisplayWidth
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import java.util.SortedMap

@Composable
fun Guide(
    layoutCoordinates: MutableState<SortedMap<Int, Triple<Float, Float, IntSize>>>, onFinished: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {

        val currentCoordinatePosition = remember { mutableIntStateOf(0) }
        val popUpLayerX = remember { mutableFloatStateOf(0f) }
        val popUpLayerHeight = remember { mutableIntStateOf(0) }
        val popUpLayerWidth = remember { mutableIntStateOf(0) }
        val values = layoutCoordinates.value.values.toList()

        if (values.isNotEmpty()) {
            val sizeTriple = values[currentCoordinatePosition.intValue]
            val left = sizeTriple.first - 8.dpToPx()
            val top = sizeTriple.second - 8.dpToPx()
            val right = left + sizeTriple.third.width.toFloat() + 16.dpToPx()
            val bottom = top + sizeTriple.third.height.toFloat() + 16.dpToPx()
            val animateLeft = animateFloatAsState(
                targetValue = left, label = "animate-left", animationSpec = tween(
                    easing = LinearOutSlowInEasing
                )
            )
            val animateTop = animateFloatAsState(
                targetValue = top, label = "animate-top", animationSpec = tween(
                    easing = LinearOutSlowInEasing
                )
            )
            val animateRight = animateFloatAsState(
                targetValue = right, label = "animate-right", animationSpec = tween(
                    easing = LinearOutSlowInEasing
                )
            )
            val animateBottom = animateFloatAsState(
                targetValue = bottom, label = "animate-bottom", animationSpec = tween(
                    easing = LinearOutSlowInEasing
                )
            )
            if (currentCoordinatePosition.intValue != 0) {
                GuideBackgroundLayer(animateLeft.value, animateTop.value, animateRight.value, animateBottom.value)
            } else {
                GuideBackgroundLayer(left, top, right, bottom)
            }
            val displayWidth = LocalContext.current.getDisplayWidth()
            val displayHeight = LocalContext.current.getDisplayHeight()
            val density = LocalDensity.current
            val centerX = sizeTriple.first + (sizeTriple.third.width.toFloat() / 2)
            val centerY = bottom - sizeTriple.third.height.toFloat() / 2 - 8.dpToPx()
            val offsetY = with(density) {
                if (displayHeight - bottom > popUpLayerHeight.intValue) {
                    animateBottom.value.toDp()
                } else {
                    val animatePopUpBottom = animateFloatAsState(
                        targetValue = bottom - sizeTriple.third.height.toFloat() - 16.dpToPx() - popUpLayerHeight.intValue, label = "animate-pop-up-bottom", animationSpec = tween(
                            easing = LinearOutSlowInEasing
                        )
                    )
                    animatePopUpBottom.value.toDp()
                }
            }

            val firstGuideLine = displayWidth / 3
            val secondGuideLine = (displayWidth / 3) * 2
            val offsetX = with(density) {
                val targetValue = when {
                    centerX <= firstGuideLine -> 0
                    centerX > firstGuideLine && centerX <= secondGuideLine -> (displayWidth / 2) - (popUpLayerWidth.intValue / 2)
                    else -> displayWidth - popUpLayerWidth.intValue
                }
                val animatePopUpX = animateFloatAsState(
                    targetValue = targetValue.toFloat(), label = "animate-pop-up-x", animationSpec = tween(
                        easing = LinearOutSlowInEasing
                    )
                )
                animatePopUpX.value.toDp()
            }
            GuidePopUpLayer(
                modifier = Modifier
                    .offset(y = offsetY, x = offsetX)
                    .onGloballyPositioned { layoutCoordinates ->
                        popUpLayerX.floatValue = layoutCoordinates.positionInWindow().x
                        popUpLayerHeight.intValue = layoutCoordinates.size.height
                        popUpLayerWidth.intValue = layoutCoordinates.size.width
                    },
                currentCoordinatePosition = currentCoordinatePosition,
                isAnchorTop = displayHeight - bottom > popUpLayerHeight.intValue,
                anchorXPosition = with(density) {
                    val animateAnchorX = animateFloatAsState(
                        targetValue = centerX - popUpLayerX.floatValue - 36.dpToPx(), label = "animate-anchor-x", animationSpec = tween(
                            easing = LinearOutSlowInEasing
                        )
                    )
                    animateAnchorX.value.toDp()
                },
                guides = values.map { GuideItem() },
                onFinished = onFinished
            )
        }
    }
}

@Composable
@PreviewLightDark
fun GuidePreview() {
    val coordinates = remember { sortedMapOf<Int, Triple<Float, Float, IntSize>>() }
    val coordinatesState = remember { mutableStateOf(sortedMapOf<Int, Triple<Float, Float, IntSize>>()) }
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .onGloballyPositioned {

                }
                .verticalScroll(rememberScrollState()),
        ) {

            Guide(coordinatesState)
        }
    }
}
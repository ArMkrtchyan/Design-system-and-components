package am.acba.compose.components.guide

import am.acba.component.R
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getDisplayHeight
import am.acba.component.extensions.getDisplayWidth
import am.acba.compose.theme.DigitalTheme
import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.SortedMap

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun Guide(
    layoutCoordinates: MutableState<SortedMap<Int, ElementPositionAndSize>>,
    guides: List<IGuide>,
    completeButtonText: String = stringResource(R.string.ok),
    scrollState: ScrollState? = null,
    onFinished: () -> Unit = {}
) {
    val currentCoordinatePosition = remember { mutableIntStateOf(0) }
    val popUpLayerX = remember { mutableFloatStateOf(0f) }
    val popUpLayerHeight = remember { mutableIntStateOf(0) }
    val popUpLayerWidth = remember { mutableIntStateOf(0) }
    val values = layoutCoordinates.value.values.toList()

    if (values.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            val elementPositionAndSize = values[currentCoordinatePosition.intValue]
            val left = elementPositionAndSize.coordinateX - 8.dpToPx()
            val top = elementPositionAndSize.coordinateY - 8.dpToPx()
            val right = left + elementPositionAndSize.width + 16.dpToPx()
            val bottom = top + elementPositionAndSize.height + 16.dpToPx()
            val centerX = elementPositionAndSize.coordinateX + (elementPositionAndSize.width / 2)
            val displayHeight = LocalContext.current.getDisplayHeight()

            if (scrollState != null && elementPositionAndSize.coordinateY > displayHeight - bottom || elementPositionAndSize.coordinateY < 0) {
                LaunchedEffect(currentCoordinatePosition.intValue) {
                    scrollState?.scrollTo(elementPositionAndSize.scrollYPosition)
                }
            }

            Background(left, top, right, bottom, currentCoordinatePosition)

            GuidePopUpLayer(
                modifier = Modifier
                    .offset(
                        y = calculateOffsetY(bottom, popUpLayerHeight.intValue, elementPositionAndSize.height),
                        x = calculateOffsetX(centerX, popUpLayerWidth.intValue)
                    )
                    .onGloballyPositioned { layoutCoordinates ->
                        popUpLayerX.floatValue = layoutCoordinates.positionInWindow().x
                        popUpLayerHeight.intValue = layoutCoordinates.size.height
                        popUpLayerWidth.intValue = layoutCoordinates.size.width
                    },
                currentCoordinatePosition = currentCoordinatePosition,
                isAnchorTop = isTopAnchor(bottom, popUpLayerHeight.intValue),
                anchorXPosition = calculateAnchorX(centerX, popUpLayerX.floatValue),
                guides = guides,
                completeButtonText = completeButtonText,
                onFinished = onFinished
            )
        }
    }
}

@Composable
private fun Background(left: Float, top: Float, right: Float, bottom: Float, currentCoordinatePosition: MutableIntState) {
    val animateLeft = animateFloatAsState(
        targetValue = left,
        label = "animate-left",
        animationSpec = tween(easing = LinearOutSlowInEasing)
    )
    val animateTop = animateFloatAsState(
        targetValue = top,
        label = "animate-top",
        animationSpec = tween(easing = LinearOutSlowInEasing)
    )
    val animateRight = animateFloatAsState(
        targetValue = right,
        label = "animate-right",
        animationSpec = tween(easing = LinearOutSlowInEasing)
    )
    val animateBottom = animateFloatAsState(
        targetValue = bottom,
        label = "animate-bottom",
        animationSpec = tween(easing = LinearOutSlowInEasing)
    )
    if (currentCoordinatePosition.intValue != 0) {
        GuideBackgroundLayer(animateLeft.value, animateTop.value, animateRight.value, animateBottom.value)
    } else {
        GuideBackgroundLayer(left, top, right, bottom)
    }
}

@Composable
private fun calculateOffsetY(bottom: Float, popUpLayerHeight: Int, elementHeight: Float): Dp {
    val displayHeight = LocalContext.current.getDisplayHeight()
    val density = LocalDensity.current
    return with(density) {
        if (displayHeight - bottom > popUpLayerHeight) {
            val animateBottom = animateFloatAsState(
                targetValue = bottom,
                label = "animate-bottom",
                animationSpec = tween(easing = LinearOutSlowInEasing)
            )
            animateBottom.value.toDp()
        } else {
            val animatePopUpBottom = animateFloatAsState(
                targetValue = bottom - elementHeight - 16.dpToPx() - popUpLayerHeight,
                label = "animate-pop-up-bottom",
                animationSpec = tween(easing = LinearOutSlowInEasing)
            )
            animatePopUpBottom.value.toDp()
        }
    }
}

@Composable
private fun calculateOffsetX(centerX: Float, popUpLayerWidth: Int): Dp {
    val displayWidth = LocalContext.current.getDisplayWidth()
    val density = LocalDensity.current
    val firstGuideLine = displayWidth / 3
    val secondGuideLine = (displayWidth / 3) * 2
    return with(density) {
        val targetValue = when {
            centerX <= firstGuideLine -> 0
            centerX > firstGuideLine && centerX <= secondGuideLine -> (displayWidth / 2) - (popUpLayerWidth / 2)
            else -> displayWidth - popUpLayerWidth
        }
        val animatePopUpX = animateFloatAsState(
            targetValue = targetValue.toFloat(),
            label = "animate-pop-up-x",
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )
        animatePopUpX.value.toDp()
    }
}

@Composable
private fun calculateAnchorX(centerX: Float, popUpLayerX: Float): Dp {
    val density = LocalDensity.current
    return with(density) {
        val animateAnchorX = animateFloatAsState(
            targetValue = centerX - popUpLayerX - 36.dpToPx(),
            label = "animate-anchor-x",
            animationSpec = tween(easing = LinearOutSlowInEasing)
        )
        animateAnchorX.value.toDp()
    }
}

@Composable
private fun isTopAnchor(bottom: Float, popUpLayerHeight: Int): Boolean {
    val displayHeight = LocalContext.current.getDisplayHeight()
    return displayHeight - bottom > popUpLayerHeight
}

@SuppressLint("MutableCollectionMutableState")
@Composable
@PreviewLightDark
fun GuidePreview() {
    val coordinatesState = remember { mutableStateOf(sortedMapOf<Int, ElementPositionAndSize>()) }
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Guide(
                coordinatesState,
                coordinatesState.value.map {
                    GuideItem(
                        title = "Ստացեք դրամական քեշբեք հավելվածի միջոցով կատարված վճարումների համար",
                        description = "Ստացեք դրամական քեշբեք հավելվածի միջոցով կատարված վճարումների համար"
                    )
                })
        }
    }
}
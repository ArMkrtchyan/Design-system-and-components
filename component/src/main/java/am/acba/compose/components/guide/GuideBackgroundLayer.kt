package am.acba.compose.components.guide

import am.acba.component.extensions.dpToPx
import am.acba.compose.components.chips.PrimaryChip
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun GuideBackgroundLayer(animateLeft: Float, animateTop: Float, animateRight: Float, animateBottom: Float) {
    val color = DigitalTheme.colorScheme.overlayBackground
    val cornerRadius = CornerRadius(12.dpToPx().toFloat(), 12.dpToPx().toFloat())
    val roundRect = RoundRect(left = animateLeft, top = animateTop, right = animateRight, bottom = animateBottom, cornerRadius = cornerRadius)
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path()
        path.addRoundRect(roundRect)
        clipPath(path, clipOp = ClipOp.Difference) {
            drawRect(SolidColor(color))
        }
    }
}

@Composable
@PreviewLightDark
fun GuideBackgroundLayerPreview(

) {
    val coordinates = remember { mutableStateOf<LayoutCoordinates?>(null) }
    DigitalTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            PrimaryChip(title = "Chip item", modifier = Modifier
                .align(Alignment.Center)
                .onGloballyPositioned {
                    coordinates.value = it
                })
            coordinates.value?.let { layoutCoordinates ->
                //   GuideBackgroundLayer(Triple(layoutCoordinates.positionInRoot().x, layoutCoordinates.positionInRoot().y, layoutCoordinates.size))
            }
            //  GuideBackgroundLayer(Triple(0f, 0f, IntSize(10, 10)))
        }
    }
}
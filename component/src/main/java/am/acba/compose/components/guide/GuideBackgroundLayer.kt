package am.acba.compose.components.guide

import am.acba.component.extensions.dpToPx
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipPath

@Composable
fun GuideBackgroundLayer(animateLeft: Float, animateTop: Float, animateRight: Float, animateBottom: Float) {
    val color = DigitalTheme.colorScheme.overlayBackground
    val cornerRadius = CornerRadius(12.dpToPx().toFloat(), 12.dpToPx().toFloat())
    val roundRect = RoundRect(left = animateLeft, top = animateTop, right = animateRight, bottom = animateBottom, cornerRadius = cornerRadius)
    Canvas(modifier = Modifier
        .fillMaxSize()
        .clickable { }) {
        val path = Path()
        path.addRoundRect(roundRect)
        clipPath(path, clipOp = ClipOp.Difference) {
            drawRect(SolidColor(color))
        }
    }
}
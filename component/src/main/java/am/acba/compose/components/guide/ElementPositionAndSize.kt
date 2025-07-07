package am.acba.compose.components.guide

import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionOnScreen

data class ElementPositionAndSize(
    val width: Float,
    val height: Float,
    val coordinateX: Float,
    val coordinateY: Float,
    val scrollYPosition: Int
) {
    constructor(
        layoutCoordinates: LayoutCoordinates
    ) : this(
        layoutCoordinates.size.width.toFloat(),
        layoutCoordinates.size.height.toFloat(),
        layoutCoordinates.positionOnScreen().x,
        layoutCoordinates.positionOnScreen().y,
        layoutCoordinates.positionInRoot().y.toInt()
    )
}

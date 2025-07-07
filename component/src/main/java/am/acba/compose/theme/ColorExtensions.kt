package am.acba.compose.theme

import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.ImageView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt


fun Color.toHexString(): String {
    return String.format("#%06X", 0xFFFFFF and toArgb())
}

fun Color.toGraphicColor(): Int {
    val colorHex = this.toHexString()
    return colorHex.toColorInt()
}

fun Color.toGraphicColorStateList(): ColorStateList {
    val colorHex = this.toHexString()
    return ColorStateList.valueOf(colorHex.toColorInt())
}

fun ImageView.paintColor(colorString: String) {
    val paint = Paint()
    val colorFilter = PorterDuffColorFilter(colorString.toColorInt(), PorterDuff.Mode.SRC_ATOP)
    paint.colorFilter = colorFilter
    setLayerPaint(paint)
}
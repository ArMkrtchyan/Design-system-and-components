package am.acba.compose.theme

import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.ImageView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb


fun Color.toHexString(): String {
    return String.format("#%06X", 0xFFFFFF and toArgb())
}

fun Color.toGraphicColor(): Int {
    val colorHex = this.toHexString()
    return android.graphics.Color.parseColor(colorHex)
}

fun Color.toGraphicColorStateList(): ColorStateList {
    val colorHex = this.toHexString()
    return ColorStateList.valueOf(android.graphics.Color.parseColor(colorHex))
}

fun ImageView.paintColor(colorString: String) {
    val paint = Paint()
    val colorFilter = PorterDuffColorFilter(android.graphics.Color.parseColor(colorString), PorterDuff.Mode.SRC_ATOP)
    paint.colorFilter = colorFilter
    setLayerPaint(paint)
}
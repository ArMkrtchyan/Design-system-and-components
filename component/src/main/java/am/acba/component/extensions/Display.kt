package am.acba.component.extensions

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.roundToInt


private val density by lazy { Resources.getSystem().displayMetrics.density }

private val densityDpi by lazy { Resources.getSystem().displayMetrics.densityDpi }

fun Int.dpToPx(): Int {
    return (this * density).roundToInt()
}

fun Double.dpToPx(): Int {
    return (this * density).roundToInt()
}

fun Int.pxToDp(): Float {
    return this / (densityDpi / 160f)
}

fun Float.dpToPx(): Int {
    return (this * density).roundToInt()
}

fun Float.pxToDp(): Float {
    return this / (densityDpi / 160f)
}
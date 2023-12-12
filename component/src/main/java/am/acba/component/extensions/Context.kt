package am.acba.component.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int, typedValue: TypedValue = TypedValue(), resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun Context.getColorStateListFromAttr(
    @AttrRes attrColor: Int, typedValue: TypedValue = TypedValue(), resolveRefs: Boolean = true
): ColorStateList {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return ColorStateList.valueOf(typedValue.data)
}

fun Context.inflater(): LayoutInflater = LayoutInflater.from(this)
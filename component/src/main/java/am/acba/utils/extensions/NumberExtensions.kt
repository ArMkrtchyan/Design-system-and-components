package am.acba.utils.extensions

import am.acba.utils.Constants.PATTERN_NUMBER_SEPARATOR
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Int?.orEmpty() = this ?: 0

fun Long?.orEmpty() = this ?: 0L

fun Double?.orEmpty() = this ?: 0.0

fun Float?.orEmpty() = this ?: 0F

fun Float.formatWithPattern(
    pattern: String = PATTERN_NUMBER_SEPARATOR,
    minimumFractionDigits: Int = 0
): String {
    return try {
        val decimalFormat = DecimalFormat(pattern, DecimalFormatSymbols(Locale.US))
        decimalFormat.minimumFractionDigits = minimumFractionDigits
        decimalFormat.maximumFractionDigits = 2
        decimalFormat.format(this)
    } catch (e: NumberFormatException) {
        e.stackTrace
        ""
    }
}

infix fun Long.toDateStringFrom(format: String): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(Date(this))
}

fun Double.formatWithPattern(
    pattern: String = PATTERN_NUMBER_SEPARATOR,
    minimumFractionDigits: Int = 0
): String {
    return try {
        val decimalFormat = DecimalFormat(pattern, DecimalFormatSymbols(Locale.US))
        decimalFormat.minimumFractionDigits = minimumFractionDigits
        decimalFormat.maximumFractionDigits = 2
        decimalFormat.format(this)
    } catch (e: NumberFormatException) {
        e.stackTrace
        ""
    }
}

fun Double.formatWithPercent(minimumFractionDigits: Int = 0, percent: String = "%"): String {
    val formatter = NumberFormat.getInstance(Locale.ENGLISH)
    formatter.maximumFractionDigits = 2
    formatter.minimumFractionDigits = minimumFractionDigits
    return formatter.format(this) + percent
}
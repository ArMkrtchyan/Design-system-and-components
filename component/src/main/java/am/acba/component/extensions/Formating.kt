package am.acba.component.extensions

import am.acba.utils.Constants.PATTERN_NUMBER_SEPARATOR
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

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
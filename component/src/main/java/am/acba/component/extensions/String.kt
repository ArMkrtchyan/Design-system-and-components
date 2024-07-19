package am.acba.component.extensions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun String.numberDeFormatting(): String {
    val index = this.indexOf(".")
    val numberAfterDot = this.substring(index + 1, this.length).toFloat()
    return if (numberAfterDot.toInt() == 0) {
        this.substring(0, index).replace(",", "")
    } else {
        this.replace(",", "")
    }
}

fun String.numberFormatting(): String {
    return try {
        val value = this.toDoubleOrNull()
        val decimalFormat = DecimalFormat("#,###.00", DecimalFormatSymbols(Locale.US))
        if (value != null) decimalFormat.format(value) else ""
    } catch (e: NumberFormatException) {
        e.stackTrace
        ""
    }
}
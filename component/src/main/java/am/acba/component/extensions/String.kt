package am.acba.component.extensions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun String.numberDeFormatting(): String {
    return if (this.contains(".")) {
        val index = this.indexOf(".")
        val numberAfterDot = if (this.substring(index + 1, this.length).isNotEmpty())
            this.substring(index + 1, this.length).toFloat()
        else 0
        return if (numberAfterDot.toInt() == 0) {
            this.substring(0, index).replace(",", "")
        } else {
            this.replace(",", "")
        }
    } else {
        this.replace(",", "")
    }

}

fun String.numberFormatting(): String {
    return try {
        val value = this.toDoubleOrNull()
        if (value != null && value > 0 && value < 1) return this
        val decimalFormat = DecimalFormat("#,###.00", DecimalFormatSymbols(Locale.US))
        if (value != null && value > 0) decimalFormat.format(value) else ""
    } catch (e: NumberFormatException) {
        e.stackTrace
        ""
    }
}

fun String.numberFormattingWithOutDot(): String {
    return try {
        val value = this.toDoubleOrNull()
        val decimalFormat = DecimalFormat("#,###", DecimalFormatSymbols(Locale.US))
        if (value != null) decimalFormat.format(value) else ""
    } catch (e: NumberFormatException) {
        e.stackTrace
        ""
    }
}
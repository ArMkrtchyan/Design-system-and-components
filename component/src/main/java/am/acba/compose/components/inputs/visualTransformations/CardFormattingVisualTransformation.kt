package am.acba.compose.components.inputs.visualTransformations

import am.acba.component.cardInput.PrimaryCardInput.CardSystemTypes
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CardFormattingVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formattedText = formatCardPattern(text.text)
        val offsetMapping = CardFormattingOffsetMapping(formattedText)
        return TransformedText(
            text = AnnotatedString(formattedText), offsetMapping = offsetMapping
        )
    }

    private fun formatCardPattern(cardNumber: String): String {
        var formatedCardNumber = cardNumber
        val cardSystemTypes = cardNumber.trim().detectCardSystem()
        if (cardSystemTypes == CardSystemTypes.AMEX) {
            if (formatedCardNumber.length > 4) {
                val str: String = formatedCardNumber.substring(0, 4)
                val endStr: String = formatedCardNumber.substring(4)
                formatedCardNumber = "$str $endStr"
            }
            if (formatedCardNumber.length > 11) {
                val str: String = formatedCardNumber.substring(0, 11)
                val endStr: String = formatedCardNumber.substring(11)
                formatedCardNumber = "$str $endStr"
            }
        } else {
            if (formatedCardNumber.length > 4) {
                val str: String = formatedCardNumber.substring(0, 4)
                val endStr: String = formatedCardNumber.substring(4)
                formatedCardNumber = "$str $endStr"
            }
            if (formatedCardNumber.length > 9) {
                val str: String = formatedCardNumber.substring(0, 9)
                val endStr: String = formatedCardNumber.substring(9)
                formatedCardNumber = "$str $endStr"
            }
            if (formatedCardNumber.length > 14) {
                val str: String = formatedCardNumber.substring(0, 14)
                val endStr: String = formatedCardNumber.substring(14)
                formatedCardNumber = "$str $endStr"
            }
        }
        return formatedCardNumber
    }


}

fun String.detectCardSystem(): CardSystemTypes {
    val digitsOnly = this.filter { it.isDigit() }
    return when {
        digitsOnly.startsWith("4") -> CardSystemTypes.VISA
        digitsOnly.take(2).toIntOrNull()?.let { it in 51..55 } == true -> CardSystemTypes.MASTER
        digitsOnly.take(4).toIntOrNull()?.let { it in 2221..2720 } == true -> CardSystemTypes.MASTER
        digitsOnly.startsWith("34") || digitsOnly.startsWith("37") -> CardSystemTypes.AMEX
        digitsOnly.startsWith("9") -> CardSystemTypes.ARCA
        digitsOnly.startsWith("62") -> CardSystemTypes.UNION
        else -> CardSystemTypes.UNKNOWN
    }
}
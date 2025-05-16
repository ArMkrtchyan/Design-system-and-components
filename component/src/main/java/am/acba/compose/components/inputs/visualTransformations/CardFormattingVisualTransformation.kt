package am.acba.compose.components.inputs.visualTransformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CardFormattingVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formattedText = formatCardPattern(text.text)
        val offsetMapping = CardFormattingOffsetMapping(formattedText)
        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }

    private fun formatCardPattern(cardNumber: String): String {
        var formatedCardNumber = cardNumber
        if (formatedCardNumber.startsWith("37")) {
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
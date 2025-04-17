package am.acba.compose.components.inputs.visualTransformations

import am.acba.component.extensions.numberFormatting
import am.acba.component.extensions.numberFormattingWithOutDot
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class AmountFormattingVisualTransformation(
    val maxLength: Int = Integer.MAX_VALUE,
    val formatDecimal: Boolean = true
) :
    VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val validText =
            if (text.text.isNotEmpty() && text.text.length > maxLength) {
                text.text.substring(0, maxLength)
            } else {
                text.text
            }
        val digitsText = validText.filter { it.isDigit() }
        val formattedText = if (formatDecimal) {
            digitsText.numberFormatting()
        } else {
            digitsText.numberFormattingWithOutDot()
        }
        val offsetMapping = if (formatDecimal) DecimalAmountFormattingOffsetMapping(formattedText, text.text)
        else AmountFormattingOffsetMapping(formattedText, text.text)
        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }
}
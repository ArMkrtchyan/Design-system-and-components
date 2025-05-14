package am.acba.compose.components.inputs.visualTransformations

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
        val validText = text.text
        val digitsText = validText.filter { it.isDigit() || it == '.' }
        val formattedText = if (formatDecimal) {
            formatDecimalAmount(digitsText)
        } else {
            digitsText.numberFormattingWithOutDot()
        }
        val offsetMapping = if (formatDecimal) DecimalAmountFormattingOffsetMapping(formattedText)
        else AmountFormattingOffsetMapping(formattedText)
        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }

    private fun formatDecimalAmount(originalText: String): String {
        if (originalText.contains(".")) {
            val stringBuilder = StringBuilder()
            val splitTextArray = originalText.split(".")
            val amountLeftSideTextFormated = splitTextArray[0].numberFormattingWithOutDot()
            stringBuilder.append(amountLeftSideTextFormated).append(".")
            if (splitTextArray.size > 1) {
                stringBuilder.append(splitTextArray[1])
            }
            return stringBuilder.toString()
        } else {
            return originalText.numberFormattingWithOutDot()
        }

    }
}
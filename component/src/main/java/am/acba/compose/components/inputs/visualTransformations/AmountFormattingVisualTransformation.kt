package am.acba.compose.components.inputs.visualTransformations

import am.acba.component.extensions.log
import am.acba.component.extensions.numberFormatting
import am.acba.component.extensions.numberFormattingWithOutDot
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.max

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
        val formattedText = if (formatDecimal) {
            validText.filter { it.isDigit() }
                .numberFormattingWithOutDot()
        } else {
            validText.filter { it.isDigit() }.numberFormatting()
        }
        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = AmountFormattingOffsetMapping(
                formatDecimal,
                formattedText, text.text
            )
        )
    }
}
package am.acba.compose.components.inputs.visualTransformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class MaxLengthVisualTransformation(val maxLength: Int = Integer.MAX_VALUE) :
    VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val validText =
            if (text.text.isNotEmpty() && text.text.length > maxLength) {
                text.text.substring(0, maxLength)
            } else {
                text.text
            }
        return TransformedText(
            text = AnnotatedString(validText),
            offsetMapping = MaxLengthOffsetMapping(maxLength)
        )
    }
}
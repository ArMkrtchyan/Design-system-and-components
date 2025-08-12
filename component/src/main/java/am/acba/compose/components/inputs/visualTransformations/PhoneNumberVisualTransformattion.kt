package am.acba.compose.components.inputs.visualTransformations

import android.content.Context
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation(val context: Context) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return return TransformedText(
            text = AnnotatedString(""), offsetMapping = CardFormattingOffsetMapping("")
        )
    }


}
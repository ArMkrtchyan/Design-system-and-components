package am.acba.compose.components.inputs.visualTransformations

import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.Locale

class PhoneNumberVisualTransformation(
    private val isoCode: String,
    private val dialCode: String,
    private var isValidPhoneNumber: (Boolean) -> Unit
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        if (text.isEmpty()) {
            isValidPhoneNumber.invoke(true)
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }
        val phoneUtil = PhoneNumberUtil.getInstance()
        val formatter = phoneUtil.getAsYouTypeFormatter(isoCode.uppercase(Locale.getDefault()))
        val fullNumber = "$dialCode${text.text}"
        var formatted = ""
        for (ch in fullNumber.toCharArray()) {
            formatted = formatter.inputDigit(ch)
        }
        val displayedText = formatted.removePrefix(dialCode).trimStart()
        try {
            val numberProto = phoneUtil.parse(fullNumber, isoCode)
            isValidPhoneNumber.invoke(phoneUtil.isValidNumber(numberProto))
        } catch (e: NumberParseException) {
            Log.w("Phone TAG", "Invalid or incomplete number: ${e.message}")
        }

        return TransformedText(
            AnnotatedString(displayedText),
            PhoneOffsetMapping(text.text, displayedText)
        )
    }
}

class PhoneOffsetMapping(
    private val original: String,
    private val transformed: String
) : OffsetMapping {


    private val digitMap: List<Pair<Int, Int>> = buildMap()

    private fun buildMap(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        var origIndex = 0
        for ((tIndex, ch) in transformed.withIndex()) {
            if (ch.isDigit()) {
                if (origIndex < original.length) {
                    result.add(origIndex to tIndex)
                    origIndex++
                }
            }
        }
        return result
    }

    override fun originalToTransformed(offset: Int): Int {
        if (digitMap.isEmpty()) return 0
        if (offset <= 0) return 0
        if (offset >= original.length) return transformed.length

        val mapping = digitMap.getOrNull(offset)
        return mapping?.second?.plus(1)?.coerceAtMost(transformed.length) ?: transformed.length
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (digitMap.isEmpty()) return 0
        if (offset <= 0) return 0
        if (offset >= transformed.length) return original.length

        val mapping = digitMap.find { it.second == offset - 1 }
        if (mapping != null) return (mapping.first + 1).coerceAtMost(original.length)

        val nearest = digitMap.lastOrNull { it.second < offset }
        return (nearest?.first?.plus(1))?.coerceAtMost(original.length) ?: 0
    }
}


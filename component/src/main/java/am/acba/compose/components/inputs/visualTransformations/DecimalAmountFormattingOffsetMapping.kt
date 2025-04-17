package am.acba.compose.components.inputs.visualTransformations

import am.acba.component.extensions.log
import androidx.compose.ui.text.input.OffsetMapping

class DecimalAmountFormattingOffsetMapping(
    val formatted: String,
    val originalString: String
) :
    OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        if (offset == 0) return 0
        val transformedOffsets = formatted.log()
            .mapIndexedNotNull { index, c ->
                index
                    .takeIf { !isSeparator(c) }
                    // convert index to an offset
                    ?.plus(1)
            }
            // We want to support an offset of 0 and shift everything to the right,
            // so we prepend that index by default
            .let { offsetList ->
                listOf(0) + offsetList
            }

        return formatted.length
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (offset == 0) return 0
        return originalString.length
    }

    fun isSeparator(char: Char): Boolean {
        return char == ','
    }
}
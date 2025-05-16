package am.acba.compose.components.inputs.visualTransformations

import am.acba.component.extensions.log
import androidx.compose.ui.text.input.OffsetMapping

class CardFormattingOffsetMapping(val formatted: String) : OffsetMapping {
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

        return transformedOffsets[offset]
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (offset == 0) return 0
        return formatted
            // This creates a list of all separator offsets
            .mapIndexedNotNull { index, c ->
                index.takeIf { isSeparator(c) }
            }
            // We want to count how many separators precede the transformed offset
            .count { separatorIndex ->
                separatorIndex < offset
            }
            // We find the original offset by subtracting the number of separators
            .let { separatorCount ->
                offset - separatorCount
            }
    }

    fun isSeparator(char: Char): Boolean {
        return char == ' '
    }
}
package am.acba.compose.components.inputs.visualTransformations

import androidx.compose.ui.text.input.OffsetMapping

class DecimalAmountFormattingOffsetMapping(val formatted: String) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        if (offset == 0) return 0
        val transformedOffsets = formatted
            .mapIndexedNotNull { index, c ->
                index.takeIf { !isSeparator(c) }?.plus(1)
            }
            .let { offsetList -> listOf(0) + offsetList }.toMutableList()
        val difference = formatted.count { isSeparator(it) }
        (1..difference).forEach {
            transformedOffsets.add(transformedOffsets.last() + it)
        }
        return transformedOffsets[offset]
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (offset == 0) return 0
        return formatted.mapIndexedNotNull { index, c ->
            index.takeIf { isSeparator(c) }
        }.count { separatorIndex ->
            separatorIndex < offset
        }.let { separatorCount -> offset - separatorCount }
    }

    fun isSeparator(char: Char): Boolean {
        return char == ','
    }
}
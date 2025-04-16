package am.acba.compose.components.inputs.visualTransformations

import androidx.compose.ui.text.input.OffsetMapping
import kotlin.math.max
import kotlin.math.min

class MaxLengthOffsetMapping(private val maxLength: Int = Integer.MAX_VALUE) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return min(maxLength,offset)
    }

    override fun transformedToOriginal(offset: Int): Int {
        return min(maxLength,offset)
    }
}
package am.acba.component.input

import android.graphics.Rect
import android.text.method.TransformationMethod
import android.view.View

class CustomPasswordTransformationMethod(private val maskChar: Char) : TransformationMethod {
    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return MaskedCharSequence(source)
    }

    override fun onFocusChanged(
        view: View, sourceText: CharSequence, focused: Boolean,
        direction: Int, previouslyFocusedRect: Rect?
    ) {

    }

    private inner class MaskedCharSequence(private val source: CharSequence) : CharSequence {
        override val length: Int
            get() = source.length

        override fun get(index: Int): Char {
            return maskChar
        }

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            return source.subSequence(startIndex, endIndex)
        }
    }
}

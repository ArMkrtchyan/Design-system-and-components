package am.acba.component.input

import am.acba.component.R
import am.acba.component.databinding.WidgetPinInputBinding
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class PinInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, R.attr.pinInputStyle) {
    private val binding by lazy { WidgetPinInputBinding.inflate(context.inflater(), this, false) }


    init {
        context.obtainStyledAttributes(attrs, R.styleable.PinInput).apply {
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            orientation = HORIZONTAL
            addView(binding.root, layoutParams)
            recycle()
            invalidate()
        }
    }
}
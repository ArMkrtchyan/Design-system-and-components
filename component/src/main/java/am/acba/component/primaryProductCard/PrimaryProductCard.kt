package am.acba.component.primaryProductCard

import am.acba.component.R
import am.acba.component.databinding.PrimaryProductCardBinding
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class PrimaryProductCard : FrameLayout {
    private val binding by lazy {
        PrimaryProductCardBinding.inflate(context.inflater(), this, false)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryProductCard).apply {
            addView(binding.root)
            recycle()
        }
    }
}
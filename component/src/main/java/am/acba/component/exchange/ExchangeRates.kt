package am.acba.component.exchange

import am.acba.component.R
import am.acba.component.databinding.WidgetExchangeRatesBinding
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class ExchangeRates @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, R.attr.exchangeRatesStyle) {
    private val binding by lazy { WidgetExchangeRatesBinding.inflate(context.inflater(), this, false) }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ExchangeRates).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            addView(binding.root, layoutParams)
            recycle()
        }
    }
}
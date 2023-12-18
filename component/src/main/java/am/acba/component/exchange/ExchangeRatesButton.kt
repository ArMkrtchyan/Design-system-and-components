package am.acba.component.exchange

import am.acba.component.R
import am.acba.component.databinding.WidgetExchangeRatesBinding
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class ExchangeRatesButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, R.attr.exchangeRatesStyle) {
    private val binding by lazy { WidgetExchangeRatesBinding.inflate(context.inflater(), this, false) }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ExchangeRatesButton).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            addView(binding.root, layoutParams)
            recycle()
            invalidate()
        }
    }

    fun setExchangeRates(triple: Triple<ExchangeRate, ExchangeRate, ExchangeRate>) {
        var rate = triple.first
        binding.flagForFirstRate.setImageResource(rate.flag)
        binding.buyAmountForFirstRate.text = rate.buyAmount
        binding.sellAmountForFirstRate.text = rate.sellAmount

        rate = triple.second
        binding.flagForSecondRate.setImageResource(rate.flag)
        binding.buyAmountForSecondRate.text = rate.buyAmount
        binding.sellAmountForSecondRate.text = rate.sellAmount

        rate = triple.third
        binding.flagForThirdRate.setImageResource(rate.flag)
        binding.buyAmountForThirdRate.text = rate.buyAmount
        binding.sellAmountForThirdRate.text = rate.sellAmount
    }
}
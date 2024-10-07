package am.acba.component.loanComponents

import am.acba.component.R
import am.acba.component.databinding.PrimaryLoanOfferCardBinding
import am.acba.component.extensions.animateHeightFromTo
import am.acba.component.extensions.collapseHeight
import am.acba.component.extensions.collapseWidth
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.expandHeight
import am.acba.component.extensions.expandHeightTo
import am.acba.component.extensions.expandWidth
import am.acba.component.extensions.expandWidthTo
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams

class PrimaryLoanOfferCardView : FrameLayout {
    private val binding by lazy { PrimaryLoanOfferCardBinding.inflate(context.inflater(), this, false) }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryLoanOfferCardView).apply {
            addView(binding.root)
            recycle()
        }
    }

    fun setOpenedOrClosedState(isOpenedState: Boolean) {
        if (!isAnimationPlayed) {
            isAnimationPlayed = true
            if (isOpenedState) openCard()
            else closeCard()
        }
    }

    private val animationDuration = 350L
    private var isAnimationPlayed = false
    private fun openCard() {
        binding.parent.animateHeightFromTo(duration = animationDuration, from = 38.dpToPx().toFloat(), to = 120.dpToPx().toFloat())
        updateTopAndBottomPadding(duration = animationDuration, from = 8.dpToPx(), to = 16.dpToPx())
        binding.loanOfferCardAmountWithCurrencyTop.collapseWidth(duration = animationDuration)
        binding.loanOfferCardEndDate.postDelayed({ binding.loanOfferCardEndDate.isVisible = true }, 150L)
        binding.loanOfferCardAmountWithCurrencyTop.postDelayed({
            binding.loanOfferCardAmountWithCurrencyTop.isVisible = false
            isAnimationPlayed = false
        }, animationDuration - 50L)
        binding.loanOfferCardArrowRight.expandHeightTo(duration = animationDuration, 24.dpToPx())
        binding.loanOfferCardArrowRight.expandWidthTo(duration = animationDuration, 24.dpToPx())
        binding.loanOfferCardEndDate.expandHeight(duration = animationDuration)
        binding.loanOfferCardAmountWithCurrency.expandHeight(duration = animationDuration)
        binding.loanOfferCardAmountWithCurrency.isVisible = true
        binding.loanOfferCardArrowRight.isVisible = true
    }

    private fun closeCard() {
        binding.parent.animateHeightFromTo(duration = animationDuration, from = 120.dpToPx().toFloat(), to = 38.dpToPx().toFloat())
        updateTopAndBottomPadding(duration = animationDuration, from = 16.dpToPx(), to = 8.dpToPx())
        binding.loanOfferCardAmountWithCurrencyTop.expandWidth(duration = animationDuration)
        binding.loanOfferCardArrowRight.collapseHeight(duration = animationDuration)
        binding.loanOfferCardArrowRight.collapseWidth(duration = animationDuration)
        binding.loanOfferCardEndDate.collapseHeight(duration = animationDuration)
        binding.loanOfferCardAmountWithCurrency.collapseHeight(duration = animationDuration)
        binding.loanOfferCardAmountWithCurrencyTop.postDelayed({
            binding.loanOfferCardAmountWithCurrency.isVisible = false
            binding.loanOfferCardEndDate.isVisible = false
            binding.loanOfferCardArrowRight.isVisible = false
            isAnimationPlayed = false
        }, animationDuration)
        binding.loanOfferCardAmountWithCurrencyTop.postDelayed({ binding.loanOfferCardAmountWithCurrencyTop.isVisible = true }, 50L)
    }

    private fun updateTopAndBottomPadding(duration: Long = 0, from: Int, to: Int) {
        val heightAnimator = ValueAnimator.ofInt(from, to)
        heightAnimator.addUpdateListener { animation ->
            val padding = animation.getAnimatedValue().toString().toInt()
            binding.parent.setPadding(0, padding, 0, padding)
            binding.loanOfferCardBadge.updateLayoutParams<MarginLayoutParams> {
                marginEnd = padding
            }
            binding.loanOfferCardAmountWithCurrencyTop.updateLayoutParams<MarginLayoutParams> {
                marginEnd = padding
            }
        }
        heightAnimator.duration = duration
        heightAnimator.start()
    }

    @SuppressLint("SetTextI18n")
    fun setLoanCard(loanCard: LoanOfferCard) {
        binding.loanOfferCardTitle.text = loanCard.title
        binding.loanOfferCardAmountWithCurrency.text = loanCard.amount + " " + loanCard.currency
        binding.loanOfferCardAmountWithCurrencyTop.text = loanCard.amount + " " + loanCard.currency
        binding.loanOfferCardEndDate.text = loanCard.endDate
        binding.loanOfferCardBadge.isVisible = loanCard.isNewBadgeVisible
        binding.parent.backgroundTintList = context.getColorStateListFromAttr(loanCard.backgroundColorAttr)
    }

    fun setState(isOpenedState: Boolean) {
        binding.parent.updateLayoutParams<LayoutParams> {
            height = if (isOpenedState) 120.dpToPx() else 38.dpToPx()
        }
        binding.parent.setPadding(0, if (isOpenedState) 16.dpToPx() else 8.dpToPx(), 0, if (isOpenedState) 16.dpToPx() else 8.dpToPx())
        binding.loanOfferCardAmountWithCurrencyTop.isVisible = !isOpenedState
        binding.loanOfferCardAmountWithCurrency.isVisible = isOpenedState
        binding.loanOfferCardEndDate.isVisible = isOpenedState
        binding.loanOfferCardArrowRight.isVisible = isOpenedState
    }
}
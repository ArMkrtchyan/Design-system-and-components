package am.acba.component.loanComponents

import am.acba.component.R
import am.acba.component.databinding.PrimaryLoanOfferCardBinding
import am.acba.component.extensions.animateAlpha
import am.acba.component.extensions.animateHeightFromTo
import am.acba.component.extensions.collapseWidth
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.expandWidth
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams

class PrimaryOfferCardView : FrameLayout {
    private val binding by lazy { PrimaryLoanOfferCardBinding.inflate(context.inflater(), this, false) }
    private val animationDuration = 350L
    private var isAnimationPlayed = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryOfferCardView).apply {
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

    private fun openCard() {
        binding.parent.animateHeightFromTo(duration = animationDuration, from = 38.dpToPx().toFloat(), to = 120.dpToPx().toFloat())
        updateTopAndBottomPadding(duration = animationDuration, from = 8.dpToPx(), to = 16.dpToPx())
        binding.loanOfferCardAmountWithCurrencyTop.collapseWidth(duration = animationDuration)
        binding.loanOfferCardEndDate.postDelayed({ binding.loanOfferCardEndDate.isVisible = true }, 150L)
        binding.loanOfferCardAmountWithCurrencyTop.postDelayed({
            binding.loanOfferCardAmountWithCurrencyTop.isVisible = false
            isAnimationPlayed = false
        }, animationDuration - 50L)
        binding.loanOfferCardArrowRight.animateAlpha(1f, duration = animationDuration)
        binding.loanOfferCardEndDate.animateAlpha(1f, duration = animationDuration)
        binding.loanOfferCardAmountWithCurrency.animateAlpha(1f, duration = animationDuration)
        binding.loanOfferCardAmountWithCurrency.isVisible = true
        binding.loanOfferCardArrowRight.isVisible = true
    }

    private fun closeCard() {
        binding.parent.animateHeightFromTo(duration = animationDuration, from = 120.dpToPx().toFloat(), to = 38.dpToPx().toFloat())
        updateTopAndBottomPadding(duration = animationDuration, from = 16.dpToPx(), to = 8.dpToPx())
        binding.loanOfferCardAmountWithCurrencyTop.expandWidth(duration = animationDuration)
        binding.loanOfferCardArrowRight.animateAlpha(0f, duration = animationDuration)
        binding.loanOfferCardEndDate.animateAlpha(0f, duration = animationDuration)
        binding.loanOfferCardAmountWithCurrency.animateAlpha(0f, duration = animationDuration)
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
    fun setLoanCard(loanCard: IOfferCard) {
        binding.loanOfferCardTitle.text = loanCard.getTitle()
        binding.loanOfferCardAmountWithCurrency.text = loanCard.getOffer()
        binding.loanOfferCardAmountWithCurrencyTop.text = loanCard.getOffer()
        binding.loanOfferCardEndDate.text = loanCard.getDescription()
        binding.loanOfferCardBadge.isVisible = loanCard.getBadgeVisibility()
        binding.loanOfferCardBadge.setBadgeText(loanCard.getBadgeText())
        binding.loanOfferCardBadge.setBadgeBackgroundTint(context.getColorStateListFromAttr(loanCard.getBadgeBackgroundColorAttr()))
        binding.parent.backgroundTintList = context.getColorStateListFromAttr(loanCard.getCardBackgroundColorAttr())
        ConstraintSet().apply {
            clone(binding.parent)
            clear(binding.loanOfferCardTitle.id, ConstraintSet.END)
            if (loanCard.getBadgeVisibility()) {
                connect(binding.loanOfferCardTitle.id, ConstraintSet.END, binding.loanOfferCardBadge.id, ConstraintSet.START, 8.dpToPx())
            } else {
                connect(binding.loanOfferCardTitle.id, ConstraintSet.END, binding.loanOfferCardArrowRight.id, ConstraintSet.START, 8.dpToPx())
            }
            applyTo(binding.parent)
        }

    }

    fun setState(isOpenedState: Boolean) {
        if (!isAnimationPlayed) {
            binding.parent.updateLayoutParams<LayoutParams> {
                height = if (isOpenedState) 120.dpToPx() else 38.dpToPx()
            }
            binding.parent.setPadding(0, if (isOpenedState) 16.dpToPx() else 8.dpToPx(), 0, if (isOpenedState) 16.dpToPx() else 8.dpToPx())
            binding.loanOfferCardBadge.updateLayoutParams<MarginLayoutParams> {
                marginEnd = if (isOpenedState) 16.dpToPx() else 8.dpToPx()
            }
            binding.loanOfferCardAmountWithCurrencyTop.isVisible = !isOpenedState
            binding.loanOfferCardAmountWithCurrency.isVisible = isOpenedState
            binding.loanOfferCardEndDate.isVisible = isOpenedState
            binding.loanOfferCardArrowRight.isVisible = isOpenedState
            val alpha = if (isOpenedState) 1f else 0f
            binding.loanOfferCardAmountWithCurrency.alpha = alpha
            binding.loanOfferCardEndDate.alpha = alpha
            binding.loanOfferCardArrowRight.alpha = alpha
        }
    }
}
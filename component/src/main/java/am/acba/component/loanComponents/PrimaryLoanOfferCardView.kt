package am.acba.component.loanComponents

import am.acba.component.R
import am.acba.component.databinding.PrimaryLoanOfferCardBinding
import am.acba.component.extensions.animateAlpha
import am.acba.component.extensions.animateTextSize
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager

class PrimaryLoanOfferCardView : FrameLayout {
    private val binding by lazy { PrimaryLoanOfferCardBinding.inflate(context.inflater(), this, false) }
    private var isOpenedState = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryLoanOfferCardView).apply {
            binding.parent.setOnClickListener {
                if (isOpenedState) setClosedState()
                else setOpenedState()
                isOpenedState = !isOpenedState
            }
            addView(binding.root)
            recycle()
        }
    }

    private fun setOpenedState() {
        val constrainedSet = ConstraintSet()
        constrainedSet.clone(binding.parent)
        val openedPadding = 16.dpToPx().toInt()
        binding.parent.setPadding(openedPadding, openedPadding, openedPadding, openedPadding)
        setOpenedStateConnections(constrainedSet)
        applyWithAnimation(constrainedSet)
    }

    private fun setClosedState() {
        val constrainedSet = ConstraintSet()
        constrainedSet.clone(binding.parent)
        val closedPadding = 8.dpToPx().toInt()
        binding.parent.setPadding(closedPadding, closedPadding, closedPadding, closedPadding)
        setClosedStateConnections(constrainedSet)
        applyWithAnimation(constrainedSet)
    }

    private fun applyWithAnimation(constrainedSet: ConstraintSet) {
        val transition = AutoTransition().apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 250L
        }
        TransitionManager.beginDelayedTransition(binding.parent, transition)
        constrainedSet.applyTo(binding.parent)
    }

    private fun setOpenedStateConnections(constrainedSet: ConstraintSet) {
        constrainedSet.connect(R.id.loan_offer_card_title, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        constrainedSet.connect(R.id.loan_offer_card_title, ConstraintSet.END, R.id.loan_offer_card_arrow_right, ConstraintSet.START, 10.dpToPx())
        constrainedSet.connect(R.id.loan_offer_card_title, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
        constrainedSet.clear(R.id.loan_offer_card_title, ConstraintSet.BOTTOM)

        constrainedSet.connect(R.id.loan_offer_card_amount_with_currency, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        constrainedSet.connect(R.id.loan_offer_card_amount_with_currency, ConstraintSet.TOP, R.id.loan_offer_card_title, ConstraintSet.BOTTOM, 4.dpToPx())
        constrainedSet.clear(R.id.loan_offer_card_amount_with_currency, ConstraintSet.BOTTOM)

        constrainedSet.connect(R.id.loan_offer_card_badge, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
        constrainedSet.connect(R.id.loan_offer_card_badge, ConstraintSet.TOP, R.id.loan_offer_card_amount_with_currency, ConstraintSet.BOTTOM, 8.dpToPx())

        binding.loanOfferCardEndDate.animateAlpha(1f)
        binding.loanOfferCardArrowRight.animateAlpha(1f)
        binding.loanOfferCardAmountWithCurrency.animateTextSize(12f, 20f)
    }

    private fun setClosedStateConnections(constrainedSet: ConstraintSet) {
        constrainedSet.connect(R.id.loan_offer_card_title, ConstraintSet.START, R.id.loan_offer_card_amount_with_currency, ConstraintSet.END, 10.dpToPx())
        constrainedSet.connect(R.id.loan_offer_card_title, ConstraintSet.TOP, R.id.loan_offer_card_amount_with_currency, ConstraintSet.TOP, 0)
        constrainedSet.connect(R.id.loan_offer_card_title, ConstraintSet.BOTTOM, R.id.loan_offer_card_amount_with_currency, ConstraintSet.BOTTOM, 0)
        constrainedSet.connect(R.id.loan_offer_card_title, ConstraintSet.END, R.id.loan_offer_card_badge, ConstraintSet.START, 0)

        constrainedSet.connect(R.id.loan_offer_card_amount_with_currency, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        constrainedSet.connect(R.id.loan_offer_card_amount_with_currency, ConstraintSet.TOP, R.id.loan_offer_card_badge, ConstraintSet.TOP, 0)
        constrainedSet.connect(R.id.loan_offer_card_amount_with_currency, ConstraintSet.BOTTOM, R.id.loan_offer_card_badge, ConstraintSet.BOTTOM, 0)

        constrainedSet.connect(R.id.loan_offer_card_badge, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
        constrainedSet.connect(R.id.loan_offer_card_badge, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)

        binding.loanOfferCardAmountWithCurrency.animateTextSize(20f, 12f)
        binding.loanOfferCardEndDate.animateAlpha(0f)
        binding.loanOfferCardArrowRight.animateAlpha(0f)
    }
}
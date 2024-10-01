package am.acba.component.loanComponents

import am.acba.component.R
import am.acba.component.databinding.PrimaryLoanCardBinding
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible

class PrimaryLoanCardView : FrameLayout {
    private val binding by lazy { PrimaryLoanCardBinding.inflate(context.inflater(), this, false) }
    private val adapter by lazy { LoanCardAdditionalInfoAdapter() }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryLoanCardView).apply {

            addView(binding.root)
            recycle()
        }
    }

    fun setLoanCard(loanCard: LoanCard) {
        binding.paymentDayContainer.isVisible = loanCard.nextPaymentDay.isNotEmpty()
        binding.paymentAmountContainer.isVisible = loanCard.nextPaymentAmount.isNotEmpty()
        binding.title.text = loanCard.title
        binding.description.text = loanCard.description
        binding.paymentDay.text = loanCard.nextPaymentDay
        binding.paymentAmount.text = loanCard.nextPaymentAmount
        if (loanCard.loanAdditionalInfo.isNotEmpty()) {
            binding.additionalInformationRecycler.adapter = adapter
            adapter.submitList(loanCard.loanAdditionalInfo)
        }
    }
}

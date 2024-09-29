package am.acba.component.loanComponents

import am.acba.component.R
import am.acba.component.databinding.PrimaryLoanOfferCardBinding
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class PrimaryLoanOfferCardView : FrameLayout {
    private val binding by lazy { PrimaryLoanOfferCardBinding.inflate(context.inflater(), this, false) }
    private var isOpenedState = false

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

            }
            addView(binding.root)
            recycle()
        }
    }

    fun setOpenedOrClosedState() {
        if (isOpenedState) binding.parent.transitionToState(R.id.start)
        else binding.parent.transitionToState(R.id.end)
        isOpenedState = !isOpenedState
    }
}
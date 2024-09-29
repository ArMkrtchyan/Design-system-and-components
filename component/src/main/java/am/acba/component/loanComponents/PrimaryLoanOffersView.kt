package am.acba.component.loanComponents

import am.acba.component.R
import am.acba.component.databinding.PrimaryLoanOffersBinding
import am.acba.component.extensions.animateRotation
import am.acba.component.extensions.collapseHeight
import am.acba.component.extensions.expandHeight
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible

class PrimaryLoanOffersView : FrameLayout {
    private val binding by lazy { PrimaryLoanOffersBinding.inflate(context.inflater(), this, false) }
    private var isExpanded = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryLoanOffersView).apply {
            binding.headerContainer.setOnClickListener {
                binding.offerCard.setOpenedOrClosedState()
                if (isExpanded) {
                    binding.seeAll.collapseHeight()
                    binding.arrow.animateRotation(0f, duration = 250)
                } else {
                    binding.seeAll.expandHeight()
                    binding.seeAll.isVisible = true
                    binding.arrow.animateRotation(180f, duration = 250)
                }
                isExpanded = !isExpanded
            }
            addView(binding.root)
            recycle()
        }
    }

}
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
                if (isOpenedState) setClosedState()
                else setOpenedState()
                isOpenedState = !isOpenedState
            }
            addView(binding.root)
            recycle()
        }
    }

    private fun setOpenedState() {
        binding.parent.setTransition(R.id.start)
    }

    private fun setClosedState() {
        binding.parent.setTransition(R.id.end)
    }

}
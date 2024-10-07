package am.acba.component.productCard

import am.acba.component.R
import am.acba.component.databinding.PrimaryLoanCardBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class PrimaryProductCardView : FrameLayout {
    private val binding by lazy { PrimaryLoanCardBinding.inflate(context.inflater(), this, false) }
    private val adapter by lazy { ProductCardAdditionalInfoAdapter() }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryProductCardView).apply {

            addView(binding.root)
            recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.productBadge.viewTreeObserver.addOnGlobalLayoutListener {
            binding.productBadge.measure(
                MeasureSpec.makeMeasureSpec(this.rootView.width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(this.rootView.height, MeasureSpec.AT_MOST)
            )
            binding.productCardBadgeText.maxWidth = binding.productBadge.measuredWidth - 50.dpToPx()
        }
    }

    fun setProductCard(productCard: IProductCard) {
        binding.paymentDayContainer.isVisible = productCard.getNextPaymentDay().isNotEmpty()
        binding.paymentAmountContainer.isVisible = productCard.getNextPaymentAmount().isNotEmpty()
        binding.bottomDivider.isVisible =
            (productCard.getNextPaymentAmount().isNotEmpty() || productCard.getNextPaymentDay().isNotEmpty()) && productCard.getCardAdditionalInfo()
                .isNotEmpty()
        binding.description.isVisible = productCard.getDescription().isNotEmpty()
        binding.title.text = productCard.getTitle()
        binding.description.text = productCard.getDescription()
        binding.paymentDay.text = productCard.getNextPaymentDay()
        binding.paymentAmount.text = productCard.getNextPaymentAmount()
        if (productCard.getCardAdditionalInfo().isNotEmpty()) {
            binding.additionalInformationRecycler.adapter = adapter
            adapter.submitList(productCard.getCardAdditionalInfo())
        }
        binding.startIconContainer.isVisible = productCard.getStartIcon() > 0
        if (productCard.getStartIcon() > 0) {
            binding.startIcon.setImageResource(productCard.getStartIcon())
            if (productCard.getBackgroundColorAttr() > 0) {
                binding.startIconContainer.backgroundTintList = context.getColorStateListFromAttr(productCard.getBackgroundColorAttr())
            } else {
                binding.startIconContainer.backgroundTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
            }
        }
        ConstraintSet().apply {
            clone(binding.headerLayout)
            if (productCard.getStartIcon() > 0) {
                connect(binding.titleDescriptionContainer.id, ConstraintSet.BOTTOM, binding.startIconContainer.id, ConstraintSet.BOTTOM)
            } else {
                clear(binding.titleDescriptionContainer.id, ConstraintSet.BOTTOM)
            }
            applyTo(binding.headerLayout)
        }
        binding.productCardBadgeContainer.isVisible = productCard.getBadgeText().isNotEmpty()
        if (productCard.getBadgeText().isNotEmpty()) {
            binding.productCardBadgeText.text = productCard.getBadgeText()
            binding.productCardBadgeIcon.setImageResource(productCard.getBadgeIcon())
            binding.productCardBadgeText.setTextColor(context.getColorStateListFromAttr(productCard.getBadgeTextAndIconColorAttr()))
            binding.productCardBadgeIcon.imageTintList = context.getColorStateListFromAttr(productCard.getBadgeTextAndIconColorAttr())
            binding.productBadge.setBackgroundColor(context.getColorFromAttr(productCard.getBadgeBackgroundColorAttr()))
        }
    }
}

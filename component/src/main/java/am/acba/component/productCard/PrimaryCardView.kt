package am.acba.component.productCard

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import am.acba.component.databinding.PrimaryLoanCardBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.extensions.load
import am.acba.component.imageView.PrimaryImageView
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding

class PrimaryCardView : FrameLayout {
    private val binding by lazy { PrimaryLoanCardBinding.inflate(context.inflater(), this, false) }
    private val adapter by lazy { CardAdditionalInfoAdapter() }
    private var mIsPreventDoubleClick = true
    var iconCornerRadius = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryCardView).apply {
            mIsPreventDoubleClick = getBoolean(R.styleable.PrimaryCardView_isPreventClick, true)
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

    fun setCardInfo(productCard: ICardInfo) {
        setProductCardTitle(productCard.getTitle())
        setProductCardDescription(productCard.getDescription())
        setProductCardPaymentDay(productCard.getNextPaymentDay())
        setProductCardPaymentAmount(productCard.getNextPaymentAmount())
        setProductCardPaymentDayTitle(productCard.getNextPaymentDayTitle())
        setProductCardPaymentAmountTitle(productCard.getNextPaymentAmountTitle())
        setProductCardStartIcon(productCard)
        submitAdditionalInfo(productCard.getCardAdditionalInfo())
        setProductCardBadge(productCard)
        updateBottomDividerVisibility(productCard)
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        if (mIsPreventDoubleClick) binding.foregroundView.setOnClickListener(PreventDoubleClickListener(onClickListener))
        else binding.foregroundView.setOnClickListener(onClickListener)
    }

    fun startIconBackgroundContainer(): FrameLayout {
        return binding.startIconContainer
    }

    fun startIcon(): PrimaryImageView {
        return binding.startIcon
    }

    private fun setProductCardTitle(title: String) {
        binding.title.text = title
    }

    private fun setProductCardDescription(description: String) {
        binding.description.isVisible = description.isNotEmpty()
        binding.description.text = description
    }

    private fun setProductCardPaymentDayTitle(paymentDayTitle: String) {
        binding.paymentDayTitle.text = paymentDayTitle
    }

    private fun setProductCardPaymentAmountTitle(paymentAmountTitle: String) {
        binding.paymentAmountTitle.text = paymentAmountTitle
    }

    private fun setProductCardPaymentDay(paymentDay: String) {
        binding.paymentDayContainer.isVisible = paymentDay.isNotEmpty()
        binding.paymentDay.text = paymentDay
    }

    private fun setProductCardPaymentAmount(paymentAmount: String) {
        binding.paymentAmountContainer.isVisible = paymentAmount.isNotEmpty()
        binding.paymentAmount.text = paymentAmount
    }

    private fun setProductCardStartIcon(productCard: ICardInfo) {
        binding.startIconContainer.isVisible = productCard.getStartIcon() > 0 || productCard.getStartIconUrl().isNotEmpty()
        if (productCard.getStartIconUrl().isNotEmpty()) {
            binding.startIcon.load(productCard.getStartIconUrl(), binding.shimmerLayout, cornerRadius = iconCornerRadius)
            binding.startIconContainer.background = null
            binding.startIcon.setPadding(0)
            binding.startIcon.imageTintList = null
        } else if (productCard.getStartIcon() > 0) {
            binding.startIcon.setImageResource(productCard.getStartIcon())
            if (productCard.getBackgroundColorAttr() > 0) {
                binding.startIconContainer.setCardBackgroundColor(context.getColorStateListFromAttr(productCard.getBackgroundColorAttr()))
            } else {
                binding.startIconContainer.setCardBackgroundColor(ContextCompat.getColorStateList(context, android.R.color.transparent))
            }
            binding.startIcon.imageTintList = if (productCard.getStartIconTint() != null) {
                context.getColorStateListFromAttr(productCard.getStartIconTint()!!)
            } else null
            binding.startIconContainer.background = ContextCompat.getDrawable(context, R.drawable.background_radius_8)
            binding.startIcon.setPadding(8.dpToPx(), 8.dpToPx(), 8.dpToPx(), 8.dpToPx())
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
    }

    private fun setProductCardBadge(productCard: ICardInfo) {
        binding.productCardBadgeContainer.isVisible = productCard.getBadgeText().isNotEmpty()
        if (productCard.getBadgeText().isNotEmpty()) {
            binding.productCardBadgeText.text = productCard.getBadgeText()
            binding.productCardBadgeIcon.setImageResource(productCard.getBadgeIcon())
            binding.productCardBadgeText.setTextColor(context.getColorStateListFromAttr(productCard.getBadgeTextAndIconColorAttr()))
            binding.productCardBadgeIcon.imageTintList = context.getColorStateListFromAttr(productCard.getBadgeTextAndIconColorAttr())
            binding.productBadge.setBackgroundColor(context.getColorFromAttr(productCard.getBadgeBackgroundColorAttr()))
        }
    }

    private fun updateBottomDividerVisibility(productCard: ICardInfo) {
        binding.bottomDivider.isVisible =
            (productCard.getNextPaymentAmount().isNotEmpty() || productCard.getNextPaymentDay().isNotEmpty()) && productCard.getCardAdditionalInfo()
                .isNotEmpty()
    }

    private fun submitAdditionalInfo(cardAdditionalInfo: List<ICardAdditionalInfo>) {
        if (cardAdditionalInfo.isNotEmpty()) {
            binding.additionalInformationRecycler.adapter = adapter
            adapter.submitList(cardAdditionalInfo)
        }
    }
}

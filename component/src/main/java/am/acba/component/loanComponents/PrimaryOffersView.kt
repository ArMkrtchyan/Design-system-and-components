package am.acba.component.loanComponents

import am.acba.component.R
import am.acba.component.databinding.PrimaryLoanOffersBinding
import am.acba.component.extensions.animateXRotation
import am.acba.component.extensions.collapseHeight
import am.acba.component.extensions.expandHeight
import am.acba.component.extensions.inflater
import android.content.Context
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.PagerSnapHelper

class PrimaryOffersView : FrameLayout {
    private val binding by lazy { PrimaryLoanOffersBinding.inflate(context.inflater(), this, false) }
    private var isExpanded = false
    private var adapter = OfferCardAdapter(isExpanded)
    private var isAnimationPlayed = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryOffersView).apply {
            val title = getString(R.styleable.PrimaryOffersView_offersTitle)
            val seeAllTitle = getString(R.styleable.PrimaryOffersView_offersSeeAllTitle)
            isExpanded = getBoolean(R.styleable.PrimaryOffersView_offersExpanded, false)
            setOffersTitle(title)
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(binding.offersRecycler)
            binding.offersRecycler.adapter = adapter
            setSeeAllText(seeAllTitle)
            binding.headerContainer.setOnClickListener {
                if (!isAnimationPlayed) {
                    isAnimationPlayed = true
                    updateOffersState()
                }
            }
            setOfferInitialState()
            addView(binding.root)
            recycle()
        }
    }

    private fun updateOffersState() {
        adapter.updateCardState()
        if (isExpanded) {
            if (!binding.seeAll.text.isNullOrEmpty()) binding.seeAll.collapseHeight()
            binding.arrow.animateXRotation(0f, duration = 350, endAction = { isAnimationPlayed = false })
        } else {
            if (!binding.seeAll.text.isNullOrEmpty()) {
                binding.seeAll.expandHeight()
                binding.seeAll.isVisible = true
            }
            binding.arrow.animateXRotation(180f, duration = 350, endAction = { isAnimationPlayed = false })
        }
        isExpanded = !isExpanded
    }

    private fun setOfferInitialState() {
        if (isExpanded) {
            if (!binding.seeAll.text.isNullOrEmpty()) binding.seeAll.collapseHeight(0)
            binding.arrow.animateXRotation(180f, duration = 0)
        } else {
            if (!binding.seeAll.text.isNullOrEmpty()) {
                binding.seeAll.expandHeight(0)
                binding.seeAll.isVisible = true
            }
            binding.arrow.animateXRotation(0f, duration = 0)
        }
    }

    fun setOffersTitle(title: String?) {
        binding.title.text = title
    }

    fun setOnOfferClick(onItemClick: (IOfferCard) -> Unit) {
        adapter.setOnOfferClick(onItemClick)
    }

    fun setOnSeeAllOfferClick(onItemClick: () -> Unit) {
        binding.seeAll.setOnClickListener { onItemClick.invoke() }
    }

    fun setNewBadgeCount(count: Int, countPrefix: String = "+") {
        binding.loanOfferCardBadge.isVisible = count > 1
        binding.loanOfferCardBadge.setBadgeText("${countPrefix}${count}")
    }

    fun setSeeAllText(text: String?) {
        if (!text.isNullOrEmpty()) {
            val mSpannableString = SpannableString(text)
            mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
            binding.seeAll.text = mSpannableString
        }
        binding.seeAll.isVisible = !text.isNullOrEmpty()
    }

    fun submitLoanOffers(list: List<IOfferCard>, countPrefix: String = "+") {
        adapter.submitList(list.toMutableList())
        setNewBadgeCount(list.count { it.getBadgeVisibility() }, countPrefix)
    }
}
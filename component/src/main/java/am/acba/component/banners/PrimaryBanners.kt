package am.acba.component.banners

import am.acba.component.R
import am.acba.component.databinding.LayoutBannersBinding
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.PagerSnapHelper

class PrimaryBanners : FrameLayout {
    private val binding by lazy { LayoutBannersBinding.inflate(context.inflater(), this, false) }
    private val adapter by lazy { BannersAdapter() }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryBanners).apply {
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(binding.offersRecycler)
            binding.offersRecycler.adapter = adapter
            addView(binding.root)
            recycle()
        }
    }

    fun setOnBannerClick(onItemClick: (IBanner) -> Unit) {
        adapter.setOnOfferClick(onItemClick)
    }

    fun setOnBannerCloseClick(onOfferCloseClick: (IBanner) -> Unit) {
        adapter.setOnCloseOfferClick(onOfferCloseClick)
    }

    fun submitBanners(list: List<IBanner>) {
        adapter.submitList(list)
    }
}
package am.acba.component.alert

import am.acba.component.databinding.AlertPaginationLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class PrimaryAlertPagination : FrameLayout {

    private val binding by lazy {
        AlertPaginationLayoutBinding.inflate(
            context.inflater(),
            this,
            false
        )
    }
    private val adapter by lazy { AlertPaginationAdapter(onCloseClick) }

    private val onCloseClick: (Int) -> Unit = { position: Int ->
        adapter.removeItemAt(position)
        binding.showAlerts(adapter.itemCount)

        if (adapter.itemCount == 1) binding.viewPager.setHorizontalPadding(0)
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            //binding.piv.selection = position
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
            addView(binding.root)
            binding.initViewPager()

            invalidate()
    }

    private fun AlertPaginationLayoutBinding.initViewPager() {
        viewPager.adapter = adapter
        viewPager.setHorizontalPadding(12.dpToPx())
    }

    private fun AlertPaginationLayoutBinding.showAlerts(
        itemSize: Int,
        registerCallback: (() -> Unit)? = null
    ) {
        viewPager.isVisible = itemSize > 0

        //if (itemSize > 1) initPiv(itemSize, registerCallback) else piv.isVisible = false
    }

    private fun AlertPaginationLayoutBinding.initPiv(
        itemSize: Int,
        registerCallback: (() -> Unit)?
    ) {
        /*piv.isVisible = true
        piv.count = itemSize*/
        registerCallback?.invoke()
    }

    private fun pageChangeCallback() {
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)
    }

    private fun ViewPager2.setHorizontalPadding(padding: Int) {
        (getChildAt(0) as RecyclerView).apply {
            setPadding(padding, 0, padding, 0)
            clipToPadding = false
        }
    }

    fun submitList(items: List<Alert>) {
        adapter.submitList(items.toMutableList())
        binding.showAlerts(items.size) {
            pageChangeCallback()
        }
    }
}
package am.acba.component.table

import am.acba.component.R
import am.acba.component.databinding.TableLayoutBinding
import am.acba.component.extensions.inflater
import am.acba.component.imageView.PrimaryImageView
import am.acba.component.listItem.PrimaryListItem.ListStartComponentType
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

class PrimaryTable : FrameLayout {

    private val binding by lazy { TableLayoutBinding.inflate(context.inflater(), this, false) }

    private var adapter: TableItemAdapter? = null
    private var isCollapsed = true
    private var collapsedItemCount: Int = DEFAULT_COLLAPSED_ITEM_COUNT

    var showArrow = true
        set(value) {
            field = value
            setArrowVisibility(value)
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryTable).apply {
            addView(binding.root)
            try {
                initRecyclerView()

                val background = getDrawable(R.styleable.PrimaryTable_primaryTableBackground)
                val backgroundTint =
                    getColorStateList(R.styleable.PrimaryTable_primaryTableBackgroundTint)

                val showTopView = getBoolean(R.styleable.PrimaryTable_primaryTableShowTopView, true)
                val showIcon = getBoolean(R.styleable.PrimaryTable_primaryTableShowIcon, true)
                showArrow = getBoolean(R.styleable.PrimaryTable_primaryTableShowArrow, true)

                val title = getString(R.styleable.PrimaryTable_primaryTableTitle)
                val icon = getDrawable(R.styleable.PrimaryTable_primaryTableIcon)
                val iconTint = getColorStateList(R.styleable.PrimaryTable_primaryTableIconTint)

                collapsedItemCount = getInt(
                    R.styleable.PrimaryTable_primaryTableCollapsedItemCount,
                    DEFAULT_COLLAPSED_ITEM_COUNT
                )

                background?.let { setLayoutBackground(background) }
                backgroundTint?.let { setLayoutBackgroundTint(backgroundTint) }

                setTopViewVisibility(showTopView)
                setIconVisibility(showIcon)

                setTitle(title)
                setIcon(icon)
                iconTint?.let { setIconTint(iconTint) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    private fun PrimaryImageView.initArrow(items: List<TableItem>, shouldCollapse: Boolean) {
        isVisible = shouldCollapse
        setOnClickListener { onArrowClick(items) }
    }

    private fun initRecyclerView() {
        adapter = TableItemAdapter()
        binding.rvItems.adapter = adapter

        val animator = DefaultItemAnimator().apply {
            addDuration = ANIMATION_DURATION
            moveDuration = ANIMATION_DURATION
            changeDuration = ANIMATION_DURATION
            removeDuration = ANIMATION_DURATION
        }
        binding.rvItems.setItemAnimator(animator)
    }

    fun setLayoutBackground(background: Drawable?) {
        binding.root.background = background
    }

    fun setLayoutBackgroundTint(colorStateList: ColorStateList?) {
        binding.root.backgroundTintList = colorStateList
    }

    fun setTopViewVisibility(isVisible: Boolean) {
        binding.listItem.isVisible = isVisible
    }

    fun setIconVisibility(isVisible: Boolean) {
        val iconType = if (isVisible) ListStartComponentType.ICON else ListStartComponentType.NONE
        binding.listItem.startComponentType = iconType
    }

    fun setArrowVisibility(isVisible: Boolean) {
        binding.ivArrow.isVisible = isVisible
    }

    fun setTitle(title: String?) {
        binding.listItem.setTitleText(title)
    }

    fun setIconLayoutParams(height: Int?, width: Int?) {
        binding.listItem.startIcon.layoutParams = LayoutParams(
            height ?: WRAP_CONTENT,
            width ?: WRAP_CONTENT
        )
    }

    fun setIcon(icon: Drawable?) {
        icon?.let { binding.listItem.startIcon.setImageDrawable(it) }
    }

    fun setIconTint(colorStateList: ColorStateList?) {
        binding.listItem.startIcon.imageTintList = colorStateList
    }

    fun setIconBackgroundTint(colorStateList: ColorStateList?) {
        binding.listItem.startIcon.backgroundTintList = colorStateList
    }

    fun setIconBackground(clipToOutline: Boolean = false, backgroundRes: Drawable?) {
        binding.listItem.startIcon.apply {
            this.clipToOutline = clipToOutline
            backgroundRes?.let { background = it }
        }
    }

    fun submitList(items: List<TableItem>) {
        if (showArrow) {
            val shouldCollapse = items.size > collapsedItemCount
            binding.ivArrow.initArrow(items, shouldCollapse)
            submitVisibleItems(items, shouldCollapse)
        } else {
            adapter?.submitList(items)
        }
    }

    private fun onArrowClick(items: List<TableItem>) {
        isCollapsed = !isCollapsed

        setImageResource()
        startMorphAnimation()

        submitVisibleItems(items, isCollapsed)
    }

    private fun submitVisibleItems(
        items: List<TableItem>,
        isCollapsed: Boolean
    ) {
        val visibleItems = if (isCollapsed) items.subList(0, collapsedItemCount - 1) else items
        adapter?.submitList(visibleItems)
    }

    private fun setImageResource() {
        val res = if (isCollapsed) R.drawable.down_arrow_morph else R.drawable.up_arrow_morph
        binding.ivArrow.setImageResource(res)
    }

    private fun startMorphAnimation() {
        val drawable = binding.ivArrow.drawable
        when (drawable) {
            is AnimatedVectorDrawableCompat -> drawable.start()
            is AnimatedVectorDrawable -> drawable.start()
        }
    }

    companion object {
        private const val DEFAULT_COLLAPSED_ITEM_COUNT = 6
        private const val ANIMATION_DURATION = 500L
    }
}
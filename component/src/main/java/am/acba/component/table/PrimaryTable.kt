package am.acba.component.table

import am.acba.component.R
import am.acba.component.button.PrimaryActionTextButton
import am.acba.component.databinding.TableLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import am.acba.component.imageView.PrimaryImageView
import am.acba.component.table.ListStartComponentType.Companion.findStartComponentTypeByOrdinal
import am.acba.component.textView.PrimaryTextView
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

class PrimaryTable : FrameLayout {

    private val binding by lazy { TableLayoutBinding.inflate(context.inflater(), this, false) }

    private var adapter: TableItemAdapter? = null
    private var isCollapsed = true
    private var collapsedItemCount: Int = DEFAULT_COLLAPSED_ITEM_COUNT

    val avatar by lazy {
        PrimaryActionTextButton(context).apply {
            setType(PrimaryActionTextButton.ActionButtonType.AVATAR)
            setActionImageSize(PrimaryActionTextButton.ActionIconSize.LARGE)
            showActionText(false)
        }
    }

    val startIcon by lazy {
        PrimaryImageView(context).apply {
            layoutParams = LayoutParams(36.dpToPx(), 36.dpToPx())
        }
    }

    var startComponentType = ListStartComponentType.NONE
        set(value) {
            field = value
            setStartComponent()
        }

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
                binding.tvTitle.initViews()

                val background = getDrawable(R.styleable.PrimaryTable_primaryTableBackground)
                val backgroundTint =
                    getColorStateList(R.styleable.PrimaryTable_primaryTableBackgroundTint)

                val showTopView = getBoolean(R.styleable.PrimaryTable_primaryTableShowTopView, true)
                showArrow = getBoolean(R.styleable.PrimaryTable_primaryTableShowArrow, true)

                val title = getString(R.styleable.PrimaryTable_primaryTableTitle)
                startComponentType = getInt(R.styleable.PrimaryTable_startImageComponentType, 0).findStartComponentTypeByOrdinal()

                val icon = getDrawable(R.styleable.PrimaryTable_primaryTableIcon)
                val iconTint = getColorStateList(R.styleable.PrimaryTable_primaryTableIconTint)

                collapsedItemCount = getInt(
                    R.styleable.PrimaryTable_primaryTableCollapsedItemCount,
                    DEFAULT_COLLAPSED_ITEM_COUNT
                )

                background?.let { setLayoutBackground(background) }
                backgroundTint?.let { setLayoutBackgroundTint(backgroundTint) }

                setTopViewVisibility(showTopView)

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

    private fun PrimaryTextView.initViews() {
        isSingleLine = false
        maxLines = 2
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

    private fun setStartComponent() {
        binding.layoutImage.apply {
            removeAllViews()
            isVisible = startComponentType != ListStartComponentType.NONE

            when (startComponentType) {
                ListStartComponentType.NONE -> Unit
                ListStartComponentType.AVATAR -> addView(avatar)
                ListStartComponentType.ICON -> addView(startIcon)
            }
        }
    }

    fun setLayoutBackground(background: Drawable?) {
        binding.root.background = background
    }

    fun setLayoutBackgroundTint(colorStateList: ColorStateList?) {
        binding.root.backgroundTintList = colorStateList
    }

    fun setTopViewVisibility(isVisible: Boolean) {
        binding.layoutTop.isVisible = isVisible
    }

    fun setArrowVisibility(isVisible: Boolean) {
        binding.ivArrow.isVisible = isVisible
    }

    fun setTitle(title: String?) {
        binding.tvTitle.setText(title)
    }

    fun setIcon(icon: Drawable?) {
        if (icon == null) return

        when (startComponentType) {
            ListStartComponentType.ICON -> startIcon.setImageDrawable(icon)
            ListStartComponentType.AVATAR -> avatar.setIcon(icon)
            else -> return
        }
    }

    fun setIconTint(colorStateList: ColorStateList?) {
        if (startComponentType == ListStartComponentType.ICON) {
            startIcon.imageTintList = colorStateList
        }
    }

    fun setIconBackgroundTint(colorStateList: ColorStateList?) {
        if (startComponentType == ListStartComponentType.ICON) {
            startIcon.backgroundTintList = colorStateList
        }
    }

    fun setIconBackground(clipToOutline: Boolean = false, backgroundRes: Drawable?) {
        if (startComponentType == ListStartComponentType.ICON) {
            startIcon.apply {
                this.clipToOutline = clipToOutline
                backgroundRes?.let { background = it }
            }
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
        val visibleItems = if (isCollapsed) items.subList(0, collapsedItemCount) else items
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

enum class ListStartComponentType {
    NONE,
    AVATAR,
    ICON;

    companion object {
        fun Int.findStartComponentTypeByOrdinal() = entries.find { it.ordinal == this } ?: NONE
    }
}
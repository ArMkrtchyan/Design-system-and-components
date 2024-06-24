package am.acba.component.chip

import am.acba.component.R
import am.acba.component.button.PrimaryActionTextButton
import am.acba.component.chip.PrimaryChip.ChipSize.Companion.findChipSizeByOrdinal
import am.acba.component.chip.PrimaryChip.ChipStartIconType.Companion.findChipStartIconTypeByOrdinal
import am.acba.component.databinding.ChipLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.imageView.PrimaryImageView
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.core.widget.TextViewCompat

class PrimaryChip : FrameLayout {
    private val binding by lazy { ChipLayoutBinding.inflate(context.inflater(), this, false) }
    val startIcon by lazy {
        PrimaryImageView(context).apply {
            updateLayoutParams<LayoutParams> {
                width = LayoutParams.MATCH_PARENT
                height = LayoutParams.MATCH_PARENT
            }
        }
    }
    val endIcon by lazy {
        PrimaryImageView(context).apply {
            updateLayoutParams<LayoutParams> {
                width = LayoutParams.MATCH_PARENT
                height = LayoutParams.MATCH_PARENT
            }
        }
    }
    val avatar by lazy {
        PrimaryActionTextButton(context).apply {
            setType(am.acba.component.button.PrimaryActionTextButton.ActionButtonType.AVATAR)
            setActionIconSize(PrimaryActionTextButton.ActionIconSize.LAGRE)
            showActionText(false)
            updateLayoutParams<LayoutParams> {
                width = LayoutParams.MATCH_PARENT
                height = LayoutParams.MATCH_PARENT
            }

        }
    }
    private var chipStartIconType = ChipStartIconType.NONE
    private var chipSize = ChipSize.SMALL

    private var chipSelectedBackgroundColor: ColorStateList? = null
    private var chipUnselectedBackgroundColor: ColorStateList? = null

    private var chipStartIconDrawable: Drawable? = null
    private var chipStartIconTint: ColorStateList? = null
    private var chipEndIconDrawable: Drawable? = null
    private var chipEndIconTint: ColorStateList? = null

    private var chipText: String? = null
    private var chipTextAppearance = -1
    private var chipTextAndIconColorSelected: ColorStateList? = null
    private var chipTextAndIconColorUnselected: ColorStateList? = null

    private var isChipSelected = false

    constructor(context: Context) : super(context) {
        addView(binding.root)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryChip).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            addView(binding.root, layoutParams)


            setChipSize(getInt(R.styleable.PrimaryChip_chipSize, 0).findChipSizeByOrdinal() ?: ChipSize.SMALL)
            setChipBackgroundColorSelected(
                getColorStateList(R.styleable.PrimaryChip_chipSelectedBackgroundColor) ?: context.getColorStateListFromAttr(R.attr.contentNeutralTonal1)
            )
            setChipBackgroundColorUnselected(
                getColorStateList(R.styleable.PrimaryChip_chipUnselectedBackgroundColor) ?: context.getColorStateListFromAttr(R.attr.backgroundTonal2)
            )

            setChipText(getString(R.styleable.PrimaryChip_chipText))
            setChipTextAppearance(getResourceId(R.styleable.PrimaryListItem_listTitleTextAppearance, R.style.Body2_Regular))
            setChipTextAndIconColorSelected(
                getColorStateList(R.styleable.PrimaryChip_chipSelectedBackgroundColor) ?: context.getColorStateListFromAttr(R.attr.contentNeutralTonal1)
            )
            setChipTextAndIconColorUnselected(
                getColorStateList(R.styleable.PrimaryChip_chipUnselectedBackgroundColor) ?: context.getColorStateListFromAttr(R.attr.contentPrimary)
            )
            setChipStartIconType(getInt(R.styleable.PrimaryChip_chipStartIconType, 0).findChipStartIconTypeByOrdinal() ?: ChipStartIconType.NONE)
            setStartIcon(getDrawable(R.styleable.PrimaryChip_chipStartIcon))
            setEndIcon(getDrawable(R.styleable.PrimaryChip_chipEndIcon))

            recycle()
            invalidate()
        }
    }


    private fun setChipSize(chipSize: ChipSize) {
        this.chipSize = chipSize
        var startPadding = 12.dpToPx()
        var verticalPAdding = 8.dpToPx()
        var endPAdding = 12.dpToPx()
        when (chipSize) {
            ChipSize.SMALL -> {
                if (chipEndIconDrawable != null) binding.lastIconContainer.updateLayoutParams<ViewGroup.LayoutParams> {
                    width = 16.dpToPx()
                    height = 16.dpToPx()
                }
                if (chipStartIconType == ChipStartIconType.ICON) {
                    startPadding = 12.dpToPx()
                    verticalPAdding = 10.dpToPx()
                    binding.firstIconContainer.updateLayoutParams<ViewGroup.LayoutParams> {
                        width = 16.dpToPx()
                        height = 16.dpToPx()
                    }
                } else if (chipStartIconType == ChipStartIconType.AVATAR) {
                    startPadding = 6.dpToPx()
                    verticalPAdding = 6.dpToPx()
                    binding.firstIconContainer.updateLayoutParams<ViewGroup.LayoutParams> {
                        width = 24.dpToPx()
                        height = 24.dpToPx()
                    }
                }
                binding.parentLayout.setPadding(startPadding, verticalPAdding, endPAdding, verticalPAdding)
            }

            ChipSize.MEDIUM -> {
                if (chipEndIconDrawable != null) binding.lastIconContainer.updateLayoutParams<ViewGroup.LayoutParams> {
                    width = 24.dpToPx()
                    height = 24.dpToPx()
                }
                if (chipStartIconType == ChipStartIconType.ICON) {
                    startPadding = 12.dpToPx()
                    verticalPAdding = 12.dpToPx()
                    binding.firstIconContainer.updateLayoutParams<ViewGroup.LayoutParams> {
                        width = 16.dpToPx()
                        height = 16.dpToPx()
                    }
                } else if (chipStartIconType == ChipStartIconType.AVATAR) {
                    startPadding = 8.dpToPx()
                    verticalPAdding = 8.dpToPx()
                    binding.firstIconContainer.updateLayoutParams<ViewGroup.LayoutParams> {
                        width = 32.dpToPx()
                        height = 32.dpToPx()
                    }
                }
                binding.parentLayout.setPadding(startPadding, verticalPAdding, endPAdding, verticalPAdding)
            }

            ChipSize.CUSTOM -> {}
        }
        invalidate()
    }

    private fun setChipBackgroundColorSelected(colorStateList: ColorStateList?) {
        chipSelectedBackgroundColor = colorStateList
        if (binding.chipCardView.isSelected) {
            binding.chipCardView.setCardBackgroundColor(colorStateList)
        }
        invalidate()
    }

    private fun setChipBackgroundColorUnselected(colorStateList: ColorStateList?) {
        chipUnselectedBackgroundColor = colorStateList
        if (!binding.chipCardView.isSelected) {
            binding.chipCardView.setCardBackgroundColor(colorStateList)
        }
        invalidate()
    }

    private fun setChipText(string: String?) {
        chipText = string
        binding.chipText.text = string
        invalidate()
    }

    private fun setChipTextAppearance(textStyle: Int) {
        chipTextAppearance = textStyle
        TextViewCompat.setTextAppearance(binding.chipText, chipTextAppearance)
        invalidate()
    }

    private fun setChipStartIconType(chipStartIconType: ChipStartIconType) {
        this.chipStartIconType = chipStartIconType
        binding.firstIconContainer.removeAllViews()
        when (chipStartIconType) {
            ChipStartIconType.NONE -> {}
            ChipStartIconType.ICON -> {
                binding.firstIconContainer.addView(startIcon)
            }

            ChipStartIconType.IMAGE -> {
                binding.firstIconContainer.addView(startIcon)
                startIcon.imageTintList = null
            }

            ChipStartIconType.AVATAR -> {
                binding.firstIconContainer.addView(avatar)
            }
        }
        invalidate()
    }

    private fun setStartIcon(drawable: Drawable?) {
        chipStartIconDrawable = drawable
        if (chipStartIconType != ChipStartIconType.NONE) {
            when (chipStartIconType) {
                ChipStartIconType.NONE -> {}
                ChipStartIconType.ICON -> {
                    startIcon.setImageDrawable(drawable)
                    if (binding.chipCardView.isSelected) {
                        startIcon.imageTintList = chipTextAndIconColorSelected
                    } else {
                        startIcon.imageTintList = chipTextAndIconColorUnselected
                    }
                }

                ChipStartIconType.IMAGE -> {
                    startIcon.setImageDrawable(drawable)
                }

                ChipStartIconType.AVATAR -> {
                    avatar.setIcon(drawable)
                }
            }
        }
        invalidate()
    }

    private fun setEndIcon(drawable: Drawable?) {
        chipEndIconDrawable = drawable
        if (chipEndIconDrawable != null) {
            if (binding.lastIconContainer.childCount == 0) {
                binding.lastIconContainer.addView(startIcon)
            }
            startIcon.setImageDrawable(drawable)
            if (!binding.chipCardView.isSelected) {
                endIcon.imageTintList = chipTextAndIconColorUnselected
            }
            if (binding.chipCardView.isSelected) {
                endIcon.imageTintList = chipTextAndIconColorSelected
            }
        } else binding.lastIconContainer.removeAllViews()
        invalidate()
    }

    private fun setChipTextAndIconColorUnselected(colorStateList: ColorStateList) {
        chipTextAndIconColorUnselected = colorStateList
        if (!binding.chipCardView.isSelected) {
            binding.chipText.setTextColor(colorStateList)
            if (chipStartIconType == ChipStartIconType.ICON) {
                startIcon.imageTintList = colorStateList
            }
            if (chipEndIconDrawable != null) {
                endIcon.imageTintList = colorStateList
            }
        }
        invalidate()
    }

    private fun setChipTextAndIconColorSelected(colorStateList: ColorStateList) {
        chipTextAndIconColorSelected = colorStateList
        if (binding.chipCardView.isSelected) {
            binding.chipText.setTextColor(colorStateList)
            if (chipStartIconType == ChipStartIconType.ICON) {
                startIcon.imageTintList = colorStateList
            }
            if (chipEndIconDrawable != null) {
                startIcon.imageTintList = colorStateList
            }
        }
        invalidate()
    }

    override fun setSelected(selected: Boolean) {
        if (selected) {
            binding.chipCardView.setCardBackgroundColor(chipSelectedBackgroundColor)
        } else {
            binding.chipCardView.setCardBackgroundColor(chipUnselectedBackgroundColor)
        }
        binding.chipCardView.isSelected = selected
        super.setSelected(selected)
    }

    enum class ChipStartIconType {
        NONE,
        ICON,
        IMAGE,
        AVATAR;

        companion object {
            fun Int.findChipStartIconTypeByOrdinal(): ChipStartIconType? {
                return entries.find { it.ordinal == this }
            }
        }
    }

    enum class ChipSize {
        SMALL,
        MEDIUM,
        CUSTOM;

        companion object {
            fun Int.findChipSizeByOrdinal(): ChipSize? {
                return entries.find { it.ordinal == this }
            }
        }
    }
}
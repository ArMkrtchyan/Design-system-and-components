package am.acba.component.chip

import am.acba.component.R
import am.acba.component.button.PrimaryActionTextButton
import am.acba.component.chip.PrimaryChip.ChipSize.Companion.findChipSizeByOrdinal
import am.acba.component.chip.PrimaryChip.ChipStartIconType.Companion.findChipStartIconTypeByOrdinal
import am.acba.component.databinding.ChipLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import am.acba.component.imageView.PrimaryImageView
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.TextViewCompat

class PrimaryChip : FrameLayout {
    private val binding by lazy { ChipLayoutBinding.inflate(context.inflater(), this, false) }
    val startIcon by lazy {
        PrimaryImageView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }
    val endIcon by lazy {
        PrimaryImageView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            imageTintList = chipTextAndIconColor
        }
    }
    val avatar by lazy {
        PrimaryActionTextButton(context).apply {
            setType(am.acba.component.button.PrimaryActionTextButton.ActionButtonType.AVATAR)
            setActionIconSize(PrimaryActionTextButton.ActionIconSize.MEDIUM)
            showActionText(false)
            setIcon(R.drawable.default_avatar)
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            invalidate()
        }
    }
    private var chipStartIconType = ChipStartIconType.NONE
    private var chipSize = ChipSize.SMALL

    private var chipStartIconDrawable: Drawable? = null
    private var chipEndIconDrawable: Drawable? = null

    private var chipText: String? = null
    private var chipTextAppearance = -1
    private var chipTextAndIconColor: ColorStateList? = null

    constructor(context: Context) : super(context) {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
        }
        addView(binding.root, layoutParams)
        val background = ContextCompat.getDrawable(context, R.drawable.background_primary_chip)
        background?.let { setChipBackground(it) }
        chipTextAndIconColor = ContextCompat.getColorStateList(context, R.color.chip_text_and_icons_selector)
        setChipSize(ChipSize.SMALL)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryChip).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }
            addView(binding.root, layoutParams)
            chipTextAndIconColor = ContextCompat.getColorStateList(context, R.color.chip_text_and_icons_selector)
            setChipText(getString(R.styleable.PrimaryChip_chipText))
            setChipTextAppearance(getResourceId(R.styleable.PrimaryChip_chipTextAppearance, R.style.Body2_Regular))
            val background = getDrawable(R.styleable.PrimaryChip_chipBackground) ?: ContextCompat.getDrawable(context, R.drawable.background_primary_chip)
            background?.let { setChipBackground(it) }
            setChipStartIconType(getInt(R.styleable.PrimaryChip_chipStartIconType, 0).findChipStartIconTypeByOrdinal() ?: ChipStartIconType.NONE)
            setStartIcon(getDrawable(R.styleable.PrimaryChip_chipStartIcon))
            setEndIcon(getDrawable(R.styleable.PrimaryChip_chipEndIcon))
            setChipSize(getInt(R.styleable.PrimaryChip_chipSize, 0).findChipSizeByOrdinal() ?: ChipSize.SMALL)
            isSelected = getBoolean(R.styleable.PrimaryChip_isChipSelected, false)
            recycle()
            invalidate()
        }
    }

    fun setChipBackground(background: Drawable) {
        this.background = background
    }

    fun setChipSize(chipSize: ChipSize) {
        this.chipSize = chipSize
        var startPadding = 4
        var verticalPAdding = 8
        var endPAdding = 4
        when (chipSize) {
            ChipSize.SMALL -> {
                if (chipEndIconDrawable != null) {
                    endPAdding = 12
                    verticalPAdding = 10
                    binding.lastIconContainer.updateLayoutParams<LinearLayout.LayoutParams> {
                        width = 16.dpToPx()
                        height = 16.dpToPx()
                    }
                }
                if (chipStartIconType == ChipStartIconType.ICON || chipStartIconType == ChipStartIconType.IMAGE) {
                    startPadding = 12
                    verticalPAdding = 10
                    binding.firstIconContainer.updateLayoutParams<LinearLayout.LayoutParams> {
                        width = 16.dpToPx()
                        height = 16.dpToPx()
                    }
                } else if (chipStartIconType == ChipStartIconType.AVATAR) {
                    startPadding = 6
                    verticalPAdding = 6
                    binding.firstIconContainer.updateLayoutParams<LinearLayout.LayoutParams> {
                        width = 24.dpToPx()
                        height = 24.dpToPx()
                    }
                    avatar.setActionIconSize(PrimaryActionTextButton.ActionIconSize.XSMALE)
                }
                binding.parentLayout.setPadding(startPadding.dpToPx(), verticalPAdding.dpToPx(), endPAdding.dpToPx(), verticalPAdding.dpToPx())
            }

            ChipSize.MEDIUM -> {
                startPadding = 8
                verticalPAdding = 14
                endPAdding = 8
                if (chipEndIconDrawable != null) {
                    endPAdding = 12
                    binding.lastIconContainer.updateLayoutParams<LinearLayout.LayoutParams> {
                        width = 24.dpToPx()
                        height = 24.dpToPx()
                    }
                }
                if (chipStartIconType == ChipStartIconType.ICON || chipStartIconType == ChipStartIconType.IMAGE) {
                    startPadding = 12
                    verticalPAdding = 12
                    binding.firstIconContainer.updateLayoutParams<LinearLayout.LayoutParams> {
                        width = 24.dpToPx()
                        height = 24.dpToPx()
                    }
                } else if (chipStartIconType == ChipStartIconType.AVATAR) {
                    startPadding = 8
                    verticalPAdding = 8
                    binding.firstIconContainer.updateLayoutParams<LinearLayout.LayoutParams> {
                        width = 32.dpToPx()
                        height = 32.dpToPx()
                    }
                    avatar.setActionIconSize(PrimaryActionTextButton.ActionIconSize.SMALE)
                }
                binding.parentLayout.setPadding(startPadding.dpToPx(), verticalPAdding.dpToPx(), endPAdding.dpToPx(), verticalPAdding.dpToPx())
            }

            ChipSize.CUSTOM -> {}
        }
        invalidate()
    }

    fun setChipText(string: String?) {
        chipText = string
        binding.chipText.text = string
        invalidate()
    }

    fun setChipTextAppearance(textStyle: Int) {
        chipTextAppearance = textStyle
        TextViewCompat.setTextAppearance(binding.chipText, chipTextAppearance)
        binding.chipText.setTextColor(chipTextAndIconColor)
        invalidate()
    }

    fun setChipStartIconType(chipStartIconType: ChipStartIconType) {
        this.chipStartIconType = chipStartIconType
        binding.firstIconContainer.removeAllViews()
        when (chipStartIconType) {
            ChipStartIconType.NONE -> {}
            ChipStartIconType.ICON -> {
                binding.firstIconContainer.addView(startIcon)
                startIcon.imageTintList = chipTextAndIconColor
            }

            ChipStartIconType.IMAGE -> {
                binding.firstIconContainer.addView(startIcon)
                startIcon.imageTintList = null
            }

            ChipStartIconType.AVATAR -> {
                binding.firstIconContainer.addView(avatar)
            }
        }
        setChipSize(chipSize)
    }

    fun setStartIcon(drawable: Drawable?) {
        chipStartIconDrawable = drawable
        if (chipStartIconType != ChipStartIconType.NONE) {
            when (chipStartIconType) {
                ChipStartIconType.NONE -> {}
                ChipStartIconType.ICON -> {
                    startIcon.setImageDrawable(drawable)
                }

                ChipStartIconType.IMAGE -> {
                    startIcon.setImageDrawable(drawable)
                }

                ChipStartIconType.AVATAR -> {
                    avatar.setIcon(drawable)
                }
            }
        }
        setChipSize(chipSize)
    }

    fun setEndIcon(drawable: Drawable?) {
        chipEndIconDrawable = drawable
        if (chipEndIconDrawable != null) {
            if (binding.lastIconContainer.childCount == 0) {
                binding.lastIconContainer.addView(endIcon)
            }
            endIcon.setImageDrawable(drawable)
        } else binding.lastIconContainer.removeAllViews()
        setChipSize(chipSize)
    }


    fun setEndIconClickListener(onClickListener: OnClickListener) {
        binding.lastIconContainer.setOnClickListener {
            if (chipEndIconDrawable != null) onClickListener.onClick(it)
        }
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
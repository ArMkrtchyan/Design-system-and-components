package am.acba.component.expandableView

import am.acba.component.R
import am.acba.component.databinding.AccordionViewBinding
import am.acba.component.expandableView.AccordionView.StartIconSize.Companion.findSizeByOrdinal
import am.acba.component.extensions.animateRotation
import am.acba.component.extensions.collapseHeight
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.expandHeight
import am.acba.component.extensions.inflater
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.setPadding

class AccordionView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs, 0) {

    private val binding by lazy { AccordionViewBinding.inflate(context.inflater(), this, false) }
    private var startIcon: Drawable?
    private var startIconBackground: Drawable?
    private var startIconTint: ColorStateList? = null
    private var startIconPadding = -1f
    private var endIcon: Drawable?
    private var startText: String
    private var startTextColor: Int = 0
    private var endText: String
    private var endTextColor: Int = 0
    private var currencyText: String
    private var currencyTextColor: Int = 0
    var animationDuration: Long = 0
    var isExpanded = false
    private var isExpandable = true

    init {
        addView(binding.root)
        orientation = VERTICAL
        context.obtainStyledAttributes(attrs, R.styleable.AccordionView).apply {
            try {
                background = getDrawable(R.styleable.AccordionView_accordionViewBackground)
                startIcon = getDrawable(R.styleable.AccordionView_accordionStartIcon)
                startIconBackground = getDrawable(R.styleable.AccordionView_accordionStartIconBackground)
                startIconTint = getColorStateList(R.styleable.AccordionView_accordionStartIconTint)
                startIconPadding = getDimension(R.styleable.AccordionView_accordionStartIconPadding, -1F)
                endIcon = getDrawable(R.styleable.AccordionView_accordionEndIcon)
                startText = getString(R.styleable.AccordionView_accordionStartText) ?: ""
                startTextColor = getColor(R.styleable.AccordionView_accordionStartTextColor, 0)
                endText = getString(R.styleable.AccordionView_accordionEndText) ?: ""
                endTextColor = getColor(R.styleable.AccordionView_accordionEndTextColor, 0)
                currencyText = getString(R.styleable.AccordionView_accordionCurrencyText) ?: ""
                currencyTextColor = getColor(R.styleable.AccordionView_accordionCurrencyTextColor, 0)
                isExpanded = getBoolean(R.styleable.AccordionView_accordionIsExpanded, false)
                isExpandable = getBoolean(R.styleable.AccordionView_accordionIsExpandable, true)
                val iconSizeEnum = getInt(R.styleable.AccordionView_startIconSize, 0).findSizeByOrdinal() ?: StartIconSize.WRAP
                setStartStartIconType(iconSizeEnum)
            } finally {
                recycle()
            }

            if (background != null) setPadding(16.dpToPx()) else setPadding(0, 16.dpToPx(), 0, 16.dpToPx())
        }
        setupUi()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        expandableViewValidationException()
        if (childCount > 1) {
            for (i in 1 until childCount) {
                children.elementAt(1).isVisible = isExpanded
            }
            binding.endIcon.animateRotation(if (isExpanded) 180f else 0F, duration = animationDuration)
        }
    }

    fun expandView() {
        if (childCount > 1) {
            val child = children.elementAt(1)
            if (isExpanded) {
                child.collapseHeight(300)
            } else {
                child.isVisible = true
                child.expandHeight(300)
            }
            binding.endIcon.animateRotation(if (isExpanded) 0F else 180F, duration = 300)
            isExpanded = !isExpanded
        }
    }

    private fun expandableViewValidationException() {
        if (childCount > 2) {
            throw IllegalStateException("Accordion View child can't be more than one element")
        } else if (childCount > 1 && children.elementAt(1) !is ViewGroup) {
            throw IllegalStateException("Accordion View child must be ViewGroup")
        }
    }

    private fun setupUi() {
        binding.startIcon.isVisible = startIcon != null
        binding.startIcon.setImageDrawable(startIcon)
        binding.startIcon.imageTintList = startIconTint
        binding.startIcon.background = startIconBackground
        binding.startIcon.setPadding(startIconPadding.toInt())
        binding.endIcon.isVisible = endIcon != null
        binding.endIcon.setImageDrawable(endIcon)
        binding.startText.isVisible = startText.isNotEmpty()
        binding.startText.text = startText
        if (startTextColor != 0) binding.startText.setTextColor(startTextColor)
        binding.endText.isVisible = endText.isNotEmpty()
        binding.endText.text = endText
        if (endTextColor != 0) binding.endText.setTextColor(endTextColor)
        binding.currencyText.isVisible = currencyText.isNotEmpty()
        binding.currencyText.text = currencyText
        if (currencyTextColor != 0) binding.currencyText.setTextColor(currencyTextColor)
    }

    private fun setStartStartIconType(startIconSize: StartIconSize) {
        when (startIconSize) {
            StartIconSize.WRAP -> {
                binding.startIcon.layoutParams = LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                )
            }

            StartIconSize.SMALL, StartIconSize.MEDIUM, StartIconSize.LARGE -> {
                setActionIconSize(startIconSize)
            }

        }
        invalidate()
    }

    private fun setActionIconSize(iconSize: StartIconSize) {
        binding.startIcon.layoutParams = LayoutParams(
            iconSize.width.dpToPx(),
            iconSize.height.dpToPx()
        )
    }

    enum class StartIconSize(val width: Int, val height: Int) {
        WRAP(WRAP_CONTENT, WRAP_CONTENT),
        SMALL(12, 12),
        MEDIUM(18, 18),
        LARGE(24, 24);

        companion object {
            fun Int.findSizeByOrdinal(): StartIconSize? {
                return entries.find { it.ordinal == this }
            }
        }
    }

    fun setStartIconDrawable(icon: Drawable?) {
        startIcon = icon
        binding.startIcon.isVisible = icon != null
        binding.startIcon.setImageDrawable(icon)

    }

    fun setStartIconTint(iconTint: ColorStateList?) {
        if (iconTint != null) {
            startIconTint = iconTint
            binding.startIcon.imageTintList = iconTint
        }
    }

    fun setEndIconDrawable(icon: Drawable?) {
        endIcon = icon
        binding.endIcon.isVisible = icon != null
        binding.endIcon.setImageDrawable(icon)
    }

    fun setStartText(text: String) {
        startText = text
        binding.startText.isVisible = text.isNotEmpty()
        binding.startText.text = text
    }

    fun setEndText(text: String) {
        endText = text
        binding.endText.isVisible = text.isNotEmpty()
        binding.endText.text = text
    }

    fun setCurrencyText(text: String) {
        currencyText = text
        binding.currencyText.isVisible = text.isNotEmpty()
        binding.currencyText.text = text
    }

    fun setEndTextColor(iconTint: ColorStateList?) {
        binding.endText.setTextColor(iconTint)
    }

    fun setCurrencyTextColor(iconTint: ColorStateList?) {
        binding.currencyText.setTextColor(iconTint)
    }

}
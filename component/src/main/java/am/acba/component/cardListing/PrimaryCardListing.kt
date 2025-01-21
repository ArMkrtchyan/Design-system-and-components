package am.acba.component.cardListing

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import am.acba.component.databinding.CardListingLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.extensions.load
import am.acba.component.imageView.PrimaryImageView
import am.acba.component.textView.PrimaryTextView
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams

class PrimaryCardListing : FrameLayout {

    private val binding by lazy {
        CardListingLayoutBinding.inflate(
            context.inflater(), this, false
        )
    }

    private var showStatus = false
    private var clickInterval = 1000

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryCardListing).apply {
            addView(binding.root)
            try {
                val layoutBackgroundColor = getInt(
                    R.styleable.PrimaryCardListing_cardListingLayoutBackgroundColor, context.getColor(R.color.Transparent)
                )
                val layoutBorderColor = getColor(
                    R.styleable.PrimaryCardListing_cardListingLayoutBorderColor, 0
                )

                val startTitle = getString(R.styleable.PrimaryCardListing_startTitleText)
                val startBody = getString(R.styleable.PrimaryCardListing_startBodyText)
                val endTitle = getString(R.styleable.PrimaryCardListing_endTitleText)
                val endBody = getString(R.styleable.PrimaryCardListing_endBodyText)
                val cardCurrency = getString(R.styleable.PrimaryCardListing_cardCurrencyText)

                val startTitleColor = getColorStateList(R.styleable.PrimaryCardListing_startTitleTextColor)
                val startBodyColor = getColorStateList(R.styleable.PrimaryCardListing_startBodyTextColor)
                val endTitleColor = getColorStateList(R.styleable.PrimaryCardListing_endTitleTextColor)
                val endBodyColor = getColorStateList(R.styleable.PrimaryCardListing_endBodyTextColor)
                val cardCurrencyColor = getColorStateList(R.styleable.PrimaryCardListing_cardCurrencyTextColor)
                setStartTitleTextColor(startTitleColor)
                setStartBodyTextColor(startBodyColor)
                setEndTitleTextColor(endTitleColor)
                setEndBodyTextColor(endBodyColor)
                setCardCurrencyTextColor(cardCurrencyColor)

                val startIconPadding = getDimension(R.styleable.PrimaryCardListing_cardListingStartIconPadding, 0f).toInt()
                val startIconWidth = getDimension(R.styleable.PrimaryCardListing_cardListingStartIconWidth, 36.dpToPx().toFloat()).toInt()
                val startIconHeight = getDimension(R.styleable.PrimaryCardListing_cardListingStartIconHeight, 36.dpToPx().toFloat()).toInt()

                val startIconBackgroundColor = getColorStateList(R.styleable.PrimaryCardListing_cardListingStartIconBackgroundTint)
                val startIconBackground = getDrawable(R.styleable.PrimaryCardListing_cardListingStartIconBackground)
                val startIconTint = getColorStateList(R.styleable.PrimaryCardListing_cardListingStartIconTint)
                val startIcon = getDrawable(R.styleable.PrimaryCardListing_cardListingStartIcon)
                val endIcon = getDrawable(R.styleable.PrimaryCardListing_cardListingEndIcon)

                showStatus = getBoolean(R.styleable.PrimaryCardListing_showCardStatus, false)
                val statusBackgroundColor = getColor(R.styleable.PrimaryCardListing_cardListingStatusBackgroundColor, context.getColorFromAttr(R.attr.borderNeutral))
                val statusText = getString(R.styleable.PrimaryCardListing_cardListingStatusText)
                val statusTextColor = getColorStateList(R.styleable.PrimaryCardListing_cardListingStatusTextColor) ?: context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)
                val statusIcon = getDrawable(R.styleable.PrimaryCardListing_cardListingStatusIcon)
                val statusIconTint = getColorStateList(R.styleable.PrimaryCardListing_cardListingStatusIconTint) ?: context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1)

                setLayoutBackgroundColor(layoutBackgroundColor)
                setLayoutBorderColor(layoutBorderColor)
                setStartTitleText(startTitle)
                setStartBodyText(startBody)
                setEndTitleText(endTitle)
                setEndBodyText(endBody)
                setCardCurrencyText(cardCurrency)

                setStartIconBackground(startIconBackground)
                setStartIconPadding(startIconPadding)
                setStartIconHeight(startIconHeight)
                setStartIconWidth(startIconWidth)
                setStartIcon(startIcon)
                setStartIconTint(startIconTint)
                setStartIconBackgroundTint(startIconBackgroundColor)
                setEndIcon(endIcon)

                showStatus(showStatus)
                setStatusBackgroundColor(statusBackgroundColor)
                setStatusIcon(statusIcon)
                setStatusIconTint(statusIconTint)
                setStatusText(statusText)
                setStatusTextColor(statusTextColor)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    private fun PrimaryTextView.updateTextView(text: String?) {
        isVisible = !text.isNullOrEmpty()
        if (isVisible) this.text = text
    }

    private fun PrimaryImageView.setIcon(icon: Drawable?) {
        isVisible = icon != null
        setImageDrawable(icon)
    }

    fun setLayoutBackgroundColor(@ColorInt color: Int?) {
        color?.let { binding.layoutCard.setCardBackgroundColor(it) }
    }

    fun setLayoutBackgroundColor(colorStateList: ColorStateList?) {
        colorStateList?.let { binding.layoutCard.setCardBackgroundColor(it) }
    }

    fun setLayoutBorderColor(@ColorInt color: Int?) {
        if (color == null) return

        binding.layoutCard.apply {
            strokeColor = color
            strokeWidth = if (color != 0) 1.dpToPx() else 0
        }
    }

    fun setStartTitleText(text: String?) {
        binding.tvStartTitle.updateTextView(text)
    }

    fun setStartTitleTextAppearance(@StyleRes resId: Int?) {
        resId?.let { binding.tvStartTitle.setTextAppearance(it) }
    }

    fun setStartBodyText(text: String?) {
        binding.tvStartBody.updateTextView(text)
    }

    fun setEndTitleText(text: String?) {
        binding.tvEndTitle.updateTextView(text)
    }

    fun setEndBodyText(text: String?) {
        binding.tvEndBody.updateTextView(text)
    }

    fun setCardCurrencyText(text: String?) {
        binding.tvCurrency.updateTextView(text)
    }

    fun setStartTitleTextColor(colorStateList: ColorStateList?) {
        colorStateList?.let { binding.tvStartTitle.setTextColor(colorStateList) }
    }

    fun setStartBodyTextColor(colorStateList: ColorStateList?) {
        colorStateList?.let { binding.tvStartBody.setTextColor(colorStateList) }
    }

    fun setEndTitleTextColor(colorStateList: ColorStateList?) {
        colorStateList?.let { binding.tvEndTitle.setTextColor(colorStateList) }
    }

    fun setEndBodyTextColor(colorStateList: ColorStateList?) {
        colorStateList?.let { binding.tvEndBody.setTextColor(colorStateList) }
    }

    fun setCardCurrencyTextColor(colorStateList: ColorStateList?) {
        colorStateList?.let { binding.tvCurrency.setTextColor(colorStateList) }
    }

    fun setStartIconPadding(iconPadding: Int) {
        binding.ivStartIcon.setPadding(iconPadding)
    }

    fun setStartIconWidth(width: Int) {
        binding.ivStartIcon.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.width = width
        }
    }

    fun setStartIconHeight(height: Int) {
        binding.ivStartIcon.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.height = height
        }
    }

    fun setStartIconBackgroundTint(colorStateList: ColorStateList?) {
        binding.ivStartIcon.backgroundTintList = colorStateList
    }

    fun setStartIconBackground(drawable: Drawable?) {
        drawable?.let { binding.ivStartIcon.background = it }
    }

    fun setStartIcon(icon: Drawable?) {
        binding.ivStartIcon.setIcon(icon)
    }

    fun setStartIcon(iconUrl: String) {
        val iconBackgroundTint = binding.ivStartIcon.backgroundTintList
        binding.shimmerInnerView.backgroundTintList = iconBackgroundTint
        binding.ivStartIcon.backgroundTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        binding.ivStartIcon.isVisible = true
        binding.ivStartIcon.load(iconUrl, binding.shimmerLayout) { _, _ ->
            binding.ivStartIcon.backgroundTintList = iconBackgroundTint
        }
    }

    fun getStartIcon() = binding.ivStartIcon

    fun setStartIconTint(colorStateList: ColorStateList?) {
        binding.ivStartIcon.imageTintList = colorStateList
    }

    fun setEndIcon(icon: Drawable?) {
        binding.ivEndIcon.setIcon(icon)
    }

    fun showStatus(isVisible: Boolean) {
        binding.layoutStatus.isVisible = isVisible
    }

    fun setStatusBackgroundColor(@ColorInt color: Int?) {
        color?.let { binding.layoutStatus.setBackgroundColor(it) }
    }

    fun setStatusIcon(icon: Drawable?) {
        binding.ivStatusIcon.setIcon(icon)
    }

    fun setStatusIconTint(tint: ColorStateList?) {
        tint?.let { binding.ivStatusIcon.imageTintList = tint }
    }

    fun setStatusText(text: String?) {
        showStatus = text?.isNotEmpty() == true
        showStatus(showStatus)
        binding.tvStatus.updateTextView(text)
    }

    fun setStatusTextColor(colorStateList: ColorStateList?) {
        colorStateList?.let { binding.tvStatus.setTextColor(it) }
    }

    fun setOnLinkClickListener(onClickListener: OnClickListener?) {
        binding.root.apply {
            if (isVisible && onClickListener != null) {
                setOnClickListener(
                    PreventDoubleClickListener(
                        onClickListener, clickInterval
                    )
                )
            } else {
                setOnClickListener(onClickListener)
            }
        }
    }

    enum class IconTypes(var padding: Int) {
        LARGE(0), SMALL(6);

        companion object {
            fun Int.findIconTypeByOrdinal() = entries.find { it.ordinal == this }
        }
    }
}
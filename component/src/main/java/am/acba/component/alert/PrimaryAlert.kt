package am.acba.component.alert

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import am.acba.component.alert.PrimaryAlert.AlertTypes.Companion.findAlertTypeByOrdinal
import am.acba.component.databinding.AlertLayoutBinding
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.imageView.PrimaryImageView
import am.acba.component.textView.PrimaryTextView
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class PrimaryAlert : FrameLayout {
    private val binding by lazy { AlertLayoutBinding.inflate(context.inflater(), this, false) }

    private var isFilledBackground = false
    private var showCloseIcon = true
    private var type = AlertTypes.INFO
    private var clickInterval = 1000

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryAlert).apply {
            addView(binding.root)
            try {
                showCloseIcon = getBoolean(R.styleable.PrimaryAlert_showCloseIcon, true)
                type = getInt(R.styleable.PrimaryAlert_type, 0).findAlertTypeByOrdinal()
                    ?: AlertTypes.INFO

                isFilledBackground = getBoolean(R.styleable.PrimaryAlert_isFilledBackground, false)
                val title = getString(R.styleable.PrimaryAlert_title)
                val body = getString(R.styleable.PrimaryAlert_body)
                val bodyMaxLines = getInt(R.styleable.PrimaryAlert_bodyMaxLines, 0)
                val link = getString(R.styleable.PrimaryAlert_link)
                val neutralIcon = getDrawable(R.styleable.PrimaryAlert_neutralIcon)

                updateCloseIconVisibility()
                setType(type)
                setTitle(title)
                setBody(body)
                setBodyMaxLines(bodyMaxLines)
                setLink(link)
                setNeutralIcon(neutralIcon)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    private fun updateCloseIconVisibility() {
        binding.ivClose.isVisible = showCloseIcon
    }

    fun setTitle(title: String?) {
        binding.tvTitle.updateTextView(title)
    }

    fun setBody(body: String?) {
        binding.tvBody.updateTextView(body)
    }

    fun setBodyMaxLines(maxLines: Int?) {
        maxLines?.takeIf { it != 0 }?.let {
            binding.tvBody.maxLines = it
        }
    }

    fun setLink(link: String?) {
        binding.tvLink.apply {
            paintFlags = Paint.UNDERLINE_TEXT_FLAG
            updateTextView(link)
        }
    }

    fun setType(type: AlertTypes) {
        this.type = type
        setFilledBackground(isFilledBackground)
        val icon = if (type.iconRes != 0) ContextCompat.getDrawable(context, type.iconRes) else null
        binding.ivStartIcon.setIcon(icon, type.iconColorRes)
    }

    fun setFilledBackground(isFilled: Boolean) {
        if (isFilled) {
            val color = context.getColorFromAttr(type.backgroundColorRes)
            setBackgroundColor(color)
        } else {
            setBackgroundResource(type.backgroundRes)
        }
    }

    fun setNeutralIcon(@DrawableRes iconRes: Int) {
        val icon = AppCompatResources.getDrawable(context, iconRes)
        setNeutralIcon(icon)
    }

    fun setNeutralIcon(icon: Drawable?) {
        if (icon != null && type == AlertTypes.NEUTRAL) {
            binding.ivStartIcon.setIcon(icon, AlertTypes.NEUTRAL.iconColorRes)
        }
    }

    fun setOnCloseClickListener(onClickListener: OnClickListener?) {
        if (showCloseIcon && onClickListener != null) {
            binding.ivClose.setOnClickListener(
                PreventDoubleClickListener(
                    onClickListener,
                    clickInterval
                )
            )
        } else {
            binding.ivClose.setOnClickListener(onClickListener)
        }
    }

    fun setOnLinkClickListener(onClickListener: OnClickListener?) {
        binding.tvLink.apply {
            if (isVisible && onClickListener != null) {
                setOnClickListener(
                    PreventDoubleClickListener(
                        onClickListener,
                        clickInterval
                    )
                )
            } else {
                setOnClickListener(onClickListener)
            }
        }
    }

    private fun PrimaryTextView.updateTextView(text: String?) {
        isVisible = !text.isNullOrEmpty()
        if (isVisible) this.text = text
    }

    private fun PrimaryImageView.setIcon(icon: Drawable?, iconColorRes: Int) {
        icon?.let {
            setImageDrawable(icon)
        }

        val color = context.getColorFromAttr(iconColorRes)
        imageTintList = ColorStateList.valueOf(color)
    }

    enum class AlertTypes(
        val iconRes: Int,
        val iconColorRes: Int,
        val backgroundRes: Int,
        val backgroundColorRes: Int
    ) {
        INFO(
            R.drawable.ic_info,
            R.attr.contentInfoTonal1,
            R.drawable.background_alert_info,
            R.attr.backgroundInfoTonal1
        ),
        DANGER(
            R.drawable.ic_close_round,
            R.attr.contentDangerTonal1,
            R.drawable.background_alert_danger,
            R.attr.backgroundDangerTonal1
        ),
        WARNING(
            R.drawable.ic_warning,
            R.attr.contentWarningTonal1,
            R.drawable.background_alert_warning,
            R.attr.backgroundWarningTonal1
        ),
        SUCCESS(
            R.drawable.ic_success,
            R.attr.contentSuccessTonal1,
            R.drawable.background_alert_success,
            R.attr.backgroundSuccessTonal1
        ),
        NEUTRAL(
            0,
            R.attr.contentPrimaryTonal1,
            R.drawable.background_alert_neutral,
            R.attr.backgroundPendingTonal1
        );

        companion object {
            fun Int.findAlertTypeByOrdinal(): AlertTypes? {
                return entries.find { it.ordinal == this }
            }
        }
    }
}
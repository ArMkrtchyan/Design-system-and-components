package am.acba.component.alert

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import am.acba.component.alert.PrimaryAlert.AlertTypes.Companion.findAlertTypeByOrdinal
import am.acba.component.databinding.AlertLayoutBinding
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.textView.PrimaryTextView
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class PrimaryAlert : FrameLayout {
    private val binding by lazy { AlertLayoutBinding.inflate(context.inflater(), this, false) }

    private var showCloseIcon = true
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
                val type = getInt(R.styleable.PrimaryAlert_type, 0).findAlertTypeByOrdinal()
                    ?: AlertTypes.INFO
                val title = getString(R.styleable.PrimaryAlert_title)
                val body = getString(R.styleable.PrimaryAlert_body)
                val link = getString(R.styleable.PrimaryAlert_link)

                updateCloseIconVisibility()
                setType(type)
                setTitle(title)
                setBody(body)
                setLink(link)
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

    fun setLink(link: String?) {
        binding.tvLink.apply {
            paintFlags = Paint.UNDERLINE_TEXT_FLAG
            updateTextView(link)
        }
    }

    private fun PrimaryTextView.updateTextView(text: String?) {
        isVisible = !text.isNullOrEmpty()
        if (isVisible) this.text = text
    }

    fun setType(type: AlertTypes) {
        binding.layout.setBackgroundResource(type.backgroundRes)
        binding.ivStartIcon.apply {
            setImageDrawable(ContextCompat.getDrawable(context, type.iconRes))
            val color = context.getColorFromAttr(type.iconColorRes)
            imageTintList = ColorStateList.valueOf(color)
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

    enum class AlertTypes(val iconRes: Int, val iconColorRes: Int, val backgroundRes: Int) {
        INFO(
            R.drawable.ic_info,
            R.attr.contentInfoTonal1,
            R.drawable.alert_info_background
        ),
        DANGER(
            R.drawable.ic_close_round,
            R.attr.contentDangerTonal1,
            R.drawable.alert_danger_background
        ),
        WARNING(
            R.drawable.ic_warning,
            R.attr.contentWarningTonal1,
            R.drawable.alert_warning_background
        ),
        SUCCESS(
            R.drawable.ic_success,
            R.attr.contentSuccessTonal1,
            R.drawable.alert_success_background
        );

        companion object {
            fun Int.findAlertTypeByOrdinal(): AlertTypes? {
                return entries.find { it.ordinal == this }
            }
        }
    }
}
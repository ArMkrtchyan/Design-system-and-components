package am.acba.component.button

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import am.acba.component.button.PrimaryActionTextButton.ActionButtonType.Companion.findTypeByOrdinal
import am.acba.component.button.PrimaryActionTextButton.ActionIconSize.Companion.findSizeByOrdinal
import am.acba.component.databinding.WidgetActionTextButtonBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import am.acba.component.imageView.MaterialTextDrawable
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View.OnClickListener
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams

class PrimaryActionTextButton : FrameLayout {
    var isChecked = false
    private var isPreventDoubleClick = true
    private var showActionText = true
    private var checkable = false
    private var clickInterval = 1000
    private var type = ActionButtonType.ACTION_BUTTON
    private val binding by lazy { WidgetActionTextButtonBinding.inflate(context.inflater(), this, false) }

    constructor(context: Context) : super(context, null, R.attr.primaryActionTextButtonStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryActionTextButtonStyle) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryActionTextButton).apply {
            addView(binding.root)
            try {
                showActionText = getBoolean(R.styleable.PrimaryActionTextButton_showActionText, true)
                isPreventDoubleClick = getBoolean(R.styleable.PrimaryActionTextButton_isPreventClick, true)
                clickInterval = getInt(R.styleable.PrimaryActionTextButton_clickInterval, 1000)

                type = getInt(R.styleable.PrimaryActionTextButton_actionButtonType, 0).findTypeByOrdinal() ?: ActionButtonType.ACTION_BUTTON

                setBadgeCheckable(getBoolean(R.styleable.PrimaryActionTextButton_checkable, false))
                setBadgeChecked(getBoolean(R.styleable.PrimaryActionTextButton_badgeChecked, false))
                val badgeIcon = getDrawable(R.styleable.PrimaryActionTextButton_badgeIcon)
                setBadgeIcon(badgeIcon)

                setIcon(getDrawable(R.styleable.PrimaryActionTextButton_actionIcon))
                val background =
                    getDrawable(R.styleable.PrimaryActionTextButton_actionIconBackground) ?: ContextCompat.getDrawable(context, R.drawable.background_rounded)
                setIconBackground(background)
                val iconSizeEnum = getInt(R.styleable.PrimaryActionTextButton_actionIconSize, 0).findSizeByOrdinal() ?: ActionIconSize.XLAGRE
                setIconPadding(getDimension(R.styleable.PrimaryActionTextButton_actionIconPadding, iconSizeEnum.padding.dpToPx().toFloat()).toInt())
                binding.actionIcon.updateLayoutParams<LayoutParams> {
                    width = iconSizeEnum.size.dpToPx()
                    height = iconSizeEnum.size.dpToPx()
                }
                binding.badgeIcon.updateLayoutParams<LayoutParams> {
                    width = iconSizeEnum.badgeSize.dpToPx()
                    height = iconSizeEnum.badgeSize.dpToPx()
                }
                binding.actionText.text = getString(R.styleable.PrimaryActionTextButton_android_text)
                when (type) {
                    ActionButtonType.ACTION_BUTTON -> {
                        binding.actionText.isVisible = !binding.actionText.text.isNullOrEmpty() && showActionText
                    }

                    else -> {
                        binding.actionText.isVisible = !binding.actionText.text.isNullOrEmpty() && showActionText
                        binding.actionIcon.setBackgroundColor(ContextCompat.getColor(context, R.color.Transparent))
                        binding.actionIcon.imageTintList = null
                        binding.actionIcon.setPadding(0)
                        if (type == ActionButtonType.TEXT) {
                            MaterialTextDrawable.with(context)
                                .text(binding.actionText.text.toString())
                                .into(binding.actionIcon)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        if (checkable) {
            val checkableClickListener = OnClickListener {
                isChecked = !isChecked
                binding.badgeIcon.isVisible = isChecked
            }
            if (isPreventDoubleClick) {
                super.setOnClickListener(PreventDoubleClickListener(checkableClickListener, clickInterval))
            } else {
                super.setOnClickListener(checkableClickListener)
            }
        } else {
            if (isPreventDoubleClick && onClickListener != null) {
                super.setOnClickListener(PreventDoubleClickListener(onClickListener, clickInterval))
            } else {
                super.setOnClickListener(onClickListener)
            }
        }
    }

    fun setBadgeIcon(@DrawableRes iconRes: Int) {
        if (!checkable) {
            binding.badgeIcon.setImageResource(iconRes)
            binding.badgeIcon.isVisible = true
        }
    }

    fun setBadgeIcon(iconDrawable: Drawable?) {
        if (!checkable) {
            binding.badgeIcon.setImageDrawable(iconDrawable)
            binding.badgeIcon.isVisible = iconDrawable != null
        }
    }

    fun setBadgeCheckable(isCheckable: Boolean) {
        checkable = isCheckable
        if (checkable) {
            binding.badgeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_success_filled))
            setOnClickListener(null)
            binding.badgeIcon.isVisible = isChecked
        }
    }

    fun setBadgeChecked(isChecked: Boolean) {
        this.isChecked = isChecked
        if (checkable) {
            binding.badgeIcon.isVisible = isChecked
        }
    }

    fun setIcon(@DrawableRes iconRes: Int) {
        binding.actionIcon.setImageResource(iconRes)
    }

    fun setIcon(iconDrawable: Drawable?) {
        binding.actionIcon.setImageDrawable(iconDrawable)
    }

    fun setIconBackground(@DrawableRes iconRes: Int) {
        setIconBackground(ContextCompat.getDrawable(context, iconRes))
    }

    fun setIconBackground(iconDrawable: Drawable?) {
        binding.actionIcon.background = iconDrawable
    }

    fun setIconPadding(padding: Int) {
        binding.actionIcon.setPadding(padding)
    }

    fun setText(@StringRes stringRes: Int) {
        binding.actionText.setText(stringRes)
    }

    fun setText(text: String?) {
        binding.actionText.text = text
    }

    enum class ActionIconSize(val size: Int, val badgeSize: Int, val padding: Int) {
        XLAGRE(56, 16, 16),
        LAGRE(40, 10, 10),
        MEDIUM(36, 8, 8),
        SMALE(32, 6, 6),
        XSMALE(24, 0, 4);

        companion object {
            fun Int.findSizeByOrdinal(): ActionIconSize? {
                return entries.find { it.ordinal == this }
            }
        }
    }

    enum class ActionButtonType {
        ACTION_BUTTON,
        AVATAR,
        TEXT;

        companion object {
            fun Int.findTypeByOrdinal(): ActionButtonType? {
                return entries.find { it.ordinal == this }
            }
        }
    }
}
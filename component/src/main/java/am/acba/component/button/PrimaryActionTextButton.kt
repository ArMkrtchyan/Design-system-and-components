package am.acba.component.button

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import am.acba.component.badge.PrimaryBadge
import am.acba.component.badge.PrimaryBadge.BadgeType.Companion.findBadgeTypeByOrdinal
import am.acba.component.button.PrimaryActionTextButton.ActionButtonType.Companion.findTypeByOrdinal
import am.acba.component.button.PrimaryActionTextButton.ActionIconSize.Companion.findSizeByOrdinal
import am.acba.component.databinding.WidgetActionTextButtonBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.imageView.MaterialTextDrawable
import am.acba.component.imageView.PrimaryImageView
import am.acba.component.textView.PrimaryTextView
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import androidx.core.widget.TextViewCompat
import com.airbnb.lottie.SimpleColorFilter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PrimaryActionTextButton : FrameLayout {

    private lateinit var badgeType: PrimaryBadge.BadgeType
    private var textColor: Int = 0
    private var text: String? = null
    private var icon: Drawable? = null
    private var iconTint: ColorStateList? = null
    private var backgroundTint: ColorStateList? = null

    var isChecked = false
    private var isPreventDoubleClick = true
    private var showActionText = true
    private var checkable = false
    private var clickInterval = 1000
    private var type = ActionButtonType.ACTION_BUTTON
    private var textDrawableBackgroundColor: Int = 0
    private var textDrawableColor: Int = 0
    private var badgeIconGravity: Int = 0
    private var badgeTextPaddingTop = 0f
    private var badgeTextPaddingBottom = 0f
    private var badgeTextPaddingEnd = 0f
    private var badgeTextPaddingStart = 0f
    private var badgeTextStyle = -1
    private val binding by lazy { WidgetActionTextButtonBinding.inflate(context.inflater(), this, false) }

    constructor(context: Context) : super(context, null, R.attr.primaryActionTextButtonStyle) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryActionTextButtonStyle) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryActionTextButton).apply {
            addView(binding.root)
            try {
                showActionText = getBoolean(R.styleable.PrimaryActionTextButton_showActionText, true)
                isPreventDoubleClick = getBoolean(R.styleable.PrimaryActionTextButton_isPreventClick, true)
                clickInterval = getInt(R.styleable.PrimaryActionTextButton_clickInterval, 1000)

                type = getInt(R.styleable.PrimaryActionTextButton_actionButtonType, 0).findTypeByOrdinal() ?: ActionButtonType.ACTION_BUTTON
                badgeIconGravity = getInt(R.styleable.PrimaryActionTextButton_badgeIconGravity, 0)
                binding.badgeIcon.updateLayoutParams<FrameLayout.LayoutParams> {
                    gravity = when (badgeIconGravity) {
                        1 -> Gravity.TOP or Gravity.END
                        else -> Gravity.BOTTOM or Gravity.END
                    }
                }

                text = getString(R.styleable.PrimaryActionTextButton_badgeText)
                textColor = getColor(R.styleable.PrimaryActionTextButton_badgeTextColor, ContextCompat.getColor(context, R.color.White))
                icon = getDrawable(R.styleable.PrimaryActionTextButton_badgeIcon)
                iconTint = getColorStateList(R.styleable.PrimaryActionTextButton_badgeIconTint)
                backgroundTint = getColorStateList(R.styleable.PrimaryActionTextButton_badgeBackgroundTint)
                badgeType = getInt(R.styleable.PrimaryActionTextButton_badgeType, 0).findBadgeTypeByOrdinal() ?: PrimaryBadge.BadgeType.DOT
                badgeTextPaddingTop = getDimension(R.styleable.PrimaryActionTextButton_badgePaddingTop, -1f)
                badgeTextPaddingBottom = getDimension(R.styleable.PrimaryActionTextButton_badgePaddingTop, -1f)
                badgeTextPaddingEnd = getDimension(R.styleable.PrimaryActionTextButton_badgePaddingTop, -1f)
                badgeTextPaddingStart = getDimension(R.styleable.PrimaryActionTextButton_badgePaddingTop, -1f)
                badgeTextStyle = getResourceId(R.styleable.PrimaryActionTextButton_badgeTextAppearance, R.style.Small_Regular)
                binding.badgeIcon.isVisible = getBoolean(R.styleable.PrimaryActionTextButton_showBadge, false)


                textDrawableColor = getColor(R.styleable.PrimaryActionTextButton_textDrawableColor, ContextCompat.getColor(context, R.color.BrandGreen_650))
                textDrawableBackgroundColor =
                    getColor(R.styleable.PrimaryActionTextButton_textDrawableBackgroundColor, ContextCompat.getColor(context, R.color.BrandGreen_200))

                setBadgeCheckable(getBoolean(R.styleable.PrimaryActionTextButton_checkable, false))
                setBadgeChecked(getBoolean(R.styleable.PrimaryActionTextButton_badgeChecked, false))

                setIcon(getDrawable(R.styleable.PrimaryActionTextButton_actionIcon))
                val background =
                    getDrawable(R.styleable.PrimaryActionTextButton_actionIconBackground) ?: ContextCompat.getDrawable(context, R.drawable.background_rounded)
                setIconBackground(background)
                val tint =
                    getColorStateList(R.styleable.PrimaryActionTextButton_actionIconTint)
                if (tint != null) setIconTint(tint)
                val iconSizeEnum = getInt(R.styleable.PrimaryActionTextButton_actionIconSize, 0).findSizeByOrdinal() ?: ActionIconSize.XLARGE
                setIconPadding(getDimension(R.styleable.PrimaryActionTextButton_actionIconPadding, iconSizeEnum.padding.dpToPx().toFloat()).toInt())
                setBadgeIconVisibility(getBoolean(R.styleable.PrimaryActionTextButton_badgeVisibility, false), iconSizeEnum)
                setActionImageSize(iconSizeEnum)
                setActionBadgeSize(iconSizeEnum)
                setBadgeSize(iconSizeEnum)
                setBadgeBackgroundTint(backgroundTint)
                binding.actionText.text = getString(R.styleable.PrimaryActionTextButton_android_text)
                val textStyle = getResourceId(R.styleable.PrimaryActionTextButton_textAppearance, R.style.Button_Style_Text)
                TextViewCompat.setTextAppearance(binding.actionText, textStyle)
                setType(type)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    fun getBadge(): FrameLayout {
        return binding.badgeIcon
    }

    fun getActionIcon(): PrimaryImageView {
        return binding.actionImage
    }

    fun getActionText(): PrimaryTextView {
        return binding.actionText
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

    fun setBadgeCheckable(isCheckable: Boolean) {
        checkable = isCheckable
        if (checkable) {
            binding.ivBadgeIcon.background = ContextCompat.getDrawable(context, R.drawable.ic_circle_filled)
            binding.badgeIcon.background = ContextCompat.getDrawable(context, R.drawable.background_rounded)
            setOnClickListener(null)
            binding.badgeIcon.isVisible = isChecked
        }
    }

    fun setAvatarCheckedStatus(isChecked: Boolean) {
        binding.actionIconCheckedBackground.isVisible = isChecked
    }

    fun setBadgeChecked(isChecked: Boolean) {
        this.isChecked = isChecked
        if (checkable) {
            binding.badgeIcon.isVisible = isChecked
        }
    }

    fun setIcon(@DrawableRes iconRes: Int) {
        binding.actionImage.setImageResource(iconRes)
    }

    fun setIcon(iconDrawable: Drawable?) {
        binding.actionImage.setImageDrawable(iconDrawable)
    }

    fun setImageUri(uri: Uri) {
        Glide.with(context).asBitmap().load(uri.path).apply(RequestOptions.circleCropTransform())
            .into(binding.actionImage)
    }

    fun setAnimation(animation: String?, color: Int) {
        binding.actionImage.visibility = View.GONE
        binding.actionAnimation.visibility = View.VISIBLE
        binding.actionAnimation.setAnimation(animation)
        binding.actionAnimation.colorFilter = SimpleColorFilter(color)
        binding.actionAnimation.playAnimation()
    }

    fun setIconBackground(@DrawableRes iconRes: Int) {
        setIconBackground(ContextCompat.getDrawable(context, iconRes))
    }

    fun setIconBackground(iconDrawable: Drawable?) {
        binding.actionImage.background = iconDrawable
    }

    fun setIconTint(colorStateList: ColorStateList?) {
        binding.actionImage.imageTintList = colorStateList
    }

    fun setIconPadding(padding: Int) {
        binding.actionImage.setPadding(padding)
    }

    fun showActionText(showText: Boolean) {
        showActionText = showText
        binding.actionText.isVisible = !binding.actionText.text.isNullOrEmpty() && showActionText
    }

    fun setActionImageSize(iconSize: ActionIconSize) {
        binding.actionImage.updateLayoutParams<LayoutParams> {
            width = iconSize.size.dpToPx()
            height = iconSize.size.dpToPx()
        }
        binding.actionAnimation.updateLayoutParams<LayoutParams> {
            width = iconSize.size.dpToPx()
            height = iconSize.size.dpToPx()
        }
        binding.actionIconCheckedBackground.updateLayoutParams<LayoutParams> {
            width = iconSize.size.dpToPx()
            height = iconSize.size.dpToPx()
        }
    }

    fun setActionBadgeSize(iconSize: ActionIconSize) {
        binding.actionBadge.updateLayoutParams<LayoutParams> {
            width = iconSize.actionButtonSize.dpToPx()
            height = iconSize.actionButtonSize.dpToPx()
            binding.actionBadge.setPadding(iconSize.actionIconPadding.dpToPx())
        }
    }

    fun setBadgeSize(iconSize: ActionIconSize) {
        binding.badgeIcon.updateLayoutParams<LayoutParams> {
            width = iconSize.badgeSize.dpToPx()
            height = iconSize.badgeSize.dpToPx()
        }
    }

    fun setBadgeIconVisibility(isVisible: Boolean, iconSize: ActionIconSize) {
        if (iconSize.size != ActionIconSize.XSMALL.size || iconSize.size != ActionIconSize.XXLARGE.size) {
            binding.ivBadgeIcon.background = ContextCompat.getDrawable(context, R.drawable.ic_circle_filled)
            binding.badgeIcon.background = ContextCompat.getDrawable(context, R.drawable.background_rounded)
            binding.badgeIcon.isVisible = isVisible
            binding.actionBadge.isVisible = !isVisible
        }
    }

    fun setBadgeBackgroundTint(colorStateList: ColorStateList?) {
        binding.badgeIcon.backgroundTintList = colorStateList
    }

    fun setBadgeIconTint(colorStateList: ColorStateList?) {
        binding.ivBadgeIcon.backgroundTintList = colorStateList
    }

    fun setActionBadgeImage(iconRes: Drawable?) {
        binding.actionBadge.setImageDrawable(iconRes)
    }

    fun setOnActionBadgeClickListener(listener: OnClickListener?) {
        binding.actionBadge.setOnClickListener(listener)
    }

    fun setActionBadgeBackground(@DrawableRes background: Int) {
        binding.actionBadge.visibility = VISIBLE
        binding.actionBadge.setBackgroundResource(background)
    }


    fun setActionBadgeImageTint(colorStateList: ColorStateList?) {
        binding.actionBadge.imageTintList = colorStateList
    }


    fun setText(@StringRes stringRes: Int) {
        binding.actionText.setText(stringRes)
        if (type == ActionButtonType.TEXT) {
            MaterialTextDrawable.with(context)
                .text(binding.actionText.text.toString())
                .into(binding.actionImage)
        }
    }

    fun setText(text: String?) {
        binding.actionText.text = text
        if (type == ActionButtonType.TEXT) {
            MaterialTextDrawable.with(context)
                .textColor(textDrawableColor)
                .textBackgroundColor(textDrawableBackgroundColor)
                .text(binding.actionText.text.toString())
                .into(binding.actionImage)
        }
    }

    fun setType(type: ActionButtonType) {
        this.type = type
        when (type) {
            ActionButtonType.ACTION_BUTTON -> {
                binding.actionText.isVisible = !binding.actionText.text.isNullOrEmpty() && showActionText
            }

            ActionButtonType.AVATAR -> {
                binding.actionImage.imageTintList = null
                binding.actionImage.setPadding(0)
                binding.actionText.isVisible = !binding.actionText.text.isNullOrEmpty() && showActionText
            }

            else -> {
                binding.actionText.isVisible = !binding.actionText.text.isNullOrEmpty() && showActionText
                binding.actionImage.setBackgroundColor(ContextCompat.getColor(context, R.color.Transparent))
                binding.actionImage.imageTintList = null
                binding.actionImage.setPadding(0)
                if (type == ActionButtonType.TEXT) {
                    MaterialTextDrawable.with(context)
                        .textColor(textDrawableColor)
                        .textBackgroundColor(textDrawableBackgroundColor)
                        .text(binding.actionText.text.toString())
                        .into(binding.actionImage)
                }
            }
        }
    }

    enum class ActionIconSize(val size: Int, val actionButtonSize: Int, val actionIconPadding: Int, val padding: Int, val badgeSize: Int) {
        XXLARGE(80, 32, 8,16, 20),
        XLARGE(56, 24, 7,16, 14),
        LARGE(40, 16, 1,10, 10),
        MEDIUM(36, 14, 1,8, 9),
        SMALL(32, 8, 1,8, 8),
        XSMALL(24, 0, 4, 4,6);

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
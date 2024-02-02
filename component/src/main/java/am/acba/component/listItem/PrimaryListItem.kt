package am.acba.component.listItem

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import am.acba.component.badge.PrimaryBadge
import am.acba.component.badge.PrimaryBadge.BadgeType.Companion.findBadgeTypeByOrdinal
import am.acba.component.button.PrimaryActionTextButton
import am.acba.component.checkbox.PrimaryCheckbox
import am.acba.component.databinding.ListItemLayoutBinding
import am.acba.component.extensions.inflater
import am.acba.component.imageView.PrimaryImageView
import am.acba.component.listItem.PrimaryListItem.ListDescriptionComponentType.Companion.findDescriptionComponentTypeByOrdinal
import am.acba.component.listItem.PrimaryListItem.ListEndComponentType.Companion.findEndComponentTypeByOrdinal
import am.acba.component.listItem.PrimaryListItem.ListStartComponentType.Companion.findStartComponentTypeByOrdinal
import am.acba.component.radiobutton.PrimaryRadioButton
import am.acba.component.switcher.PrimarySwitcher
import am.acba.component.textView.PrimaryTextView
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat

class PrimaryListItem : FrameLayout {
    private val binding by lazy { ListItemLayoutBinding.inflate(context.inflater(), this, false) }
    val avatar by lazy {
        PrimaryActionTextButton(context).apply {
            setType(am.acba.component.button.PrimaryActionTextButton.ActionButtonType.AVATAR)
            setActionIconSize(PrimaryActionTextButton.ActionIconSize.LAGRE)
            showActionText(false)
        }
    }
    val badge by lazy { PrimaryBadge(context) }
    val startIcon by lazy { PrimaryImageView(context) }
    val endIcon by lazy { PrimaryImageView(context) }
    val switch by lazy { PrimarySwitcher(context) }
    val checkbox by lazy { PrimaryCheckbox(context) }
    val radiobutton by lazy { PrimaryRadioButton(context) }
    val description by lazy { PrimaryTextView(context) }
    val secondaryDescription by lazy { PrimaryTextView(context) }

    private var startComponentType = ListStartComponentType.NONE
    private var endComponentType = ListEndComponentType.NONE
    private var descriptionComponentType = ListDescriptionComponentType.NONE
    private var secondaryDescriptionComponentType = ListDescriptionComponentType.NONE

    private var startIconDrawable: Drawable? = null
    private var endIconDrawable: Drawable? = null

    private var titleText: String? = null
    private var titleTextStyle = -1
    private var descriptionText: String? = null
    private var descriptionTextStyle = -1
    private var secondaryDescriptionText: String? = null
    private var secondaryDescriptionTextStyle = -1

    private var isChecked = false
    private var isPreventDoubleClick = true
    private var clickInterval = 1000

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryListItem).apply {
            addView(binding.root)
            try {
                isPreventDoubleClick = getBoolean(R.styleable.PrimaryListItem_isPreventClick, true)
                val checked = getBoolean(R.styleable.PrimaryListItem_isChecked, false)
                clickInterval = getInt(R.styleable.PrimaryListItem_clickInterval, 1000)

                startComponentType = getInt(R.styleable.PrimaryListItem_startComponentType, 0).findStartComponentTypeByOrdinal()
                    ?: ListStartComponentType.NONE
                endComponentType = getInt(R.styleable.PrimaryListItem_endComponentType, 0).findEndComponentTypeByOrdinal()
                    ?: ListEndComponentType.NONE
                descriptionComponentType = getInt(R.styleable.PrimaryListItem_descriptionComponentType, 0).findDescriptionComponentTypeByOrdinal()
                    ?: ListDescriptionComponentType.NONE
                secondaryDescriptionComponentType =
                    getInt(R.styleable.PrimaryListItem_secondaryDescriptionComponentType, 0).findDescriptionComponentTypeByOrdinal()
                        ?: ListDescriptionComponentType.NONE

                val startIconTint = getColorStateList(R.styleable.PrimaryListItem_listStartIconTint)
                val endIconTint = getColorStateList(R.styleable.PrimaryListItem_listEndIconTint)
                setStartIconTint(startIconTint)
                setEndIconTint(endIconTint)

                setIsChecked(checked)
                setStartIcon(getDrawable(R.styleable.PrimaryListItem_listStartIcon))
                setEndIcon(getDrawable(R.styleable.PrimaryListItem_listEndIcon))
                setTitleText(getString(R.styleable.PrimaryListItem_listTitleText))
                setTitleTextAppearance(getResourceId(R.styleable.PrimaryListItem_listTitleTextAppearance, R.style.Body1_Bold))

                descriptionText = getString(R.styleable.PrimaryListItem_listDescriptionText)
                descriptionTextStyle = getResourceId(R.styleable.PrimaryListItem_listDescriptionTextAppearance, R.style.Body2_Regular)

                secondaryDescriptionText = getString(R.styleable.PrimaryListItem_listSecondaryDescriptionText)
                secondaryDescriptionTextStyle = getResourceId(R.styleable.PrimaryListItem_listSecondaryDescriptionTextAppearance, R.style.Body2_Regular)

                setStartComponent()
                setEndComponent()
                setDescription(binding.listDescriptionComponentContainer, descriptionComponentType, description, descriptionTextStyle, descriptionText)
                setDescription(
                    binding.listSecondaryDescriptionComponentContainer,
                    secondaryDescriptionComponentType,
                    secondaryDescription,
                    secondaryDescriptionTextStyle,
                    secondaryDescriptionText
                )
                val showBadge = getBoolean(R.styleable.PrimaryListItem_showBadge, false)
                binding.listBadgeContainer.isVisible = showBadge
                if (showBadge) {
                    val text = getString(R.styleable.PrimaryListItem_badgeText)
                    val textColor = getColor(R.styleable.PrimaryListItem_badgeTextColor, ContextCompat.getColor(context, R.color.White))
                    val icon = getDrawable(R.styleable.PrimaryListItem_badgeIcon)
                    val iconTint = getColorStateList(R.styleable.PrimaryListItem_badgeIconTint)
                    val backgroundTint = getColorStateList(R.styleable.PrimaryListItem_badgeBackgroundTint)

                    val badgeTextPaddingTop = getDimension(R.styleable.PrimaryListItem_badgePaddingTop, -1f)
                    val badgeTextPaddingBottom = getDimension(R.styleable.PrimaryListItem_badgePaddingTop, -1f)
                    val badgeTextPaddingEnd = getDimension(R.styleable.PrimaryListItem_badgePaddingTop, -1f)
                    val badgeTextPaddingStart = getDimension(R.styleable.PrimaryListItem_badgePaddingTop, -1f)
                    val badgeTextStyle = getResourceId(R.styleable.PrimaryListItem_badgeTextAppearance, R.style.Small_Regular)
                    val badgeType = getInt(R.styleable.PrimaryListItem_badgeType, 0).findBadgeTypeByOrdinal() ?: PrimaryBadge.BadgeType.TEXT
                    badge.setBadgeType(badgeType)
                    when (badgeType) {
                        PrimaryBadge.BadgeType.DOT -> {
                            badge.setBadgeIconTint(iconTint)
                            badge.setBadgeBackgroundTint(backgroundTint)
                        }

                        PrimaryBadge.BadgeType.SMALL_ICON -> {
                            badge.setBadgeIcon(icon)
                            badge.setBadgeIconTint(iconTint)
                        }

                        PrimaryBadge.BadgeType.LARGE_ICON -> {
                            badge.setBadgeIcon(icon)
                            badge.setBadgeIconTint(iconTint)
                        }

                        PrimaryBadge.BadgeType.NUMBER -> {
                            badge.setBadgeText(text)
                            badge.setBadgeTextColor(textColor)
                            badge.setBadgeBackgroundTint(backgroundTint)
                            badge.setPaddings(badgeTextPaddingStart, badgeTextPaddingEnd, badgeTextPaddingTop, badgeTextPaddingBottom)
                            badge.setTextAppearance(badgeTextStyle)
                        }

                        PrimaryBadge.BadgeType.TEXT -> {
                            badge.setBadgeText(text)
                            badge.setBadgeTextColor(textColor)
                            badge.setBadgeBackgroundTint(backgroundTint)
                            badge.setPaddings(badgeTextPaddingStart, badgeTextPaddingEnd, badgeTextPaddingTop, badgeTextPaddingBottom)
                            badge.setTextAppearance(badgeTextStyle)
                        }
                    }
                    binding.listBadgeContainer.addView(badge)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    fun getTitleTextView() = binding.listTitle

    fun showBadge() {
        binding.listBadgeContainer.removeAllViews()
        binding.listBadgeContainer.addView(badge)
        binding.listBadgeContainer.isVisible = true
    }

    fun hideBadge() {
        binding.listBadgeContainer.removeAllViews()
        binding.listBadgeContainer.isVisible = false
    }

    fun setStartIconTint(colorStateList: ColorStateList?) {
        if (startComponentType == ListStartComponentType.ICON) {
            startIcon.imageTintList = colorStateList
        }
    }

    fun setEndIconTint(colorStateList: ColorStateList?) {
        if (endComponentType == ListEndComponentType.ICON) {
            endIcon.imageTintList = colorStateList
        }
    }

    fun setIsChecked(checked: Boolean) {
        isChecked = checked
        when (endComponentType) {
            ListEndComponentType.NONE -> Unit
            ListEndComponentType.ICON -> Unit
            ListEndComponentType.CHECKBOX -> checkbox.isChecked = isChecked
            ListEndComponentType.RADIOBUTTON -> radiobutton.isChecked = isChecked
            ListEndComponentType.SWITCH -> switch.isChecked = isChecked
        }
    }

    fun isChecked(): Boolean {
        return isChecked
    }

    fun setStartComponent() {
        binding.listStartComponentContainer.removeAllViews()
        binding.listStartComponentContainer.isVisible = startComponentType != ListStartComponentType.NONE
        when (startComponentType) {
            ListStartComponentType.NONE -> Unit
            ListStartComponentType.AVATAR -> {
                binding.listStartComponentContainer.addView(avatar)
            }

            ListStartComponentType.ICON -> {
                binding.listStartComponentContainer.addView(startIcon)
            }
        }
    }

    fun setEndComponent() {
        binding.listEndComponentContainer.removeAllViews()
        binding.listEndComponentContainer.isVisible = endComponentType != ListEndComponentType.NONE
        when (endComponentType) {
            ListEndComponentType.NONE -> Unit
            ListEndComponentType.ICON -> {
                binding.listEndComponentContainer.addView(endIcon)
            }

            ListEndComponentType.CHECKBOX -> {
                binding.listEndComponentContainer.addView(checkbox)
            }

            ListEndComponentType.RADIOBUTTON -> {
                binding.listEndComponentContainer.addView(radiobutton)
            }

            ListEndComponentType.SWITCH -> {
                binding.listEndComponentContainer.addView(switch)
            }
        }
    }

    fun setStartIcon(icon: Drawable?) {
        startIconDrawable = icon
        if (startComponentType == ListStartComponentType.ICON) {
            startIcon.setImageDrawable(icon)
        } else if (startComponentType == ListStartComponentType.AVATAR) {
            avatar.setIcon(icon)
        }
    }

    fun setEndIcon(icon: Drawable?) {
        endIconDrawable = icon
        if (endComponentType == ListEndComponentType.ICON) {
            endIcon.setImageDrawable(icon)
        }
    }

    fun setTitleText(text: String?) {
        titleText = text
        binding.listTitle.text = titleText
    }

    fun setTitleTextAppearance(textStyle: Int) {
        titleTextStyle = textStyle
        TextViewCompat.setTextAppearance(binding.listTitle, titleTextStyle)
    }

    fun setDescription(
        listDescriptionComponentContainer: FrameLayout,
        descriptionComponentType: ListDescriptionComponentType,
        description: PrimaryTextView,
        textStyle: Int,
        descriptionText: String?
    ) {
        listDescriptionComponentContainer.isVisible = descriptionComponentType != ListDescriptionComponentType.NONE
        listDescriptionComponentContainer.removeAllViews()
        when (descriptionComponentType) {
            ListDescriptionComponentType.NONE -> Unit
            ListDescriptionComponentType.SINGLE_LINE -> {
                listDescriptionComponentContainer.addView(description)
                description.setSingleLine()
                description.ellipsize = TextUtils.TruncateAt.END
                TextViewCompat.setTextAppearance(description, textStyle)
                description.text = descriptionText
            }

            ListDescriptionComponentType.TWO_LINE -> {
                listDescriptionComponentContainer.addView(description)
                description.isSingleLine = false
                description.maxLines = 2
                description.ellipsize = TextUtils.TruncateAt.END
                TextViewCompat.setTextAppearance(description, textStyle)
                description.text = descriptionText
            }

            ListDescriptionComponentType.THREE_LINE -> {
                listDescriptionComponentContainer.addView(description)
                description.isSingleLine = false
                description.maxLines = 3
                description.ellipsize = TextUtils.TruncateAt.END
                TextViewCompat.setTextAppearance(description, textStyle)
                description.text = descriptionText
            }
        }
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        if (isPreventDoubleClick && onClickListener != null) {
            super.setOnClickListener(PreventDoubleClickListener(onClickListener, clickInterval))
        } else {
            super.setOnClickListener(onClickListener)
        }
    }

    enum class ListStartComponentType {
        NONE,
        AVATAR,
        ICON;

        companion object {
            fun Int.findStartComponentTypeByOrdinal(): ListStartComponentType? {
                return entries.find { it.ordinal == this }
            }
        }
    }

    enum class ListDescriptionComponentType {
        NONE,
        SINGLE_LINE,
        TWO_LINE,
        THREE_LINE;

        companion object {
            fun Int.findDescriptionComponentTypeByOrdinal(): ListDescriptionComponentType? {
                return entries.find { it.ordinal == this }
            }
        }
    }

    enum class ListEndComponentType {
        NONE,
        ICON,
        CHECKBOX,
        RADIOBUTTON,
        SWITCH;

        companion object {
            fun Int.findEndComponentTypeByOrdinal(): ListEndComponentType? {
                return entries.find { it.ordinal == this }
            }
        }
    }
}
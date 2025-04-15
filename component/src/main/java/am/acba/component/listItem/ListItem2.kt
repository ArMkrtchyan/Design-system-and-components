package am.acba.component.listItem

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import am.acba.component.badge.PrimaryBadge
import am.acba.component.badge.PrimaryBadge.BadgeType.Companion.findBadgeTypeByOrdinal
import am.acba.component.button.PrimaryActionTextButton
import am.acba.component.checkbox.PrimaryCheckbox
import am.acba.component.databinding.LayoutListItem2Binding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.imageView.PrimaryImageView
import am.acba.component.listItem.ListItem2.ListEndComponentType.Companion.findEndComponentTypeByOrdinal
import am.acba.component.listItem.ListItem2.ListIconDimensionType.Companion.findIconDimensionByOrdinal
import am.acba.component.listItem.ListItem2.ListStartComponentType.Companion.findStartComponentTypeByOrdinal
import am.acba.component.listItem.ListItem2.ListTextComponentType.Companion.findTextComponentTypeByOrdinal
import am.acba.component.radiobutton.PrimaryRadioButton
import am.acba.component.switcher.PrimarySwitcher
import am.acba.component.textView.PrimaryTextView
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.contains
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams

class ListItem2 : FrameLayout {
    private val binding by lazy {
        LayoutListItem2Binding.inflate(
            context.inflater(),
            this,
            false
        )
    }
    val avatar by lazy {
        PrimaryActionTextButton(context).apply {
            setType(PrimaryActionTextButton.ActionButtonType.AVATAR)
            setActionImageSize(PrimaryActionTextButton.ActionIconSize.LARGE)
            showActionText(false)
        }
    }

    private val startIcon by lazy { PrimaryImageView(context) }
    private val endIcon by lazy { PrimaryImageView(context) }
    private val switch by lazy { PrimarySwitcher(context) }
    private val checkbox by lazy { PrimaryCheckbox(context) }
    private val radiobutton by lazy { PrimaryRadioButton(context) }
    private val body1 by lazy {
        PrimaryTextView(context).apply {
            isSingleLine = true
            ellipsize = TextUtils.TruncateAt.END
        }
    }
    private val body2 by lazy {
        PrimaryTextView(context).apply {
            isSingleLine = true
            ellipsize = TextUtils.TruncateAt.END
        }
    }
    private val body3 by lazy {
        PrimaryTextView(context).apply {
            isSingleLine = true
            ellipsize = TextUtils.TruncateAt.END
        }
    }
    private val body4 by lazy {
        PrimaryTextView(context).apply {
            isSingleLine = true
            ellipsize = TextUtils.TruncateAt.END
        }
    }
    var startComponentType = ListStartComponentType.NONE
        set(value) {
            field = value
            setStartComponent(value)
        }
    var endComponentType = ListEndComponentType.NONE
        set(value) {
            field = value
            setSecondEndComponent(value)
        }
    var textComponentType = ListTextComponentType.DEFAULT_SINGLE_VIEW_BODY
        set(value) {
            field = value
            setTextComponent(value)
        }
    var startIconDimensionsType = ListIconDimensionType.CUSTOM
        set(value) {
            field = value
            setStartIconParams(value)
        }
    var firstEndIconDimensionsType = ListIconDimensionType.CUSTOM
        set(value) {
            field = value
            setFirstEndIconParams(value)
        }
    var secondEndIconDimensionsType = ListIconDimensionType.CUSTOM
        set(value) {
            field = value
            setSecondEndIconParams(value)
        }
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
        context.obtainStyledAttributes(attrs, R.styleable.ListItem2).apply {
            addView(binding.root)
            try {
                val transparent = context.getColor(R.color.Transparent)
                val layoutBackgroundColor = getColor(R.styleable.ListItem2_listItemBackgroundColor, transparent)
                val layoutBackgroundRadius = getInt(R.styleable.ListItem2_listItemRadius, 0)
                val layoutBackgroundStrokeWidth = getInt(R.styleable.ListItem2_listItemStrokeWidth, 0)
                val layoutBackgroundStrokeColor = getColor(R.styleable.ListItem2_listItemStrokeColor, transparent)

                val text = getString(R.styleable.ListItem2_listItemTitle)
                val isTitleSingleLine: Boolean = getBoolean(R.styleable.ListItem2_listItemIsTitleSingleLine, true)
                val titleMaxLines = getInt(R.styleable.ListItem2_listItemTitleMaxLines, -1)
                val textAppearance = getResourceId(R.styleable.ListItem2_listItemTitleTextAppearance, -1)
                val textColor = getColor(R.styleable.ListItem2_listItemTitleTextColor, -1)

                val startIcon = getDrawable(R.styleable.ListItem2_listItemStartIcon)
                val startIconTint = getColorStateList(R.styleable.ListItem2_listItemStartIconTint)
                val startIconBackgroundColor = getColorStateList(R.styleable.ListItem2_listItemStartIconBackgroundTint)

                val showStartIconBadge = getBoolean(R.styleable.ListItem2_listItemShowStartIconBadge, false)
                val startIconBadgeStrokeColor = getResourceId(R.styleable.ListItem2_listItemStartIconBadgeStrokeColor, -1)
                val startIconBadgeDotTint = getColorStateList(R.styleable.ListItem2_listItemStartIconBadgeDotTint)

                val showBadge = getBoolean(R.styleable.ListItem2_listItemShowBadge, false)
                val badgeType = getInt(R.styleable.ListItem2_listItemBadgeType, 0).findBadgeTypeByOrdinal() ?: PrimaryBadge.BadgeType.TEXT
                val badgeText = getString(R.styleable.ListItem2_listItemBadgeText)
                val badgeTextStyle = getResourceId(R.styleable.ListItem2_listItemBadgeTextAppearance, R.style.Small_Regular)
                val badgeTextColor = getColor(R.styleable.ListItem2_listItemBadgeTextColor, context.getColorFromAttr(R.attr.contentPrimary))
                val badgeIcon = getDrawable(R.styleable.ListItem2_listItemBadgeIcon)
                val badgeIconTint = getColorStateList(R.styleable.ListItem2_listItemBadgeIconTint)
                val badgeBackgroundTint = getColorStateList(R.styleable.ListItem2_listItemBadgeBackgroundTint)

                val firstEndIcon = getDrawable(R.styleable.ListItem2_listItemFirstEndIcon)
                val firstEndIconTint = getColorStateList(R.styleable.ListItem2_listItemFirstEndIconTint)
                val firstEndIconBackgroundTint = getColorStateList(R.styleable.ListItem2_listItemFirstEndIconBackgroundTint)

                val secondEndIcon = getDrawable(R.styleable.ListItem2_listItemSecondEndIcon)
                val secondEndIconTint = getColorStateList(R.styleable.ListItem2_listItemSecondEndIconTint)
                val secondEndIconBackgroundTint = getColorStateList(R.styleable.ListItem2_listItemSecondEndIconBackgroundTint)

                val isCheckboxChecked = getBoolean(R.styleable.ListItem2_listItemCheckboxChecked, false)
                val isCheckboxEnabled = getBoolean(R.styleable.ListItem2_listItemCheckboxEnabled, true)

                val isSwitchChecked = getBoolean(R.styleable.ListItem2_listItemSwitchChecked, false)
                val isSwitchEnabled = getBoolean(R.styleable.ListItem2_listItemSwitchEnabled, true)

                val isRadioButtonChecked = getBoolean(R.styleable.ListItem2_listItemRadioButtonChecked, false)
                val isRadioButtonEnabled = getBoolean(R.styleable.ListItem2_listItemRadioButtonEnabled, true)

                val isBodyContainerVisible = getBoolean(R.styleable.ListItem2_listItemIsBodyContainerVisible, false)
                val bodyColor = getColor(R.styleable.ListItem2_listItemBodyColor, -1)
                val bodyStyle = getResourceId(R.styleable.ListItem2_listItemBodyStyle, -1)
                val isBody1SingleLine = getBoolean(R.styleable.ListItem2_listItemBody1SingleLine, false)
                val body1MaxLines = getInt(R.styleable.ListItem2_listItemBody1MaxLines, -1)

                val body1Text = getString(R.styleable.ListItem2_listItemBody1Text)
                val body2Text = getString(R.styleable.ListItem2_listItemBody2Text)
                val body3Text = getString(R.styleable.ListItem2_listItemBody3Text)
                val body4Text = getString(R.styleable.ListItem2_listItemBody4Text)

                val showDivider = getBoolean(R.styleable.ListItem2_listItemShowDivider, false)

                textComponentType = getInt(R.styleable.ListItem2_listItemTextType, 0).findTextComponentTypeByOrdinal()
                startComponentType = getInt(R.styleable.ListItem2_listItemStartComponentType, 0).findStartComponentTypeByOrdinal()
                endComponentType = getInt(R.styleable.ListItem2_listItemEndComponentType, 0).findEndComponentTypeByOrdinal()
                startIconDimensionsType = getInt(R.styleable.ListItem2_listItemStartIconDimensionsType, 0).findIconDimensionByOrdinal()
                firstEndIconDimensionsType = getInt(R.styleable.ListItem2_listItemFirstEndIconDimensionsType, 0).findIconDimensionByOrdinal()
                secondEndIconDimensionsType = getInt(R.styleable.ListItem2_listItemSecondEndIconDimensionsType, 0).findIconDimensionByOrdinal()

                setListItemBackgroundColor(layoutBackgroundColor)
                setListItemRadius(layoutBackgroundRadius)
                setListItemStrokeWidth(layoutBackgroundStrokeWidth)
                setListItemStrokeColor(layoutBackgroundStrokeColor)

                setTitleText(text)
                if (textComponentType == ListTextComponentType.CUSTOM) {
                    setTitleSingleLine(isTitleSingleLine)
                }
                if (titleMaxLines != -1) setTitleMaxLines(titleMaxLines)
                if (textAppearance != -1) setTitleTextAppearance(textAppearance)
                if (textColor != -1) setTitleTextColor(textColor)

                if (startComponentType == ListStartComponentType.ICON && startIcon != null) {
                    setStartIconDrawable(startIcon)
                    setStartIconTint(startIconTint)
                    setStartIconBackgroundTint(startIconBackgroundColor)
                }

                if (showStartIconBadge) {
                    setStartIconBadgeDot(startIconBadgeDotTint, startIconBadgeStrokeColor)
                }

                if (showBadge) {
                    setBadge(
                        type = badgeType,
                        text = badgeText,
                        textStyle = badgeTextStyle,
                        textColor = badgeTextColor,
                        icon = badgeIcon,
                        iconTint = badgeIconTint,
                        backgroundTint = badgeBackgroundTint
                    )
                }

                firstEndIcon?.let {
                    setFirstEndIconDrawable(it)
                    setFirstEndIconTint(firstEndIconTint)
                    setFirstEndIconBackgroundTint(firstEndIconBackgroundTint)
                }

                if (endComponentType == ListEndComponentType.ICON && secondEndIcon != null) {
                    setSecondEndComponentIconDrawable(secondEndIcon)
                    setSecondEndComponentIconTint(secondEndIconTint)
                    setSecondEndComponentIconBackgroundTint(secondEndIconBackgroundTint)
                }

                if (endComponentType == ListEndComponentType.CHECKBOX) {
                    setCheckboxChecked(isCheckboxChecked)
                    setCheckboxEnabled(isCheckboxEnabled)
                }

                if (endComponentType == ListEndComponentType.SWITCH) {
                    setSwitchChecked(isSwitchChecked)
                    setSwitchEnabled(isSwitchEnabled)
                }

                if (endComponentType == ListEndComponentType.RADIOBUTTON) {
                    setRadioButtonChecked(isRadioButtonChecked)
                    setRadioButtonEnabled(isRadioButtonEnabled)
                }

                if (textComponentType != ListTextComponentType.TITLE_ONLY) {
                    setBodyContainerVisibility(isBodyContainerVisible)
                    setBody1Text(body1Text)
                    setBody2Text(body2Text)
                    setBody3Text(body3Text)
                    setBody4Text(body4Text)

                    if (body1MaxLines != -1) setBody1MaxLines(body1MaxLines)
                    if (bodyColor != -1) setBodyColor(bodyColor)
                    if (bodyStyle != -1) (setBodyStyle(bodyStyle))

                    if (textComponentType == ListTextComponentType.CUSTOM) {
                        setBody1SingleLine(isBody1SingleLine)
                    }
                }

                setDividerVisibility(showDivider)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    private fun setStartIconBadgeDot(dotTint: ColorStateList?, @ColorRes strokeColor: Int) {
        binding.badgeStartIcon.apply {
            isVisible = true
            setBadgeType(PrimaryBadge.BadgeType.DOT)
            setBadgeBackgroundTint(dotTint)
            if (strokeColor != -1) {
                setStrokeColor(strokeColor)
            }
        }
    }

    private fun setBadge(
        type: PrimaryBadge.BadgeType,
        text: String?,
        textStyle: Int,
        textColor: Int,
        icon: Drawable?,
        iconTint: ColorStateList?,
        backgroundTint: ColorStateList?
    ) {
        binding.badgeListItem.apply {
            isVisible = true
            setBadgeType(type)
            setBadgeText(text)
            setTextAppearance(textStyle)
            setBadgeTextColor(textColor)
            setBadgeIcon(icon)
            setBadgeIconTint(iconTint)
            setBadgeBackgroundTint(backgroundTint)
        }
    }

    private fun setStartComponent(startComponentType: ListStartComponentType) {
        binding.layoutStartComponent.apply {
            removeAllViews()
            when (startComponentType) {
                ListStartComponentType.ICON -> {
                    addView(startIcon)
                    isVisible = true
                }

                ListStartComponentType.AVATAR -> {
                    addView(avatar)
                    isVisible = true
                }

                ListStartComponentType.NONE -> {
                    isVisible = false
                }
            }
        }
    }

    private fun setStartIconParams(startIconSize: ListIconDimensionType) {
        startIcon.apply {
            when (startIconSize) {
                ListIconDimensionType.CUSTOM -> Unit
                ListIconDimensionType.WITH_BACKGROUND -> {
                    updateIconDimensions(36.dpToPx(), 8.dpToPx())
                    setStartIconBackground(true, AppCompatResources.getDrawable(context, R.drawable.background_rounded_corner_8))
                }

                ListIconDimensionType.WITHOUT_BACKGROUND -> {
                    updateIconDimensions(24.dpToPx(), 0)
                    setStartIconBackgroundTint(context.getColorStateList(R.color.Transparent))
                }
            }
        }
    }

    private fun setFirstEndIconParams(startIconSize: ListIconDimensionType) {
        binding.ivFirstEndIcon.apply {
            when (startIconSize) {
                ListIconDimensionType.CUSTOM -> Unit
                ListIconDimensionType.WITH_BACKGROUND -> {
                    updateIconDimensions(24.dpToPx(), 4.dpToPx())
                    setFirstEndIconBackground(true, AppCompatResources.getDrawable(context, R.drawable.background_with_border_4))
                }

                ListIconDimensionType.WITHOUT_BACKGROUND -> {
                    updateIconDimensions(24.dpToPx(), 0)
                    setFirstEndIconBackgroundTint(context.getColorStateList(R.color.Transparent))
                }
            }
        }
    }

    private fun setSecondEndIconParams(startIconSize: ListIconDimensionType) {
        endIcon.apply {
            when (startIconSize) {
                ListIconDimensionType.CUSTOM -> Unit
                ListIconDimensionType.WITH_BACKGROUND -> {
                    updateIconDimensions(24.dpToPx(), 4.dpToPx())
                    setSecondEndComponentIconBackground(true, AppCompatResources.getDrawable(context, R.drawable.background_with_border_4))
                }

                ListIconDimensionType.WITHOUT_BACKGROUND -> {
                    updateIconDimensions(24.dpToPx(), 0)
                    setSecondEndComponentIconBackgroundTint(context.getColorStateList(R.color.Transparent))
                }
            }
        }
    }

    private fun PrimaryImageView.updateIconDimensions(size: Int, padding: Int) {
        updateLayoutParams<ViewGroup.LayoutParams> {
            height = size
            width = size
        }
        setPadding(padding, padding, padding, padding)
    }

    private fun setTextComponent(textComponentType: ListTextComponentType) {
        when (textComponentType) {
            ListTextComponentType.DEFAULT_SINGLE_VIEW_BODY -> setTextStyleDefault(true)
            ListTextComponentType.DEFAULT_MULTI_VIEW_BODY -> setTextStyleDefault(false)
            ListTextComponentType.TITLE_ONLY -> setTextStyleTitleOnly()
            ListTextComponentType.OVER_LINE_SINGLE_VIEW_BODY -> setTextStyleOverLine(true)
            ListTextComponentType.OVER_LINE_MULTI_VIEW_BODY -> setTextStyleOverLine(false)
            ListTextComponentType.CUSTOM -> alignStartIconTop(false)
        }
    }

    private fun setTextStyleDefault(hasSingleViewBody: Boolean) {
        alignStartIconTop(true)
        setTitleTextStyleOverLine(false)
        setBodyContainerVisibility(true)
        setBodyStyle(R.style.Body2_Regular)
        setBodyColor(context.getColorFromAttr(R.attr.contentPrimaryTonal1))
        setBodyLinesType(hasSingleViewBody, 4)
    }

    private fun setTextStyleTitleOnly() {
        alignStartIconTop(false)
        setTitleTextStyleOverLine(false)
        setBodyContainerVisibility(false)
    }

    private fun setTextStyleOverLine(hasSingleViewBody: Boolean) {
        alignStartIconTop(true)
        setTitleTextStyleOverLine(true)
        setBodyContainerVisibility(true)
        setBodyStyle(R.style.Body1_Regular)
        setBodyColor(context.getColorFromAttr(R.attr.contentPrimary))
        setBodyLinesType(hasSingleViewBody, 2)
    }

    private fun setTitleTextStyleOverLine(isOverLine: Boolean) {
        setTitleSingleLine(isOverLine)
        if (isOverLine) {
            setTitleTextAppearance(R.style.Small_Regular)
            setTitleTextColor(context.getColorFromAttr(R.attr.contentPrimaryTonal1))
        } else {
            setTitleMaxLines(2)
            setTitleTextAppearance(R.style.Body1_Bold)
            setTitleTextColor(context.getColorFromAttr(R.attr.contentPrimary))
        }
    }

    private fun setBodyLinesType(hasSingleViewBody: Boolean, maxLines: Int = 2) {
        setBody1SingleLine(!hasSingleViewBody)
        setBody1Visibility(true)
        if (hasSingleViewBody) {
            setBody1MaxLines(maxLines)
            setBody2Visibility(false)
            setBody3Visibility(false)
            setBody4Visibility(false)
        }
    }

    private fun setSecondEndComponent(endComponentType: ListEndComponentType) {
        binding.layoutSecondEndComponent.apply {
            removeAllViews()
            var rightPadding = 0
            var topPadding = 0
            when (endComponentType) {
                ListEndComponentType.NONE -> {
                    isVisible = false
                }

                ListEndComponentType.ICON -> {
                    rightPadding = 10.dpToPx()
                    addView(endIcon)
                    isVisible = true
                }

                ListEndComponentType.CHECKBOX -> {
                    topPadding = 2.dpToPx()
                    addView(checkbox)
                    isVisible = true
                }

                ListEndComponentType.SWITCH -> {
                    rightPadding = 10.dpToPx()
                    addView(switch)
                    isVisible = true
                }

                ListEndComponentType.RADIOBUTTON -> {
                    addView(radiobutton)
                    isVisible = true
                }
            }
            setPadding(0, topPadding, rightPadding, 0)
        }
    }


    private fun alignStartIconTop(alignTop: Boolean) {
        binding.apply {
            barrierCentre.referencedIds = getBarrierReferenceIds(alignTop)

            layoutStartComponent.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToTop = if (alignTop) tvTitle.id else ConstraintLayout.LayoutParams.PARENT_ID
                bottomToBottom = topToTop
            }

            tvTitle.updateLayoutParams<ConstraintLayout.LayoutParams> {
                if (alignTop) {
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = barrierCentre.id
                } else {
                    topToTop = binding.layoutStartComponent.id
                    bottomToBottom = binding.layoutStartComponent.id
                }
            }
        }
    }

    private fun getBarrierReferenceIds(alignTop: Boolean) = mutableListOf(
        binding.tvTitle.id,
        binding.ivFirstEndIcon.id,
        binding.layoutSecondEndComponent.id,
    ).apply {
        if (!alignTop) add(binding.layoutStartComponent.id)
    }.toIntArray()

    private fun ensureStartComponentIcon() {
        if (startComponentType != ListStartComponentType.ICON) {
            startComponentType = ListStartComponentType.ICON
        }
    }

    private fun ensureSecondEndComponentIcon() {
        if (endComponentType != ListEndComponentType.ICON) {
            endComponentType = ListEndComponentType.ICON
        }
    }

    private fun ensureSecondEndComponentCheckbox() {
        if (endComponentType != ListEndComponentType.CHECKBOX) {
            endComponentType = ListEndComponentType.CHECKBOX
        }
    }

    private fun ensureSecondEndComponentSwitch() {
        if (endComponentType != ListEndComponentType.SWITCH) {
            endComponentType = ListEndComponentType.SWITCH
        }
    }

    private fun ensureSecondEndComponentRadioButton() {
        if (endComponentType != ListEndComponentType.RADIOBUTTON) {
            endComponentType = ListEndComponentType.RADIOBUTTON
        }
    }

    private fun ensureBodyAdded(body: PrimaryTextView) {
        binding.layoutBodies.apply {
            if (!contains(body)) addView(body)
            isVisible = true
            body.isVisible = true
        }
    }

    private fun clearBody(body: PrimaryTextView) {
        binding.layoutBodies.apply {
            if (contains(body)) {
                removeView(body)
                if (isEmpty()) isVisible = false
            }
        }
    }

    private fun PrimaryTextView.setAppearance(colorRes: Int) {
        setTextAppearance(context, colorRes)
    }

    fun setListItemBackgroundColor(@ColorInt color: Int?) {
        color?.let { binding.cvListItem.setCardBackgroundColor(it) }
    }

    fun setListItemRadius(radius: Int?) {
        radius?.let { binding.cvListItem.radius = it.dpToPx().toFloat() }
    }

    fun setListItemStrokeWidth(width: Int?) {
        width?.let { binding.cvListItem.strokeWidth = it }
    }

    fun setListItemStrokeColor(@ColorInt color: Int?) {
        color?.let { binding.cvListItem.strokeColor = it }
    }

    fun setTitleText(text: String?) {
        text?.let { binding.tvTitle.text = it }
    }

    fun setTitleSingleLine(isSingleLine: Boolean?) {
        binding.tvTitle.isSingleLine = isSingleLine == true
    }

    fun setTitleMaxLines(maxLines: Int?) {
        maxLines?.let {
            binding.tvTitle.apply {
                post { this.maxLines = it }
            }
        }
    }

    fun setTitleTextAppearance(resId: Int?) {
        resId?.let { binding.tvTitle.setTextAppearance(it) }
    }

    fun setTitleTextColor(resId: Int?) {
        resId?.let { binding.tvTitle.setTextColor(it) }
    }

    fun setStartIconDrawable(drawable: Drawable?) {
        drawable?.let {
            ensureStartComponentIcon()
            startIcon.setImageDrawable(it)
        }
    }

    fun setStartIconTint(colorStateList: ColorStateList?) {
        ensureStartComponentIcon()
        startIcon.imageTintList = colorStateList

    }

    fun setStartIconBackground(clipToOutline: Boolean = false, backgroundRes: Drawable?) {
        ensureStartComponentIcon()
        startIcon.apply {
            this.clipToOutline = clipToOutline
            backgroundRes?.let { background = it }
        }
    }

    fun setStartIconBackgroundTint(color: ColorStateList?) {
        color?.let {
            ensureStartComponentIcon()
            startIcon.backgroundTintList = it
        }
    }

    fun getBadgeListItem() = binding.badgeListItem
    fun getBadgeStartIcon() = binding.badgeStartIcon

    fun setFirstEndIconVisibility(isVisible: Boolean) {
        binding.ivFirstEndIcon.isVisible = isVisible
    }

    fun setFirstEndIconDrawable(drawable: Drawable?) {
        drawable?.let {
            setFirstEndIconVisibility(true)
            binding.ivFirstEndIcon.setImageDrawable(drawable)
        }
    }

    fun setFirstEndIconTint(colorStateList: ColorStateList?) {
        binding.ivFirstEndIcon.imageTintList = colorStateList
    }

    fun setFirstEndIconBackgroundTint(color: ColorStateList?) {
        color?.let { binding.ivFirstEndIcon.backgroundTintList = it }
    }

    fun setFirstEndIconBackground(clipToOutline: Boolean = false, backgroundRes: Drawable?) {
        binding.ivFirstEndIcon.apply {
            this.clipToOutline = clipToOutline
            backgroundRes?.let { background = it }
        }
    }

    fun setSecondEndComponentIconDrawable(drawable: Drawable?) {
        drawable?.let {
            ensureSecondEndComponentIcon()
            endIcon.setImageDrawable(it)
        }
    }

    fun setSecondEndComponentIconTint(colorStateList: ColorStateList?) {
        colorStateList?.let {
            ensureSecondEndComponentIcon()
            endIcon.imageTintList = it
        }
    }

    fun setSecondEndComponentIconBackgroundTint(color: ColorStateList?) {
        color?.let {
            ensureSecondEndComponentIcon()
            endIcon.backgroundTintList = it
        }
    }

    fun setSecondEndComponentIconBackground(
        clipToOutline: Boolean = false,
        backgroundRes: Drawable?
    ) {
        ensureSecondEndComponentIcon()
        endIcon.apply {
            this.clipToOutline = clipToOutline
            backgroundRes?.let { background = it }
        }
    }

    fun setCheckboxChecked(isChecked: Boolean) {
        ensureSecondEndComponentCheckbox()
        checkbox.isChecked = isChecked
    }

    fun isCheckboxChecked() = checkbox.isChecked

    fun setCheckboxEnabled(isEnabled: Boolean) {
        ensureSecondEndComponentCheckbox()
        checkbox.isEnabled = isEnabled
    }

    fun isCheckboxEnabled() = checkbox.isEnabled

    fun setSwitchChecked(isChecked: Boolean) {
        ensureSecondEndComponentSwitch()
        switch.isChecked = isChecked
    }

    fun isSwitchChecked() = switch.isChecked

    fun setSwitchEnabled(isEnabled: Boolean) {
        ensureSecondEndComponentSwitch()
        switch.isEnabled = isEnabled
    }

    fun isSwitchEnabled() = switch.isEnabled

    fun setRadioButtonChecked(isChecked: Boolean) {
        ensureSecondEndComponentRadioButton()
        radiobutton.isChecked = isChecked
    }

    fun isRadioButtonChecked() = radiobutton.isChecked

    fun setRadioButtonEnabled(isEnabled: Boolean) {
        ensureSecondEndComponentRadioButton()
        radiobutton.isEnabled = isEnabled
    }

    fun isRadioButtonEnabled() = radiobutton.isEnabled

    fun setMultiLineBody(isCombined: Boolean?, maxLines: Int? = null) {
        if (isCombined == true) {
            setBody1MaxLines(maxLines ?: 1)

            setBody1Visibility(true)
            setBody2Visibility(false)
            setBody3Visibility(false)
            setBody4Visibility(false)
        } else {
            setBody1SingleLine(true)
        }
    }

    fun setBodyColor(colorRes: Int?) {
        if (colorRes == null) return

        body1.setTextColor(colorRes)
        body2.setTextColor(colorRes)
        body3.setTextColor(colorRes)
        body4.setTextColor(colorRes)
    }

    fun setBodyStyle(@StyleRes styleRes: Int?) {
        if (styleRes == null) return

        body1.setAppearance(styleRes)
        body2.setAppearance(styleRes)
        body3.setAppearance(styleRes)
        body4.setAppearance(styleRes)
    }

    fun setBody1Text(text: String?) {
        text?.let {
            ensureBodyAdded(body1)
            body1.text = it
        }
    }

    fun setBody1SingleLine(isSingleLine: Boolean?) {
        body1.isSingleLine = isSingleLine == true
    }

    fun setBody1MaxLines(maxLines: Int?) {
        maxLines?.let {
            body1.post { body1.maxLines = it }
        }
    }

    fun setBody1Visibility(isVisible: Boolean) {
        body1.isVisible = isVisible
    }

    fun clearBody1() {
        clearBody(body1)
    }

    fun setBody2Text(text: String?) {
        text?.let {
            ensureBodyAdded(body2)
            body2.text = it
        }
    }

    fun setBody2Visibility(isVisible: Boolean) {
        body2.isVisible = isVisible
    }

    fun clearBody2() {
        clearBody(body2)
    }

    fun setBody3Text(text: String?) {
        text?.let {
            ensureBodyAdded(body3)
            body3.text = it
        }
    }

    fun setBody3Visibility(isVisible: Boolean) {
        body3.isVisible = isVisible
    }

    fun clearBody3() {
        clearBody(body3)
    }

    fun setBody4Text(text: String?) {
        text?.let {
            ensureBodyAdded(body4)
            body4.text = it
        }
    }

    fun setBody4Visibility(isVisible: Boolean) {
        body4.isVisible = isVisible
    }

    fun clearBody4() {
        clearBody(body4)
    }

    fun setBodyContainerVisibility(isVisible: Boolean) {
        binding.layoutBodies.isVisible = isVisible
    }

    fun setDividerVisibility(isVisible: Boolean) {
        binding.divider.isVisible = isVisible
    }

    fun setDividerText(text: String?) {
        text?.let { binding.divider.setText(it) }
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        if (isPreventDoubleClick && onClickListener != null) {
            super.setOnClickListener(PreventDoubleClickListener(onClickListener, clickInterval))
        } else {
            super.setOnClickListener(onClickListener)
        }
    }

    fun setStartComponentClickListener(listener: OnClickListener?) {
        listener?.let {
            binding.layoutStartComponent.setOnClickListener(
                PreventDoubleClickListener(listener, clickInterval)
            )
        }
    }

    fun setFirstEndIconClickListener(listener: OnClickListener?) {
        listener?.let {
            binding.ivFirstEndIcon.setOnClickListener(
                PreventDoubleClickListener(listener, clickInterval)
            )
        }
    }

    fun setSecondEndComponentClickListener(listener: OnClickListener?) {
        listener?.let {
            binding.layoutSecondEndComponent.setOnClickListener(
                PreventDoubleClickListener(listener, clickInterval)
            )
        }
    }

    fun setOnCheckboxChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        listener?.let {
            ensureSecondEndComponentCheckbox()
            checkbox.setOnCheckedChangeListener(it)
        }
    }

    fun setOnSwitchChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        listener?.let {
            ensureSecondEndComponentSwitch()
            switch.setOnCheckedChangeListener(it)
        }
    }

    fun setOnRadioButtonChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        listener?.let {
            ensureSecondEndComponentRadioButton()
            radiobutton.setOnCheckedChangeListener(it)
        }
    }

    enum class ListStartComponentType {
        NONE,
        ICON,
        AVATAR;

        companion object {
            fun Int.findStartComponentTypeByOrdinal() = entries.find { it.ordinal == this.toInt() } ?: NONE
        }
    }

    enum class ListTextComponentType {
        DEFAULT_SINGLE_VIEW_BODY,
        DEFAULT_MULTI_VIEW_BODY,
        TITLE_ONLY,
        OVER_LINE_SINGLE_VIEW_BODY,
        OVER_LINE_MULTI_VIEW_BODY,
        CUSTOM;

        companion object {
            fun Int.findTextComponentTypeByOrdinal() = entries.find { it.ordinal == this.toInt() } ?: DEFAULT_SINGLE_VIEW_BODY
        }
    }

    enum class ListEndComponentType {
        NONE,
        ICON,
        CHECKBOX,
        RADIOBUTTON,
        SWITCH;

        companion object {
            fun Int.findEndComponentTypeByOrdinal() = entries.find { it.ordinal == this.toInt() } ?: NONE
        }
    }

    enum class ListIconDimensionType {
        CUSTOM,
        WITH_BACKGROUND,
        WITHOUT_BACKGROUND;

        companion object {
            fun Int.findIconDimensionByOrdinal() = entries.find { it.ordinal == this.toInt() } ?: CUSTOM
        }
    }
}
package am.acba.component.badge

import am.acba.component.R
import am.acba.component.badge.PrimaryBadge.BadgeType.Companion.findBadgeTypeByOrdinal
import am.acba.component.databinding.BadgeIconBinding
import am.acba.component.databinding.BadgeTextBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.TextViewCompat

class PrimaryBadge : FrameLayout {
    private var type: BadgeType = BadgeType.DOT
    private var textColor: Int = 0
    private var text: String? = null
    private var icon: Drawable? = null
    private var iconTint: ColorStateList? = null
    private var backgroundTint: ColorStateList? = null
    private var textPaddingTop = -1f
    private var textPaddingBottom = -1f
    private var textPaddingEnd = -1f
    private var textPaddingStart = -1f
    private var textStyle = R.style.Small_Regular
    private val iconBinding by lazy { BadgeIconBinding.inflate(context.inflater(), this, false) }
    private val textBinding by lazy { BadgeTextBinding.inflate(context.inflater(), this, false) }

    constructor(context: Context) : super(context){
        textColor = ContextCompat.getColor(context, R.color.White)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryBadge).apply {
            try {
                text = getString(R.styleable.PrimaryBadge_badgeText)
                textColor = getColor(R.styleable.PrimaryBadge_badgeTextColor, ContextCompat.getColor(context, R.color.White))
                icon = getDrawable(R.styleable.PrimaryBadge_badgeIcon)
                iconTint = getColorStateList(R.styleable.PrimaryBadge_badgeIconTint)
                backgroundTint = getColorStateList(R.styleable.PrimaryBadge_badgeBackgroundTint)
                type = getInt(R.styleable.PrimaryBadge_badgeType, 0).findBadgeTypeByOrdinal() ?: BadgeType.DOT
                textPaddingTop = getDimension(R.styleable.PrimaryBadge_badgePaddingTop, -1f)
                textPaddingBottom = getDimension(R.styleable.PrimaryBadge_badgePaddingBottom, -1f)
                textPaddingEnd = getDimension(R.styleable.PrimaryBadge_badgePaddingEnd, -1f)
                textPaddingStart = getDimension(R.styleable.PrimaryBadge_badgePaddingStart, -1f)
                textStyle = getResourceId(R.styleable.PrimaryBadge_badgeTextAppearance, R.style.Small_Regular)
                updateBadge()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    fun setBadgeText(text: String?) {
        this.text = text
        textBinding.text.text = text
    }

    fun setBadgeTextColor(textColor: Int) {
        this.textColor = textColor
        textBinding.text.setTextColor(textColor)
    }

    fun setBadgeIcon(icon: Drawable?) {
        this.icon = icon
        iconBinding.icon.setImageDrawable(icon)
    }

    fun setBadgeIconTint(iconTint: ColorStateList?) {
        this.iconTint = iconTint
        when (type) {
            BadgeType.SMALL_ICON -> {
                iconBinding.icon.imageTintList = iconTint
            }

            BadgeType.LARGE_ICON -> {
                iconBinding.icon.imageTintList = iconTint
            }

            else -> Unit
        }
    }

    fun setBadgeBackgroundTint(backgroundTint: ColorStateList?) {
        this.backgroundTint = backgroundTint
        when (type) {
            BadgeType.DOT -> {
                iconBinding.icon.imageTintList = backgroundTint
            }

            BadgeType.NUMBER -> {
                textBinding.text.backgroundTintList = backgroundTint
            }

            BadgeType.TEXT -> {
                textBinding.text.backgroundTintList = backgroundTint
            }

            else -> Unit
        }
    }

    fun setBadgeType(type: BadgeType) {
        this.type = type
        updateBadge()
    }

    fun setTextAppearance(textAppearance: Int) {
        this.textStyle = textAppearance
        TextViewCompat.setTextAppearance(textBinding.text, textStyle)
    }

    fun setPaddings(
        startPadding: Float = textPaddingStart,
        endPadding: Float = textPaddingEnd,
        topPadding: Float = textPaddingTop,
        bottomPadding: Float = textPaddingBottom,
    ) {
        textPaddingStart = startPadding
        textPaddingEnd = endPadding
        textPaddingTop = topPadding
        textPaddingBottom = bottomPadding
        when (type) {
            BadgeType.NUMBER -> {
                val startPadding = if (textPaddingStart == -1f) resources.getDimension(R.dimen.spacing_4).toInt() else textPaddingStart.toInt()
                val topPadding = if (textPaddingTop == -1f) 0 else textPaddingTop.toInt()
                val endPadding = if (textPaddingEnd == -1f) resources.getDimension(R.dimen.spacing_4).toInt() else textPaddingEnd.toInt()
                val bottomPadding = if (textPaddingBottom == -1f) 0 else textPaddingBottom.toInt()
                textBinding.text.setPadding(
                    startPadding,
                    topPadding,
                    endPadding,
                    bottomPadding
                )
            }

            BadgeType.TEXT -> {
                val startPadding = if (textPaddingStart == -1f) resources.getDimension(R.dimen.spacing_8).toInt() else textPaddingStart.toInt()
                val topPadding = if (textPaddingTop == -1f) resources.getDimension(R.dimen.spacing_2).toInt() else textPaddingTop.toInt()
                val endPadding = if (textPaddingEnd == -1f) resources.getDimension(R.dimen.spacing_8).toInt() else textPaddingEnd.toInt()
                val bottomPadding = if (textPaddingBottom == -1f) resources.getDimension(R.dimen.spacing_4).toInt() else textPaddingBottom.toInt()
                textBinding.text.setPadding(
                    startPadding,
                    topPadding,
                    endPadding,
                    bottomPadding
                )
            }

            else -> Unit
        }
    }

    private fun updateBadge() {
        removeAllViews()
        when (type) {
            BadgeType.DOT -> {
                addView(iconBinding.root)
                iconBinding.root.updateLayoutParams<LayoutParams> {
                    width = 10.dpToPx()
                    height = 10.dpToPx()
                }
                iconBinding.icon.setImageResource(R.drawable.background_rounded)
                iconBinding.icon.imageTintList = backgroundTint
            }

            BadgeType.SMALL_ICON -> {
                addView(iconBinding.root)
                iconBinding.icon.setImageDrawable(icon)
                iconBinding.icon.imageTintList = iconTint
                iconBinding.root.updateLayoutParams<LayoutParams> {
                    width = 12.dpToPx()
                    height = 12.dpToPx()
                }
            }

            BadgeType.LARGE_ICON -> {
                addView(iconBinding.root)
                iconBinding.icon.setImageDrawable(icon)
                iconBinding.icon.imageTintList = iconTint
                iconBinding.root.updateLayoutParams<LayoutParams> {
                    width = 14.dpToPx()
                    height = 14.dpToPx()
                }
            }

            BadgeType.NUMBER -> {
                addView(textBinding.root)
                try {
                    val number = text?.toInt() ?: -1
                    if (number == -1) text = ""
                    else if (number >= 100) text = "99+"
                } catch (e: Exception) {

                }
                TextViewCompat.setTextAppearance(textBinding.text, textStyle)
                textBinding.text.backgroundTintList = backgroundTint
                textBinding.text.text = text
                textBinding.text.setTextColor(textColor)
                val startPadding = if (textPaddingStart == -1f) resources.getDimension(R.dimen.spacing_4).toInt() else textPaddingStart.toInt()
                val topPadding = if (textPaddingTop == -1f) 0 else textPaddingTop.toInt()
                val endPadding = if (textPaddingEnd == -1f) resources.getDimension(R.dimen.spacing_4).toInt() else textPaddingEnd.toInt()
                val bottomPadding = if (textPaddingBottom == -1f) 0 else textPaddingBottom.toInt()
                textBinding.text.setPadding(
                    startPadding,
                    topPadding,
                    endPadding,
                    bottomPadding
                )
            }

            BadgeType.TEXT -> {
                TextViewCompat.setTextAppearance(textBinding.text, textStyle)
                addView(textBinding.root)
                textBinding.text.backgroundTintList = backgroundTint
                textBinding.text.setTextColor(textColor)
                textBinding.text.text = text

                val startPadding = if (textPaddingStart == -1f) resources.getDimension(R.dimen.spacing_8).toInt() else textPaddingStart.toInt()
                val topPadding = if (textPaddingTop == -1f) resources.getDimension(R.dimen.spacing_2).toInt() else textPaddingTop.toInt()
                val endPadding = if (textPaddingEnd == -1f) resources.getDimension(R.dimen.spacing_8).toInt() else textPaddingEnd.toInt()
                val bottomPadding = if (textPaddingBottom == -1f) resources.getDimension(R.dimen.spacing_4).toInt() else textPaddingBottom.toInt()
                textBinding.text.setPadding(
                    startPadding,
                    topPadding,
                    endPadding,
                    bottomPadding
                )
            }
        }
    }

    enum class BadgeType {
        DOT,
        SMALL_ICON,
        LARGE_ICON,
        NUMBER,
        TEXT;

        companion object {
            fun Int.findBadgeTypeByOrdinal(): BadgeType? {
                return entries.find { it.ordinal == this }
            }
        }
    }
}

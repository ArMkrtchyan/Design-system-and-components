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

class PrimaryBadge : FrameLayout {
    private lateinit var type: BadgeType
    private var textColor: Int = 0
    private var text: String? = null
    private var icon: Drawable? = null
    private var iconTint: ColorStateList? = null
    private var backgroundTint: ColorStateList? = null
    private val iconBinding by lazy { BadgeIconBinding.inflate(context.inflater(), this, false) }
    private val textBinding by lazy { BadgeTextBinding.inflate(context.inflater(), this, false) }

    constructor(context: Context) : super(context)

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
    }

    fun setBadgeTextColor(textColor: Int) {
        this.textColor = textColor
    }

    fun setBadgeIcon(icon: Drawable?) {
        this.icon = icon
    }

    fun setBadgeIconTint(iconTint: ColorStateList?) {
        this.iconTint = iconTint
    }

    fun setBadgeBackgroundTint(backgroundTint: ColorStateList?) {
        this.backgroundTint = backgroundTint
    }

    fun setBadgeType(type: BadgeType) {
        this.type = type
    }

    fun updateBadge() {
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
                textBinding.text.backgroundTintList = backgroundTint
                textBinding.text.text = text
                textBinding.text.setTextColor(textColor)
                textBinding.text.setPadding(
                    resources.getDimension(R.dimen.spacing_4).toInt(),
                    0,
                    resources.getDimension(R.dimen.spacing_4).toInt(),
                    0
                )
            }

            BadgeType.TEXT -> {
                addView(textBinding.root)
                textBinding.text.backgroundTintList = backgroundTint
                textBinding.text.setTextColor(textColor)
                textBinding.text.text = text
                textBinding.text.setPadding(
                    resources.getDimension(R.dimen.spacing_8).toInt(),
                    resources.getDimension(R.dimen.spacing_2).toInt(),
                    resources.getDimension(R.dimen.spacing_8).toInt(),
                    resources.getDimension(R.dimen.spacing_2).toInt()
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

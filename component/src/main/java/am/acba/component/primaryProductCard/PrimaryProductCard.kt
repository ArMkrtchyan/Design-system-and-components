package am.acba.component.primaryProductCard

import am.acba.component.R
import am.acba.component.databinding.LayoutBadgesGroupBinding
import am.acba.component.databinding.LayoutBulletsBinding
import am.acba.component.databinding.PrimaryProductCardBinding
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.extensions.load
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams

class PrimaryProductCard : FrameLayout {
    private val binding by lazy {
        PrimaryProductCardBinding.inflate(context.inflater(), this, false)
    }
    private var title: String? = null
    private var description: String? = null
    private var subTitle: String? = null
    private var subDescription: String? = null
    private var image: Drawable? = null
    private val badgesGroup = ArrayList<View>()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryProductCard).apply {
            title = getString(R.styleable.PrimaryProductCard_productCardTitle)
            description = getString(R.styleable.PrimaryProductCard_productCardDescription)
            subTitle = getString(R.styleable.PrimaryProductCard_productCardSubTitle)
            subDescription = getString(R.styleable.PrimaryProductCard_productCardSubDescription)
            image = getDrawable(R.styleable.PrimaryProductCard_productCardImage)
            val cardHeight = getDimensionPixelOffset(R.styleable.PrimaryProductCard_productCardHeight, -2)
            val backgroundColor =
                getColorStateList(R.styleable.PrimaryProductCard_productCardBackgroundColor)
                    ?: context.getColorStateListFromAttr(R.attr.backgroundTonal1)
            setTitle(title)
            setSubTitle(subTitle)
            setDescription(description)
            setSubDescription(subDescription)
            setImage(image)
            setCardHeight(cardHeight)
            setCardBackgroundColor(backgroundColor)
            addView(binding.root)
            recycle()
        }
    }

    private fun setCardBackgroundColor(backgroundColor: ColorStateList) {
        binding.root.setCardBackgroundColor(backgroundColor)
    }

    private fun setCardHeight(cardHeight: Int) {
        binding.root.updateLayoutParams<LayoutParams> {
            height = cardHeight
        }
    }

    fun setTitle(title: String?) {
        this.title = title
        binding.title.text = title
        invalidate()
    }

    fun setSubTitle(subTitle: String?) {
        this.subTitle = subTitle
        binding.subTitle.text = subTitle
        binding.subTitle.isVisible = !subTitle.isNullOrEmpty()
        invalidate()
    }

    fun setDescription(description: String?) {
        this.description = description
        binding.description.text = description
        binding.description.isVisible = !description.isNullOrEmpty()
        invalidate()
    }

    fun setSubDescription(subDescription: String?) {
        this.subDescription = subDescription
        binding.subDescription.text = subDescription
        binding.subDescription.isVisible = !subDescription.isNullOrEmpty()
        invalidate()
    }

    fun setImage(image: Drawable?) {
        this.image = image
        binding.image.setImageDrawable(image)
        invalidate()
    }


    fun setImage(imageUrl: String) {
        binding.imageBackground.visibility = View.INVISIBLE
        binding.image.load(imageUrl, binding.shimmerLayout) { drawable, _ ->
            this.image = drawable
            binding.imageBackground.isVisible = true
        }
    }

    fun setBadgesGroup(
        badgesGroup: List<Pair<Drawable?, String>>,
        backgroundTintColor: Int = R.attr.backgroundPending
    ) {
        binding.flow.referencedIds = intArrayOf()
        this.badgesGroup.forEach { binding.parentLayout.removeView(it) }
        this.badgesGroup.clear()

        badgesGroup.forEach {
            val badgeGroupView = LayoutBadgesGroupBinding.inflate(context.inflater(), this, false)
            badgeGroupView.root.id = View.generateViewId()

            // Use the backgroundTintColor, which defaults to Color.TRANSPARENT if not provided
            badgeGroupView.badgeParent.backgroundTintList = context.getColorStateListFromAttr(backgroundTintColor)

            if (it.first == null) {
                badgeGroupView.startIcon.visibility = View.GONE
            } else {
                badgeGroupView.startIcon.visibility = View.VISIBLE
                badgeGroupView.startIcon.setImageDrawable(it.first)
            }

            badgeGroupView.title.text = it.second

            binding.flow.addView(badgeGroupView.root)
            binding.parentLayout.addView(badgeGroupView.root)
            this.badgesGroup.add(badgeGroupView.root)
        }

        binding.flow.referencedIds = this.badgesGroup.map { it.id }.toIntArray()
        binding.flow.isVisible = badgesGroup.isNotEmpty()
        invalidate()
    }

    fun setBullets(bullets: List<String>) {
        binding.bullets.removeAllViews()
        bullets.forEach {
            val bulletView = LayoutBulletsBinding.inflate(context.inflater(), this, false)
            bulletView.title.text = it
            binding.bullets.addView(bulletView.root)
        }
        binding.bullets.isVisible = bullets.isNotEmpty()
        invalidate()
    }
}
package am.acba.component.primaryEmptyView

import am.acba.component.R
import am.acba.component.databinding.PrimaryEmptyLayoutBinding
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.widget.TextViewCompat

class PrimaryEmptyView : FrameLayout {
    private val binding by lazy {
        PrimaryEmptyLayoutBinding.inflate(context.inflater(), this, false)
    }
    private var emptyIconType: Int = 0
    private var emptyIconTint: ColorStateList? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryEmptyView).apply {
            emptyIconType = getInt(R.styleable.PrimaryEmptyView_emptyIconType, 0)
            val emptyIcon = getDrawable(R.styleable.PrimaryEmptyView_emptyIcon)
            emptyIconTint = getColorStateList(R.styleable.PrimaryEmptyView_emptyIconTint) ?: context.getColorStateListFromAttr(R.attr.contentPrimary)
            val emptyLottieIcon = getString(R.styleable.PrimaryEmptyView_emptyLottieIcon)
            val emptyIconWidth = getDimensionPixelOffset(R.styleable.PrimaryEmptyView_emptyIconWidth, -2)
            val emptyIconHeight = getDimensionPixelOffset(R.styleable.PrimaryEmptyView_emptyIconHeight, -2)
            val emptyTitle = getString(R.styleable.PrimaryEmptyView_emptyTitle)
            val emptyTitleColor = getColorStateList(R.styleable.PrimaryEmptyView_emptyTitleColor)
            val emptyTitleTextAppearance = getResourceId(R.styleable.PrimaryEmptyView_emptyTitleTextAppearance, -1)
            val emptyDescription = getString(R.styleable.PrimaryEmptyView_emptyDescription)
            val emptyDescriptionColor = getColorStateList(R.styleable.PrimaryEmptyView_emptyDescriptionColor)
            val emptyDescriptionTextAppearance = getResourceId(R.styleable.PrimaryEmptyView_emptyDescriptionTextAppearance, -1)
            val emptyButtonTitle = getString(R.styleable.PrimaryEmptyView_emptyButtonTitle)
            val emptyButtonIcon = getDrawable(R.styleable.PrimaryEmptyView_emptyButtonIcon)
            val emptyButtonAppearance = getResourceId(R.styleable.PrimaryEmptyView_emptyButtonAppearance, R.style.Button_Style_Secondary)

            setEmptyIconType()
            setEmptyIconWidthAndHeight(emptyIconWidth, emptyIconHeight)
            setEmptyIcon(emptyIcon)
            setEmptyIconTint()
            setEmptyLottieIcon(emptyLottieIcon)
            setEmptyTitle(emptyTitle)
            if (emptyTitleTextAppearance != -1) setEmptyTitleTextAppearance(emptyTitleTextAppearance)
            emptyTitleColor?.let { setEmptyTitleColor(it) }
            setEmptyDescription(emptyDescription)
            if (emptyDescriptionTextAppearance != -1) setEmptyDescriptionTextAppearance(emptyDescriptionTextAppearance)
            emptyDescriptionColor?.let { setEmptyDescriptionColor(it) }
            setEmptyButtonTitle(emptyButtonTitle)
            setEmptyButtonIcon(emptyButtonIcon)
            setEmptyButtonAppearance(emptyButtonAppearance)
            addView(binding.root)
            recycle()
        }
    }

    fun setEmptyIconType() {
        binding.icon.isVisible = emptyIconType != 2
        binding.lottie.isVisible = emptyIconType == 2
        when (emptyIconType) {
            0 -> {
                binding.icon.imageTintList = null
            }

            1 -> {
                binding.icon.imageTintList = emptyIconTint
            }

            2 -> {
                binding.lottie.imageTintList = emptyIconTint
            }
        }
    }

    fun setEmptyIcon(icon: Drawable?) {
        binding.icon.setImageDrawable(icon)
    }

    fun setEmptyIconWidthAndHeight(width: Int, height: Int) {
        when (emptyIconType) {
            0, 1 -> {
                binding.icon.updateLayoutParams<LinearLayout.LayoutParams> {
                    this.width = width
                    this.height = height
                }
            }

            2 -> {
                binding.lottie.updateLayoutParams<LinearLayout.LayoutParams> {
                    this.width = width
                    this.height = height
                }
            }
        }
    }

    fun setEmptyLottieIcon(icon: String?) {
        icon?.let { binding.lottie.setAnimation(it) }
    }

    fun setEmptyIconTint() {
        when (emptyIconType) {
            0 -> {
                binding.icon.imageTintList = null
            }

            1 -> {
                binding.icon.imageTintList = emptyIconTint
            }

            2 -> {
                binding.lottie.imageTintList = emptyIconTint
            }
        }
    }

    fun setEmptyTitle(title: String?) {
        binding.title.text = title
        binding.title.isVisible = !title.isNullOrEmpty()
    }

    fun setEmptyTitleColor(color: ColorStateList?) {
        binding.title.setTextColor(color)
    }

    fun setEmptyTitleTextAppearance(textStyle: Int) {
        TextViewCompat.setTextAppearance(binding.title, textStyle)
    }

    fun setEmptyDescription(title: String?) {
        binding.description.text = title
        binding.description.isVisible = !title.isNullOrEmpty()
    }

    fun setEmptyDescriptionColor(color: ColorStateList?) {
        binding.description.setTextColor(color)
    }

    fun setEmptyDescriptionTextAppearance(textStyle: Int) {
        TextViewCompat.setTextAppearance(binding.description, textStyle)
    }

    fun setEmptyButtonTitle(title: String?) {
        binding.actionButton.text = title
        binding.actionButton.isVisible = !title.isNullOrEmpty()
    }

    fun setEmptyButtonIcon(icon: Drawable?) {
        binding.actionButton.icon = icon
    }

    private fun setEmptyButtonAppearance(buttonStyle: Int) {
        //   binding.actionButton.set(buttonStyle)
    }


    fun setOnActionButtonClickListener(onClickListener: OnClickListener?) {
        binding.actionButton.setOnClickListener(onClickListener)
    }
}
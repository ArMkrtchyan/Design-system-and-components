package am.acba.component.dialog

import am.acba.component.databinding.DialogLayoutBinding
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible

class PrimaryAlertDialog(
    context: Context,
    private val mCancelable: Boolean = true,
    @DrawableRes private val icon: Int? = null,
    private val title: String = "",
    private val description: String = "",
    private val content: View? = null,
    private val positiveButtonText: String = "",
    private val positiveButtonTextColor: ColorStateList? = null,
    private val negativeButtonText: String = "",
    private val negativeButtonTextColor: ColorStateList? = null,
    private var mOnNegativeButtonClick: (() -> Unit)?,
    private var mOnPositiveButtonClick: (() -> Unit)?,
) : Dialog(context) {

    private lateinit var mBinding: DialogLayoutBinding

    private constructor(builder: Builder) : this(
        builder.context,
        builder.mCancelable,
        builder.icon,
        builder.title,
        builder.description,
        builder.content,
        builder.positiveButtonText,
        builder.positiveButtonTextColor,
        builder.negativeButtonText,
        builder.negativeButtonTextColor,
        builder.mOnNegativeButtonClick,
        builder.mOnPositiveButtonClick
    )

    companion object {
        inline fun build(required: Context, block: Builder.() -> Unit) =
            Builder(required).apply(block)
                .build()
    }

    data class Builder(
        val context: Context,
        var mCancelable: Boolean = true,
        @DrawableRes var icon: Int? = null,
        var title: String = "",
        var description: String = "",
        var content: View? = null,
        var positiveButtonText: String = "",
        var positiveButtonTextColor: ColorStateList? = null,
        var negativeButtonText: String = "",
        var negativeButtonTextColor: ColorStateList? = null,
        var mOnNegativeButtonClick: (() -> Unit)? = null,
        var mOnPositiveButtonClick: (() -> Unit)? = null,
    ) {

        fun setCancelable(isCancelable: Boolean) = apply { this.mCancelable = isCancelable }

        fun negativeButtonClick(onNegativeButtonClick: (() -> Unit)?) =
            apply { this.mOnNegativeButtonClick = onNegativeButtonClick }

        fun positiveButtonClick(onPositiveButtonClick: (() -> Unit)?) =
            apply { this.mOnPositiveButtonClick = onPositiveButtonClick }

        fun icon(icon: Int) =
            apply { this.icon = icon }

        fun title(title: String) =
            apply { this.title = title }

        fun description(description: String) =
            apply { this.description = description }

        fun content(content: View) =
            apply { this.content = content }

        fun positiveButtonText(positiveButtonText: String) =
            apply { this.positiveButtonText = positiveButtonText }

        fun positiveButtonTextColor(positiveButtonTextColor: ColorStateList) =
            apply { this.positiveButtonTextColor = positiveButtonTextColor }

        fun negativeButtonText(negativeButtonText: String) =
            apply { this.negativeButtonText = negativeButtonText }

        fun negativeButtonTextColor(negativeButtonTextColor: ColorStateList) =
            apply { this.negativeButtonTextColor = negativeButtonTextColor }

        fun build(): PrimaryAlertDialog {
            val v = PrimaryAlertDialog(this)
            v.show()
            return v
        }
    }

    init {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogLayoutBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        mBinding.apply {
            setCancelable(mCancelable)
            content?.let { contentContainer.addView(it) }
        }

        mBinding.topIcon.isVisible = icon != 0
        icon?.let { mBinding.topIcon.setImageResource(it) }

        mBinding.title.isVisible = title.isNotEmpty()
        mBinding.title.text = title
        mBinding.description.isVisible = description.isNotEmpty()
        mBinding.description.text = description
        mBinding.contentContainer.isVisible = content != null
        mBinding.buttonPrimary.isVisible = positiveButtonText.isNotEmpty()
        mBinding.buttonPrimary.text = positiveButtonText
        positiveButtonTextColor?.let { mBinding.buttonPrimary.setTextColor(positiveButtonTextColor) }
        mBinding.topDivider.isVisible = positiveButtonText.isNotEmpty()
        mBinding.buttonSecondary.isVisible = negativeButtonText.isNotEmpty()
        mBinding.buttonSecondary.text = negativeButtonText
        negativeButtonTextColor?.let { mBinding.buttonSecondary.setTextColor(negativeButtonTextColor) }
        mBinding.bottomDivider.isVisible = negativeButtonText.isNotEmpty()

        mBinding.buttonPrimary.setOnClickListener {
            mOnPositiveButtonClick?.invoke()
        }

        mBinding.buttonSecondary.setOnClickListener {
            mOnNegativeButtonClick?.invoke() ?: run { dismiss() }
        }

    }
}
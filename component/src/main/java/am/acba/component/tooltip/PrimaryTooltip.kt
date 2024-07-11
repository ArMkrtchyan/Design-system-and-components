package am.acba.component.tooltip

import am.acba.component.databinding.PrimaryTooltipLayoutBinding
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PrimaryTooltip : FrameLayout {
    private val binding by lazy {
        PrimaryTooltipLayoutBinding.inflate(
            context.inflater(),
            this,
            false
        )
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        addView(binding.root)
    }

    fun setTitle(title: String) {
        binding.title.text = title

    }

    fun setTitle(@StringRes stringRes: Int) {
        binding.title.text = context.getString(stringRes)

    }

    fun setDescription(title: String) {
        binding.description.text = title

    }

    fun setDescription(@StringRes stringRes: Int) {
        binding.description.text = context.getString(stringRes)

    }

    fun setButtonTitle(title: String?) {
        binding.skip.text = title

    }

    fun setButtonTitle(@StringRes stringRes: Int) {
        binding.skip.text = context.getString(stringRes)

    }

    fun setTooltipCount(currentPosition: Int, tooltipCount: Int) {
        binding.tooltipCount.text = "$currentPosition/$tooltipCount"
    }

    fun setBackwardClickListener(onClickListener: OnClickListener) {
        binding.backward.setOnClickListener(onClickListener)
    }

    fun setForwardClickListener(onClickListener: OnClickListener) {
        binding.forward.setOnClickListener(onClickListener)
    }

    fun setSkipClickListener(onClickListener: OnClickListener) {
        binding.skip.setOnClickListener(onClickListener)
    }

    fun setCloseTooltipClickListener(onClickListener: OnClickListener) {
        binding.close.setOnClickListener(onClickListener)
    }

    fun setForwardVisibility(isVisible: Boolean) {
        binding.forward.isVisible = isVisible
    }


    fun setBackwardVisibility(isVisible: Boolean) {
        binding.backward.isVisible = isVisible
    }

    fun setSkipVisibility(isVisible: Boolean) {
        binding.skip.isVisible = isVisible
    }

    fun isImageVisible(): Boolean {
        return binding.contentImage.isVisible
    }

    fun isLottieVisible(): Boolean {
        return binding.lottie.isVisible
    }

    fun setTooltip(tooltipModel: TooltipModel) {
        binding.title.text = tooltipModel.title
        binding.description.text = tooltipModel.description
        if (tooltipModel.imageUrl.isNotEmpty()) {
            binding.contentImage.visibility = FrameLayout.VISIBLE
            Glide.with(context)
                .load(tooltipModel.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.contentImage)
        } else {
            binding.contentImage.visibility = GONE
        }

        tooltipModel.localImage?.let {
            binding.contentImage.setImageResource(tooltipModel.localImage)
        }

        if (tooltipModel.lottieAnimationName.isNotEmpty()) {
            binding.lottie.visibility = VISIBLE
            binding.lottie.setAnimation(tooltipModel.lottieAnimationName)
            binding.lottie.playAnimation()
        } else {
            binding.lottie.visibility = GONE
        }

    }


}
package am.acba.component.statusScreen

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import am.acba.component.databinding.StatusScreenLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import am.acba.component.extensions.playLottieAnimation
import am.acba.component.imageView.PrimaryImageView
import am.acba.component.statusScreen.PrimaryStatusScreen.MediaTypes.Companion.findMediaTypeByOrdinal
import android.animation.Animator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.annotation.RawRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PrimaryStatusScreen : FrameLayout {

    private val binding by lazy {
        StatusScreenLayoutBinding.inflate(
            context.inflater(), this, false
        )
    }

    var onCentreAnimationEnd: ((LottieAnimationView, Animator) -> Unit) = { view, _ ->
        onCentreAnimationEnd(view)
    }

    private var clickInterval = 1000
    private var mediaType: MediaTypes = MediaTypes.SMALL

    var showCloseIcon = false
        set(value) {
            field = value

            setCloseIconVisibility()
        }

    var showTitle = false
        set(value) {
            field = value

            setTitleVisibility()
        }

    var showBody = false
        set(value) {
            field = value

            setBodyVisibility()
        }

    var showPrimaryButton = false
        set(value) {
            field = value

            setPrimaryButtonVisibility()
        }

    var showGhostButton = false
        set(value) {
            field = value

            setGhostButtonVisibility()
        }


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryStatusScreen).apply {
            addView(binding.root)
            try {
                showCloseIcon = getBoolean(
                    R.styleable.PrimaryStatusScreen_showStatusScreenCloseIcon, false
                )
                showTitle = getBoolean(
                    R.styleable.PrimaryStatusScreen_showStatusScreenTitle, false
                )
                showBody = getBoolean(
                    R.styleable.PrimaryStatusScreen_showStatusScreenBody, false
                )
                showPrimaryButton = getBoolean(
                    R.styleable.PrimaryStatusScreen_showStatusScreenPrimaryButton, false
                )
                showGhostButton = getBoolean(
                    R.styleable.PrimaryStatusScreen_showStatusScreenGhostButton, false
                )

                val mediaType = getInt(
                    R.styleable.PrimaryStatusScreen_statusScreenMediaType, 0
                ).findMediaTypeByOrdinal()

                val centreImage = getDrawable(
                    R.styleable.PrimaryStatusScreen_statusScreenCentreImage
                )
                val centreImageBackgroundColor = getColorStateList(
                    R.styleable.PrimaryStatusScreen_statusScreenCentreImageBackgroundColor
                )
                val centreImageBackgroundTint = getColorStateList(
                    R.styleable.PrimaryStatusScreen_statusScreenCentreImageBackgroundTint
                )
                val centreMediaAnimationFileName = getString(
                    R.styleable.PrimaryStatusScreen_statusScreenCentreMediaAnimationFileName
                )
                val title = getString(R.styleable.PrimaryStatusScreen_statusScreenTitleText)
                val body = getString(R.styleable.PrimaryStatusScreen_statusScreenBodyText)

                val primaryButtonText = getString(
                    R.styleable.PrimaryStatusScreen_statusScreenPrimaryButtonText
                )
                val ghostButtonText = getString(
                    R.styleable.PrimaryStatusScreen_statusScreenGhostButtonText
                )

                val animationFileName = getString(
                    R.styleable.PrimaryStatusScreen_statusScreenAnimationFileName
                )

                mediaType?.let {
                    setCentreMediaType(mediaType)
                    setCentreImage(centreImage)
                    setCentreImageBackgroundColor(centreImageBackgroundColor)
                    setCentreImageTint(centreImageBackgroundTint)
                    setCentreMediaAnimation(centreMediaAnimationFileName)
                }
                setTitle(title)
                setBody(body)

                setPrimaryButtonText(primaryButtonText)
                setGhostButtonText(ghostButtonText)

                setCloseIconVisibility()
                setTitleVisibility()
                setBodyVisibility()
                setPrimaryButtonVisibility()
                setGhostButtonVisibility()

                setLottieAnimation(animationFileName)

                onDynamicContainerVisibilityChanged()
                binding.scrollView.setOnScrollChangeListener { view, _, _, _, _ ->
                    updateBordersVisibility(view as ScrollView)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    private fun updateBordersVisibility(scrollView: ScrollView) {
        val offset = scrollView.scrollY
        val contentHeight = scrollView.getChildAt(0).height
        val scrollViewHeight = scrollView.height

        val isContentUnderTopView = offset > binding.ivClose.bottom
        val isContentUnderBottomView = offset + scrollViewHeight < binding.layoutButtons.top
        val isContentLargerScrollView = contentHeight > scrollViewHeight

        binding.dividerTop.isVisible = isContentLargerScrollView && isContentUnderTopView
        binding.dividerBottom.isVisible = isContentLargerScrollView && isContentUnderBottomView
    }

    private fun setCloseIconVisibility() {
        binding.ivClose.isVisible = showCloseIcon
    }

    private fun addCentreImage() {
        binding.centreMedia.apply {
            if (childCount == 0 || children.first() !is PrimaryImageView) {
                removeAllViews()
                addView(PrimaryImageView(context))
            }
        }
    }

    private fun addCentreAnimation() {
        binding.centreMedia.apply {
            if (childCount == 0 || children.first() !is LottieAnimationView) {
                removeAllViews()
                addView(LottieAnimationView(context))
            }
        }
    }

    private fun setTitleVisibility() {
        binding.tvTitle.isVisible = showTitle
    }

    private fun setBodyVisibility() {
        binding.tvBody.isVisible = showBody
    }

    private fun setPrimaryButtonVisibility() {
        binding.btnPrimary.isVisible = showPrimaryButton
    }

    private fun setGhostButtonVisibility() {
        binding.btnGhost.isVisible = showGhostButton
    }

    fun setCentreMediaType(type: MediaTypes?) {
        mediaType = type ?: MediaTypes.SMALL

        val size = type?.size?.dpToPx() ?: MediaTypes.SMALL.size
        binding.centreMedia.layoutParams.apply {
            width = size
            height = size
        }
    }

    fun setCentreImage(icon: Drawable?) {
        addCentreImage()
        (binding.centreMedia.children.first() as? PrimaryImageView)?.setImageDrawable(icon)
    }

    fun setCentreImageFromUrl(url: String) {
        addCentreImage()

        val imageView = binding.centreMedia.children.firstOrNull() as? PrimaryImageView ?: return

        Glide.with(imageView.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }


    fun setCentreImageBackgroundColor(colorStateList: ColorStateList?) {
        binding.centreMedia.backgroundTintList = colorStateList
    }

    fun setCentreImageTint(colorStateList: ColorStateList?) {
        (binding.centreMedia.children.first() as? PrimaryImageView)?.imageTintList = colorStateList
    }

    fun setAnimationBackgroundTint(color: Int) {
        (binding.centreMedia.children.first() as? LottieAnimationView)?.addValueCallback(
            KeyPath("**"), LottieProperty.COLOR_FILTER, LottieValueCallback(
                SimpleColorFilter(
                    color
                )
            )
        )
    }

    fun setCentreMediaAnimation(animation: String?) {
        if (animation != null) {
            addCentreAnimation()
            (binding.centreMedia.children.first() as? LottieAnimationView)?.let { animationView ->
                addAnimationListener(animationView)
                animationView.playLottieAnimation {
                    animationView.setAnimation(animation)
                }
            }
        }
    }

    fun setCentreMediaAnimationFromUrl(animation: String?) {
        if (animation != null) {
            addCentreAnimation()
            (binding.centreMedia.children.first() as? LottieAnimationView)?.let { animationView ->
                addAnimationListener(animationView)
                animationView.playLottieAnimation {
                    animationView.setAnimationFromUrl(animation)
                }
            }
        }
    }

    fun setCentreMediaAnimation(animation: Animation?) {
        if (animation != null) {
            addCentreAnimation()
            (binding.centreMedia.children.first() as? LottieAnimationView)?.let { animationView ->
                addAnimationListener(animationView)
                animationView.playLottieAnimation {
                    animationView.animation = animation
                }
            }
        }
    }

    fun setTitle(text: String?) {
        binding.tvTitle.text = text
    }

    fun setBody(text: String?) {
        binding.tvBody.text = text
    }

    fun addContainer(child: View?) {
        binding.dynamicContainer.isVisible = child != null
        onDynamicContainerVisibilityChanged()

        child?.let { binding.dynamicContainer.addView(it) }
    }

    fun addContainer(child: View?, width: Int, height: Int) {
        binding.dynamicContainer.isVisible = child != null
        onDynamicContainerVisibilityChanged()

        child?.let { binding.dynamicContainer.addView(child, width, height) }
    }

    fun addContainer(child: View?, params: ViewGroup.LayoutParams) {
        binding.dynamicContainer.isVisible = child != null
        onDynamicContainerVisibilityChanged()

        child?.let { binding.dynamicContainer.addView(child, params) }
    }

    fun setPrimaryButtonText(text: String?) {
        binding.btnPrimary.text = text
    }

    fun setGhostButtonText(text: String?) {
        binding.btnGhost.text = text
    }

    fun setOnCloseClickListener(onClickListener: OnClickListener?) {
        binding.ivClose.apply {
            if (isVisible && onClickListener != null) {
                setOnClickListener(
                    PreventDoubleClickListener(
                        onClickListener, clickInterval
                    )
                )
            } else {
                setOnClickListener(onClickListener)
            }
        }
    }

    fun setOnPrimaryButtonClickListener(onClickListener: OnClickListener?) {
        binding.btnPrimary.apply {
            if (isVisible && onClickListener != null) {
                setOnClickListener(
                    PreventDoubleClickListener(
                        onClickListener, clickInterval
                    )
                )
            } else {
                setOnClickListener(onClickListener)
            }
        }
    }

    fun setOnGhostButtonClickListener(onClickListener: OnClickListener?) {
        binding.btnGhost.apply {
            if (isVisible && onClickListener != null) {
                setOnClickListener(
                    PreventDoubleClickListener(
                        onClickListener, clickInterval
                    )
                )
            } else {
                setOnClickListener(onClickListener)
            }
        }
    }

    fun setLottieAnimation(animation: String?) {
        binding.lottie.isVisible = !animation.isNullOrEmpty()

        if (!animation.isNullOrEmpty()) {
            binding.lottie.playLottieAnimation {
                binding.lottie.setAnimation(animation)
            }
        }
    }

    fun setLottieAnimation(animation: Animation?) {
        binding.lottie.isVisible = animation != null

        animation?.let {
            binding.lottie.playLottieAnimation {
                binding.lottie.animation = animation
            }
        }
    }

    fun setLottieAnimation(@RawRes res: Int?) {
        binding.lottie.isVisible = res != null

        res?.let {
            binding.lottie.playLottieAnimation {
                binding.lottie.setAnimation(it)
            }
        }
    }

    private fun onDynamicContainerVisibilityChanged() {
        val isDynamicContainerVisible = binding.dynamicContainer.isVisible

        binding.layoutInfo.updateLayoutParams<ConstraintLayout.LayoutParams> {
            if (!isDynamicContainerVisible) {
                bottomToTop = ConstraintLayout.LayoutParams.UNSET
                verticalBias = 0.5f
            } else {
                bottomToTop = binding.dynamicContainer.id
                verticalBias = 1f
            }
        }

        if (isDynamicContainerVisible) {
            binding.scrollView.viewTreeObserver.addOnGlobalLayoutListener {
                updateBordersVisibility(binding.scrollView)
            }
        }
    }

    private fun addAnimationListener(animationView: LottieAnimationView) {
        val listener = object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) = Unit
            override fun onAnimationRepeat(p0: Animator) = Unit
            override fun onAnimationCancel(p0: Animator) = Unit

            override fun onAnimationEnd(animator: Animator) {
                onCentreAnimationEnd.invoke(animationView, animator)
            }
        }
        animationView.addAnimatorListener(listener)
    }

    private fun onCentreAnimationEnd(animationView: LottieAnimationView) {
        animationView.frame = animationView.maxFrame.toInt()
        animationView.pauseAnimation()
    }

    enum class MediaTypes(var size: Int, val type: Int) {
        SMALL(40, 1), LARGE(130, 2);

        companion object {
            private val map = entries.associateBy(MediaTypes::type)
            fun from(type: Int?) = map[type] ?: SMALL
            fun Int.findMediaTypeByOrdinal() = entries.find { it.ordinal == this }
        }
    }
}
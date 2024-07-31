package am.acba.component.statusScreen

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import am.acba.component.databinding.StatusScreenLayoutBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import am.acba.component.statusScreen.PrimaryStatusScreen.ImageTypes.Companion.findImageTypeByOrdinal
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
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PrimaryStatusScreen : FrameLayout {

    private val binding by lazy {
        StatusScreenLayoutBinding.inflate(
            context.inflater(),
            this,
            false
        )
    }

    private var clickInterval = 1000

    var showCloseIcon = false
        set(value) {
            field = value

            setCloseIconVisibility()
        }

    var showCentreImage = false
        set(value) {
            field = value

            setCentreImageVisibility()
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

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryStatusScreen).apply {
            addView(binding.root)
            try {
                showCloseIcon = getBoolean(
                    R.styleable.PrimaryStatusScreen_showStatusScreenCloseIcon,
                    false
                )
                showCentreImage = getBoolean(
                    R.styleable.PrimaryStatusScreen_showStatusScreenCentreImage,
                    false
                )
                showTitle = getBoolean(
                    R.styleable.PrimaryStatusScreen_showStatusScreenTitle,
                    false
                )
                showBody = getBoolean(
                    R.styleable.PrimaryStatusScreen_showStatusScreenBody,
                    false
                )
                showPrimaryButton = getBoolean(
                    R.styleable.PrimaryStatusScreen_showStatusScreenPrimaryButton,
                    false
                )
                showGhostButton = getBoolean(
                    R.styleable.PrimaryStatusScreen_showStatusScreenGhostButton,
                    false
                )

                val centreImageType = getInt(
                    R.styleable.PrimaryStatusScreen_statusScreenCentreImageType,
                    0
                ).findImageTypeByOrdinal() ?: ImageTypes.ICON

                val centreImage = getDrawable(
                    R.styleable.PrimaryStatusScreen_statusScreenCentreImage
                )
                val centreImageBackgroundColor = getColorStateList(
                    R.styleable.PrimaryStatusScreen_statusScreenCentreImageBackgroundColor
                )
                val centreImageBackgroundTint = getColorStateList(
                    R.styleable.PrimaryStatusScreen_statusScreenCentreImageBackgroundTint
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

                setCentreImageType(centreImageType)
                setCentreImage(centreImage)
                setCentreImageBackgroundColor(centreImageBackgroundColor)
                setCentreImageBackgroundTint(centreImageBackgroundTint)
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

    private fun setCentreImageVisibility() {
        binding.ivCentre.isVisible = showCentreImage
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

    fun setCentreImageType(type: ImageTypes?) {
        val size = type?.size?.dpToPx() ?: ImageTypes.ICON.size
        binding.ivCentre.layoutParams.apply {
            width = size
            height = size
        }
    }

    fun setCentreImage(icon: Drawable?) {
        binding.ivCentre.background = icon
    }

    fun setCentreImageBackgroundColor(colorStateList: ColorStateList?) {
        binding.ivCentre.backgroundTintList = colorStateList
    }

    fun setCentreImageBackgroundTint(colorStateList: ColorStateList?) {
        binding.ivCentre.imageTintList = colorStateList
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
                        onClickListener,
                        clickInterval
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
                        onClickListener,
                        clickInterval
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
                        onClickListener,
                        clickInterval
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

    private fun LottieAnimationView.playLottieAnimation(setAnimation: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            setAnimation.invoke()
            playAnimation()
        }
    }

    enum class ImageTypes(var size: Int) {
        ICON(40),
        MEDIA(130);

        companion object {
            fun Int.findImageTypeByOrdinal() = entries.find { it.ordinal == this }
        }
    }
}
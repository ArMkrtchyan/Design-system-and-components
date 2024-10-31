package am.acba.component.slider

import  am.acba.component.R
import am.acba.component.databinding.LayoutPrimarySliderBinding
import am.acba.component.extensions.inflater
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import androidx.core.view.isVisible
import kotlin.apply
import kotlin.text.isNullOrEmpty

class PrimarySlider @JvmOverloads constructor(
    context: android.content.Context, attrs: android.util.AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { LayoutPrimarySliderBinding.inflate(context.inflater(), this, false) }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.PrimarySlider).apply {
            layoutParams = LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            addView(binding.root, layoutParams)
            val sliderStartText = getString(R.styleable.PrimarySlider_sliderStartText)
            val sliderEndText = getString(R.styleable.PrimarySlider_sliderEndText)
            val value = getFloat(R.styleable.PrimarySlider_android_value, 1f)
            val valueTo = getFloat(R.styleable.PrimarySlider_android_valueTo, 1f)
            val valueFrom = getFloat(R.styleable.PrimarySlider_android_valueFrom, 0f)
            val stepSize = getFloat(R.styleable.PrimarySlider_android_stepSize, 1f)
            setStartText(sliderStartText)
            setEndText(sliderEndText)
            setMinValue(valueFrom)
            setMaxValue(valueTo)
            setValue(value)
            setStepSize(stepSize)
            recycle()
            invalidate()
        }
    }

    fun setStartText(title: String?) {
        binding.startText.text = title
        binding.startText.isVisible = !title.isNullOrEmpty()
    }

    fun setEndText(title: String?) {
        binding.endText.text = title
        binding.endText.isVisible = !title.isNullOrEmpty()
    }

    fun setMinValue(valueFrom: Float) {
        binding.slider.valueFrom = valueFrom
    }

    fun setMaxValue(valueTo: Float) {
        binding.slider.valueTo = valueTo
    }

    fun setValue(value: Float) {
        binding.slider.value = value
    }

    fun setStepSize(stepSize: Float) {
        binding.slider.stepSize = stepSize
    }

    fun addOnSliderChangeListener(listener: com.google.android.material.slider.Slider.OnChangeListener) {
        binding.slider.addOnChangeListener(listener)
    }

    fun removeOnSliderChangeListener(listener: com.google.android.material.slider.Slider.OnChangeListener) {
        binding.slider.removeOnChangeListener(listener)
    }

    fun addOnSliderChangeListener(listener: com.google.android.material.slider.Slider.OnSliderTouchListener) {
        binding.slider.addOnSliderTouchListener(listener)
    }

    fun removeOnSliderChangeListener(listener: com.google.android.material.slider.Slider.OnSliderTouchListener) {
        binding.slider.removeOnSliderTouchListener(listener)
    }
}
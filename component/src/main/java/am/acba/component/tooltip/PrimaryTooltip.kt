package am.acba.component.tooltip

import am.acba.component.R
import am.acba.component.databinding.PrimaryTooltipLayoutBinding
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.StringRes

class PrimaryTooltip : FrameLayout {
    private val binding by lazy {
        PrimaryTooltipLayoutBinding.inflate(
            context.inflater(),
            this,
            false
        )
    }

    constructor(context: Context) : super(context) {

    }

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
        setBackwardClickListener()
        setSkipClickListener()
        setForwardClickListener()
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

    fun setButtonTitle(@StringRes stringRes: Int) {
        binding.skip.text = context.getString(stringRes)

    }

    fun setTooltipCount(count: String) {
        binding.tooltipCount.text = count
    }

    fun setBackwardClickListener() {
        binding.backward.setOnClickListener {
            Toast.makeText(context,"back",Toast.LENGTH_SHORT).show()
        }
    }

    fun setForwardClickListener() {
        binding.forward.setOnClickListener {
            Toast.makeText(context,"forward",Toast.LENGTH_SHORT).show()

        }
    }

    fun setSkipClickListener() {
        binding.skip.setOnClickListener {
            Toast.makeText(context,"skip",Toast.LENGTH_SHORT).show()

        }
    }


}
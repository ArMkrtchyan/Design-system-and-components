package am.acba.component.button

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.button.MaterialButton

class PrimaryButton : MaterialButton {
    private var isPreventDoubleClick = true
    private var clickInterval = 1000

    constructor(context: Context) : super(context, null, R.attr.primaryButtonStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryButtonStyle) {
        init(attrs, R.attr.primaryButtonStyle)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, R.attr.primaryButtonStyle) {
        init(attrs, R.attr.primaryButtonStyle)
    }

    private fun init(attrs: AttributeSet, defStyleAttr: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryButton, defStyleAttr, R.style.Button_Style_Primary).apply {
            isPreventDoubleClick = getBoolean(R.styleable.PrimaryButton_isPreventClick, true)
            clickInterval = getInt(R.styleable.PrimaryButton_clickInterval, 1000)
            recycle()
        }
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        if (isPreventDoubleClick && onClickListener != null) {
            super.setOnClickListener(PreventDoubleClickListener(onClickListener, clickInterval))
        } else {
            super.setOnClickListener(onClickListener)
        }
    }
}
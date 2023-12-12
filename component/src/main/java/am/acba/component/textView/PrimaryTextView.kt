package am.acba.component.textView

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView

class PrimaryTextView : MaterialTextView {
    constructor(context: Context) : super(context, null, R.attr.primaryTextViewStyle)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryTextViewStyle) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, R.attr.primaryTextViewStyle) {
        init(attrs)
    }

    private var mIsPreventDoubleClick = true

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryTextView).apply {
            mIsPreventDoubleClick = getBoolean(R.styleable.PrimaryTextView_isPreventClick, true)
            recycle()
        }
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        if (mIsPreventDoubleClick) super.setOnClickListener(PreventDoubleClickListener(onClickListener))
        else super.setOnClickListener(onClickListener)
    }
}
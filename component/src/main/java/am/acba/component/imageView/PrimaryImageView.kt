package am.acba.component.imageView

import am.acba.component.PreventDoubleClickListener
import am.acba.component.R
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class PrimaryImageView : AppCompatImageView {
    constructor(context: Context) : super(context, null, R.attr.primaryImageViewStyle)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryImageViewStyle) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, R.attr.primaryImageViewStyle) {
        init(attrs)
    }

    private var mIsPreventDoubleClick = true

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryImageView).apply {
            mIsPreventDoubleClick = getBoolean(R.styleable.PrimaryImageView_isPreventClick, true)
            recycle()
        }
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        if (mIsPreventDoubleClick) super.setOnClickListener(PreventDoubleClickListener(onClickListener))
        else super.setOnClickListener(onClickListener)
    }
}
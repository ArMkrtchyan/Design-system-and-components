package am.acba.component.toolbar

import am.acba.component.R
import am.acba.component.extensions.dpToPx
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.google.android.material.appbar.MaterialToolbar

class PrimaryToolbar : MaterialToolbar {
    constructor(context: Context) : super(context, null, R.attr.primaryToolbarStyle) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryToolbarStyle) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, R.attr.primaryToolbarStyle) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryToolbar).apply {
            val titleTextView = getTextView()
            titleTextView?.translationX = (-12).dpToPx().toFloat()
            recycle()
        }
    }

    private fun getTextView(): TextView? {
        for (i in 0 until this.childCount) {
            val child = this.getChildAt(i)
            if (child is TextView) {
                return child
            }
        }
        return null
    }
}
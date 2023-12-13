package am.acba.component.toolbar

import am.acba.component.R
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
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
            val navIconView = getNavIcon()
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

    private fun getNavIcon(): AppCompatImageButton? {
        for (i in 0 until this.childCount) {
            val child = this.getChildAt(i)
            if (child is AppCompatImageButton) {
                return child
            }
        }
        return null
    }
}
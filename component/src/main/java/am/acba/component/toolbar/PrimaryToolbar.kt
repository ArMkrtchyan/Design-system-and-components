package am.acba.component.toolbar

import am.acba.component.R
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.getStatusBarHeight
import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.appbar.MaterialToolbar

class PrimaryToolbar : MaterialToolbar {
    private var collapseStatusBarHeight = false

    constructor(context: Context) : super(context, null, R.attr.primaryToolbarStyle) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryToolbarStyle) {
        init(attrs, R.attr.primaryToolbarStyle)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, R.attr.primaryToolbarStyle) {
        init(attrs, R.attr.primaryToolbarStyle)
    }

    private fun init(attrs: AttributeSet, defStyleAttr: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryToolbar, defStyleAttr, R.style.Toolbar_Primary).apply {
            collapseStatusBarHeight = getBoolean(R.styleable.PrimaryToolbar_collapseStatusBarHeight, false)
            for (i in 0 until menu.size()) {
                menu.getItem(i).icon?.let { meniIcon ->
                    DrawableCompat.setTintList(meniIcon, context.getColorStateListFromAttr(R.attr.contentPrimary))
                }
            }
            val titleTextView = getTextView()
            titleTextView?.translationX = (-12).dpToPx().toFloat()
            recycle()
        }
    }

    fun createOptionsMenu(
        fragmentActivity: FragmentActivity,
        @MenuRes optionsMenu: Int,
        viewLifecycleOwner: LifecycleOwner,
        onMenuItemSelected: (menuItem: MenuItem) -> Boolean
    ) {
        val menuHost: MenuHost = fragmentActivity
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(optionsMenu, menu)
                for (i in 0 until menu.size()) {
                    menu.getItem(i).icon?.let { meniIcon ->
                        DrawableCompat.setTintList(meniIcon, context.getColorStateListFromAttr(R.attr.contentPrimary))
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return onMenuItemSelected.invoke(menuItem)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!collapseStatusBarHeight && parent != null) {
            (parent as View).setPadding(0.dpToPx(), context.getStatusBarHeight(), 0.dpToPx(), 0.dpToPx())
        }
    }
}
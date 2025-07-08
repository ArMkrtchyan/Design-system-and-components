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
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.appbar.MaterialToolbar

class PrimaryToolbar : MaterialToolbar, LifecycleEventObserver {
    private var collapseStatusBarHeight = false
    private var menuProvider: MenuProvider? = null
    private var menuHost: MenuHost? = null
    private var currentMenu: Int? = -1

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
            recycle()
        }
        getTextView()?.id = R.id.centre_title
        getIcon()?.id = R.id.leftIcon
    }

    fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.removeObserver(this)
        lifecycleOwner.lifecycle.addObserver(this)
    }

    fun createOptionsMenu(
        fragmentActivity: FragmentActivity,
        @MenuRes optionsMenu: Int,
        onMenuItemSelected: (menuItem: MenuItem) -> Boolean
    ) {
        menuHost = fragmentActivity
        if (currentMenu != optionsMenu) {
            currentMenu = optionsMenu
            menuProvider?.let { menuHost?.removeMenuProvider(it) }
            menuProvider = object : MenuProvider {
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
            }
            menuHost?.addMenuProvider(menuProvider!!)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                menuProvider?.let {
                    menuHost?.removeMenuProvider(it)
                    menuHost?.addMenuProvider(it)
                }
            }

            Lifecycle.Event.ON_PAUSE -> {
                menuProvider?.let {
                    menuHost?.removeMenuProvider(it)
                }
            }

            else -> {}
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

    private fun getIcon(): ImageView? {
        for (i in 0 until this.childCount) {
            val child = this.getChildAt(i)
            if (child is ImageView) {
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
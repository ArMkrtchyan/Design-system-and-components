package am.acba.component.toolbar

import am.acba.component.R
import am.acba.component.databinding.CollapsingToolbarLayoutBinding
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.appbar.AppBarLayout

class PrimaryCollapsingToolbar : AppBarLayout {
    private val binding by lazy { CollapsingToolbarLayoutBinding.inflate(context.inflater(), this, false) }
    lateinit var toolbar: PrimaryToolbar

    constructor(context: Context) : super(context, null, R.attr.primaryCollapsingToolbarStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primaryCollapsingToolbarStyle) {
        init(attrs, R.attr.primaryCollapsingToolbarStyle)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, R.attr.primaryCollapsingToolbarStyle) {
        init(attrs, R.attr.primaryCollapsingToolbarStyle)
    }


    private fun init(attrs: AttributeSet, defStyleAttr: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryCollapsingToolbar, defStyleAttr, R.style.CollapsingToolbarStyle).apply {
            toolbar = binding.toolbar
            background = null
            binding.collapsingToolbar.title = getString(R.styleable.PrimaryCollapsingToolbar_toolbarTitle)
            addView(binding.root)
            recycle()
            invalidate()
        }
    }

    fun setNavigationOnClickListener(listener: OnClickListener?) {
        toolbar.setNavigationOnClickListener(listener)
    }

    fun setToolbarTitle(title: String?) {
        binding.collapsingToolbar.title = title
    }

    fun setToolbarTitle(@StringRes stringRes: Int) {
        binding.collapsingToolbar.title = context.getString(stringRes)
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
}
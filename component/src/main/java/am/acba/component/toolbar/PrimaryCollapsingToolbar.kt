package am.acba.component.toolbar

import am.acba.component.R
import am.acba.component.databinding.CollapsingToolbarLayoutBinding
import am.acba.component.extensions.inflater
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
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


    @SuppressLint("RestrictedApi")
    private fun init(attrs: AttributeSet, defStyleAttr: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryCollapsingToolbar, defStyleAttr, R.style.CollapsingToolbarStyle).apply {
            toolbar = binding.toolbar
            background = null
            binding.collapsingToolbar.title = getString(R.styleable.PrimaryCollapsingToolbar_toolbarTitle)
            binding.collapsingToolbar.maxLines = getInt(R.styleable.PrimaryCollapsingToolbar_toolbarTitleMaxLines, 30)
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
        onMenuItemSelected: (menuItem: MenuItem) -> Boolean
    ) {
        toolbar.createOptionsMenu(fragmentActivity, optionsMenu, onMenuItemSelected)
    }
}
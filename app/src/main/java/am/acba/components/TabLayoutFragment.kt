package am.acba.components

import am.acba.component.extensions.log
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentTabLayoutBinding
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout


class TabLayoutFragment : BaseViewBindingFragment<FragmentTabLayoutBinding>() {
    override val inflate: Inflater<FragmentTabLayoutBinding>
        get() = FragmentTabLayoutBinding::inflate

    override val toolbar: Toolbar
        get() = mBinding.toolbar

    private val tabName = arrayListOf("Tab1", "Tab2","Tab3", "Tab4","Tab5", "Tab6")

    override fun FragmentTabLayoutBinding.initView() {
        tabLayout.setTabItemsList(tabName)
        tabLayout.setTabItemBadgeText(1, "4")
        tabLayout.setSelectTab(3)
        tabLayout.tabGravity = TabLayout.GRAVITY_CENTER
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.onTabSelectListener { position->
            position.log("TabPosition")
        }
    }

}

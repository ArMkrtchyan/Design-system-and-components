package am.acba.components

import am.acba.component.extensions.log
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentTabLayoutBinding
import com.google.android.material.tabs.TabLayout


class TabLayoutFragment : BaseViewBindingFragment<FragmentTabLayoutBinding>() {
    override val inflate: Inflater<FragmentTabLayoutBinding>
        get() = FragmentTabLayoutBinding::inflate

    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    private val tabName = arrayListOf("Բոլորը", "Acba Digital գործարքներ", "Tab3", "Tab4", "Tab5", "Tab6")

    override fun FragmentTabLayoutBinding.initView() {
        tabLayout.setTabItemsList(tabName)
        tabLayout.setTabItemBadgeText(0, "3")
        tabLayout.setTabItemBadgeText(1, "44+")
        tabLayout.setTabItemBadgeText(2, "44+")
        tabLayout.setSelectTab(1)
        tabLayout.tabGravity = TabLayout.GRAVITY_CENTER
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.onTabSelectListener { position ->
            position.log("TabPosition")
        }
    }

}

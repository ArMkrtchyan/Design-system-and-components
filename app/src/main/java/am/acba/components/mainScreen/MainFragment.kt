package am.acba.components.mainScreen

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentMainBinding
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : BaseViewBindingFragment<FragmentMainBinding>() {

    private var fragments = arrayListOf<Fragment>()
    override val inflate: Inflater<FragmentMainBinding>
        get() = FragmentMainBinding::inflate

    override fun FragmentMainBinding.initView() {
        switcher.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                MainActivity.darkTheme = isChecked
                mActivity.recreate()
            }
        }
        initViewPager()
    }

    private fun initViewPager() {
        val adapter = StatePagerAdapter(childFragmentManager, lifecycle, fragments)
        fragments.clear()
        fragments.add(ComponentsFragment())
        fragments.add(ComponentsFragment(true))
        mBinding.viewPager.adapter = adapter
        mBinding.viewPager.isSaveEnabled = false
        val tabName = arrayListOf("Native components", "Compose components")
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            tab.text = tabName[position]
        }.attach()
    }
}
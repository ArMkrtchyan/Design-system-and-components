package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentToolbarBinding
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class ToolbarFragment : BaseViewBindingFragment<FragmentToolbarBinding>() {
    override val inflate: Inflater<FragmentToolbarBinding>
        get() = FragmentToolbarBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.collapsingToolbar.toolbar

    override fun FragmentToolbarBinding.initView() {
        mActivity.setSupportActionBar(collapsingToolbar.toolbar)
        collapsingToolbar.createOptionsMenu(requireActivity(), am.acba.component.R.menu.toolbar_menu) { menuItem ->
            when (menuItem.itemId) {
                am.acba.component.R.id.menu_main_download -> {
                    Toast.makeText(requireContext(), "Download", Toast.LENGTH_SHORT).show()
                    true
                }

                am.acba.component.R.id.menu_main_setting2 -> {
                    Toast.makeText(requireContext(), "Settings", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
        texx.setOnClickListener { findNavController().navigate(R.id.chipsFragment) }
    }
}
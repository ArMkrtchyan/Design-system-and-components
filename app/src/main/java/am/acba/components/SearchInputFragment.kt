package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentSearchBinding
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class SearchInputFragment : BaseViewBindingFragment<FragmentSearchBinding>() {
    override val inflate: Inflater<FragmentSearchBinding>
        get() = FragmentSearchBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentSearchBinding.initView() {
        search2.setOnClickListener {
            Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
        }
    }

}
package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentSearchBinding
import android.widget.Toast

class SearchInputFragment : BaseViewBindingFragment<FragmentSearchBinding>() {
    override val inflate: Inflater<FragmentSearchBinding>
        get() = FragmentSearchBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentSearchBinding.initView() {
        search2.setOnClickListener {
            Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
        }
    }

}
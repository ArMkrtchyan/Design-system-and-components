package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentListItemBinding
import androidx.appcompat.widget.Toolbar

class ListItemFragment : BaseViewBindingFragment<FragmentListItemBinding>() {
    override val inflate: Inflater<FragmentListItemBinding>
        get() = FragmentListItemBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentListItemBinding.initView() {

    }

}
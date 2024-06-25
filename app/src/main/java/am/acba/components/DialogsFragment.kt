package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentDialogsBinding
import androidx.appcompat.widget.Toolbar

class DialogsFragment : BaseViewBindingFragment<FragmentDialogsBinding>() {
    override val inflate: Inflater<FragmentDialogsBinding>
        get() = FragmentDialogsBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentDialogsBinding.initView() {

    }

}
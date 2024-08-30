package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentDropDownBinding
import androidx.appcompat.widget.Toolbar

class DropDownFragment : BaseViewBindingFragment<FragmentDropDownBinding>() {
    override val inflate: Inflater<FragmentDropDownBinding>
        get() = FragmentDropDownBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentDropDownBinding.initView() {
        dropDown.setStartIcon(am.acba.component.R.drawable.ic_flag_am)
        dropDown.setOnClickListener {
            dropDown.addFocus()
        }
    }

}
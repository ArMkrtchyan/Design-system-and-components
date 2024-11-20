package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentDropDownBinding

class DropDownFragment : BaseViewBindingFragment<FragmentDropDownBinding>() {
    override val inflate: Inflater<FragmentDropDownBinding>
        get() = FragmentDropDownBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentDropDownBinding.initView() {
        dropDown.loadStartIcon("https://online1-test.acba.am/Shared/Currencies/EUR.png")
        dropDown.setOnClickListener {
            dropDown.addFocus()
        }
    }

}
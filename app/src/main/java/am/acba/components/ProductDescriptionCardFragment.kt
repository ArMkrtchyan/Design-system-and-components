package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentProductDescriptionCardBinding

class ProductDescriptionCardFragment : BaseViewBindingFragment<FragmentProductDescriptionCardBinding>() {
    override val inflate: Inflater<FragmentProductDescriptionCardBinding>
        get() = FragmentProductDescriptionCardBinding::inflate

    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentProductDescriptionCardBinding.initView() {

    }
}
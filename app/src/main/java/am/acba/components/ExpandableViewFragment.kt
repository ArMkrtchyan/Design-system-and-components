package am.acba.components

import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentExpandableViewBinding

class ExpandableViewFragment : BaseViewBindingFragment<FragmentExpandableViewBinding>() {
    override val inflate: Inflater<FragmentExpandableViewBinding>
        get() = FragmentExpandableViewBinding::inflate

    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentExpandableViewBinding.initView() {
        mBinding.accordionView2.setEndTextColor(context?.getColorStateListFromAttr(am.acba.component.R.attr.contentBrand))
        mBinding.accordionView2.setCurrencyTextColor(context?.getColorStateListFromAttr(am.acba.component.R.attr.contentPrimaryTonal1))
        mBinding.accordionView2.setOnAccordionClickListener { }
    }
}

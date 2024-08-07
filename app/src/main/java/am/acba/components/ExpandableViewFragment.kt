package am.acba.components

import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentExpandableViewBinding
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class ExpandableViewFragment : BaseViewBindingFragment<FragmentExpandableViewBinding>() {
    override val inflate: Inflater<FragmentExpandableViewBinding>
        get() = FragmentExpandableViewBinding::inflate

    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentExpandableViewBinding.initView() {
//        mBinding.accordionView.setStartIconDrawable(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.ic_info))
//        mBinding.accordionView.setStartIconTint(context?.getColorStateListFromAttr(am.acba.component.R.attr.contentPrimary))
    }
}

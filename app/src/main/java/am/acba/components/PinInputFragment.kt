package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentPinInputBinding
import androidx.appcompat.widget.Toolbar

class PinInputFragment : BaseViewBindingFragment<FragmentPinInputBinding>() {
    override val inflate: Inflater<FragmentPinInputBinding>
        get() = FragmentPinInputBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentPinInputBinding.initView() {

    }

}
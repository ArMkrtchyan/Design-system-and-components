package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentPhoneNumberInputBinding
import androidx.appcompat.widget.Toolbar

class PhoneNumberInputFragment : BaseViewBindingFragment<FragmentPhoneNumberInputBinding>() {
    override val inflate: Inflater<FragmentPhoneNumberInputBinding>
        get() = FragmentPhoneNumberInputBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentPhoneNumberInputBinding.initView() {

    }

}
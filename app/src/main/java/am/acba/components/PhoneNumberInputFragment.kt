package am.acba.components

import am.acba.component.extensions.log
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentPhoneNumberInputBinding
import android.content.Intent
import androidx.appcompat.widget.Toolbar

class PhoneNumberInputFragment : BaseViewBindingFragment<FragmentPhoneNumberInputBinding>() {
    override val inflate: Inflater<FragmentPhoneNumberInputBinding>
        get() = FragmentPhoneNumberInputBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentPhoneNumberInputBinding.initView() {
        phoneNumber.isEnabled = true
        phoneNumber.handleAcbaContactClick {
            "Open acba contact dialog".log("Acba Contact")
        }
        search.setOnClickListener { phoneNumber.getFormattedFullNumber().log("PhoneNumber") }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.phoneNumber.onActivityResult(requestCode, resultCode, data)
    }
}
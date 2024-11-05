package am.acba.components

import am.acba.component.extensions.log
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentPhoneNumberInputBinding
import android.content.Intent

class PhoneNumberInputFragment : BaseViewBindingFragment<FragmentPhoneNumberInputBinding>() {
    override val inflate: Inflater<FragmentPhoneNumberInputBinding>
        get() = FragmentPhoneNumberInputBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentPhoneNumberInputBinding.initView() {
        phoneNumber.isEnabled = true
        phoneNumber.handleAcbaContactClick {
            "Open acba contact dialog".log("Acba Contact")
        }
        phoneNumber.helpText = "Min 25 symbol"
        phoneNumber.errorText = "Something wrong happened"
        phoneNumber.fixCountyCode()
        search.setOnClickListener {
            phoneNumber.getFormattedFullNumber().log("PhoneNumber")
            phoneNumber.getFullNumber().log("PhoneNumber")
            phoneNumber.getFullNumberWithPlus().log("PhoneNumber")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.phoneNumber.onActivityResult(requestCode, resultCode, data)
    }
}
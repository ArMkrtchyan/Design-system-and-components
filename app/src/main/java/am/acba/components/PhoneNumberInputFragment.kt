package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentPhoneNumberInputBinding
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat

class PhoneNumberInputFragment : BaseViewBindingFragment<FragmentPhoneNumberInputBinding>() {
    override val inflate: Inflater<FragmentPhoneNumberInputBinding>
        get() = FragmentPhoneNumberInputBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentPhoneNumberInputBinding.initView() {
        phoneNumber.isEnabled = true
        phoneNumber.handleAcbaContactClick {
            Log.i("Acba Contact", "Open acba contact dialog")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.phoneNumber.onActivityResult(requestCode, resultCode, data)
    }
}
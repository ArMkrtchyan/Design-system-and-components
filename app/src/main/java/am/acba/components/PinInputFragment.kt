package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentPinInputBinding
import android.util.Log
import android.widget.Toast

class PinInputFragment : BaseViewBindingFragment<FragmentPinInputBinding>() {
    override val inflate: Inflater<FragmentPinInputBinding>
        get() = FragmentPinInputBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentPinInputBinding.initView() {
        toolbar.createOptionsMenu(requireActivity(), am.acba.component.R.menu.toolbar_menu) { menuItem ->
            when (menuItem.itemId) {
                am.acba.component.R.id.menu_main_download -> {
                    Toast.makeText(requireContext(), "Download", Toast.LENGTH_SHORT).show()
                    true
                }

                am.acba.component.R.id.menu_main_setting2 -> {
                    Toast.makeText(requireContext(), "Settings", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
        var pinCount = ""

        mBinding.showPin.setOnClickListener {
            mBinding.pinInput.setPinShow(true)
        }
        mBinding.hidePin.setOnClickListener {
            mBinding.pinInput.setPinShow(false)
        }

        mBinding.addError.setOnClickListener {
            mBinding.pinInput.addErrorState("Error")
        }
        mBinding.removeError.setOnClickListener {
            mBinding.pinInput.removeErrorState()
        }

        mBinding.clearPinInput.setOnClickListener {
            mBinding.pinInput.clearPinInput()
        }

        mBinding.switchPinVisibility.setOnClickListener {
            mBinding.pinInput.switchPinVisibility()
        }

        mBinding.pinInput.setOnPinFocusChangeListener { it ->
            Log.d("TAG", "setOnPinFocusChangeListener $it")
        }
    }
}
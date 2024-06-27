package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentPinInputBinding
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class PinInputFragment : BaseViewBindingFragment<FragmentPinInputBinding>() {
    override val inflate: Inflater<FragmentPinInputBinding>
        get() = FragmentPinInputBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentPinInputBinding.initView() {
        toolbar.createOptionsMenu(requireActivity(), am.acba.component.R.menu.toolbar_menu, viewLifecycleOwner) { menuItem ->
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
        mBinding.addError.setOnClickListener {
            mBinding.pinInput.addErrorState("Error")
        }
        mBinding.removeError.setOnClickListener {
            mBinding.pinInput.removeErrorState()
        }

        mBinding.clearPinInput.setOnClickListener{
            mBinding.pinInput.clearPinInput()
        }

        mBinding.addPinCount.setOnClickListener {
            pinCount = "$pinCount*"
            mBinding.pinInput.setUiPinCount(pinCount = pinCount)
        }

        mBinding.removePinCount.setOnClickListener {
            pinCount = pinCount.dropLast(1)
            mBinding.pinInput.setUiPinCount(pinCount = pinCount)
        }
    }

}
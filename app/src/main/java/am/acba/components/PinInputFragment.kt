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
package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentInputsBinding
import am.acba.component.extensions.hideSoftInput
import android.graphics.Rect
import android.text.InputType
import androidx.appcompat.widget.Toolbar

class InputsFragment : BaseViewBindingFragment<FragmentInputsBinding>() {
    override val inflate: Inflater<FragmentInputsBinding>
        get() = FragmentInputsBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentInputsBinding.initView() {
        input.apply {
            suffixText = "AMD"
            setOnFocusChangeListener { _, _ ->

            }
        }
    }

}
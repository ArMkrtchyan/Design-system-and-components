package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentInputsBinding
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class InputsFragment : BaseViewBindingFragment<FragmentInputsBinding>() {
    override val inflate: Inflater<FragmentInputsBinding>
        get() = FragmentInputsBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentInputsBinding.initView() {

        input.apply {
            setEndIconOnClickListener { Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show() }
            setStartIconOnClickListener { Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show() }
            setOnFocusChangeListener { _, _ ->

            }
        }
    }

}
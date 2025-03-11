package am.acba.components

import am.acba.component.snackbar.PrimarySnackBar
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.databinding.FragmentSnackBarBinding
import android.view.LayoutInflater
import android.view.ViewGroup


class SnackBarFragment : BaseViewBindingFragment<FragmentSnackBarBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSnackBarBinding
        get() = FragmentSnackBarBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentSnackBarBinding.initView() {
        showSnackbar.setOnClickListener {
            PrimarySnackBar.build(requireActivity()) {
                title = getText()
            }
        }
        showSnackbarWithIcon.setOnClickListener {
            PrimarySnackBar.build(requireActivity()) {
                title = getText()
                icon = am.acba.component.R.drawable.ic_info
            }
        }
        showSnackbarWithLottie.setOnClickListener {
            PrimarySnackBar.build(requireActivity()) {
                title = getText()
                lottieIcon = "check_test.json"
            }
        }
        showSnackbarWithCloseIcon.setOnClickListener {
            PrimarySnackBar.build(requireActivity()) {
                title = getText()
                isUserClosable = true
                lifecycleOwner = viewLifecycleOwner
            }
        }
        showSnackbarWithIconAndCloseAicon.setOnClickListener {
            PrimarySnackBar.build(requireActivity()) {
                title = getText()
                icon = am.acba.component.R.drawable.ic_info
                isUserClosable = true
                lifecycleOwner = viewLifecycleOwner
            }
        }
        showSnackbarWithLottieAndCloseAicon.setOnClickListener {
            PrimarySnackBar.build(requireActivity()) {
                title = getText()
                lottieIcon = "check_test.json"
                isUserClosable = true
                lifecycleOwner = viewLifecycleOwner
            }
        }
    }

    private fun getText(): String {
        val text = mBinding.message.editText?.text.toString().ifEmpty { "Enter your text in input field" }
        return text
    }
}
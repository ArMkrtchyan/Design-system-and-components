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
                title = "Հասկանալի տեքստ"
            }
        }
        showSnackbarWithIcon.setOnClickListener {
            PrimarySnackBar.build(requireActivity()) {
                title = "Հայտնի է, որ ընթերցողը, կարդալով հասկանալի տեքստ Հայտնի է, որ ընթերցողը, կարդալով հասկանալի "
                icon = am.acba.component.R.drawable.ic_info
            }
        }
        showSnackbarWithLottie.setOnClickListener {
            PrimarySnackBar.build(requireActivity()) {
                title =
                    "Հայտնի է, որ ընթերցողը, կարդալով հասկանալի տեքստ Հայտնի է, որ ընթերցողը, կարդալով հասկանալի Հայտնի է, որ ընթերցողը, կարդալով հասկանալի տեքստ Հայտնի է, որ ընթերցողը, կարդալով հասկանալի Հայտնի է, որ ընթերցողը, կարդալով հասկանալի տեքստ Հայտնի է, որ ընթերցողը, կարդալով հասկանալի "
                lottieIcon = "check_test.json"
            }
        }
        showSnackbarWithCloseIcon.setOnClickListener {
            PrimarySnackBar.build(requireActivity()) {
                title = "Հայտնի է, որ ընթերցողը, կարդալով հասկանալի տեքստ"
                isUserClosable = true
            }
        }
        showSnackbarWithIconAndCloseAicon.setOnClickListener {
            PrimarySnackBar.build(requireActivity()) {
                title = "Հայտնի է, որ ընթերցողը, կարդալով հասկանալի տեքստ"
                icon = am.acba.component.R.drawable.ic_info
                isUserClosable = true
            }
        }
        showSnackbarWithLottieAndCloseAicon.setOnClickListener {
            PrimarySnackBar.build(requireActivity()) {
                title = "Հայտնի է, որ ընթերցողը, կարդալով հասկանալի տեքստ"
                lottieIcon = "check_test.json"
                isUserClosable = true
            }
        }
    }
}
package am.acba.components.base

import am.acba.component.cardInput.PrimaryCardInput
import am.acba.component.phoneNumberInput.PhoneNumberInput
import am.acba.component.toolbar.PrimaryToolbar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingFragment<VB : ViewBinding> : BaseFragment() {

    protected open val keepBindingAlive: Boolean = true
    private lateinit var _binding: VB
    val mBinding: VB
        get() = _binding
    protected abstract val inflate: Inflater<VB>
    protected abstract fun VB.initView()
    open val toolbar: PrimaryToolbar? = null
    open val setInsets: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!::_binding.isInitialized || !keepBindingAlive) {
            _binding = inflate(inflater, container, false)
            _binding.initView()
        }
        if (setInsets) {
            ViewCompat.setOnApplyWindowInsetsListener(_binding.root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
        return _binding.root
    }

    override fun onResume() {
        super.onResume()
        toolbar?.let { mToolbar ->
            mActivity.setSupportActionBar(toolbar)
            mToolbar.setNavigationOnClickListener { mActivity.onBackPressed() }
            mToolbar.setLifecycleOwner(viewLifecycleOwner)
            arguments?.getString("Title")?.let { mToolbar.setTitle(it) }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i("PermissionsResult", "$requestCode")
        findPhoneNumberInputRecursively(view)?.onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun findPhoneNumberInputRecursively(rootView: View?): PhoneNumberInput? {
        if (rootView is ViewGroup) {
            for (i in 0 until rootView.childCount) {
                val child = rootView.getChildAt(i)
                if (child is PhoneNumberInput) {
                    return child
                } else if (child is ViewGroup) {
                    val phoneNumberInput = findPhoneNumberInputRecursively(child)
                    if (phoneNumberInput != null) {
                        return phoneNumberInput
                    }
                }
            }
        }
        return null
    }

    private fun findCreditCardInputRecursively(rootView: View?): PrimaryCardInput? {
        if (rootView is ViewGroup) {
            for (i in 0 until rootView.childCount) {
                val child = rootView.getChildAt(i)
                if (child is PrimaryCardInput) {
                    return child
                } else if (child is ViewGroup) {
                    val cardInput = findCreditCardInputRecursively(child)
                    if (cardInput != null) {
                        return cardInput
                    }
                }
            }
        }
        return null
    }
}
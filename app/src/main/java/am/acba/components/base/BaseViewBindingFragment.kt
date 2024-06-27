package am.acba.components.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingFragment<VB : ViewBinding> : BaseFragment() {

    protected open val keepBindingAlive: Boolean = true
    private lateinit var _binding: VB
    val mBinding: VB
        get() = _binding
    protected abstract val inflate: Inflater<VB>
    protected abstract fun VB.initView()
    open val toolbar: Toolbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!::_binding.isInitialized || !keepBindingAlive) {
            _binding = inflate(inflater, container, false)
            _binding.initView()
        }
        return _binding.root
    }

    override fun onResume() {
        super.onResume()
        toolbar?.let {
            it.setNavigationOnClickListener { mActivity.onBackPressed() }
        }
    }
}
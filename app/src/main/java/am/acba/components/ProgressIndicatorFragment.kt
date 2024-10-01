package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentProgressIndicatorBinding

class ProgressIndicatorFragment : BaseViewBindingFragment<FragmentProgressIndicatorBinding>() {
    override val inflate: Inflater<FragmentProgressIndicatorBinding>
        get() = FragmentProgressIndicatorBinding::inflate


    override fun FragmentProgressIndicatorBinding.initView() {
        mBinding.btn.setOnClickListener {
            mBinding.progressBar.setProgress(3)
        }
    }
}
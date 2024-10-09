package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentProgressViewBinding

class ProgressViewFragment : BaseViewBindingFragment<FragmentProgressViewBinding>() {
    override val inflate: Inflater<FragmentProgressViewBinding>
        get() = FragmentProgressViewBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentProgressViewBinding.initView() {
        search.setOnClickListener {
            mBinding.progressCardView.setProgress(1, true)
        }
    }
}
package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.databinding.FragmentLoanComponentsBinding
import android.view.LayoutInflater
import android.view.ViewGroup

class LoanComponentsFragment : BaseViewBindingFragment<FragmentLoanComponentsBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoanComponentsBinding
        get() = FragmentLoanComponentsBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentLoanComponentsBinding.initView() {

    }

}

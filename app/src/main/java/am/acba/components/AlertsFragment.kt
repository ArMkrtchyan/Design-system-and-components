package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentAlertsBinding
import androidx.appcompat.widget.Toolbar

class AlertsFragment : BaseViewBindingFragment<FragmentAlertsBinding>() {
    override val inflate: Inflater<FragmentAlertsBinding>
        get() = FragmentAlertsBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentAlertsBinding.initView() {

    }

}
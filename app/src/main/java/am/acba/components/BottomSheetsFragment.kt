package am.acba.components

import am.acba.component.bottomsheet.ModalBottomSheet
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentAlertsBinding
import am.acba.components.databinding.FragmentBottomSheetsBinding
import am.acba.components.databinding.FragmentInputsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetsFragment : BaseViewBindingFragment<FragmentBottomSheetsBinding>() {
    override val inflate: Inflater<FragmentBottomSheetsBinding>
        get() = FragmentBottomSheetsBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentBottomSheetsBinding.initView() {
        val dynamicContainer = FragmentAlertsBinding.inflate(layoutInflater).root
        bsPersistent.setDynamicContainer(dynamicContainer)

        btnPersistent.setOnClickListener {
            bsPersistent.setState(BottomSheetBehavior.STATE_EXPANDED)
        }

        btnModal.setOnClickListener {
            val dynamicContainer = FragmentInputsBinding.inflate(layoutInflater).root
            val bottomSheet = ModalBottomSheet("Bottom Sheet title", dynamicContainer)
            bottomSheet.show(childFragmentManager, ModalBottomSheet.TAG)
        }
    }
}
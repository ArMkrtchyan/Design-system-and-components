package am.acba.components

import am.acba.component.bottomsheet.ModalBottomSheet
import am.acba.component.textView.PrimaryTextView
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentCardInputBinding

class CardInputFragment : BaseViewBindingFragment<FragmentCardInputBinding>() {

    override val inflate: Inflater<FragmentCardInputBinding>
        get() = FragmentCardInputBinding::inflate

    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentCardInputBinding.initView() {
        mBinding.card.setEndIconClickListener {
            val dynamicContainer = PrimaryTextView(requireContext()).apply {
                text = "text"
            }
            val bottomSheet = ModalBottomSheet("Bottom Sheet title", dynamicContainer)
            bottomSheet.show(childFragmentManager, ModalBottomSheet.TAG)
        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.card.onActivityResult(requestCode, resultCode, data)

        mBinding.card.getCardScanData { cardData ->
            val cardExDate = cardData.cardExDate
            val cardNumber = cardData.cardNumber
            val cardOwner = cardData.cardOwner
            val cardCVV = cardData.cardCVV
            "$cardOwner  $cardNumber  $cardExDate   $cardCVV".log("scanCardData")
        }
    }*/
}
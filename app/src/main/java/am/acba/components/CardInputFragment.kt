package am.acba.components

import am.acba.component.extensions.log
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentCardInputBinding
import android.content.Intent

class CardInputFragment : BaseViewBindingFragment<FragmentCardInputBinding>() {

    override val inflate: Inflater<FragmentCardInputBinding>
        get() = FragmentCardInputBinding::inflate

    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentCardInputBinding.initView() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.card.onActivityResult(requestCode, resultCode, data)

        mBinding.card.getCardScanData { cardData ->
            val cardExDate = cardData.cardExDate
            val cardNumber = cardData.cardNumber
            val cardOwner = cardData.cardOwner
            val cardCVV = cardData.cardCVV
            "$cardOwner  $cardNumber  $cardExDate   $cardCVV".log("scanCardData")
        }
    }
}
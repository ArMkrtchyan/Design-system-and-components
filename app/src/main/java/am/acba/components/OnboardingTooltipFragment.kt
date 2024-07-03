package am.acba.components

import am.acba.component.databinding.DialogContentTestBinding
import am.acba.component.dialog.PrimaryAlertDialog
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentOnboardingTooltipBinding
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class OnboardingTooltipFragment: BaseViewBindingFragment<FragmentOnboardingTooltipBinding>() {
    override val inflate: Inflater<FragmentOnboardingTooltipBinding>
        get() = FragmentOnboardingTooltipBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentOnboardingTooltipBinding.initView() {
        buttonPr.apply {
            setOnClickListener {
//                showPrimaryAlertDialog(requireContext(), layoutInflater)
            }
        }

        mBinding.tooltip.setTitle("AAA")
        mBinding.tooltip.setDescription("AAA AAA AAA AAA")
        mBinding.tooltip.setButtonTitle(R.string.ok)
        mBinding.tooltip.setTooltipCount("2/3")
    }

    private fun showPrimaryAlertDialog(context: Context, inflater: LayoutInflater) {
        val positiveButtonTextColor =
            context.getColorStateListFromAttr(am.acba.component.R.attr.contentBrandTonal1)
        val negativeButtonTextColor =
            context.getColorStateListFromAttr(am.acba.component.R.attr.contentDangerTonal1)
        val dialogLayoutBinding = DialogContentTestBinding.inflate(inflater)
        PrimaryAlertDialog.Builder(context)
            .icon(am.acba.component.R.drawable.checkbox_button_icon)
            .title("Օգտատերը բլոկավորված է")
            .description("Դուք կարող եք ապաբլոկավորել սեղմելով ապաբլոկավորման կոճակը:")
            .positiveButtonText("Ok")
            .positiveButtonTextColor(positiveButtonTextColor)
            .negativeButtonText("Cancel")
            .negativeButtonTextColor(negativeButtonTextColor)
            .content(dialogLayoutBinding.root)
            .positiveButtonClick {
                Toast.makeText(
                    context,
                    "positive Click",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .negativeButtonClick {
                Toast.makeText(
                    context,
                    "negative Click",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setCancelable(true)
            .build()
    }
}
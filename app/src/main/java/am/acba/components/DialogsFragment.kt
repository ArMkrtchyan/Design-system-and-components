package am.acba.components

import am.acba.component.chip.PrimaryChip
import am.acba.component.databinding.DialogContentTestBinding
import am.acba.component.dialog.PrimaryAlertDialog
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.log
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentDialogsBinding
import am.acba.components.models.RepaymentFrequency
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast

class DialogsFragment : BaseViewBindingFragment<FragmentDialogsBinding>() {
    override val inflate: Inflater<FragmentDialogsBinding>
        get() = FragmentDialogsBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentDialogsBinding.initView() {
        buttonPr.apply {
            setOnClickListener {
                showPrimaryAlertDialog(requireContext(), layoutInflater)
            }
        }
        slider.addOnSliderChangeListener { slider, value, _ ->
            if (slider.isPressed)
                value.log()
        }
        chipsView.submitChips(arrayListOf(RepaymentFrequency(1, "Ամեն ամիս", am.acba.component.R.drawable.ic_sort,PrimaryChip.ChipStartIconType.ICON),
            RepaymentFrequency(1, "3 ամիսը մեկ",am.acba.component.R.drawable.ic_sort,PrimaryChip.ChipStartIconType.ICON),
            RepaymentFrequency(1, "6 ամիսը մեկ",am.acba.component.R.drawable.ic_sort,PrimaryChip.ChipStartIconType.ICON))) {
            it.log()
        }
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
            .setCancelable(true)
            .build()
    }
}
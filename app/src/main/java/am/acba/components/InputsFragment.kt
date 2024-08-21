package am.acba.components

import am.acba.component.databinding.DialogContentTestBinding
import am.acba.component.dialog.PrimaryAlertDialog
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentInputsBinding
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class InputsFragment : BaseViewBindingFragment<FragmentInputsBinding>() {
    override val inflate: Inflater<FragmentInputsBinding>
        get() = FragmentInputsBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentInputsBinding.initView() {
        input.apply {
            suffixText = "AMD"
            setOnFocusChangeListener { _, _ ->

            }
        }

        datePicker.apply {
           this.setOnClickListener{
               showPrimaryAlertDialog(requireContext(), layoutInflater)
               this.setInputExpandedHintEnabled(false)
               this.setText("Text")
           }
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
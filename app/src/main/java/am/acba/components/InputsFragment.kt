package am.acba.components

import am.acba.component.databinding.DialogContentTestBinding
import am.acba.component.dialog.PrimaryAlertDialog
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentInputsBinding
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.widget.Toast

class InputsFragment : BaseViewBindingFragment<FragmentInputsBinding>() {
    override val inflate: Inflater<FragmentInputsBinding>
        get() = FragmentInputsBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentInputsBinding.initView() {
        input.apply {
            editText?.isSingleLine = true
            helperText = "Min amount"
            hint = "Amount"
            editText?.onFocusChangeListener = OnFocusChangeListener { _, _ ->
                isErrorEnabled =
                    ((!editText?.text.isNullOrEmpty() && (editText?.text?.length ?: 0) < 4))
                if (isErrorEnabled) {
                    error = "Amount is too short"
                }
            }
        }

        datePicker.apply {
           this.setOnClickListener{
               showPrimaryAlertDialog(requireContext(), layoutInflater)
               //Use this function if you want to have in input both text and hint(You MUST set text in input by code or XML)
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
        dialogLayoutBinding.infoPhoto.setOnClickListener {
            Log.v("sd", "")
        }
        PrimaryAlertDialog.Builder(context)
            .icon(am.acba.component.R.drawable.ic_lock)
            .title("Օգտատերը բլոկավորված է")
            .description("Դուք կարող եք ապաբլոկավորել սեղմելով ապաբլոկավորման կոճակը:")
            .positiveButtonTextColor(positiveButtonTextColor)
            .iconColor(context.getColorStateListFromAttr(am.acba.component.R.attr.contentBrandTonal1))
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
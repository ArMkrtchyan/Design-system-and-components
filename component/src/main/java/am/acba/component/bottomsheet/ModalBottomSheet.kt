package am.acba.component.bottomsheet

import am.acba.component.databinding.ModalBottomSheetBinding
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ModalBottomSheet(
    private val title: String?,
    private val dynamicContainer: View?
) : BottomSheetDialogFragment() {

    private val binding: ModalBottomSheetBinding by lazy {
        ModalBottomSheetBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dynamicContainer?.let { addContainer(it) }
        setTitle(title ?: "")
        initListeners()

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        initDialog(dialog)

        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        binding.dynamicContainer.removeAllViews()
    }

    private fun initDialog(dialog: BottomSheetDialog) {
        val screenHeight = requireActivity().window.decorView.height.toFloat()
        binding.root.post {
            val dialogHeight = binding.root.height
            val maxPeekPercentage = 0.70
            if (dialogHeight / screenHeight >= maxPeekPercentage) {
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private fun initListeners() {
        binding.ivClose.setOnClickListener { dialog?.dismiss() }
    }

    private fun addContainer(child: View) {
        binding.dynamicContainer.addView(child)
    }

    private fun setTitle(text: String) {
        binding.tvTitle.text = text
    }

    companion object {
        const val TAG = "BottomSheet"
    }
}
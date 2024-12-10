package am.acba.component.dialog

import am.acba.component.databinding.ContactBookBottomSheetLayoutBinding
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ListBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var _binding: ContactBookBottomSheetLayoutBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ContactBookBottomSheetLayoutBinding.inflate(inflater, container, false)
        binding.btnClose.setOnClickListener { dismiss() }
        binding.acbaContacts.setOnClickListener { handleActions(1) }
        binding.phoneContacts.setOnClickListener { handleActions(2) }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog = BottomSheetDialog(requireContext(), theme).apply {
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior.isDraggable = true
    }

    override fun onDismiss(dialog: DialogInterface) {
        instance = null
        super.onDismiss(dialog)
    }

    companion object {
        private var instance: ListBottomSheetDialog? = null
        fun show(
            fragmentManager: FragmentManager,
            action: ((Int) -> Unit)? = null,
        ): ListBottomSheetDialog {
            if (instance == null) {
                instance = ListBottomSheetDialog().apply { mAction = action }
                instance?.show(fragmentManager, ListBottomSheetDialog::class.java.simpleName)
            }
            return instance as ListBottomSheetDialog
        }
    }

    private fun handleActions(type: Int) {
        mAction?.invoke(type)
        dismiss()
    }

    private var mAction: ((Int) -> Unit?)? = null
}
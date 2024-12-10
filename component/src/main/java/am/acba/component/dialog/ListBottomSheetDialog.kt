package am.acba.component.dialog

import am.acba.component.bottomsheet.PrimaryBottomSheetDialog
import am.acba.component.databinding.ContactBookBottomSheetLayoutBinding
import am.acba.component.databinding.CountryBottomSheetBinding
import am.acba.component.extensions.Inflater
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ContactBooksBottomSheetDialog : PrimaryBottomSheetDialog<ContactBookBottomSheetLayoutBinding>() {

    override val inflate: Inflater<ContactBookBottomSheetLayoutBinding>
        get() = ContactBookBottomSheetLayoutBinding::inflate

    override val state: Int
        get() = BottomSheetBehavior.STATE_EXPANDED

    override val contentPaddingStart: Int
        get() = 0
    override val contentPaddingEnd: Int
        get() = 0
    override val contentPaddingBottom: Int
        get() = 0

    override fun ContactBookBottomSheetLayoutBinding.initView() {
        acbaContacts.setOnClickListener { handleActions(1) }
        phoneContacts.setOnClickListener { handleActions(2) }
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
        private var instance: ContactBooksBottomSheetDialog? = null
        fun show(
            fragmentManager: FragmentManager,
            bundle: Bundle? = null,
            action: ((Int) -> Unit)? = null,
        ): ContactBooksBottomSheetDialog {
            if (instance == null) {
                instance = ContactBooksBottomSheetDialog().apply {arguments = bundle; mAction = action }
                instance?.show(fragmentManager, ContactBooksBottomSheetDialog::class.java.simpleName)
            }
            return instance as ContactBooksBottomSheetDialog
        }
    }

    private fun handleActions(type: Int) {
        mAction?.invoke(type)
        dismiss()
    }

    private var mAction: ((Int) -> Unit?)? = null
}
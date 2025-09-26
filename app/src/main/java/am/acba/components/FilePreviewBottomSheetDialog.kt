package am.acba.components

import am.acba.component.bottomsheet.PrimaryBottomSheetDialog
import am.acba.component.extensions.Inflater
import am.acba.component.extensions.parcelable
import am.acba.components.databinding.BottomSheetFilePreviewBinding
import am.acba.components.models.ClickListener
import android.net.Uri
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.File


class FilePreviewBottomSheetDialog : PrimaryBottomSheetDialog<BottomSheetFilePreviewBinding>() {
    override val inflate: Inflater<BottomSheetFilePreviewBinding>
        get() = BottomSheetFilePreviewBinding::inflate

    override val state: Int
        get() = BottomSheetBehavior.STATE_EXPANDED
    override val contentPaddingStart: Int
        get() = 0
    override val contentPaddingEnd: Int
        get() = 0

    override fun BottomSheetFilePreviewBinding.initView() {
        openFullScreen = true
        arguments?.getString(IMAGE_URI)?.let { imagePath ->
            fileUpload.showImage(requireContext(), File(imagePath))
        }
        arguments?.parcelable<Uri>(FILE_URI)?.let { fileUri ->
            fileUpload.showFile(requireContext(), fileUri)
        }
        initListeners()
    }

    private fun BottomSheetFilePreviewBinding.initListeners() {
        val listener = arguments?.parcelable<ClickListener>(CLEAR_IMAGE)
        fileUpload.fileDeleteDialogClickListener = {
            listener?.onClick?.invoke(true)
            dialog?.dismiss()
        }
        btnPrimary.setOnClickListener {
            dialog?.dismiss()
        }
        btnGhost.setOnClickListener {
            dialog?.dismiss()
        }
    }

    companion object {
        const val IMAGE_URI = "args.imageUri"
        const val FILE_URI = "args.fileUri"
        const val CLEAR_IMAGE = "args.clearImage"
    }
}

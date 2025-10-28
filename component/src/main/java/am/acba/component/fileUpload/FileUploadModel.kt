package am.acba.component.fileUpload

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import java.io.File
import java.util.UUID

data class FileUploadModel(
    val id: String = UUID.randomUUID().toString(),
    var fileUploadState: FileUpload.FileUploadState = FileUpload.FileUploadState.EMPTY,
    var isDescriptionVisible: Boolean? = null,
    val title: String? = null,
    val body: String? = null,
    var description: String? = null,
    val emptyIcon: Drawable?,
    val emptyIconTint: ColorStateList? = null,
    val emptyIconBackground: Drawable? = null,
    val emptyIconBackgroundTint: ColorStateList? = null,
    val uploadedIcon: Drawable? = null,
    val uploadedIconTint: ColorStateList? = null,
    val uploadedIconBackground: Drawable? = null,
    val uploadedIconBackgroundTint: ColorStateList? = null,
    val uploadedFile: Uri? = null,
    var uploadedImage: File? = null,
    val validExtensions: List<String>? = null,
    val fileDeleteDialogTitle: String? = null,
    val fileDeleteDialogDescription: String? = null,
    val fileDeleteDialogPositiveButtonText: String? = null,
    val fileDeleteDialogNegativeButtonText: String? = null,
    var itemClickListener: ((View, FileUploadModel) -> Unit)?,
    var fileDeleteDialogClickListener: ((List<FileUploadModel>, Int) -> Unit)? = null,
)

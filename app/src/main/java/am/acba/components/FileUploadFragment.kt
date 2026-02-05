package am.acba.components

import am.acba.component.fileUpload.FileUpload
import am.acba.component.fileUpload.FileUploadModel
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.FilePreviewBottomSheetDialog.Companion.CLEAR_IMAGE
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentFileUploadBinding
import am.acba.components.models.ClickListener
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileUploadFragment : BaseViewBindingFragment<FragmentFileUploadBinding>() {
    override val inflate: Inflater<FragmentFileUploadBinding>
        get() = FragmentFileUploadBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar
    private var file: File? = null
    private var uri: Uri? = null
    private var authority: String = "am.acba.components.provider"
    private var imageRequestSourceView: FileUpload? = null
    private var imageRequestSourceItem: FileUploadModel? = null

    private lateinit var list: List<FileUploadModel>
    private val galleryResultRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::handleGalleryResult)
    private val cameraResultRequest = registerForActivityResult(ActivityResultContracts.TakePicture(), ::handleCameraResult)
    private val cameraPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission(), ::handleCameraPermissionResult)
    private val pickPdfLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            requireActivity().contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            mBinding.fThird.setUploadedMediaFile(
                fileType = FileUpload.FileType.FILE,
                uri = it
            )
            mBinding.fThird.getBase64()
        }
    }

    override fun FragmentFileUploadBinding.initView() {
        fFirst.setOnItemClickListener {
            onClicked(fFirst.fileUploadState, fFirst, null)
        }
        fSecond.setOnItemClickListener {
            if (fSecond.fileUploadState == FileUpload.FileUploadState.EMPTY || fSecond.fileUploadState == FileUpload.FileUploadState.ERROR) {
                requestCameraPermissionIfNotGranted()
            } else {
                openPreview(fSecond)
            }
        }
        fThird.setOnItemClickListener {
            if (fThird.fileUploadState == FileUpload.FileUploadState.EMPTY || fThird.fileUploadState == FileUpload.FileUploadState.ERROR) {
                pickPdf()
            } else {
                openPreview(fThird)
            }
        }

        val first = FileUploadModel(
            fileUploadState = FileUpload.FileUploadState.EMPTY,
            body = getString(am.acba.component.R.string.search),
            emptyIcon = AppCompatResources.getDrawable(requireContext(), am.acba.component.R.drawable.ic_account),
            title = getString(R.string.app_name),
            itemClickListener = { view, model ->
                onClicked(model.fileUploadState, null, model)
            }
        )

        val second = FileUploadModel(
            fileUploadState = FileUpload.FileUploadState.EMPTY,
            body = getString(am.acba.component.R.string.search),
            emptyIcon = AppCompatResources.getDrawable(requireContext(), am.acba.component.R.drawable.ic_account),
            title = "App Name",
            itemClickListener = { _, _ -> requestCameraPermissionIfNotGranted() }
        )

        val third = FileUploadModel(
            fileUploadState = FileUpload.FileUploadState.EMPTY,
            body = getString(am.acba.component.R.string.search),
            emptyIcon = AppCompatResources.getDrawable(requireContext(), am.acba.component.R.drawable.ic_account),
            title = getString(R.string.app_name),
            itemClickListener = { _, _ -> pickPdf() }
        )

        list = listOf(
            first, second, third
        )
        fileUploadList.initList(list)

        val first1 = FileUploadModel(
            fileUploadState = FileUpload.FileUploadState.EMPTY,
            body = getString(am.acba.component.R.string.search),
            emptyIcon = AppCompatResources.getDrawable(requireContext(), am.acba.component.R.drawable.ic_account),
            title = getString(R.string.app_name),
            itemClickListener = { _, _ -> requestCameraPermissionIfNotGranted() }
        )


        val second1 = FileUploadModel(
            body = getString(am.acba.component.R.string.search),
            emptyIcon = AppCompatResources.getDrawable(requireContext(), am.acba.component.R.drawable.ic_account),
            title = "App Name",
            itemClickListener = { _, _ -> requestCameraPermissionIfNotGranted() }
        )

        val third1 = FileUploadModel(
            fileUploadState = FileUpload.FileUploadState.EMPTY,
            body = getString(am.acba.component.R.string.search),
            emptyIcon = AppCompatResources.getDrawable(requireContext(), am.acba.component.R.drawable.ic_account),
            title = getString(R.string.app_name),
            itemClickListener = { _, _ -> requestCameraPermissionIfNotGranted() }
        )
        fileUploadList1.initList(listOf(first1, second1, third1), 1)
    }

    private fun onClicked(fileUploadState: FileUpload.FileUploadState, fileUpload: FileUpload? = null, model: FileUploadModel? = null) {
        if (fileUploadState == FileUpload.FileUploadState.EMPTY || fileUploadState == FileUpload.FileUploadState.ERROR) {
            imageRequestSourceView = fileUpload
            imageRequestSourceItem = model
            requestGalleryResult()
        } else {
            fileUpload?.let {
                openPreview(it)
            } ?: run {
                openPreview(model)
            }
        }
    }

    private fun openPreview(fileUpload: FileUpload? = null) {
        val filePath = fileUpload?.fileUri

        val bundle = bundleOf(
            "title" to "Ավելացնել պատճենը",
            FilePreviewBottomSheetDialog.FILE_TYPE to fileUpload?.fileType,
            FilePreviewBottomSheetDialog.FILE_URI to filePath,
            CLEAR_IMAGE to ClickListener({ clearImage ->
                if (clearImage) fileUpload?.setFileUploadState(FileUpload.FileUploadState.EMPTY)
            })
        )
        findNavController().navigate(R.id.filePreviewBottomSheetDialog, bundle)
    }

    private fun openPreview(model: FileUploadModel? = null) {
        val imagePath = model?.fileUri
        val bundle = bundleOf(
            "title" to "Ավելացնել պատճենը",
            FilePreviewBottomSheetDialog.FILE_TYPE to model?.fileType,
            FilePreviewBottomSheetDialog.FILE_URI to imagePath,
            CLEAR_IMAGE to ClickListener({ clearImage ->
                if (clearImage) {
                    model?.let {
                        mBinding.fileUploadList.updateList(model) {
                            model.copy(fileUploadState = FileUpload.FileUploadState.EMPTY, description = "")
                        }
                    }
                }
            })
        )
        findNavController().navigate(R.id.filePreviewBottomSheetDialog, bundle)
    }

    private fun pickPdf() {
        pickPdfLauncher.launch(arrayOf("application/pdf"))
    }

    private fun handleCameraPermissionResult(isGranted: Boolean) {
        if (isGranted) openCamera()
    }

    private fun handleCameraResult(success: Boolean) {
        if (success) showImage(mBinding.fSecond, uri)
    }

    private fun showImage(fileUpload: FileUpload, uri: Uri? = null) {
        if (uri != null) {
            onMediaFileResultSuccess(uri, fileUpload)
        }
    }

    private fun onMediaFileResultSuccess(file: Uri, fileUpload: FileUpload) {
        lifecycleScope.launch(Dispatchers.Main) {
            fileUpload.setFileUploadState(FileUpload.FileUploadState.UPLOADING) {
                lifecycleScope.launch(Dispatchers.Main) {
                    fileUpload.setUploadedMediaFile(FileUpload.FileType.IMAGE, file)
                    fileUpload.getBase64()
                }
            }
        }
    }

    private fun requestGalleryResult() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
        galleryResultRequest.launch(intent)
    }

    private fun requestCameraPermissionIfNotGranted() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            cameraPermissionRequest.launch(Manifest.permission.CAMERA)
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        val myDir = mActivity.getExternalFilesDir(null)
        if (myDir?.exists() != true) {
            myDir?.mkdirs()
        }
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "IMG_$timeStamp.jpg"
        file = File(myDir, fileName)

        file?.let {
            uri = FileProvider.getUriForFile(requireActivity(), authority, it)
            cameraResultRequest.launch(uri)
        }
    }

    private fun handleGalleryResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            imageRequestSourceView?.let { showImage(it, uri) }
            imageRequestSourceItem?.let { item ->
                lifecycleScope.launch(Dispatchers.Main) {
                    mBinding.fileUploadList.updateList(item) {
                        item.copy(description = " ", fileUploadState = FileUpload.FileUploadState.UPLOADING, fileUri = uri)
                    }
                }
            }
            imageRequestSourceView = null
            imageRequestSourceItem = null
        }
    }
}
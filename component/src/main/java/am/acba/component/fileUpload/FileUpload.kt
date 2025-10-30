﻿package am.acba.component.fileUpload

import am.acba.component.R
import am.acba.component.databinding.FileUploadBinding
import am.acba.component.dialog.PrimaryAlertDialog
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.getFileExtension
import am.acba.component.extensions.inflater
import am.acba.component.extensions.onLottieAnimationEndListener
import am.acba.component.extensions.playLottieAnimation
import am.acba.component.extensions.renderPdfPageAsBitmap
import am.acba.component.fileUpload.FileUpload.FileUploadState.Companion.findStateByOrdinal
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Parcelable
import android.provider.OpenableColumns
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class FileUpload : FrameLayout {
    private val binding by lazy {
        FileUploadBinding.inflate(
            context.inflater(),
            this,
            false
        )
    }

    private var validExtensions = listOf<String>()
    private var hasBody = false
    private var errorMessage: String? = null

    var fileType: FileType? = null

    var fileUploadState: FileUploadState = FileUploadState.EMPTY
        private set
    var fileUri: Uri? = null
    var descriptionText: String? = null
    var emptyIconDrawable: Drawable? = null
    var emptyIconBackground: Drawable? = null
    var emptyIconTint: ColorStateList? = context.getColorStateListFromAttr(R.attr.contentPrimary)
    var emptyIconBackgroundTint: ColorStateList? = null

    var uploadedIconDrawable: Drawable? = AppCompatResources.getDrawable(context, R.drawable.ic_file_preview)
    var uploadedIconBackground: Drawable? = null
    var uploadedIconTint: ColorStateList? = context.getColorStateListFromAttr(R.attr.contentSecondary)
    var uploadedIconBackgroundTint: ColorStateList? = null

    var fileDeleteDialogTitle: String = context.getString(R.string.document_deletion_title)
    var fileDeleteDialogDescription: String = context.getString(R.string.document_deletion_message)
    var fileDeleteDialogPositiveButtonText: String = context.getString(R.string.remove)

    var fileDeleteDialogNegativeButtonText: String = context.getString(R.string.cancel)

    var fileDeleteDialogClickListener: (() -> Unit)? = null
    var closeIconClickListener: ((View) -> Unit)? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.FileUpload).apply {
            addView(binding.root)
            try {
                val type = getInt(R.styleable.FileUpload_fileType, 0)
                fileType = FileType.Companion.getFileType(type)

                val title = getString(R.styleable.FileUpload_fileUploadTitle)
                val body = getString(R.styleable.FileUpload_fileUploadBody)
                emptyIconDrawable = getDrawable(R.styleable.FileUpload_fileUploadEmptyIcon)
                emptyIconTint = getColorStateList(R.styleable.FileUpload_fileUploadEmptyIconTint) ?: emptyIconTint
                emptyIconBackground = getDrawable(R.styleable.FileUpload_fileUploadEmptyIconBackground)
                emptyIconBackgroundTint = getColorStateList(R.styleable.FileUpload_fileUploadEmptyIconBackgroundTint)

                uploadedIconDrawable = getDrawable(R.styleable.FileUpload_fileUploadUploadedIcon)
                    ?: AppCompatResources.getDrawable(context, R.drawable.ic_file_preview)
                uploadedIconTint = getColorStateList(R.styleable.FileUpload_fileUploadUploadedIconTint)
                    ?: uploadedIconTint
                uploadedIconBackground = getDrawable(R.styleable.FileUpload_fileUploadUploadedIconBackground)
                uploadedIconBackgroundTint = getColorStateList(R.styleable.FileUpload_fileUploadUploadedIconBackgroundTint)

                val fileUploadState = getInt(R.styleable.FileUpload_fileUploadState, 0).findStateByOrdinal()
                setFileUploadState(fileUploadState)

                getString(R.styleable.FileUpload_fileDeleteDialogTitle)?.let {
                    fileDeleteDialogTitle = it
                }
                getString(R.styleable.FileUpload_fileDeleteDialogDescription)?.let {
                    fileDeleteDialogDescription = it
                }
                getString(R.styleable.FileUpload_fileDeleteDialogPositiveButtonText)?.let {
                    fileDeleteDialogPositiveButtonText = it
                }
                getString(R.styleable.FileUpload_fileDeleteDialogNegativeButtonText)?.let {
                    fileDeleteDialogNegativeButtonText = it
                }

                setTitle(title)
                setBody(body)

                initListeners()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    fun setFileUploadState(state: FileUploadState, onLoading: (() -> Unit)? = null) {
        fileUploadState = state
        when (state) {
            FileUploadState.EMPTY -> initEmptyState()
            FileUploadState.UPLOADING -> initUploadingState(onLoading)
            FileUploadState.UPLOAD_FAILED -> initFailedState()
            FileUploadState.UPLOADED -> initUploadedState()
            FileUploadState.PREVIEW -> initPreviewState()
            FileUploadState.ERROR -> initErrorState()
        }
    }

    private fun initEmptyState() {
        clearUploadedImage()
        setDescription("")
        setDescriptionVisibility(false)
        setBorder(R.attr.borderPrimaryTonal1)
        setInitialState()
    }

    private fun setInitialState() {
        setContainerClickable(true)
        showEmptyIcon()
        setFileStatusMediaType(FileStatusMediaType.ICON)

        setCloseIconVisibility(false)
        setTitleVisibility(true)
        setBodyVisibility(hasBody)
    }

    private fun initUploadingState(onFinished: (() -> Unit)?) {
        binding.container.apply {
            post {
                val height = this.height
                val width = this.width

                setContainerClickable(false)
                setFileStatusMediaType(FileStatusMediaType.PROGRESS)
                setBorder(0, false)
                setDescriptionTextColor(context.getColorFromAttr(R.attr.contentPrimaryTonal1))
                hideContainerTexts()
                setDescription(context.getString(R.string.uploading))
                startProgress(onFinished)
                setUploadedImageVisibility(false)

                layoutParams = layoutParams.apply {
                    this.width = width
                    this.height = height
                }
            }
        }
    }

    private fun initUploadedState() {
        playLottieAnimation()
        hideContainerTexts()
        setCloseIconVisibility(true)
        setUploadedImageVisibility(true)
    }

    private fun initPreviewState() {
        setCloseIconVisibility(true)
        hideContainerTexts()
        setUploadedImageVisibility(true)
        setContainerClickable(false)
        setFileStatusMediaType(FileStatusMediaType.NOTHING)
    }

    private fun initErrorState() {
        setInitialState()
        setBorder(R.attr.contentDangerTonal1)
        setDescriptionTextColor(context.getColorFromAttr(R.attr.contentDangerTonal1))
        setDescription(errorMessage)
        setDescriptionVisibility(errorMessage != null)
    }

    private fun initFailedState() {
        showRepeatIcon(AppCompatResources.getDrawable(context, R.drawable.ic_repeat_2))
        setFileStatusMediaType(FileStatusMediaType.ICON)
    }

    private fun setBorder(@AttrRes borderColorRes: Int, hasDashSize: Boolean = true) {
        val drawable = GradientDrawable().apply {
            val dashSize = if (hasDashSize) 16.dpToPx().toFloat() else 0f
            val borderColor = context.getColorStateListFromAttr(borderColorRes)
            setStroke(1.dpToPx(), borderColor, dashSize, dashSize)
            setColor(context.getColorFromAttr(R.attr.backgroundTonal2))
            cornerRadius = 12.dpToPx().toFloat()
        }
        binding.container.background = drawable
    }

    private fun setContainerClickable(isClickable: Boolean) {
        binding.container.isClickable = isClickable
    }

    private fun setTitleVisibility(isVisible: Boolean) {
        binding.tvTitle.isVisible = isVisible
    }

    private fun setBodyVisibility(isVisible: Boolean) {
        binding.tvBody.isVisible = isVisible
    }

    private fun setFileStatusMediaType(type: FileStatusMediaType) {
        binding.apply {
            ivIcon.isVisible = type == FileStatusMediaType.ICON
            progressContainer.isVisible = type == FileStatusMediaType.PROGRESS
            lottie.isVisible = type == FileStatusMediaType.LOTTIE
        }
    }

    private fun playLottieAnimation() {
        binding.lottie.apply {
            removeAllAnimatorListeners()
            cancelAnimation()
            playLottieAnimation() {
                setFileStatusMediaType(FileStatusMediaType.LOTTIE)
                setAnimation("upload_success.json")
            }
            onLottieAnimationEndListener {
                showUploadedIcon()
                setFileStatusMediaType(FileStatusMediaType.ICON)
                setContainerClickable(true)
            }
        }
    }

    private fun hideContainerTexts() {
        setTitleVisibility(false)
        setBodyVisibility(false)
    }

    private fun startProgress(onFinished: (() -> Unit)? = null) {
        CoroutineScope(Dispatchers.Main).launch {
            binding.apply {
                progressBar.progress = 1
                tvProgress.isVisible = true

                for (i in 1..100) {
                    progressBar.setProgressCompat(i, false)
                    tvProgress.text = "$i%"
                    delay(10L)
                }

                tvProgress.isVisible = false
                onFinished?.invoke()
            }
        }
    }

    private fun showEmptyIcon() {
        showEmptyIconDrawable(emptyIconDrawable)
        showEmptyIconTint(emptyIconTint)
        showEmptyIconBackground(backgroundRes = emptyIconBackground)
        showEmptyIconBackgroundTint(emptyIconBackgroundTint)
        setIconPadding(4.dpToPx())
    }

    private fun showUploadedIcon() {
        showUploadedIconDrawable(uploadedIconDrawable)
        showUploadedIconTint(uploadedIconTint)
        showUploadedIconBackground(backgroundRes = uploadedIconBackground)
        showUploadedIconBackgroundTint(uploadedIconBackgroundTint)
        setIconPadding(0)
    }

    private fun showRepeatIcon(drawable: Drawable?) {
        setIconPadding(0)
        binding.ivIcon.setImageDrawable(drawable)
    }

    private fun showEmptyIconDrawable(drawable: Drawable?) {
        drawable?.let {
            emptyIconDrawable = drawable
            binding.ivIcon.setImageDrawable(it)
        }
    }

    private fun showEmptyIconTint(colorStateList: ColorStateList?) {
        emptyIconTint = colorStateList
        binding.ivIcon.imageTintList = colorStateList
    }

    private fun showEmptyIconBackground(clipToOutline: Boolean = false, backgroundRes: Drawable?) {
        binding.ivIcon.apply {
            this.clipToOutline = clipToOutline
            backgroundRes?.let {
                emptyIconBackground = it
                background = it
            }
        }
    }

    private fun showEmptyIconBackgroundTint(color: ColorStateList?) {
        emptyIconBackgroundTint = color
        binding.ivIcon.backgroundTintList = color
    }

    private fun showUploadedIconDrawable(drawable: Drawable?) {
        uploadedIconDrawable = drawable ?: AppCompatResources.getDrawable(context, R.drawable.ic_file_preview)
        binding.ivIcon.setImageDrawable(uploadedIconDrawable)
    }

    private fun showUploadedIconTint(colorStateList: ColorStateList?) {
        uploadedIconTint = colorStateList
        binding.ivIcon.imageTintList = colorStateList
    }

    private fun showUploadedIconBackground(
        clipToOutline: Boolean = false,
        backgroundRes: Drawable?
    ) {
        binding.ivIcon.apply {
            this.clipToOutline = clipToOutline
            backgroundRes?.let {
                uploadedIconBackground = it
                background = it
            }
        }
    }

    private fun showUploadedIconBackgroundTint(color: ColorStateList?) {
        uploadedIconBackgroundTint = color
        binding.ivIcon.backgroundTintList = color
    }

    private fun clearUploadedImage() {
        binding.ivUploadedImage.apply {
            fileUri = null
            fileType = null
            isVisible = false
            setImageDrawable(null)
        }
    }

    private fun loadImage(uri: Uri?) {
        load(uri)
    }

    private fun loadFile(uri: Uri?) {
        val bitmap = uri?.let {
            renderPdfPageAsBitmap(
                context = context,
                uri = it,
                backgroundColor = context.getColorFromAttr(R.attr.overlayBackgroundTonal1)
            )
        }?.toDrawable(context.resources)
        load(bitmap)
    }

    private fun load(model: Any?) {
        post {
            val width = binding.container.width
            val height = binding.container.height
            val requestOptions = RequestOptions()
                .override(width, height)
                .transform(CenterCrop(), RoundedCorners(12.dpToPx()))
            Glide.with(context)
                .load(model)
                .apply(requestOptions)
                .into(binding.ivUploadedImage)
        }
    }

    private fun isUriValid(uri: Uri?, extension: String?): Boolean {
        if (uri == null) return false

        val maxSize = 700 * 1024 // 700 KB
        val resolver = context.contentResolver
        val fileSize = resolver.query(uri, arrayOf(OpenableColumns.SIZE), null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (cursor.moveToFirst() && sizeIndex != -1) cursor.getLong(sizeIndex) else 0L
        } ?: 0L

        return when {
            validExtensions.isNotEmpty() && extension != null && !validExtensions.contains(extension.lowercase()) -> {
                getError(R.string.file_format_error, validExtensions.joinToString(", "))
            }

            fileSize > maxSize -> getError(R.string.file_limit_error)
            else -> true
        }
    }

    private fun getError(errorRes: Int, formatArgs: String? = null): Boolean {
        errorMessage = context.getString(errorRes, formatArgs)
        setFileUploadState(FileUploadState.ERROR)
        fileUri = null
        fileType = null
        return false
    }

    private fun openDialog() {
        val positiveButtonTextColor = context.getColorStateListFromAttr(R.attr.contentDangerTonal1)
        val negativeButtonTextColor = context.getColorStateListFromAttr(R.attr.contentPrimary)
        PrimaryAlertDialog.Builder(context)
            .icon(R.drawable.ic_warning)
            .title(fileDeleteDialogTitle)
            .description(fileDeleteDialogDescription)
            .positiveButtonText(fileDeleteDialogPositiveButtonText)
            .positiveButtonTextColor(positiveButtonTextColor)
            .negativeButtonText(fileDeleteDialogNegativeButtonText)
            .negativeButtonTextColor(negativeButtonTextColor)
            .positiveButtonClick {
                setFileUploadState(FileUploadState.EMPTY)
                fileDeleteDialogClickListener?.invoke()
            }
            .setCancelable(true)
            .build()
    }

    private fun initListeners() {
        binding.ivClose.setOnClickListener { view ->
            closeIconClickListener
                ?.invoke(view)
                ?: run { openDialog() }
        }
    }

    fun setValidExtensions(extensions: List<String>) {
        validExtensions = extensions
    }

    fun setTitle(text: String?) {
        text?.let {
            setTitleVisibility(true)
            binding.tvTitle.text = it
        }
    }

    fun setBody(text: String?) {
        hasBody = text != null
        setBodyVisibility(hasBody)

        text?.let {
            binding.tvBody.text = it
        }
    }

    fun setDescriptionVisibility(isVisible: Boolean) {
        binding.tvDescription.isVisible = isVisible
    }

    fun setDescription(text: String?) {
        descriptionText = text
        text?.let {
            setDescriptionVisibility(true)
            binding.tvDescription.text = it
        }
    }

    fun setDescriptionTextColor(@ColorInt color: Int?) {
        color?.let {
            binding.tvDescription.setTextColor(it)
        }
    }

    fun setIconPadding(padding: Int) {
        binding.ivIcon.setPadding(padding, padding, padding, padding)
    }

    fun setUploadedImageVisibility(isVisible: Boolean) {
        binding.ivUploadedImage.isVisible = isVisible
    }

    fun setUploadedMediaFile(fileType: FileType, uri: Uri? = null) {
        val extension = context.getFileExtension(uri)
        if (!isUriValid(uri, extension)) return
        setFileUploadState(FileUploadState.UPLOADED)
        showMediaFile(fileType, uri)
    }

    fun showMediaFile(fileType: FileType, uri: Uri?) {
        loadMediaFile(fileType, uri)
        val extension = ".${context.getFileExtension(uri)}"
        val path = uri?.path?.substringAfterLast('/').orEmpty()
        val description = if (path.lowercase().endsWith(extension.lowercase())) {
            path
        } else {
            path + extension
        }
        setDescription(description)
    }

    private fun loadMediaFile(fileType: FileType, uri: Uri?) {
        this.fileType = fileType
        this.fileUri = uri
        when (fileType) {
            FileType.IMAGE -> loadImage(uri)
            FileType.FILE -> loadFile(uri)
        }
    }

    fun setOnItemClickListener(listener: OnClickListener) {
        binding.container.setOnClickListener(listener)
    }

    fun setCloseIconVisibility(isVisible: Boolean) {
        binding.ivClose.isVisible = isVisible
    }

    enum class FileUploadState {
        EMPTY,
        UPLOADING,
        UPLOAD_FAILED,
        UPLOADED,
        ERROR,
        PREVIEW;

        companion object {
            fun Int.findStateByOrdinal() = entries.find { it.ordinal == this } ?: EMPTY
        }
    }

    enum class FileStatusMediaType {
        ICON,
        PROGRESS,
        LOTTIE,
        NOTHING
    }

    @Parcelize
    enum class FileType(val type: Int) : Parcelable {
        IMAGE(0),
        FILE(1);

        companion object {
            private val map = entries.associateBy(FileType::type)
            fun getFileType(type: Int) = map[type]
        }
    }
}
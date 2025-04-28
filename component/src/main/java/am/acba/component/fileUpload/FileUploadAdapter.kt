package am.acba.component.fileUpload

import am.acba.component.R
import am.acba.component.databinding.ItemFileUploadBinding
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class FileUploadAdapter() : ListAdapter<FileUploadModel, FileUploadAdapter.ViewHolder>(FileUploadDiffCallBack()) {

    private var spanCount = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFileUploadBinding.inflate(parent.context.inflater(), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemFileUploadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: FileUploadModel) {
            binding.fileUpload.apply {
                setTitle(model.title)
                setBody(model.body)

                emptyIconDrawable = model.emptyIcon
                emptyIconTint = model.emptyIconTint ?: context.getColorStateListFromAttr(R.attr.contentSecondary)
                emptyIconBackground = model.emptyIconBackground
                emptyIconBackgroundTint = model.emptyIconBackgroundTint

                uploadedIconDrawable = model.uploadedIcon
                uploadedIconTint = model.uploadedIconTint ?: context.getColorStateListFromAttr(R.attr.contentSecondary)
                uploadedIconBackground = model.uploadedIconBackground
                uploadedIconBackgroundTint = model.uploadedIconBackgroundTint
                setupFileUploadState(model)

                model.description?.let { setDescription(it) } ?: setDescriptionVisibility(false)

                model.fileDeleteDialogTitle?.let { fileDeleteDialogTitle = it }
                model.fileDeleteDialogDescription?.let { fileDeleteDialogDescription = it }
                model.fileDeleteDialogPositiveButtonText?.let { fileDeleteDialogPositiveButtonText = it }
                model.fileDeleteDialogNegativeButtonText?.let { fileDeleteDialogNegativeButtonText = it }

                fileDeleteDialogClickListener = { resetEmptyStateAfterDeletion(model) }
                setOnItemClickListener { model.itemClickListener?.invoke(it, model) }
            }
        }

        private fun setupFileUploadState(model: FileUploadModel) {
            binding.fileUpload.apply {
                if (model.fileUploadState == FileUpload.FileUploadState.UPLOADING) {
                    setFileUploadState(model.fileUploadState) {
                        model.uploadedImage?.let { setUploadedImage(context, it) }
                        model.uploadedFile?.let { setUploadedFile(context, it) }
                        model.fileUploadState = fileUploadState
                    }
                } else {
                    setFileUploadState(model.fileUploadState)
                }
            }
        }

        private fun resetEmptyStateAfterDeletion(model: FileUploadModel) {
            var position = 0
            val updatedList = currentList.map {
                if (it.id == model.id) {
                    position = currentList.indexOf(it)
                    it.copy(fileUploadState = FileUpload.FileUploadState.EMPTY, description = null)
                } else {
                    it.copy()
                }
            }
            submitList(updatedList)
            model.fileDeleteDialogClickListener?.invoke(updatedList, position)
        }
    }

    fun setSpanCount(spanCount: Int) {
        this.spanCount = spanCount
    }

    override fun onCurrentListChanged(previousList: List<FileUploadModel>, currentList: List<FileUploadModel>) {
        super.onCurrentListChanged(previousList, currentList)

        if (spanCount != 2) return

        currentList.chunked(2).forEach { pair ->
            if (pair.size == 1) return

            val (first, second) = pair
            val isVisible = !first.description.isNullOrEmpty() || !second.description.isNullOrEmpty()
            if (isVisible) {
                if (first.description == null) first.description = " "
                if (second.description == null) second.description = " "
            }
        }
    }

    private class FileUploadDiffCallBack : DiffUtil.ItemCallback<FileUploadModel>() {

        override fun areItemsTheSame(oldItem: FileUploadModel, newItem: FileUploadModel) = oldItem.id == newItem.id
            && oldItem.fileUploadState == newItem.fileUploadState
            && oldItem.title == newItem.title
            && oldItem.description == newItem.description
            && oldItem.uploadedImage == newItem.uploadedImage
            && oldItem.uploadedFile == newItem.uploadedFile

        override fun areContentsTheSame(oldItem: FileUploadModel, newItem: FileUploadModel) = oldItem.id == newItem.id
            && oldItem.fileUploadState == newItem.fileUploadState
            && oldItem.title == newItem.title
            && oldItem.description == newItem.description
            && oldItem.uploadedImage == newItem.uploadedImage
            && oldItem.uploadedFile == newItem.uploadedFile
    }
}
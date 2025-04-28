package am.acba.component.fileUpload

import am.acba.component.R
import am.acba.component.databinding.FileUploadListBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import am.acba.component.helpers.GridItemDecorator
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager

class FileUploadList : FrameLayout {
    private val binding by lazy {
        FileUploadListBinding.inflate(
            context.inflater(),
            this,
            false
        )
    }

    private val fileUploadAdapter by lazy { FileUploadAdapter() }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.FileUploadList).apply {
            addView(binding.root)
            try {
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    fun initList(list: List<FileUploadModel>, spanCount: Int = 2) {
        binding.rvFileUpload.apply {
            isVisible = list.isNotEmpty()
            if (!isVisible) return

            val gridLayoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(GridItemDecorator(spanCount, 8.dpToPx()))
            if (spanCount == 2) gridLayoutManager.spanSizeLookup = getSpanSizeLookup(list)
            layoutManager = gridLayoutManager
            adapter = fileUploadAdapter
        }
        fileUploadAdapter.setSpanCount(spanCount)
        fileUploadAdapter.submitList(list)
    }

    private fun getSpanSizeLookup(list: List<FileUploadModel>) = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (list.size % 2 == 1 && position == list.lastIndex) 2 else 1
        }
    }

    fun submitList(list: List<FileUploadModel>) {
        fileUploadAdapter.submitList(list)
    }

    fun getCurrentList(): List<FileUploadModel> = fileUploadAdapter.currentList
    fun onCurrentListChanged(previousList: List<FileUploadModel>, currentList: List<FileUploadModel>) {
        fileUploadAdapter.onCurrentListChanged(previousList, currentList)
    }

    fun updateList(
        item: FileUploadModel,
        copy: (() -> FileUploadModel)
    ) {
        val oldList = getCurrentList()
        val newList = oldList
            .map { if (it.id == item.id) copy.invoke() else it.copy() }
            .toMutableList()
        onCurrentListChanged(oldList, newList)
        submitList(newList)
    }
}
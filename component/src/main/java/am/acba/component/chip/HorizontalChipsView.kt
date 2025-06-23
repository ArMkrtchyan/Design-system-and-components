package am.acba.component.chip

import am.acba.component.R
import am.acba.component.databinding.LayoutHorizontalChipsBinding
import am.acba.component.extensions.inflater
import android.widget.FrameLayout
import androidx.core.view.isVisible

class HorizontalChipsView @JvmOverloads constructor(
    context: android.content.Context, attrs: android.util.AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { LayoutHorizontalChipsBinding.inflate(context.inflater(), this, false) }
    private var isFirstSelected = true

    init {
        context.obtainStyledAttributes(attrs, R.styleable.HorizontalChipsView).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            addView(binding.root, layoutParams)
            val horizontalChipsTitle = getString(R.styleable.HorizontalChipsView_horizontalChipsTitle)
            setTitle(horizontalChipsTitle)
            setDefaultFirstSelected(getBoolean(R.styleable.HorizontalChipsView_isFirstSelected, true))
            recycle()
            invalidate()
        }
    }

    fun setDefaultFirstSelected(isSelected: Boolean) {
        isFirstSelected = isSelected
    }

    fun setTitle(title: String?) {
        binding.title.text = title
        binding.title.isVisible = !title.isNullOrEmpty()
    }

    fun <T : IChipModel> submitChips(chips: List<T>, onChipClick: (T) -> Unit) {
        val chipsAdapter: ChipsAdapter<T> = ChipsAdapter()
        val indexOfFirst = chips.indexOfFirst { it.getSelected() }
        if (isFirstSelected) {
            chipsAdapter.selectedChip.postValue(if (indexOfFirst > -1) indexOfFirst else 0)
        } else {
            chipsAdapter.selectedChip.postValue(indexOfFirst)
        }
        binding.chipsRecycler.adapter = chipsAdapter
        chipsAdapter.setOnChipClick(onChipClick)
        chipsAdapter.submitList(chips)
    }
}
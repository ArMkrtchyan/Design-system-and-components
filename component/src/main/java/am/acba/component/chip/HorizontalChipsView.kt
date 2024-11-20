package am.acba.component.chip

import am.acba.acbamobile.ui.kotlin.screens.loan.IChipModel
import am.acba.component.R
import am.acba.component.databinding.LayoutHorizontalChipsBinding
import am.acba.component.extensions.inflater
import android.widget.FrameLayout
import androidx.core.view.isVisible

class HorizontalChipsView @JvmOverloads constructor(
    context: android.content.Context, attrs: android.util.AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { LayoutHorizontalChipsBinding.inflate(context.inflater(), this, false) }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.HorizontalChipsView).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            addView(binding.root, layoutParams)
            val horizontalChipsTitle = getString(R.styleable.HorizontalChipsView_horizontalChipsTitle)
            setTitle(horizontalChipsTitle)
            recycle()
            invalidate()
        }
    }

    fun setTitle(title: String?) {
        binding.title.text = title
        binding.title.isVisible = !title.isNullOrEmpty()
    }

    fun <T : IChipModel> submitChips(chips: List<T>, onChipClick: (T) -> Unit) {
        val chipsAdapter: ChipsAdapter<T> = ChipsAdapter()
        val indexOfFirst = chips.indexOfFirst { it.getSelected() }
        chipsAdapter.selectedChip.postValue(if (indexOfFirst > -1) indexOfFirst else 0)
        binding.chipsRecycler.adapter = chipsAdapter
//        if (binding.chipsRecycler.adapter == null) {
//            chipsAdapter = ChipsAdapter<T>()
//            binding.chipsRecycler.adapter = chipsAdapter
//        } else {
//            chipsAdapter = binding.chipsRecycler.adapter as ChipsAdapter<T>
//        }
        chipsAdapter.setOnChipClick(onChipClick)
        chipsAdapter.submitList(chips)
    }
}
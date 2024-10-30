package am.acba.component.chip

import am.acba.acbamobile.ui.kotlin.screens.loan.IChipModel
import am.acba.component.databinding.ItemChipBinding
import am.acba.component.extensions.inflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ChipsAdapter<T : IChipModel> :
    ListAdapter<T, ChipsAdapter<T>.ViewHolder<T>>(ChipsDiffCallBack()) {
    var selectedChip: MutableLiveData<Int> = MutableLiveData(0)
    private var onChipClick: ((T) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipsAdapter<T>.ViewHolder<T> {
        val binding = ItemChipBinding.inflate(parent.context.inflater(), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChipsAdapter<T>.ViewHolder<T>, position: Int) {
        val binding = holder.binding
        val item = getItem(position)
        binding.chip.setChipText(item.getTitle())
        binding.chip.setChipStartIconType(item.getChipIconType())
        binding.chip.setStartIcon(getDrawable(holder.binding.root.context, item.getStartIcon2()))
        binding.root.setOnClickListener {
            onChipClick?.invoke(item)
            selectedChip.postValue(position)
        }
        selectedChip.observeForever { binding.chip.isSelected = it == position }
    }

    fun setOnChipClick(onChipClick: (T) -> Unit) {
        this.onChipClick = onChipClick
    }

    inner class ViewHolder<T>(val binding: ItemChipBinding) : RecyclerView.ViewHolder(binding.root)

    private class ChipsDiffCallBack<T : IChipModel> : DiffUtil.ItemCallback<T>() {
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
}
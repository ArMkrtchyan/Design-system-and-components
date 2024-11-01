package am.acba.component.table

import am.acba.component.databinding.ItemTableBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TableItemAdapter : ListAdapter<TableItem, TableItemAdapter.ViewHolder>(
    TableItemDiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTableBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ViewHolder(private val binding: ItemTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: TableItem) {
            binding.apply {
                tvTitle.text = item.fieldTitle
                tvValue.text = item.fieldValue
                if (item.fieldValueColor != 0) {
                    tvValue.setTextColor(item.fieldValueColor)
                }
            }
        }
    }

    private class TableItemDiffCallBack : DiffUtil.ItemCallback<TableItem>() {
        override fun areItemsTheSame(oldItem: TableItem, newItem: TableItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TableItem, newItem: TableItem) =
            oldItem.fieldTitle == newItem.fieldTitle &&
                    oldItem.fieldValue == newItem.fieldValue &&
                    oldItem.fieldValueColor == newItem.fieldValueColor
    }
}
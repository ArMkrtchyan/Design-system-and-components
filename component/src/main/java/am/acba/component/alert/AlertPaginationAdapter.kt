package am.acba.component.alert

import am.acba.component.databinding.ItemAlertPaginationBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class AlertPaginationAdapter(private val onCloseClick: (Int) -> Unit) :
    ListAdapter<Alert, AlertPaginationAdapter.ViewHolder>(AlertDiffCallBack()) {

    private var list: MutableList<Alert>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlertPaginationBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position), onCloseClick)
    }

    override fun submitList(list: MutableList<Alert>?) {
        super.submitList(list)

        this.list = list
    }

    fun removeItemAt(position: Int) {
        list?.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(private val binding: ItemAlertPaginationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(alert: Alert, onCloseClick: (Int) -> Unit) {
            binding.alert.apply {
                setType(alert.type)
                setFilledBackground(alert.isFilledBackground)
                setTitle(alert.title)
                setBody(alert.body)
                setBodyMaxLines(2)
                setLink(alert.link)
                setOnLinkClickListener(alert.onLinkClick)
                alert.neutralIcon?.let {
                    setNeutralIcon(it)
                }
                setOnCloseClickListener { _ ->
                    onCloseClick.invoke(adapterPosition)
                }
            }
        }
    }

    private class AlertDiffCallBack : DiffUtil.ItemCallback<Alert>() {
        override fun areItemsTheSame(oldItem: Alert, newItem: Alert) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Alert, newItem: Alert) =
            oldItem == newItem
    }
}
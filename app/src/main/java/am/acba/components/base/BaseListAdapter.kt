package am.acba.components.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T : Any, VB : ViewBinding>(callback: DiffUtil.ItemCallback<T> = getDiffCallback()) :
    ListAdapter<T, BaseListAdapter<T, VB>.ViewHolder>(callback) {

    private lateinit var _binding: VB
    protected abstract val inflate: Inflater<VB>
    open val isRecyclable: Boolean = true
    protected lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = inflate(LayoutInflater.from(parent.context), parent, false)
        mContext = _binding.root.context
        return ViewHolder(_binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.onBind(position, getItem(position))
        holder.setIsRecyclable(isRecyclable)
    }

    abstract fun VB.onBind(position: Int, item: T)
    inner class ViewHolder(val binding: VB) : BaseViewHolder(binding) {
        override fun onBind() {

        }
    }
}
package am.acba.component.phoneNumberInput.adapter

import am.acba.component.databinding.TitleItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TitleAdapter(private val item: String) : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val binding = TitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.bind()
    }

    inner class TitleViewHolder(private val binding: TitleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                title.text = item
            }
        }
    }
}
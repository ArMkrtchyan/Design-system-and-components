package am.acba.component.productCard

import am.acba.component.databinding.ItemLoanAdditionalInformationBinding
import am.acba.component.extensions.inflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CardAdditionalInfoAdapter() :
    ListAdapter<ICardAdditionalInfo, CardAdditionalInfoAdapter.ViewHolder>(LoanCardAdditionalInfoDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLoanAdditionalInformationBinding.inflate(parent.context.inflater(), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    inner class ViewHolder(private val binding: ItemLoanAdditionalInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(loanOfferCard: ICardAdditionalInfo, position: Int) {
            binding.infoTitle.text = loanOfferCard.getTitle()
            binding.info.text = loanOfferCard.getInfo()
            binding.divider.isVisible = position != itemCount - 1
        }
    }

    private class LoanCardAdditionalInfoDiffCallBack<T : ICardAdditionalInfo> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
}
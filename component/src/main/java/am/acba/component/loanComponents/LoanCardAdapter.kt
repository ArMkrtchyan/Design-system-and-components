package am.acba.component.loanComponents

import am.acba.component.databinding.ItemLoanAdditionalInformationBinding
import am.acba.component.extensions.inflater
import am.acba.component.loanComponents.LoanCardAdditionalInfoAdapter.ViewHolder
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class LoanCardAdditionalInfoAdapter() : ListAdapter<LoanCardAdditionalInfo, ViewHolder>(LoanCardAdditionalInfoDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLoanAdditionalInformationBinding.inflate(parent.context.inflater(), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemLoanAdditionalInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(loanOfferCard: LoanCardAdditionalInfo) {
            setIsRecyclable(false)
            binding.infoTitle.text = loanOfferCard.title
            binding.info.text = loanOfferCard.info
        }
    }


    private class LoanCardAdditionalInfoDiffCallBack : DiffUtil.ItemCallback<LoanCardAdditionalInfo>() {
        override fun areItemsTheSame(oldItem: LoanCardAdditionalInfo, newItem: LoanCardAdditionalInfo) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: LoanCardAdditionalInfo, newItem: LoanCardAdditionalInfo) =
            oldItem == newItem
    }
}
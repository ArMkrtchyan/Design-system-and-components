package am.acba.component.loanComponents

import am.acba.component.databinding.ItemLoanOfferCardBinding
import am.acba.component.extensions.inflater
import am.acba.component.loanComponents.LoanOfferCardAdapter.ViewHolder
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hadilq.liveevent.LiveEvent

class LoanOfferCardAdapter(private val onItemClick: (LoanOfferCard) -> Unit) : ListAdapter<LoanOfferCard, ViewHolder>(LoanCardDiffCallBack()) {
    private val openCloseLiveEvent = LiveEvent<Boolean>()
    private var isOpenedState = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLoanOfferCardBinding.inflate(parent.context.inflater(), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position), onItemClick)
    }

    inner class ViewHolder(private val binding: ItemLoanOfferCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(loanOfferCard: LoanOfferCard, onItemClick: (LoanOfferCard) -> Unit) {
            setIsRecyclable(false)
            binding.root.setLoanCard(loanOfferCard)
            binding.root.setOnClickListener { onItemClick.invoke(loanOfferCard) }
            binding.root.setState(isOpenedState)
            openCloseLiveEvent.observeForever {
                isOpenedState = it
                binding.root.setOpenedOrClosedState(isOpenedState)
            }
        }
    }

    fun updateCardState() {
        openCloseLiveEvent.value = !isOpenedState
    }

    private class LoanCardDiffCallBack : DiffUtil.ItemCallback<LoanOfferCard>() {
        override fun areItemsTheSame(oldItem: LoanOfferCard, newItem: LoanOfferCard) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: LoanOfferCard, newItem: LoanOfferCard) =
            oldItem == newItem
    }
}
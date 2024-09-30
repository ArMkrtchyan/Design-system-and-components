package am.acba.component.loanComponents

import am.acba.component.databinding.ItemLoanOfferCardBinding
import am.acba.component.extensions.inflater
import am.acba.component.loanComponents.LoanCardAdapter.ViewHolder
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hadilq.liveevent.LiveEvent

class LoanCardAdapter(private val onItemClick: (LoanCard) -> Unit) : ListAdapter<LoanCard, ViewHolder>(LoanCardDiffCallBack()) {
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

        fun onBind(loanCard: LoanCard, onItemClick: (LoanCard) -> Unit) {
            setIsRecyclable(false)
            binding.root.setLoanCard(loanCard)
            binding.root.setOnClickListener { onItemClick.invoke(loanCard) }
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

    private class LoanCardDiffCallBack : DiffUtil.ItemCallback<LoanCard>() {
        override fun areItemsTheSame(oldItem: LoanCard, newItem: LoanCard) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: LoanCard, newItem: LoanCard) =
            oldItem == newItem
    }
}
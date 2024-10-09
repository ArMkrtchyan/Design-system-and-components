package am.acba.component.loanComponents

import am.acba.component.databinding.ItemLoanOfferCardBinding
import am.acba.component.extensions.getDisplayWidth
import am.acba.component.extensions.inflater
import am.acba.component.extensions.log
import am.acba.component.loanComponents.OfferCardAdapter.ViewHolder
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hadilq.liveevent.LiveEvent

class OfferCardAdapter(private var isOpenedState: Boolean = false) :
    ListAdapter<IOfferCard, ViewHolder>(OfferCardDiffCallBack()) {
    private val openCloseLiveEvent = LiveEvent<Boolean>()
    private var onItemClick: ((IOfferCard) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLoanOfferCardBinding.inflate(parent.context.inflater(), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position), onItemClick)
    }

    inner class ViewHolder(private val binding: ItemLoanOfferCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(offerCard: IOfferCard, onItemClick: ((IOfferCard) -> Unit)?) {
            setIsRecyclable(false)
            binding.root.setLoanCard(offerCard)
            binding.root.setOnClickListener {
                onItemClick?.invoke(offerCard)
            }
            binding.root.setState(isOpenedState.log())
            openCloseLiveEvent.observeForever {
                isOpenedState = it
                binding.root.setOpenedOrClosedState(isOpenedState)
            }
            binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
                width = (binding.root.context.getDisplayWidth() * 0.72).toInt()
            }
        }
    }

    fun setOnOfferClick(onItemClick: (IOfferCard) -> Unit) {
        this.onItemClick = onItemClick
    }

    fun updateCardState() {
        openCloseLiveEvent.value = !isOpenedState
    }

    private class OfferCardDiffCallBack : DiffUtil.ItemCallback<IOfferCard>() {
        override fun areItemsTheSame(oldItem: IOfferCard, newItem: IOfferCard) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: IOfferCard, newItem: IOfferCard) =
            oldItem == newItem
    }
}
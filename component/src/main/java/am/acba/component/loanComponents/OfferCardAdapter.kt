package am.acba.component.loanComponents

import am.acba.component.R
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
    private val holders = arrayListOf<ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLoanOfferCardBinding.inflate(parent.context.inflater(), parent, false)
        return ViewHolder(binding).also { holders.add(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position), onItemClick)
    }

    inner class ViewHolder(val binding: ItemLoanOfferCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(offerCard: IOfferCard, onItemClick: ((IOfferCard) -> Unit)?) {
            setIsRecyclable(false)
            binding.root.setTag(R.id.offer_title, offerCard.getUniqueId())
            binding.root.setLoanCard(offerCard)
            binding.root.setOnClickListener {
                onItemClick?.invoke(offerCard)
            }
            offerCard.getTitle().log("LoanItemTag")
            binding.root.setState(isOpenedState.log())
            openCloseLiveEvent.observeForever {
                isOpenedState = it
                offerCard.setOpened(it)
                offerCard.getTitle().log("LoanItemTag", "change state ->")
                binding.root.setOpenedOrClosedState(isOpenedState)
            }
            binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
                width = if (itemCount == 1) ViewGroup.LayoutParams.MATCH_PARENT else (binding.root.context.getDisplayWidth() * 0.72).toInt()
            }
        }
    }

    fun setOnOfferClick(onItemClick: (IOfferCard) -> Unit) {
        this.onItemClick = onItemClick
    }

    fun updateCardState() {
        openCloseLiveEvent.value = !isOpenedState
    }

    override fun submitList(list: MutableList<IOfferCard>?) {
        holders.forEach { holder ->
            holder.setIsRecyclable(list?.any { it.getUniqueId() == holder.binding.root.getTag(R.id.offer_title).toString().toInt() } == false)
        }
        super.submitList(list)
    }

    class OfferCardDiffCallBack : DiffUtil.ItemCallback<IOfferCard>() {
        override fun areItemsTheSame(oldItem: IOfferCard, newItem: IOfferCard): Boolean {
            return oldItem.getUniqueId() == newItem.getUniqueId()
        }

        override fun areContentsTheSame(oldItem: IOfferCard, newItem: IOfferCard): Boolean {
            return oldItem.getTitle() == newItem.getTitle()
                    && oldItem.getDescription() == newItem.getDescription()
                    && oldItem.getOffer() == newItem.getOffer()
                    && oldItem.getBadgeVisibility() == newItem.getBadgeVisibility()

        }
    }
}
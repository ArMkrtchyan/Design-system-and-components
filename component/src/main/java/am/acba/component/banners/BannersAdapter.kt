package am.acba.component.banners

import am.acba.component.databinding.ItemBannersBinding
import am.acba.component.extensions.inflater
import am.acba.component.extensions.load
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlin.text.isNotEmpty

class BannersAdapter : ListAdapter<IBanner, BannersAdapter.ViewHolder>(BannerDiffCallBack()) {

    private var onItemClick: ((IBanner) -> Unit)? = null
    private var onBannerCloseClick: ((IBanner) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBannersBinding.inflate(parent.context.inflater(), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemBannersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(banner: IBanner) {
            binding.apply {
                if (banner.getLinkText().isNotEmpty()) {
                    val mSpannableString = android.text.SpannableString(banner.getLinkText())
                    mSpannableString.setSpan(android.text.style.UnderlineSpan(), 0, mSpannableString.length, 0)
                    offerLinkButton.text = mSpannableString
                }
                if (banner.getImageUrl().isNotEmpty()) {
                    offerImage.load(banner.getImageUrl())
                }
                offerTitle.text = banner.getTitle()
                offerDescription.text = androidx.core.text.HtmlCompat.fromHtml(banner.getDescription(), androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY)
                closeOffer.isVisible = banner.isCloseVisible()
                closeOffer.setOnClickListener { onBannerCloseClick?.invoke(banner) }
                root.setOnClickListener { onItemClick?.invoke(banner) }
            }
        }
    }

    fun setOnOfferClick(onItemClick: (IBanner) -> Unit) {
        this.onItemClick = onItemClick
    }

    fun setOnCloseOfferClick(onOfferCloseClick: (IBanner) -> Unit) {
        this.onBannerCloseClick = onOfferCloseClick
    }

    private class BannerDiffCallBack : DiffUtil.ItemCallback<IBanner>() {
        override fun areItemsTheSame(oldItem: IBanner, newItem: IBanner) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: IBanner, newItem: IBanner) =
            oldItem == newItem
    }
}
package am.acba.component.phoneNumberInput.adapter

import am.acba.component.R
import am.acba.component.databinding.CountryItemBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.phoneNumberInput.CountryModel
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class CountriesListAdapter(
    private val itemClick: ((CountryModel) -> Unit)? = null,
    private var countries: List<CountryModel>,
    private var bottomSheetType: Int,
) : RecyclerView.Adapter<CountriesListAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun replaceList(tList: List<CountryModel>) {
        countries = tList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = countries.size

    inner class CountryViewHolder(private val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            val selectedIndex = countries.indexOfFirst { it.isSelected }
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClick?.invoke(countries[position])
                }
                if (position != selectedIndex) {
                    countries[selectedIndex].isSelected = false
                    countries[position].isSelected = true
                }
                notifyItemChanged(selectedIndex)
                notifyItemChanged(position)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(country: CountryModel) {
            binding.apply {
                Glide.with(root.context).asBitmap().load(country.flagResId)
                    .apply(RequestOptions.circleCropTransform().override(24.dpToPx(), 24.dpToPx()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.icFlag)
                countryText.text = when (bottomSheetType) {
                    1 -> "${country.name} (+${country.phoneCode})"
                    else -> country.name
                }
                val colorRes = if (country.isSelected) R.attr.backgroundTonal2 else 0
                val backgroundColor = root.context.getColorFromAttr(colorRes)
                root.setBackgroundColor(backgroundColor)
            }
        }
    }
}
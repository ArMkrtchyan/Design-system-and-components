package am.acba.component.phoneNumberInput

import am.acba.component.databinding.CountryItemBinding
import am.acba.component.extensions.dpToPx
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CountriesAdapter(
    private val itemClick: ((CountryModel) -> Unit)? = null,
    private val countries: List<CountryModel>
) : RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    inner class CountryViewHolder(private val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClick?.invoke(countries[position])
                }
            }
        }

        fun bind(country: CountryModel) {
            binding.apply {
                Glide.with(binding.root.context).asBitmap().load(country.flagResId)
                    .apply(RequestOptions.circleCropTransform().override(24.dpToPx(), 24.dpToPx()))
                    .into(binding.icFlag)
                countryText.text = country.name
            }

        }
    }
}
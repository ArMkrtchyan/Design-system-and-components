package am.acba.component.phoneNumberInput.adapter

import am.acba.component.chip.PrimaryChip
import am.acba.component.databinding.ItemCountryChipsBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.phoneNumberInput.CountryModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.logging.Handler

class CountriesChipsAdapter(
    private var countries: List<CountryModel>,
    private val itemClick: ((CountryModel) -> Unit)? = null
) : RecyclerView.Adapter<CountriesChipsAdapter.CountryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryChipsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val binding = holder.binding
        binding.apply {
            if (chipConstraint.childCount == 1) {
                val ids = arrayListOf<Int>()
                countries.forEach { country ->
                    val chip = PrimaryChip(root.context)
                    chip.setChipStartIconType(PrimaryChip.ChipStartIconType.IMAGE)
                    chip.setChipSize(PrimaryChip.ChipSize.SMALL)
                    val id = View.generateViewId()
                    chip.id = id
                    ids.add(id)

                    chipConstraint.addView(chip)
                    flow.addView(chip)
                    chip.requestLayout()
                    chip.setChipText(country.name)
                    Glide.with(binding.root.context).asBitmap().load(country.flagResId)
                        .apply(RequestOptions.circleCropTransform().override(30.dpToPx(), 30.dpToPx()))
                        .into(chip.startIcon)

                    chip.setOnClickListener {
                        itemClick?.invoke(country)
                    }
                }
                flow.referencedIds = ids.toIntArray()
                flow.requestLayout()
            }
        }

    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class CountryViewHolder(val binding: ItemCountryChipsBinding) : RecyclerView.ViewHolder(binding.root)
}
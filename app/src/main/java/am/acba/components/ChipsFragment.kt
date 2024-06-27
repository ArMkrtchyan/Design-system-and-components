package am.acba.components

import am.acba.component.chip.PrimaryChip
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentChipsBinding
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class ChipsFragment : BaseViewBindingFragment<FragmentChipsBinding>() {
    override val inflate: Inflater<FragmentChipsBinding>
        get() = FragmentChipsBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentChipsBinding.initView() {
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == am.acba.component.R.id.menu_main_download) {
                Toast.makeText(requireContext(), "Download", Toast.LENGTH_SHORT).show()
            } else if (it.itemId == am.acba.component.R.id.menu_main_download) {
                Toast.makeText(requireContext(), "Settings", Toast.LENGTH_SHORT).show()
            }
            false
        }
        setChipClicks(chipSmall1)
        setChipClicks(chipSmall2)
        setChipClicks(chipSmall3)
        setChipClicks(chipSmall4)
        setChipClicks(chipMedium1)
        setChipClicks(chipMedium2)
        setChipClicks(chipMedium3)
        setChipClicks(chipMedium4)
        chipSmall1.setEndIconClickListener {
            Toast.makeText(requireContext(), "Click on close", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setChipClicks(chip: PrimaryChip) {
        chip.setOnClickListener { chip.isSelected = !chip.isSelected }
    }
}
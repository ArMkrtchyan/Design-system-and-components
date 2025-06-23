package am.acba.components.models

import am.acba.component.chip.IChipModel
import am.acba.component.chip.PrimaryChip

data class RepaymentFrequency(
    val id: Int = 0, val frequency: String = "", val startIconRes: Int,
    val iconType: PrimaryChip.ChipStartIconType, val isSelected: Boolean
) :
    IChipModel {
    override fun getTitle(): String {
        return frequency
    }

    override fun getStartIcon(): Int {
        return startIconRes
    }

    override fun getSelected(): Boolean {
        return isSelected
    }

    override fun getChipIconType(): PrimaryChip.ChipStartIconType {
        return iconType
    }
}

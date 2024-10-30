package am.acba.components.models

import am.acba.acbamobile.ui.kotlin.screens.loan.IChipModel
import am.acba.component.chip.PrimaryChip

data class RepaymentFrequency(val id: Int = 0, val frequency: String = "", val startIcon: Int,val iconType:PrimaryChip.ChipStartIconType) : IChipModel {
    override fun getTitle(): String {
        return frequency
    }

    override fun getStartIcon2(): Int {
        return startIcon
    }

    override fun getChipIconType(): PrimaryChip.ChipStartIconType {
        return iconType
    }
}

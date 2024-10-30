package am.acba.acbamobile.ui.kotlin.screens.loan

import am.acba.component.chip.PrimaryChip

interface IChipModel {
    fun getTitle(): String
    fun getStartIcon2(): Int = 0
    fun getChipIconType(): PrimaryChip.ChipStartIconType = PrimaryChip.ChipStartIconType.NONE
}
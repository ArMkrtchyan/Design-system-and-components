package am.acba.acbamobile.ui.kotlin.screens.loan

import am.acba.component.chip.PrimaryChip

interface IChipModel {
    fun getTitle(): String
    fun getStartIcon(): Int = 0
    fun getSelected(): Boolean = false
    fun getChipIconType(): PrimaryChip.ChipStartIconType = PrimaryChip.ChipStartIconType.NONE
}
package am.acba.acbamobile.ui.kotlin.screens.loan

import am.acba.component.chip.PrimaryChip
import androidx.annotation.DrawableRes

interface IChipModel {
    fun getTitle(): String

    @DrawableRes
    fun getStartIcon(): Int = 0
    fun getSelected(): Boolean = false
    fun getChipIconType(): PrimaryChip.ChipStartIconType = PrimaryChip.ChipStartIconType.NONE
}
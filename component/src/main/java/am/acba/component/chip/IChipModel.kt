package am.acba.component.chip

import androidx.annotation.DrawableRes

interface IChipModel {
    fun getTitle(): String

    @DrawableRes
    fun getStartIcon(): Int = 0
    fun getSelected(): Boolean = false
    fun getChipIconType(): PrimaryChip.ChipStartIconType = PrimaryChip.ChipStartIconType.NONE
}
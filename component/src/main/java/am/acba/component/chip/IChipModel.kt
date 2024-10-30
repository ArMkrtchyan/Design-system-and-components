package am.acba.acbamobile.ui.kotlin.screens.loan

import am.acba.component.chip.PrimaryChip
import android.graphics.drawable.Drawable

interface IChipModel {
    fun getTitle(): String
    fun getResId(): Drawable? = null
    fun getChipIconType(): PrimaryChip.ChipStartIconType = PrimaryChip.ChipStartIconType.NONE
}
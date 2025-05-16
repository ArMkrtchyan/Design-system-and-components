package am.acba.compose.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

@Stable
class ThemedResources(
    defaultCardIcon: Int,
    visaCardIcon: Int,
    masterCardIcon: Int,
    arcaCardIcon: Int,
    amexCardIcon: Int,
    uPayCardIcon: Int,
) {
    var defaultCardIcon by mutableIntStateOf(defaultCardIcon)
        private set
    var visaCardIcon by mutableIntStateOf(visaCardIcon)
        private set
    var masterCardIcon by mutableIntStateOf(masterCardIcon)
        private set
    var arcaCardIcon by mutableIntStateOf(arcaCardIcon)
        private set
    var amexCardIcon by mutableIntStateOf(amexCardIcon)
        private set
    var uPayCardIcon by mutableIntStateOf(uPayCardIcon)
        private set


    fun update(oldColors: ThemedResources) {
        defaultCardIcon = oldColors.defaultCardIcon
        visaCardIcon = oldColors.visaCardIcon
        masterCardIcon = oldColors.masterCardIcon
        arcaCardIcon = oldColors.arcaCardIcon
        amexCardIcon = oldColors.amexCardIcon
        uPayCardIcon = oldColors.uPayCardIcon
    }
}

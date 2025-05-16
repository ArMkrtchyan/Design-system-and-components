package am.acba.compose.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

@Stable
class ThemedResources(
    defaultCardIcon: Int,
    visaCardIcon: Int,
) {
    var defaultCardIcon by mutableIntStateOf(defaultCardIcon)
        private set
    var visaCardIcon by mutableIntStateOf(visaCardIcon)
        private set


    fun update(oldColors: ThemedResources) {
        defaultCardIcon = oldColors.defaultCardIcon
        visaCardIcon = oldColors.visaCardIcon
    }
}

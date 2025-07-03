package am.acba.compose.theme

import androidx.compose.runtime.Stable

@Stable
data class ThemedResources(
    val defaultCardIcon: Int,
    val visaCardIcon: Int,
    val masterCardIcon: Int,
    val arcaCardIcon: Int,
    val amexCardIcon: Int,
    val uPayCardIcon: Int,
)

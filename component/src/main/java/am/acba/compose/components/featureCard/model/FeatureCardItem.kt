package am.acba.compose.components.featureCard.model

import androidx.compose.ui.graphics.Color

data class FeatureCardItem(
    val text: String = "",
    val secondaryText: String = "",
    val tertiaryText: String = "",
    val badgeText: String = "",
    val background: Color? = null,
    val badgeBackground: Color? = null
)
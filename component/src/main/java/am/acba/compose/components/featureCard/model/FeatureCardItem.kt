package am.acba.compose.components.featureCard.model

import androidx.compose.ui.graphics.Color

data class FeatureCardItem(
    val offerAmount: String = "",
    val creditLimitTitle: String = "",
    val offerExpirationDate: String = "",
    val badge: String = "",
    val background: Color? = null,
    val badgeBackground: Color? = null
)
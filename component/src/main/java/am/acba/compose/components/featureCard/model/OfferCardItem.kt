package am.acba.compose.components.featureCard.model

import androidx.compose.ui.graphics.Color

data class OfferCardItem(
    val amount: String = "",
    val creditLimitTitle: String = "",
    val expirationDate: String = "",
    val badge: String = "",
    val background: Color? = null,
    val badgeBackground: Color? = null
)
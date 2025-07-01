package am.acba.compose.components.featureCard.model

import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.ui.graphics.Color

data class OfferCardItem(
    override val amount: String,
    override val creditLimitTitle: String,
    override val expirationDate: String,
    override val badge: String,
) : IOfferCardItem

interface IOfferCardItem {
    val amount: String
        get() = EMPTY_STRING
    val creditLimitTitle: String
        get() = EMPTY_STRING
    val expirationDate: String
        get() = EMPTY_STRING
    val badge: String
        get() = EMPTY_STRING
    val background: Color?
        get() = null
    val badgeBackground: Color?
        get() = null
    val badgeTextColor: Color?
        get() = null
}
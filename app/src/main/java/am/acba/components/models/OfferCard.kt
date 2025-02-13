package am.acba.components.models

import am.acba.component.loanComponents.IOfferCard
import androidx.annotation.AttrRes

data class OfferCard(
    val offerCardId: Long = -1,
    val offerCardTitle: String = "",
    val offerCardAmount: String = "",
    val offerCardCurrency: String = "",
    val offerCardEndDate: String = "",
    val offerCardBadgeText: String = "",
    @AttrRes val offerCardBadgeBackgroundColorAttr: Int = 0,
    val newBadgeVisibility: Boolean = false,
    @AttrRes val offerCardBackgroundColorAttr: Int = 0
) : IOfferCard {
    override fun getUniqueId(): Long {
        return offerCardId
    }

    override fun getTitle(): String {
        return offerCardTitle
    }

    override fun getOffer(): String {
        return "$offerCardAmount $offerCardCurrency"
    }

    override fun getDescription(): String {
        return offerCardEndDate
    }

    override fun getBadgeVisibility(): Boolean {
        return newBadgeVisibility
    }

    override fun getCardBackgroundColorAttr(): Int {
        return offerCardBackgroundColorAttr
    }

    override fun getBadgeText(): String {
        return offerCardBadgeText
    }

    override fun getBadgeBackgroundColorAttr(): Int {
        return offerCardBadgeBackgroundColorAttr
    }
}

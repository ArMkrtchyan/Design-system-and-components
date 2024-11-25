package am.acba.components.models

import am.acba.component.productCard.ICardAdditionalInfo
import am.acba.component.productCard.ICardInfo
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes

data class CardInfoExample(
    val productTitle: String = "",
    val productDescription: String = "",
    val productIconUrl: String = "",
    @DrawableRes val productStartIcon: Int = 0,
    @AttrRes val productStartIconTint: Int? = null,
    val productNextPaymentDay: String = "",
    val productNextPaymentAmount: String = "",
    val productNextPaymentDayTitle: String = "",
    val productNextPaymentAmountTitle: String = "",
    val productCardAdditionalInfo: List<ICardAdditionalInfo> = emptyList(),
    @AttrRes val productBackgroundColorAttr: Int = 0,
    @AttrRes val productBadgeBackgroundColorAttr: Int = 0,
    @AttrRes val productBadgeColorAndIconColorAttr: Int = 0,
    @DrawableRes val productBadgeIcon: Int = 0,
    val productBadgeText: String = "",
) : ICardInfo {
    override fun getTitle(): String {
        return productTitle
    }

    override fun getDescription(): String {
        return productDescription
    }

    override fun getStartIcon(): Int {
        return productStartIcon
    }

    override fun getStartIconUrl(): String {
        return productIconUrl
    }

    override fun getStartIconTint(): Int? {
        return productStartIconTint
    }

    override fun getNextPaymentDay(): String {
        return productNextPaymentDay
    }

    override fun getNextPaymentDayTitle(): String {
        return productNextPaymentDayTitle
    }

    override fun getNextPaymentAmount(): String {
        return productNextPaymentAmount
    }

    override fun getNextPaymentAmountTitle(): String {
        return productNextPaymentAmountTitle
    }

    override fun getCardAdditionalInfo(): List<ICardAdditionalInfo> {
        return productCardAdditionalInfo
    }

    override fun getBackgroundColorAttr(): Int {
        return productBackgroundColorAttr
    }

    override fun getBadgeBackgroundColorAttr(): Int {
        return productBadgeBackgroundColorAttr
    }

    override fun getBadgeTextAndIconColorAttr(): Int {
        return productBadgeColorAndIconColorAttr
    }

    override fun getBadgeIcon(): Int {
        return productBadgeIcon
    }

    override fun getBadgeText(): String {
        return productBadgeText
    }
}

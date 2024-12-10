package am.acba.component.productCard

interface ICardInfo {
    fun getTitle(): String
    fun getDescription(): String
    fun getStartIconUrl(): String
    fun getStartIcon(): Int
    fun getStartIconTint(): Int?
    fun getNextPaymentDay(): String
    fun getNextPaymentAmount(): String
    fun getNextPaymentDayTitle(): String
    fun getNextPaymentAmountTitle(): String
    fun getCardAdditionalInfo(): List<ICardAdditionalInfo>
    fun getBackgroundColorAttr(): Int
    fun getBadgeBackgroundColorAttr(): Int
    fun getBadgeTextAndIconColorAttr(): Int
    fun getBadgeIcon(): Int
    fun getBadgeText(): String
}
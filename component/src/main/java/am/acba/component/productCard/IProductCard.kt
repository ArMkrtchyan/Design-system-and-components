package am.acba.component.productCard

interface IProductCard {
    fun getTitle(): String
    fun getDescription(): String
    fun getStartIcon(): Int
    fun getNextPaymentDay(): String
    fun getNextPaymentAmount(): String
    fun getCardAdditionalInfo(): List<IProductAdditionalInfo>
    fun getBackgroundColorAttr(): Int
    fun getBadgeBackgroundColorAttr(): Int
    fun getBadgeTextAndIconColorAttr(): Int
    fun getBadgeIcon(): Int
    fun getBadgeText(): String
}
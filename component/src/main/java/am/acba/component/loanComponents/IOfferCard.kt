package am.acba.component.loanComponents

interface IOfferCard {
    fun getTitle(): String
    fun getOffer(): String
    fun getDescription(): String
    fun getBadgeVisibility(): Boolean
    fun getCardBackgroundColorAttr(): Int
    fun getBadgeText(): String
    fun getBadgeBackgroundColorAttr(): Int
}
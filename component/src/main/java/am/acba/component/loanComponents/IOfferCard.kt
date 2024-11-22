package am.acba.component.loanComponents

interface IOfferCard {
    fun getUniqueId(): Int
    fun getTitle(): String
    fun getOffer(): String
    fun getDescription(): String
    fun getBadgeVisibility(): Boolean
    fun getCardBackgroundColorAttr(): Int
    fun getBadgeText(): String
    fun getBadgeBackgroundColorAttr(): Int
    fun isOpened(): Boolean {
        return false
    }

    fun setOpened(openedState: Boolean)
}
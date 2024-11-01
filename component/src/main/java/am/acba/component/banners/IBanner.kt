package am.acba.component.banners

interface IBanner {
    fun getTitle(): String
    fun getDescription(): String
    fun getImageUrl(): String
    fun getLinkText(): String
    fun isCloseVisible(): Boolean
}
package am.acba.component.productCard

data class ProductCardAdditionalInfo(
    val additionalInfoTitle: String = "",
    val additionalInfo: String = "",
) : IProductAdditionalInfo {
    override fun getTitle(): String {
        return additionalInfoTitle
    }

    override fun getInfo(): String {
        return additionalInfo
    }
}
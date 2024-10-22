package am.acba.components.models

import am.acba.component.productCard.ICardAdditionalInfo

data class CardAdditionalInfo(
    val additionalInfoTitle: String = "",
    val additionalInfo: String = "",
) : ICardAdditionalInfo {
    override fun getTitle(): String {
        return additionalInfoTitle
    }

    override fun getInfo(): String {
        return additionalInfo
    }
}
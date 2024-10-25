package am.acba.components.models

import am.acba.component.banners.IBanner

data class HomeOffer(
    val offerId: Int = 0,
    val offerImageUrl: String = "https://online1-test.acba.am/Shared/Shared//Accounts/CurrentAccount.png",
    val offerTitle: String = "",
    val offerDescription: String = "",
    val offerLinkText: String = "",
    val isOfferClosable: Boolean = true,
) : IBanner {
    override fun getTitle(): String {
        return offerTitle
    }

    override fun getDescription(): String {
        return offerDescription
    }

    override fun getImageUrl(): String {
        return offerImageUrl
    }

    override fun getLinkText(): String {
        return offerLinkText
    }

    override fun isCloseVisible(): Boolean {
        return isOfferClosable
    }
}
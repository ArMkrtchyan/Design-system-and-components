package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentBannersBinding
import am.acba.components.models.HomeOffer

class BannersFragment : BaseViewBindingFragment<FragmentBannersBinding>() {
    override val inflate: Inflater<FragmentBannersBinding>
        get() = FragmentBannersBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentBannersBinding.initView() {
        banners.submitBanners(
            arrayListOf(
                HomeOffer(
                    offerId = 1,
                    offerImageUrl = "https://online1-test.acba.am/Shared/Shared//Accounts/CurrentAccount.png",
                    offerTitle = "3,500,000.00 AMD",
                    offerDescription = "Գյուղ. վարկ առաջարկ<br />Ստացման վերջնաժամկետ <b>12/04/2024</b>",
                    offerLinkText = "Ստանալ հիմա",
                    isOfferClosable = true,
                )
            )
        )
    }
}

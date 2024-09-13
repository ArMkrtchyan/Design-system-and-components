package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentProductDescriptionCardBinding
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class ProductDescriptionCardFragment : BaseViewBindingFragment<FragmentProductDescriptionCardBinding>() {
    override val inflate: Inflater<FragmentProductDescriptionCardBinding>
        get() = FragmentProductDescriptionCardBinding::inflate

    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar
    private var list1 = arrayListOf<Pair<Drawable?, String>>()
    private var list2 = arrayListOf<Pair<Drawable?, String>>()
    private var list3 = arrayListOf<String>()
    private var list4 = arrayListOf<String>()
    override fun FragmentProductDescriptionCardBinding.initView() {
        list1 = arrayListOf(
            Pair(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.ic_info), "-50% տարեկան վճար"),
            Pair(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.ic_card), "Բարերար"),
            Pair(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.ic_id_card), "Նոր"),
        )
        list2 = arrayListOf(Pair(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.ic_info), "-50% տարեկան վճար"))
        list3 = arrayListOf(
            "Մինչև 4% տարեկան տոկոսադրույք",
            "Հաշվի անսահմանափակ համալրման հնարավորություն",
            "Հաշվին առկա միջոցների օգտագործում՝ առանց կուտակված տոկոսագումարների կորստի",
        )
        list4 = arrayListOf(
            "հաշիվ՝ 4 արժույթով",
            "Մինչև 4% տարեկան տոկոսադրույք"
        )
        setProductCard0()
        setProductCard1()
        setProductCard2()
        setProductCard3()
        setProductCard4()
        setProductCard5()
        setProductCard6()
    }

    private fun setProductCard0() {
        mBinding.productCardFull.setTitle("Խնայողական")
        mBinding.productCardFull.setDescription("Հետևիր խնայողություններիդ աճին")
        mBinding.productCardFull.setImage(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.coins_illustration))
        mBinding.productCardFull.setBullets(list3)
        mBinding.productCardExcample.setBadgesGroup(list1)
        mBinding.productCardExcample.setBullets(list3)
    }

    private fun setProductCard1() {
        mBinding.productCard1.setTitle("Խնայողական")
        mBinding.productCard1.setSubTitle("ԹՎԱՅԻՆ ՔԱՐՏ")
        mBinding.productCard1.setImage(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.coins_illustration))
        mBinding.productCard1.setBadgesGroup(list1)
        mBinding.productCard1.setBullets(list3)
    }

    private fun setProductCard2() {
        mBinding.productCard2.setTitle("Խնայողական")
        mBinding.productCard2.setImage(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.coins_illustration))
        mBinding.productCard2.setBadgesGroup(list1)
        mBinding.productCard2.setBullets(list3)
    }

    private fun setProductCard3() {
        mBinding.productCard3.setTitle("Խնայողական")
        mBinding.productCard3.setImage(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.coins_illustration))
        mBinding.productCard3.setBadgesGroup(list2)
        mBinding.productCard3.setBullets(list3)
    }

    private fun setProductCard4() {
        mBinding.productCard4.setTitle("Խնայողական")
        mBinding.productCard4.setImage(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.coins_illustration))
        mBinding.productCard4.setBadgesGroup(list2)
        mBinding.productCard4.setBullets(list4)
    }

    private fun setProductCard5() {
        mBinding.productCard5.setTitle("Խնայողական")
        mBinding.productCard5.setSubDescription("Հասանելի 7 արժույթagxyagxgaxgaxajhcahjcahjgxahjgcahjchajcahach svcavchjsvhjcshcbshjcbshjcbshjbcsjkbcksbs")
        mBinding.productCard5.setImage(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.coins_illustration))
        mBinding.productCard5.setBadgesGroup(list2)
    }

    private fun setProductCard6() {
        mBinding.productCard6.setTitle("Խնայողական Խնայողական Խնայողական Խնայողական Խնայողական Խնայողական Խնայողական")
        mBinding.productCard6.setImage(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.coins_illustration))
        mBinding.productCard6.setBadgesGroup(list2)
        mBinding.productCard6.setBullets(list3)
    }
}
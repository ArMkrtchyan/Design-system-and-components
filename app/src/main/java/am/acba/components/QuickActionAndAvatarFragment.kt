package am.acba.components

import am.acba.component.R
import am.acba.component.button.PrimaryActionTextButton.ActionButtonType
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentQuickActionAndAvatarBinding
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class QuickActionAndAvatarFragment : BaseViewBindingFragment<FragmentQuickActionAndAvatarBinding>() {
    override val inflate: Inflater<FragmentQuickActionAndAvatarBinding>
        get() = FragmentQuickActionAndAvatarBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentQuickActionAndAvatarBinding.initView() {
        mBinding.quickAction.setAnimation("music.json")
        mBinding.quickAction.setAnimationColor(colorAttr = am.acba.component.R.attr.contentAlternative4)
        mBinding.quickAction.setAnimationColor(keyPathValues = "BG", colorAttr = am.acba.component.R.attr.backgroundAlternative4)
        mBinding.quickAction.setIconBackground(am.acba.component.R.drawable.background_rounded)
        mBinding.quickAction.getActionIcon().isVisible = false
        mBinding.quickAction.setIconBackgroundTint(requireContext().getColorStateListFromAttr(am.acba.component.R.attr.contentBrandTonal1))
        mBinding.quickAction.setActionBadgeImage(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.ic_success_filled))
        mBinding.quickAction.setOnActionBadgeClickListener {
            Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
        }
        mBinding.quickAction.setActionBadgeBackground(am.acba.component.R.drawable.background_rounded)
        mBinding.quickAction.setActionBadgeImageTint(context?.getColorStateListFromAttr(am.acba.component.R.attr.contentBrand))
        mBinding.quickAction.setActionBadgeImage(getDrawable(requireContext(), am.acba.component.R.drawable.ic_camera))


        mBinding.fourth.setActionBadgeImage(ContextCompat.getDrawable(requireContext(), am.acba.component.R.drawable.ic_success_filled))
        mBinding.fourth.setOnActionBadgeClickListener {
            Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
        }
        mBinding.fourth.setActionBadgeBackground(am.acba.component.R.drawable.background_rounded)
        mBinding.fourth.setActionBadgeImageTint(context?.getColorStateListFromAttr(am.acba.component.R.attr.contentBrand))
        mBinding.fourth.setActionBadgeImage(getDrawable(requireContext(), am.acba.component.R.drawable.ic_camera))

        mBinding.third.setAvatarCheckedStatus(true)
        mBinding.third.setBadgeIconTint(context?.getColorStateListFromAttr(am.acba.component.R.attr.backgroundWarning))

        mBinding.changeTextFromAnotherState.setOnClickListener {
            testAvatar.setType(ActionButtonType.TEXT)
            testAvatar.setText("Misak Manukyan")
            testAvatar.setActionButtonColors(requireContext().getColorFromAttr(R.attr.contentAlternative3), requireContext().getColorFromAttr(R.attr.backgroundAlternative3))
        }

        mBinding.changeTextColor.setOnClickListener {
            testAvatar.setActionButtonColors(requireContext().getColorFromAttr(R.attr.contentAlternative2), requireContext().getColorFromAttr(R.attr.backgroundAlternative2))
        }

        mBinding.avatar.setOnClickListener {
            testAvatar.setType(ActionButtonType.AVATAR)
            testAvatar.getActionIcon().setBackgroundResource(R.drawable.background_rounded)
            testAvatar.setAnimation("smililng.json")
            testAvatar.setAnimationRepeatCount(repeatCount = 3)
        }

        mBinding.color.setOnClickListener {
            testAvatar.setIconBackground(R.drawable.background_rounded)
            testAvatar.setIconBackgroundTint(context?.getColorStateListFromAttr(R.attr.backgroundAlternative4))
            testAvatar.setAnimationColor(colorAttr = R.attr.contentAlternative4)
        }
    }
}
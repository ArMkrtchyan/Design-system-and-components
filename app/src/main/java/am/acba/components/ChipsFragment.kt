package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.component.tooltip.OnboardingHint
import am.acba.component.tooltip.TooltipModel
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentChipsBinding
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChipsFragment : BaseViewBindingFragment<FragmentChipsBinding>() {
    override val inflate: Inflater<FragmentChipsBinding>
        get() = FragmentChipsBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar
    private var onboardingHint: OnboardingHint? = null

    override fun FragmentChipsBinding.initView() {
        setChipClicks(chipSmall1)
        setChipClicks(chipSmall2)
        setChipClicks(chipSmall3)
        setChipClicks(chipSmall4)

        val tooltipModel1 = TooltipModel(
            "AAA  AAA  AAA AAA AAA AAA  AAA AAA AAA AAA",
            "AAA AAA AAA AAA",
            localImage = R.drawable.ic_launcher_background
        )
        val tooltipModel2 = TooltipModel("BBB", "BBB BBB BBB BBB", "", "welcome_animation.json")
        val tooltipModel3 = TooltipModel("CCC", "CCC CCC CCC CCC")
        val tooltipModel4 = TooltipModel(
            "CCC",
            "CCC 444 CCC CCC",
            "https://pixlr.com/images/index/ai-image-generator-one.webp"
        )
        val tooltipModel5 = TooltipModel(
            "CCC",
            "CCC 555 CCC CCC",
            localImage = R.drawable.ic_launcher_background
        )
        val tooltipModel6 = TooltipModel(
            "CCC",
            "info 555 CCC CCC",
            "https://pixlr.com/images/index/ai-image-generator-one.webp"
        )
        val tooltipModel7 = TooltipModel("CCC", "info 555 CCC CCC")

        val tooltipList = arrayListOf(
            tooltipModel1 to mBinding.chipSmall1,
            tooltipModel2 to mBinding.chipSmall2,
            tooltipModel3 to mBinding.chipSmall3,
            tooltipModel4 to mBinding.chipSmall4,
            tooltipModel5 to mBinding.chipSmall5,
            tooltipModel6 to mBinding.chipSmall6,
            tooltipModel7 to mBinding.chipSmall8
        )


        chipSmall1.setEndIconClickListener {
            Toast.makeText(requireContext(), "Click on close", Toast.LENGTH_LONG).show()
        }
        lifecycleScope.launch {
            delay(100)
            if (onboardingHint == null)
                onboardingHint = OnboardingHint(
                    requireContext(),
                    tooltipList,
                    R.string.ok,
                    onLastButtonClick = {
                        Toast.makeText(requireContext(), "Click on close", Toast.LENGTH_LONG).show()
                    },
                    onCloseButtonClick = {
                        if (tooltipList.size > 1) {
                            Toast.makeText(requireContext(), "Closed by x button", Toast.LENGTH_LONG).show()
                        }
                        mBinding.clContainer.removeView(onboardingHint)
                    }


                )
            mBinding.clContainer.addView(onboardingHint)
            onboardingHint?.handleSkip {
                mBinding.clContainer.removeView(onboardingHint)
            }

            if (tooltipList.size == 1)
                Toast.makeText(requireContext(), "Onboarding has 1 tooltip", Toast.LENGTH_LONG).show()


            onboardingHint?.setForwardClickListener { position ->
                if (position == tooltipList.size - 1) {
                    Toast.makeText(requireContext(), "Onboarding reached to last tooltip", Toast.LENGTH_LONG).show()
                }
            }

            onboardingHint?.handleSkip { }
        }

    }


    private fun setChipClicks(chip: View) {
        chip.setOnClickListener {
            //  chip.isSelected = !chip.isSelected
        }
    }
}
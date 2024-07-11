package am.acba.components

import am.acba.component.tooltip.OnboardingHint
import am.acba.component.tooltip.TooltipModel
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentChipsBinding
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChipsFragment : BaseViewBindingFragment<FragmentChipsBinding>() {
    override val inflate: Inflater<FragmentChipsBinding>
        get() = FragmentChipsBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar
    private var onboardingHint: OnboardingHint? = null

    override fun FragmentChipsBinding.initView() {
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == am.acba.component.R.id.menu_main_download) {
                Toast.makeText(requireContext(), "Download", Toast.LENGTH_SHORT).show()
            } else if (it.itemId == am.acba.component.R.id.menu_main_download) {
                Toast.makeText(requireContext(), "Settings", Toast.LENGTH_SHORT).show()
            }
            false
        }
        setChipClicks(chipSmall1)
        setChipClicks(chipSmall2)
        setChipClicks(chipSmall3)
        setChipClicks(chipSmall4)

        val tooltipModel1 = TooltipModel("AAA", "AAA AAA AAA AAA", "https://pixlr.com/images/index/ai-image-generator-one.webp")
        val tooltipModel2 = TooltipModel("BBB", "BBB BBB BBB BBB", "", "welcome_animation.json")
        val tooltipModel3 = TooltipModel("CCC", "CCC CCC CCC CCC")
        val tooltipModel4 = TooltipModel("CCC", "CCC 444 CCC CCC","https://pixlr.com/images/index/ai-image-generator-one.webp")
        val tooltipModel5 = TooltipModel("CCC", "CCC 555 CCC CCC", "https://pixlr.com/images/index/ai-image-generator-one.webp")
        val tooltipModel6 = TooltipModel("CCC", "info 555 CCC CCC","https://pixlr.com/images/index/ai-image-generator-one.webp")
        val tooltipModel7 = TooltipModel("CCC", "info 555 CCC CCC")

        chipSmall1.setEndIconClickListener {
            Toast.makeText(requireContext(), "Click on close", Toast.LENGTH_SHORT).show()
        }
        lifecycleScope.launch {
            delay(100)
            if (onboardingHint == null)
                onboardingHint = OnboardingHint(requireContext(), null, requireActivity()).apply {
                    setButtonTitle(R.string.ok)
                    setTooltipList(
                        arrayListOf(
                            tooltipModel1,
                            tooltipModel2,
                            tooltipModel3,
                            tooltipModel4,
                            tooltipModel5,
                            tooltipModel6,
                            tooltipModel7
                        )
                    )
                    setTargetViews(
                        arrayListOf(
                            mBinding.chipSmall1,
                            mBinding.chipSmall2,
                            mBinding.chipSmall3,
                            mBinding.chipSmall4,
                            mBinding.chipSmall5,
                            mBinding.chipSmall6,
                            mBinding.chipSmall8
                        ), requireActivity()
                    )
                }

            mBinding.clContainer.addView(onboardingHint)
            onboardingHint?.handleSkip {
                mBinding.clContainer.removeView(onboardingHint)
            }
        }

    }


    private fun setChipClicks(chip: View) {
        chip.setOnClickListener {
            //  chip.isSelected = !chip.isSelected
        }
    }
}
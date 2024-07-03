package am.acba.components

import am.acba.component.toolbar.OnboardingHint
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
        setChipClicks(chipMedium1)
        setChipClicks(chipMedium2)
        setChipClicks(chipMedium3)
        setChipClicks(chipMedium4)
        setChipClicks(linear)
        chipSmall1.setEndIconClickListener {
            Toast.makeText(requireContext(), "Click on close", Toast.LENGTH_SHORT).show()
        }
        lifecycleScope.launch {
            delay(100)
            if (onboardingHint == null)
                onboardingHint = OnboardingHint(requireContext()).apply {
                    setTargetViews(
                        arrayListOf(
                            mBinding.chipSmall2, mBinding.chipSmall4, mBinding.chipMedium3
                        )
                    )
                }
            mBinding.frame.addView(onboardingHint)
            onboardingHint?.handleSkip {
                mBinding.frame.removeView(onboardingHint)
            }
        }
    }


    private fun setChipClicks(chip: View) {
        chip.setOnClickListener {

            //  chip.isSelected = !chip.isSelected
        }
    }
}
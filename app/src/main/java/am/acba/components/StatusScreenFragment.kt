package am.acba.components

import am.acba.component.extensions.dpToPx
import am.acba.component.imageView.PrimaryImageView
import am.acba.component.statusScreen.PrimaryStatusScreen
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentStatusScreenBinding
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources

class StatusScreenFragment : BaseViewBindingFragment<FragmentStatusScreenBinding>() {
    override val inflate: Inflater<FragmentStatusScreenBinding>
        get() = FragmentStatusScreenBinding::inflate

    override fun FragmentStatusScreenBinding.initView() {
        layoutRoot.apply {
//            addDynamicContainer()
            addCentreMedia()
//            setLottieAnimation("welcome_animation.json")

            setOnCloseClickListener {
                Toast.makeText(requireContext(), "Click on close", Toast.LENGTH_LONG).show()
            }
            setOnPrimaryButtonClickListener {
                Toast.makeText(requireContext(), "Click on primary button", Toast.LENGTH_LONG)
                    .show()
            }
            setOnGhostButtonClickListener {
                Toast.makeText(requireContext(), "Click on ghost button", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun PrimaryStatusScreen.addDynamicContainer() {
        val view = PrimaryImageView(requireContext())
        view.background = AppCompatResources.getDrawable(
            requireContext(),
            am.acba.component.R.color.White
        )
        addContainer(view, ViewGroup.LayoutParams(800.dpToPx(), 50.dpToPx()))
    }

    private fun PrimaryStatusScreen.addCentreMedia() {
        setCentreMediaType(PrimaryStatusScreen.MediaTypes.SMALL)
        setCentreMediaAnimation("welcome_animation.json")
        onCentreAnimationEnd = { animationView, _ ->
            animationView.frame = animationView.maxFrame.toInt() - 40
            animationView.pauseAnimation()
        }
//            setCentreImage(AppCompatResources.getDrawable(requireContext(),am.acba.component.R.drawable.ic_block))
    }
}
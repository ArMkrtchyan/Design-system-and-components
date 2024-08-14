package am.acba.components

import am.acba.component.imageView.PrimaryImageView
import am.acba.component.statusScreen.PrimaryStatusScreen
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentStatusScreenBinding
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources

class StatusScreenFragment : BaseViewBindingFragment<FragmentStatusScreenBinding>() {
    override val inflate: Inflater<FragmentStatusScreenBinding>
        get() = FragmentStatusScreenBinding::inflate

    override fun FragmentStatusScreenBinding.initView() {
        layoutRoot.apply {
            val view = PrimaryImageView(requireContext())
            view.background =
                AppCompatResources.getDrawable(requireContext(), am.acba.component.R.color.White)
//            addContainer(view, ViewGroup.LayoutParams(800.dpToPx(), 50.dpToPx()))
//            setLottieAnimation("welcome_animation.json")
            setCentreMediaType(PrimaryStatusScreen.MediaTypes.SMALL)
            setCentreMediaAnimation("welcome_animation.json")
            onCentreAnimationEnd = { animationView, _ ->
                animationView.frame = animationView.maxFrame.toInt() - 40
                animationView.pauseAnimation()
            }
//            setCentreImage(AppCompatResources.getDrawable(requireContext(),am.acba.component.R.drawable.ic_block))
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
}
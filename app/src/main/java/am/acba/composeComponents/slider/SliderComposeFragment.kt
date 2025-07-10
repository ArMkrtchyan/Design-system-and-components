package am.acba.composeComponents.slider

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class SliderComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        SliderScreen(title)
    }
}
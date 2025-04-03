package am.acba.composeComponents.controls

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class ControlsComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ControlsScreen(title)
    }
}
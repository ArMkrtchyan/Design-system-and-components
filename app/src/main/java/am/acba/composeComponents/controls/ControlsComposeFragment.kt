package am.acba.composeComponents.controls

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class ControlsComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ControlsScreen(title)
    }
}
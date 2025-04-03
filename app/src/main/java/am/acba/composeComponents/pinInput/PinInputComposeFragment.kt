package am.acba.composeComponents.pinInput

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class PinInputComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        PinInputScreen(title)
    }
}
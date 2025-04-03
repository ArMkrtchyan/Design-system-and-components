package am.acba.composeComponents.chips

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class ChipsComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ChipsScreen(title)
    }
}
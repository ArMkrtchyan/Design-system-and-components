package am.acba.composeComponents.divider

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class DividerComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        DividerScreen(title)
    }
}
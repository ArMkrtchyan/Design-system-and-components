package am.acba.composeComponents.toolbar

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable


class ToolbarComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ToolbarScreen(title)
    }
}
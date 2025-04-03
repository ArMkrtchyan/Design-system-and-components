package am.acba.composeComponents.liatItem

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class ListItemComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ButtonsScreen(title)
    }
}
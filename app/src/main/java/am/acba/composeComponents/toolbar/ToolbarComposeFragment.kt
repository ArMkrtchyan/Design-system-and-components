package am.acba.composeComponents.toolbar

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable


class ToolbarComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ToolbarScreen(title)
    }
}
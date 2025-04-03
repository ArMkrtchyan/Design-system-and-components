package am.acba.composeComponents.progressIndicator

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class ProgressIndicatorComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ProgressIndicatorScreen(title)
    }
}
package am.acba.composeComponents.alerts

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class AlertsComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        AlertsScreen(title)
    }
}
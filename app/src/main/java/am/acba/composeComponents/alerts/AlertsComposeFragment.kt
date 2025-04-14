package am.acba.composeComponents.alerts

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class AlertsComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        AlertsScreen(title)
    }
}
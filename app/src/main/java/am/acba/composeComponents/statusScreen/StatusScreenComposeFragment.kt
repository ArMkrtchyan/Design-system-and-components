package am.acba.composeComponents.statusScreen

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class StatusScreenComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        StatusScreen(title)
    }
}
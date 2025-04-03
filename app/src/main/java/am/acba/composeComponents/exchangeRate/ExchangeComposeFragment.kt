package am.acba.composeComponents.exchangeRate

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class ExchangeComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ExchangeScreen(title)
    }
}
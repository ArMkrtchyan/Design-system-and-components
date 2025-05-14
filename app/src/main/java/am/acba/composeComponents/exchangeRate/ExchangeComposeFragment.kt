package am.acba.composeComponents.exchangeRate

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class ExchangeComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ExchangeScreen(title)
    }
}
package am.acba.composeComponents.currencyInput

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class CurrencyInputComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        CurrencyInputScreen(title)
    }
}
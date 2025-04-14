package am.acba.composeComponents.currencyInput

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class CurrencyInputComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        CurrencyInputScreen(title)
    }
}
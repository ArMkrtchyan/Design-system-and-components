package am.acba.composeComponents.productDescriptionCard

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class ProductDescriptionCardComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ProductDescriptionCardScreen(title)
    }
}
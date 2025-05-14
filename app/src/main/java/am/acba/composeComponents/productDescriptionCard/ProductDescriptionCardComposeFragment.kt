package am.acba.composeComponents.productDescriptionCard

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class ProductDescriptionCardComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ProductDescriptionCardScreen(title)
    }
}
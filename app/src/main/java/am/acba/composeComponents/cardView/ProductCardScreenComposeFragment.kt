package am.acba.composeComponents.cardView

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class ProductCardScreenComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ProductCardScreen(title)
    }
}
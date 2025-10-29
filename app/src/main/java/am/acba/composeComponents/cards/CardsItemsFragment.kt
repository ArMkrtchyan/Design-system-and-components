package am.acba.composeComponents.cards

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class CardsItemsFragment : BaseComposeFragment() {

    @Composable
    override fun SetContent() {
        CardsItemScreen(title)
    }
}
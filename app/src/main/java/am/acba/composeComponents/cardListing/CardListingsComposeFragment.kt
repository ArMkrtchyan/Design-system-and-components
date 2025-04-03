package am.acba.composeComponents.cardListing

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class CardListingsComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        CardListingsScreen(title)
    }
}
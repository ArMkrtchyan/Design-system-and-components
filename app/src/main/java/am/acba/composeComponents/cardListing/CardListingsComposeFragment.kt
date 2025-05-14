package am.acba.composeComponents.cardListing

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class CardListingsComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        CardListingsScreen(title)
    }
}
package am.acba.composeComponents.offers

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class OffersComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        OffersScreen(title)
    }
}
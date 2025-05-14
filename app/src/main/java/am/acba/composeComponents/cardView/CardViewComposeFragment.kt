package am.acba.composeComponents.cardView

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class CardViewComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        CardViewScreen(title)
    }
}
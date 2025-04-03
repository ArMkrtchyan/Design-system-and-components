package am.acba.composeComponents.cardView

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class CardViewComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        CardViewScreen(title)
    }
}
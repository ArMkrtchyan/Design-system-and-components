package am.acba.composeComponents.cardInput

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class CardInputComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        CardInputScreen(title)
    }
}
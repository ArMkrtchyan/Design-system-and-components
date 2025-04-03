package am.acba.composeComponents.badges

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class BadgesComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        BadgesScreen(title)
    }
}
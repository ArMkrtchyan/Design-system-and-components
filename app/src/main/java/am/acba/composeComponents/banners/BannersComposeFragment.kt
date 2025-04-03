package am.acba.composeComponents.banners

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class BannersComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        BannersScreen(title)
    }
}
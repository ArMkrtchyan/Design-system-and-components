package am.acba.composeComponents.banners

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class BannersComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        BannersScreen(title)
    }
}
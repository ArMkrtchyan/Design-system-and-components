package am.acba.composeComponents.tabLayout

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable


class TabLayoutComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        TabLayoutScreen(title)
    }
}
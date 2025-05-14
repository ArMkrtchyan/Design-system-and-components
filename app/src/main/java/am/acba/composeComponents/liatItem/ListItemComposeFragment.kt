package am.acba.composeComponents.liatItem

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class ListItemComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ListItemScreen(title)
    }
}
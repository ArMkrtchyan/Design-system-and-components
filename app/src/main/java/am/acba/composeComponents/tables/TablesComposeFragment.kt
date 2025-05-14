package am.acba.composeComponents.tables

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class TablesComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        TablesScreen(title)
    }
}
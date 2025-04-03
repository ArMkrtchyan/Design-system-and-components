package am.acba.composeComponents.searchInput

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class SearchInputComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        SearchScreen(title)
    }
}
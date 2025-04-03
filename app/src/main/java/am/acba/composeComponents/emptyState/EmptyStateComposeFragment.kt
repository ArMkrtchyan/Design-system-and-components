package am.acba.composeComponents.emptyState

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class EmptyStateComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        EmptyStateScreen(title)
    }
}
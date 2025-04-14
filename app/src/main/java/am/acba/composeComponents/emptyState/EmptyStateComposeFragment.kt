package am.acba.composeComponents.emptyState

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class EmptyStateComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        EmptyStateScreen(title)
    }
}
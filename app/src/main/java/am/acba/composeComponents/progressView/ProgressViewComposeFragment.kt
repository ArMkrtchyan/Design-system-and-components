package am.acba.composeComponents.progressView

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class ProgressViewComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ProgressViewScreen(title)
    }
}
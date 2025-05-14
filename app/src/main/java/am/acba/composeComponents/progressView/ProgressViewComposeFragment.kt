package am.acba.composeComponents.progressView

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class ProgressViewComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ProgressViewScreen(title)
    }
}
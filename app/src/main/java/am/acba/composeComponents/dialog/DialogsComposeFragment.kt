package am.acba.composeComponents.dialog

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class DialogsComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        DialogsScreen(title)
    }
}
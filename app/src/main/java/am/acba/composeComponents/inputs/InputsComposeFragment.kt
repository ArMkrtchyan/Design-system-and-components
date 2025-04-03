package am.acba.composeComponents.inputs

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class InputsComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        InputsScreen(title)
    }
}
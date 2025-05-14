package am.acba.composeComponents.dropDown

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class DropDownComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        DropDownScreen(title)
    }
}
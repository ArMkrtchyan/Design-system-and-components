package am.acba.composeComponents.pinInput

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class PinInputComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        PinInputScreen(title)
    }
}
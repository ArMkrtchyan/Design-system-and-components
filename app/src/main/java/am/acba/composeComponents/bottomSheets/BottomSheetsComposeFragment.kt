package am.acba.composeComponents.bottomSheets

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class BottomSheetsComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        BottomSheetsScreen(title)
    }
}
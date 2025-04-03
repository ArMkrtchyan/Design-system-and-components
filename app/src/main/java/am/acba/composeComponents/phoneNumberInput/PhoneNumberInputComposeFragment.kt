package am.acba.composeComponents.phoneNumberInput

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class PhoneNumberInputComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        PhoneNumberInputScreen(title)
    }
}
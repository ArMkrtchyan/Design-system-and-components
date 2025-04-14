package am.acba.composeComponents.phoneNumberInput

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class PhoneNumberInputComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        PhoneNumberInputScreen(title)
    }
}
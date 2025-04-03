package am.acba.composeComponents.datePicker

import am.acba.composeComponents.base.BaseComposeFragment
import am.acba.composeComponents.buttons.ButtonsScreen
import androidx.compose.runtime.Composable

class DatePickerComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        DatePickerScreen(title)
    }
}
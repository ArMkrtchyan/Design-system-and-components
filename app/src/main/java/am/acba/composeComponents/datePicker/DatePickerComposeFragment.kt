package am.acba.composeComponents.datePicker

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class DatePickerComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        DatePickerScreen(title)
    }
}
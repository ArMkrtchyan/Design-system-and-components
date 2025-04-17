package am.acba.composeComponents.currencyInput

import am.acba.components.R
import am.acba.components.mainScreen.ComponentTypeEnum
import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class CurrencyInputComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        title = ComponentTypeEnum.values().find { it.navigationId == R.id.currencyInputComposeFragment }?.componentName ?: ""
        CurrencyInputScreen(title)
    }
}
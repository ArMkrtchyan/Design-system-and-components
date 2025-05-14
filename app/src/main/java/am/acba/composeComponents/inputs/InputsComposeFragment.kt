package am.acba.composeComponents.inputs

import am.acba.components.R
import am.acba.components.mainScreen.ComponentTypeEnum
import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class InputsComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        title = ComponentTypeEnum.values().find { it.navigationId == R.id.inputsComposeFragment }?.componentName ?: ""
        InputsScreen(title)
    }
}
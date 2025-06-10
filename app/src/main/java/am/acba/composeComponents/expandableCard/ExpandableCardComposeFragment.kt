package am.acba.composeComponents.expandableCard

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class ExpandableCardComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ExpandableCardScreen(title)
    }
}
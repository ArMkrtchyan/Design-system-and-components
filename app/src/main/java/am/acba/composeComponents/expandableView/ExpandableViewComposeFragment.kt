package am.acba.composeComponents.expandableView

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class ExpandableViewComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        ExpandableViewScreen(title)
    }
}
package am.acba.composeComponents.piechart

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class PieChartComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        PieChartScreen(title)
    }
}
package am.acba.composeComponents.dataTable

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class DataTableComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        DataTableScreen(title)
    }
}
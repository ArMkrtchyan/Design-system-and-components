package am.acba.composeComponents.stockListItem

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable


class StockListItemComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        StockListItemScreen(title)
    }
}
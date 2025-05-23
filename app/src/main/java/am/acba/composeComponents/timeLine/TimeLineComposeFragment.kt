package am.acba.composeComponents.timeLine

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable


class TimeLineComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        TimeLineScreen(title)
    }
}
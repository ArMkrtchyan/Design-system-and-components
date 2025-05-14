package am.acba.composeComponents.quickActionAndAvatar

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class QuickActionAndAvatarComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        QuickActionAndAvatarScreen(title)
    }
}
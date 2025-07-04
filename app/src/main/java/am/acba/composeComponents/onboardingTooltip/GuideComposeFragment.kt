package am.acba.composeComponents.onboardingTooltip

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class GuideComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        GuideScreen(title)
    }
}
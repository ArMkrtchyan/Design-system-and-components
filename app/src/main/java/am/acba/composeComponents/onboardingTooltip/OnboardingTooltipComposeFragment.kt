package am.acba.composeComponents.onboardingTooltip

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable

class OnboardingTooltipComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        TooltipScreen(title)
    }
}
package am.acba.compose.components.guide

import androidx.compose.runtime.Composable

class GuideItem(
    override val title: String = "",
    override val description: String = "",
    override var content: (@Composable () -> Unit)? = null
) : IGuide
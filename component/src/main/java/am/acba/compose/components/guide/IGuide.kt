package am.acba.compose.components.guide

import androidx.compose.runtime.Composable

interface IGuide {
    val title: String
        get() = ""
    val description: String
        get() = ""
    val content: (@Composable () -> Unit)?
        get() = null
}
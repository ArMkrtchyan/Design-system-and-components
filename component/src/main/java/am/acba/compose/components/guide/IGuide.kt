package am.acba.compose.components.guide

import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.runtime.Composable

interface IGuide {
    val title: String
        get() = EMPTY_STRING
    val description: String
        get() = EMPTY_STRING
    val content: (@Composable () -> Unit)?
        get() = null
}
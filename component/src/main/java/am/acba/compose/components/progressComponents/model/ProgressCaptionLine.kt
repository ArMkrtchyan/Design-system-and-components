package am.acba.compose.components.progressComponents.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class ProgressCaptionLine(
    val leading: Triple<String?, Color?, TextStyle?>? = null,
    val trailing: Triple<String?, Color?, TextStyle?>? = null
)
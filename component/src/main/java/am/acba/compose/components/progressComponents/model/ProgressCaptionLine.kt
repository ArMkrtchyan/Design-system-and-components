package am.acba.compose.components.progressComponents.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class ProgressCaptionLine(
    val leading: ProgressCaption? = null,
    val trailing: ProgressCaption? = null
)

data class ProgressCaption(
    val value: String? = null,
    val color: Color? = null,
    val style: TextStyle? = null
)
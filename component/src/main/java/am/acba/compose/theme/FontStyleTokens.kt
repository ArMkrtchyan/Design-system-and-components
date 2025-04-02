package am.acba.compose.theme

import am.acba.component.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

object FontStyleTokens {
    val Bold = Font(R.font.bold, FontWeight.Bold)
    val Light = Font(R.font.light, FontWeight.Light)
    val Medium = Font(R.font.medium, FontWeight.Medium)
    val Normal = Font(R.font.regular, FontWeight.Normal)
}

object FontFamily {
    val DefaultFontFamily = FontFamily(FontStyleTokens.Bold, FontStyleTokens.Light, FontStyleTokens.Medium, FontStyleTokens.Normal)
}
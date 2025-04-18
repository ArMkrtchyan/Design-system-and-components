package am.acba.compose.components.divider

import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryDivider(
    text: String? = null,
    textColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    style: TextStyle = DigitalTheme.typography.smallRegular,
    modifyTextAllCaps: Boolean = true,
    dividerColor: Color = DigitalTheme.colorScheme.borderPrimaryTonal1,
    thickness: Dp = 1.dp,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = dividerColor, thickness = thickness)
        text?.let {
            val modifiedText = if (modifyTextAllCaps) text.uppercase() else text
            Spacer(Modifier.width(8.dp))
            PrimaryText(modifiedText, style = style, color = textColor)
            Spacer(Modifier.width(8.dp))
            HorizontalDivider(modifier = Modifier.weight(1f), color = dividerColor, thickness = thickness)
        }
    }
}

@Composable
@PreviewLightDark
fun PrimaryDividerPreview() {
    DigitalTheme {
        PrimaryDivider(text = "Primary Divider")
    }
}
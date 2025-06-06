﻿package am.acba.compose.components.divider

import am.acba.compose.HorizontalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.layout.Row
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
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = dividerColor, thickness = thickness)
        text?.let {
            val modifiedText = if (modifyTextAllCaps) text.uppercase() else text
            HorizontalSpacer(8)
            PrimaryText(modifiedText, style = style, color = textColor)
            HorizontalSpacer(8)
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
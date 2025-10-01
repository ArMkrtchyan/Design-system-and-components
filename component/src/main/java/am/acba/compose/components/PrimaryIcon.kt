package am.acba.compose.components

import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun PrimaryIcon(painter: Painter, tint: Color = DigitalTheme.colorScheme.contentPrimary, modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier
            .wrapContentSize(),
        painter = painter,
        contentDescription = "primary_icon",
        tint = tint,
    )
}
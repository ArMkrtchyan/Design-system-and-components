package am.acba.compose.components

import am.acba.component.R
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryListItem(
    startIconPainter: Painter? = null,
    startIconTint: Color = Color.Transparent,
    endIconPainter: Painter? = null,
    endIconTint: Color = Color.Transparent,
    title: String = "",
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(ShapeTokens.shapePrimaryButton)
            .background(DigitalTheme.colorScheme.backgroundTonal1)
            .padding(16.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Box(Modifier.align(Alignment.TopStart)) {
            startIconPainter?.let { PrimaryIcon(painter = startIconPainter, tint = startIconTint) }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .align(Alignment.Center)
        ) {
            PrimaryText(text = title, style = DigitalTheme.typography.body1Bold)
        }
        Box(Modifier.align(Alignment.TopEnd)) {
            endIconPainter?.let { PrimaryIcon(painter = endIconPainter, tint = endIconTint) }
        }
    }
}

@Composable
@PreviewLightDark
fun PrimaryListItemPreview() {
    DigitalTheme {
        PrimaryListItem(
            startIconPainter = painterResource(R.drawable.ic_user),
            startIconTint = DigitalTheme.colorScheme.contentBrand,
            endIconPainter = painterResource(R.drawable.ic_right),
            endIconTint = DigitalTheme.colorScheme.contentPrimaryTonal1,
            title = "djcknsdkjbncsdbcsdbcjhds"
        )
    }
}
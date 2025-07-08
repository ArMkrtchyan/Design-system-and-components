package am.acba.compose.components

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun StatusBadge(
    modifier: Modifier = Modifier,
    title: String = "",
    icon: Int? = null,
    backgroundColor: Color = DigitalTheme.colorScheme.borderNeutral,
    iconColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    textColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    align: Alignment = Alignment.TopEnd,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = ShapeTokens.shapeStatus)
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .align(align),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                PrimaryIcon(modifier = Modifier.size(14.dp), painter = painterResource(icon), tint = iconColor)
                HorizontalSpacer(4)
            }
            PrimaryText(text = title, color = textColor, style = DigitalTheme.typography.xSmallRegular, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
@PreviewLightDark
fun StatusBadgePreview() {
    DigitalTheme {
        StatusBadge(title = "jdscnkjdsc kjdbcnjkds cjksdncjkna", icon = R.drawable.ic_user)
    }
}
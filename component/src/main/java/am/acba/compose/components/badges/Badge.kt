package am.acba.compose.components.badges

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    badgeType: BadgeEnum = BadgeEnum.INFO,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    badgeBorderColor: Color = DigitalTheme.colorScheme.borderSecondary,
    iconColor: Color = DigitalTheme.colorScheme.contentSecondary,
    textColor: Color = DigitalTheme.colorScheme.contentSecondary,
    text: String? = null,
    icon: Int? = null,
) {
    when (badgeType) {
        BadgeEnum.DOT -> Dot(modifier = modifier, backgroundColor = backgroundColor, borderColor = badgeBorderColor)
        BadgeEnum.ICON -> BadgeIcon(modifier = modifier, icon = icon, backgroundColor = backgroundColor, iconColor = iconColor)
        BadgeEnum.NUMBER -> BadgeNumber(modifier = modifier, text = text, backgroundColor = backgroundColor, textColor = textColor)
        BadgeEnum.INFO -> BadgeTextAndIcon(modifier = modifier, icon = icon, text = text, backgroundColor = backgroundColor, textColor = textColor)
        BadgeEnum.NONE -> Unit
    }
}

@Composable
private fun Dot(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    borderColor: Color,
) {
    Box(
        modifier = modifier
            .width(10.dp)
            .height(10.dp)
            .background(backgroundColor, ShapeTokens.shapeRound)
            .border(1.dp, borderColor, ShapeTokens.shapeRound)
    )
}

@Composable
private fun BadgeIcon(
    modifier: Modifier = Modifier,
    iconColor: Color,
    backgroundColor: Color,
    @DrawableRes icon: Int? = null,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(backgroundColor)
    ) {
        PrimaryIcon(
            painter = painterResource(icon ?: R.drawable.default_icon),
            tint = iconColor,
            modifier = modifier
                .width(12.dp)
                .height(12.dp)
                .padding(2.dp),
        )
    }
}

@Composable
private fun BadgeNumber(
    modifier: Modifier = Modifier,
    text: String? = null,
    textColor: Color,
    backgroundColor: Color,
) {
    Box(
        modifier = Modifier
            .background(backgroundColor, ShapeTokens.shapeBadge)
    ) {
        BadgeText(modifier.padding(horizontal = 4.dp), text ?: "", textColor)
    }
}

@Composable
private fun BadgeTextAndIcon(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    icon: Int? = null,
    text: String? = null,
    textColor: Color,
    backgroundColor: Color,
) {
    Row(
        modifier = modifier
            .background(backgroundColor, ShapeTokens.shapeBadge)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            if (icon > 0) {
                PrimaryIcon(
                    painter = painterResource(icon),
                    tint = textColor,
                    modifier = iconModifier
                        .width(16.dp)
                        .height(16.dp),
                )
                HorizontalSpacer(width = 4)
            }
        }
        BadgeText(textModifier, text ?: "", textColor)
    }
}

@Composable
private fun BadgeText(modifier: Modifier, text: String, textColor: Color) {
    PrimaryText(
        text = text,
        color = textColor,
        style = DigitalTheme.typography.smallRegular,
        modifier = modifier
    )
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            Badge(badgeType = BadgeEnum.DOT, backgroundColor = DigitalTheme.colorScheme.backgroundWarning)
            VerticalSpacer(16)
            Badge(
                badgeType = BadgeEnum.ICON, modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(4.dp), icon = R.drawable.ic_edit
            )
            VerticalSpacer(16)
            Badge(badgeType = BadgeEnum.ICON)
            VerticalSpacer(16)
            Badge(badgeType = BadgeEnum.NUMBER, text = "2")
            VerticalSpacer(16)
            Badge(badgeType = BadgeEnum.INFO, text = "Badge")
            VerticalSpacer(16)
            Badge(badgeType = BadgeEnum.INFO, icon = R.drawable.ic_info, text = "Info text")
            VerticalSpacer(16)
        }
    }
}
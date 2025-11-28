package am.acba.compose.components.accordion

import am.acba.component.R
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.avatar.Avatar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.avatar.AvatarSizeEnum
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.id
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Accordion(
    modifier: Modifier = Modifier,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal1,
    backgroundRadius: Int = 12,

    title: String,
    titleColor: Color = DigitalTheme.colorScheme.contentPrimary,
    titleStyle: TextStyle = DigitalTheme.typography.smallRegular,
    titleMaxLines: Int = 1,

    endText: String? = null,
    endTextColor: Color = DigitalTheme.colorScheme.contentPrimary,
    endTextStyle: TextStyle = DigitalTheme.typography.body2Bold,

    currency: String? = null,
    currencyColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    currencyStyle: TextStyle = DigitalTheme.typography.body2Regular,

    endIcon: Int? = R.drawable.ic_down,
    endIconColor: Color = DigitalTheme.colorScheme.contentPrimary,

    avatarBackgroundModifier: Modifier = Modifier,
    avatarBadgeModifier: Modifier = Modifier,
    avatarContentModifier: Modifier = Modifier,
    avatarBackgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal2,
    avatarBackgroundRadius: Int = 4,
    avatarIcon: Int? = null,
    avatarIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    avatarIconPadding: Dp = 5.dp,
    avatarImageUrl: String? = null,
    avatarClipPercent: Int = 0,
    avatarImageCornerRadius: Int? = null,
    avatarContentScale: ContentScale = ContentScale.Crop,
    avatarText: String? = null,
    avatarTextColor: Color = DigitalTheme.colorScheme.contentSecondary,
    avatarBadgeType: BadgeEnum = BadgeEnum.NONE,
    avatarBadgeIcon: Int? = null,
    avatarBadgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    avatarBadgeIconColor: Color = DigitalTheme.colorScheme.contentSecondary,
    avatarBadgeBorderColor: Color = DigitalTheme.colorScheme.borderSecondary,

    showDivider: Boolean = false,

    expanded: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null
) {
    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "accordion-arrow",
        animationSpec = tween(
            easing = LinearOutSlowInEasing
        )
    )
    Column(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(backgroundRadius.dp))
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .clickable {
                onClick?.invoke()
            }
    ) {
        val showStartAvatar = avatarIcon != null || avatarImageUrl != null || avatarText != null
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showStartAvatar) {
                Avatar(
                    backgroundModifier = Modifier
                        .id("accordionAvatar")
                        .then(avatarBackgroundModifier),
                    badgeModifier = avatarBadgeModifier,
                    contentModifier = avatarContentModifier,
                    avatarType = AvatarEnum.ICON,
                    avatarSize = AvatarSizeEnum.AVATAR_SIZE_24,
                    backgroundColor = avatarBackgroundColor,
                    backgroundRadius = avatarBackgroundRadius,
                    icon = avatarIcon,
                    iconColor = avatarIconColor,
                    iconPadding = avatarIconPadding,
                    imageUrl = avatarImageUrl,
                    clipPercent = avatarClipPercent,
                    imageCornerRadius = avatarImageCornerRadius,
                    contentScale = avatarContentScale,
                    text = avatarText,
                    textColor = avatarTextColor,
                    badgeType = avatarBadgeType,
                    badgeIcon = avatarBadgeIcon,
                    badgeBackgroundColor = avatarBadgeBackgroundColor,
                    badgeIconColor = avatarBadgeIconColor,
                    badgeBorderColor = avatarBadgeBorderColor,
                )
                HorizontalSpacer(16.dp)
            }
            PrimaryText(text = title, style = titleStyle, color = titleColor, modifier = Modifier
                .weight(1f)
                .id("startText"), maxLines = titleMaxLines
            )
            Row(
                modifier = Modifier
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!endText.isNullOrEmpty() || !currency.isNullOrEmpty()) {
                    HorizontalSpacer(8.dp)
                }
                if (!endText.isNullOrEmpty()) {
                    PrimaryText(modifier = Modifier.id("endText"), text = endText, style = endTextStyle, color = endTextColor, maxLines = 1)
                }
                if (!endText.isNullOrEmpty() && !currency.isNullOrEmpty()) {
                    HorizontalSpacer(2.dp)
                }
                if (!currency.isNullOrEmpty()) {
                    PrimaryText(modifier = Modifier.id("currency"),text = currency, style = currencyStyle, color = currencyColor, maxLines = 1)
                }
                if (endIcon != null) {
                    HorizontalSpacer(8.dp)
                    PrimaryIcon(painter = painterResource(endIcon), modifier = Modifier
                        .rotate(arrowRotation)
                        .id("endIcon"), tint = endIconColor)
                }
            }
        }
        content?.let {
            val enterTransition = remember {
                expandVertically(
                    expandFrom = Alignment.Top,
                    animationSpec = tween(
                        easing = LinearOutSlowInEasing
                    )
                )
            }
            val exitTransition = remember {
                shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = tween(
                        easing = LinearOutSlowInEasing
                    )
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = enterTransition,
                exit = exitTransition
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    VerticalSpacer(12.dp)
                    it.invoke()
                }
            }

        }
        VerticalSpacer(16.dp)
        if (showDivider) {
            PrimaryDivider()
        }
    }
}

@Composable
@PreviewLightDark
fun AccordionPreview() {
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            val expanded = remember { mutableStateOf(true) }
            Accordion(
                avatarIcon = R.drawable.ic_income,
                title = "Փոխանցում հաշվին ",
                expanded = expanded.value,
                onClick = { expanded.value = !expanded.value }) {
                PrimaryText(text = "ՊՔ սպասարկման միջն./վճ. գանձում")
            }
        }
    }
}


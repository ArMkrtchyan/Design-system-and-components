package am.acba.compose.components.accordion

import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.avatar.Avatar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.avatar.AvatarSizeEnum
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Accordion(
    modifier: Modifier = Modifier,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal1,
    backgroundRadius: Int = 12,

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

    expanded: MutableState<Boolean> = mutableStateOf(false),
    onClick: (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null
) {
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
            verticalAlignment = Alignment.Top
        ) {
            if (showStartAvatar) {
                Box(

                ) {
                    Avatar(
                        backgroundModifier = avatarBackgroundModifier,
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
                }
                HorizontalSpacer(16)
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
                visible = expanded.value,
                enter = enterTransition,
                exit = exitTransition
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    VerticalSpacer(12)
                    it.invoke()
                }
            }

        }
        VerticalSpacer(16)
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
            Accordion(avatarIcon = am.acba.component.R.drawable.ic_income, expanded = expanded, onClick = { expanded.value = !expanded.value }) {
                PrimaryText(text = "hcdujbjhdscb dsjchbjhdsbc jdshbcjhds dshjiubcsd cjhbds jchsdb cjhbsd chjsd jh")
            }
        }
    }
}


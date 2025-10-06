package am.acba.compose.components.chips

import am.acba.component.R
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.avatar.Avatar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.avatar.AvatarSizeEnum
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryChip(
    modifier: Modifier = Modifier,
    chipSizeEnum: ChipSizeEnum = ChipSizeEnum.SMALL,
    chipStateEnum: ChipStateEnum = ChipStateEnum.NOT_SELECTED,
    icon: Int? = null,
    iconStartPadding: Dp? = null,
    iconColor: Color? = null,
    imageRes: Int? = null,
    imageUrl: String? = null,
    clipPercent: Int = 0,
    contentScale: ContentScale = ContentScale.Crop,
    badgeType: BadgeEnum = BadgeEnum.NONE,
    badgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    title: String = "",
    endIcon: Int? = null,
    onEndIconClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    ChipContent(
        modifier = modifier,
        chipSizeEnum = chipSizeEnum,
        chipStateEnum = chipStateEnum,
        icon = icon,
        iconStartPadding = iconStartPadding,
        imageRes = imageRes,
        imageUrl = imageUrl,
        title = title,
        endIcon = endIcon,
        onEndIconClick = onEndIconClick,
        onClick = onClick
    ) { avatarType, avatarSize ->
        Avatar(
            avatarType = avatarType,
            avatarSize = avatarSize,
            icon = icon ?: imageRes,
            iconColor = iconColor,
            imageUrl = imageUrl,
            clipPercent = clipPercent,
            contentScale = contentScale,
            badgeBackgroundColor = badgeBackgroundColor,
            badgeType = if (badgeType == BadgeEnum.DOT) BadgeEnum.DOT else BadgeEnum.NONE,
            badgeBorderColor = DigitalTheme.colorScheme.borderSecondary
        )
    }
}


@Composable
fun PrimaryChip(
    modifier: Modifier = Modifier,
    chipSizeEnum: ChipSizeEnum = ChipSizeEnum.SMALL,
    chipStateEnum: ChipStateEnum = ChipStateEnum.NOT_SELECTED,
    icon: Int? = null,
    iconStartPadding: Dp? = null,
    imageRes: Int? = null,
    imageUrl: String? = null,
    clipPercent: Int = 0,
    contentScale: ContentScale = ContentScale.Crop,
    badgeType: BadgeEnum = BadgeEnum.NONE,
    badgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    title: String = "",
    endIcon: Int? = null,
    onEndIconClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    ChipContent(
        modifier = modifier,
        chipSizeEnum = chipSizeEnum,
        chipStateEnum = chipStateEnum,
        icon = icon,
        iconStartPadding = iconStartPadding,
        imageRes = imageRes,
        imageUrl = imageUrl,
        title = title,
        endIcon = endIcon,
        onEndIconClick = onEndIconClick,
        onClick = onClick
    ) { avatarType, avatarSize ->
        Avatar(
            avatarType = avatarType,
            avatarSize = avatarSize,
            icon = icon ?: imageRes,
            iconColor = chipStateEnum.getContentColor(),
            imageUrl = imageUrl,
            clipPercent = clipPercent,
            contentScale = contentScale,
            badgeBackgroundColor = badgeBackgroundColor,
            badgeType = if (badgeType == BadgeEnum.DOT) BadgeEnum.DOT else BadgeEnum.NONE,
            badgeBorderColor = DigitalTheme.colorScheme.borderSecondary
        )
    }
}

@Composable
private fun ChipContent(
    modifier: Modifier = Modifier,
    chipSizeEnum: ChipSizeEnum = ChipSizeEnum.SMALL,
    chipStateEnum: ChipStateEnum = ChipStateEnum.NOT_SELECTED,
    icon: Int? = null,
    iconStartPadding: Dp? = null,
    imageRes: Int? = null,
    imageUrl: String? = null,
    title: String = "",
    endIcon: Int? = null,
    onEndIconClick: () -> Unit = {},
    onClick: () -> Unit = {},
    avatarContent: @Composable (avatarType: AvatarEnum, avatarSize: AvatarSizeEnum) -> Unit,
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .height(chipSizeEnum.height)
            .widthIn(min = chipSizeEnum.minWidth)
            .background(chipStateEnum.getBackgroundColor(), ShapeTokens.shapeRound)
            .clickable {
                onClick.invoke()
            }
            .padding(horizontal = chipSizeEnum.paddingHorizontal),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null || imageUrl != null || imageRes != null) {
            var avatarType = AvatarEnum.IMAGE
            var avatarSize = chipSizeEnum.avatarSizeForImage
            Row(Modifier) {
                if (icon != null) {
                    HorizontalSpacer(chipSizeEnum.avatarSpacerWidth)
                    avatarType = AvatarEnum.ICON
                    avatarSize = chipSizeEnum.avatarSizeForIcon
                } else {
                    if (iconStartPadding != null) HorizontalSpacer(iconStartPadding)
                }
                avatarContent(avatarType, avatarSize)
            }
        }
        HorizontalSpacer(iconStartPadding ?: 8.dp)
        PrimaryText(
            text = title,
            style = DigitalTheme.typography.body2Regular,
            color = chipStateEnum.getContentColor()
        )
        if (endIcon == null) {
            HorizontalSpacer(8.dp)
        } else {
            Box(
                Modifier
                    .fillMaxHeight()
                    .clickable { onEndIconClick.invoke() }) {
                PrimaryIcon(
                    painterResource(endIcon), modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(chipSizeEnum.endIconSize)
                        .align(Alignment.Center), tint = chipStateEnum.getContentColor()
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun PrimaryChipPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            PrimaryChip(
                title = "Chi"
            )
            VerticalSpacer(20.dp)
            PrimaryChip(
                chipSizeEnum = ChipSizeEnum.LARGE,
                title = "Chip component"
            )
            VerticalSpacer(20.dp)
            PrimaryChip(
                title = "Chip component",
                endIcon = R.drawable.ic_down
            )
            VerticalSpacer(20.dp)
            PrimaryChip(
                chipSizeEnum = ChipSizeEnum.LARGE,
                title = "Chip component",
                endIcon = R.drawable.ic_down
            )
            VerticalSpacer(20.dp)
            PrimaryChip(
                chipStateEnum = ChipStateEnum.SELECTED,
                title = "Chip component"
            )
            VerticalSpacer(20.dp)
            PrimaryChip(
                chipStateEnum = ChipStateEnum.SELECTED,
                chipSizeEnum = ChipSizeEnum.LARGE,
                title = "Chip component"
            )
            VerticalSpacer(20.dp)
            PrimaryChip(
                chipStateEnum = ChipStateEnum.SELECTED,
                title = "Chip component",
                endIcon = R.drawable.ic_down,
                icon = R.drawable.ic_camera
            )
            VerticalSpacer(20.dp)
            PrimaryChip(
                chipStateEnum = ChipStateEnum.SELECTED,
                chipSizeEnum = ChipSizeEnum.LARGE,
                title = "Chip component",
                endIcon = R.drawable.ic_down,
                imageRes = R.drawable.default_avatar,
                badgeType = BadgeEnum.DOT
            )
            VerticalSpacer(20.dp)
        }
    }
}
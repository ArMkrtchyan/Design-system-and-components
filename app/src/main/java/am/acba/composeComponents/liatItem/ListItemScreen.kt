package am.acba.composeComponents.liatItem

import am.acba.component.R
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.components.listItem.ControllerTypeEnum
import am.acba.compose.components.listItem.ListItem
import am.acba.compose.components.listItem.ListItemStartAvatarSizeEnum
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemScreen(title: String = "") {
    Box(
        modifier = Modifier
            .background(DigitalTheme.colorScheme.backgroundBase)
            .fillMaxSize()
            .padding(
                bottom = TopAppBarDefaults.windowInsets
                    .only(WindowInsetsSides.Bottom)
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
    ) {
        Column(Modifier.fillMaxSize()) {
            PrimaryToolbar(title = title, actions = {
                IconButton(onClick = {

                }) {

                }
            })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                ListItemWithDivider(
                    title = "djcknsdkjbncsdbcsdbcjhds"
                )
                ListItemWithDivider(
                    title = "djcknsdkjbncsdbcsdbcjhds",
                    endIcon = R.drawable.ic_right
                )
                ListItemWithDivider(
                    title = "djcknsdkjbncsdbcsdbcjhds",
                    description = "dsicndskk jdkj cnk jdsn",
                    endIcon = R.drawable.ic_right
                )
                ListItemWithDivider(
                    title = "djcknsdkjbncsdbcsdbcjhds",
                    avatarIcon = R.drawable.ic_info
                )
                ListItemWithDivider(
                    title = "djcknsdkjbncsdbcsdbcjhds",
                    avatarIcon = R.drawable.ic_info,
                    endIcon = R.drawable.ic_right
                )
                ListItemWithDivider(
                    title = "djcknsdkjbncsdbcsdbcjhds",
                    avatarIcon = R.drawable.ic_info,
                    description = "dsicndskk jdkj cnk jdsn",
                    endIcon = R.drawable.ic_right
                )
                ListItemWithDivider(
                    title = "djcknsdkjbncsdbcsdbcjhds",
                    avatarIcon = R.drawable.ic_info,
                    listItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.ICON_WITH_BACKGROUND,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
                    avatarIconPadding = 8.dp,
                    avatarBackgroundRadius = 8,
                    endIcon = R.drawable.ic_right
                )
                ListItemWithDivider(
                    title = "Supporting line text lorem  Supporting line text lorem  Supporting line text lorem ",
                    avatarType = AvatarEnum.IMAGE,
                    listItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.AVATAR,
                    avatarIcon = R.drawable.default_avatar,
                    avatarBadgeType = BadgeEnum.ICON,
                    avatarBadgeIcon = R.drawable.ic_camera,
                    avatarBadgeIconColor = DigitalTheme.colorScheme.contentBrand,
                    avatarBadgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
                    endIcon = R.drawable.ic_right
                )
                ListItemWithDivider(
                    title = "Supporting line text lorem  Supporting line text lorem  Supporting line text lorem ",
                    avatarType = AvatarEnum.IMAGE,
                    listItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.AVATAR,
                    avatarIcon = R.drawable.default_avatar,
                    avatarBadgeType = BadgeEnum.ICON,
                    avatarBadgeIcon = R.drawable.ic_camera,
                    avatarBadgeIconColor = DigitalTheme.colorScheme.contentBrand,
                    avatarBadgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
                    endIcon = R.drawable.ic_right,
                    badgeType = BadgeEnum.INFO,
                    badgeBackgroundColor = DigitalTheme.colorScheme.backgroundWarning,
                    badgeText = "New",
                    badgeTextColor = DigitalTheme.colorScheme.contentWarning
                )
                ListItemWithDivider(
                    title = "Supporting line text lorem  Supporting line text lorem  Supporting line text lorem ",
                    avatarIcon = R.drawable.ic_info,
                    listItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.ICON_WITH_BACKGROUND,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
                    avatarIconPadding = 8.dp,
                    avatarBackgroundRadius = 8,
                    endIcon = R.drawable.ic_warning,
                    endIconSecond = R.drawable.ic_right
                )

                ListItemWithDivider(
                    title = "Supporting line text lorem  Supporting line text lorem  Supporting line text lorem ",
                    avatarIcon = R.drawable.ic_info,
                    listItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.ICON_WITH_BACKGROUND,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
                    avatarIconPadding = 8.dp,
                    avatarBackgroundRadius = 8,
                    endIcon = R.drawable.ic_warning,
                    endIconBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
                    endIconPadding = 5,
                    endIconSecond = R.drawable.ic_right
                )

                ListItemWithDivider(
                    title = "Supporting line ",
                    avatarIcon = R.drawable.ic_info,
                    listItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.ICON_WITH_BACKGROUND,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
                    avatarIconPadding = 8.dp,
                    avatarBackgroundRadius = 8,
                    endIcon = R.drawable.ic_warning,
                    endIconBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
                    endIconPadding = 5,
                    endIconSecond = R.drawable.ic_right
                )
                ListItemWithDivider(
                    title = "Supporting line ",
                    avatarIcon = R.drawable.ic_info,
                    listItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.ICON_WITH_BACKGROUND,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
                    avatarIconPadding = 8.dp,
                    avatarBackgroundRadius = 8,
                    controllerType = ControllerTypeEnum.CHECK_BOX
                )
                ListItemWithDivider(
                    title = "Supporting line ",
                    avatarIcon = R.drawable.ic_info,
                    listItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.ICON_WITH_BACKGROUND,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
                    avatarIconPadding = 8.dp,
                    avatarBackgroundRadius = 8,
                    controllerType = ControllerTypeEnum.SWITCH
                )
                ListItemWithDivider(
                    title = "Supporting line ",
                    avatarIcon = R.drawable.ic_info,
                    listItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.ICON_WITH_BACKGROUND,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
                    avatarIconPadding = 8.dp,
                    avatarBackgroundRadius = 8,
                    controllerType = ControllerTypeEnum.RADIO_BUTTON
                )
            }
        }
    }
}

@Composable
private fun ListItemWithDivider(
    dividerTitle: String? = null,
    modifier: Modifier = Modifier,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal1,
    backgroundRadius: Int = 12,

    showBorder: Boolean = false,
    borderColor: Color = DigitalTheme.colorScheme.borderPrimaryTonal1,
    borderRadius: Int = 12,

    endIcon: Int? = null,
    endIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    endIconPadding: Int = 0,
    endIconBackgroundColor: Color = Color.Transparent,
    endIconBackgroundRadius: Int = 4,

    endIconSecond: Int? = null,
    endIconSecondColor: Color = DigitalTheme.colorScheme.contentPrimary,
    endIconSecondPadding: Int = 0,
    endIconSecondBackgroundColor: Color = Color.Transparent,
    endIconSecondBackgroundRadius: Int = 4,

    title: String = "",
    titleMaxLines: Int = 2,
    titleColor: Color = DigitalTheme.colorScheme.contentPrimary,
    titleStyle: TextStyle = DigitalTheme.typography.body1Bold,

    description: String = "",
    descriptionMaxLines: Int = 4,
    descriptionColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    descriptionStyle: TextStyle = DigitalTheme.typography.body2Regular,

    description2: String = "",
    description2MaxLines: Int = 3,
    description3: String = "",
    description3MaxLines: Int = 3,
    description4: String = "",
    description4MaxLines: Int = 2,

    avatarBackgroundModifier: Modifier = Modifier,
    avatarBadgeModifier: Modifier = Modifier,
    avatarContentModifier: Modifier = Modifier,
    avatarType: AvatarEnum = AvatarEnum.ICON,
    listItemStartAvatarSizeEnum: ListItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.ICON,
    avatarBackgroundColor: Color = Color.Transparent,
    avatarBackgroundRadius: Int = 0,
    avatarIcon: Int? = null,
    avatarIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    avatarIconPadding: Dp = Dp.Unspecified,
    avatarImageUrl: String? = null,
    avatarClipPercent: Int = 0,
    avatarImageCornerRadius: Int? = null,
    avatarContentScale: ContentScale = ContentScale.Crop,
    avatarText: String? = null,
    avatarTextColor: Color = DigitalTheme.colorScheme.contentSecondary,
    avatarBadgeType: BadgeEnum = BadgeEnum.NONE,
    avatarBadgeIcon: Int? = null,
    avatarBadgeBackgroundColor: Color = Color.Transparent,
    avatarBadgeIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    avatarBadgeBorderColor: Color = Color.Transparent,

    badgeType: BadgeEnum = BadgeEnum.NONE,
    badgeIcon: Int? = null,
    badgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    badgeIconColor: Color = DigitalTheme.colorScheme.contentSecondary,
    badgeBorderColor: Color = DigitalTheme.colorScheme.borderSecondary,
    badgeModifier: Modifier = Modifier,
    badgeTextColor: Color = DigitalTheme.colorScheme.contentSecondary,
    badgeText: String? = null,

    showDivider: Boolean = false,

    controllerType: ControllerTypeEnum = ControllerTypeEnum.NONE,

    onCheckedChangeListener: (Boolean) -> Unit = {},
    onClick: () -> Unit = {}
) {
    PrimaryDivider()
    VerticalSpacer(20)
    ListItem(
        modifier = modifier,
        backgroundColor = backgroundColor,
        backgroundRadius = backgroundRadius,

        showBorder = showBorder,
        borderColor = borderColor,
        borderRadius = borderRadius,
        endIcon = endIcon,
        endIconColor = endIconColor,
        endIconPadding = endIconPadding,
        endIconBackgroundColor = endIconBackgroundColor,
        endIconBackgroundRadius = endIconBackgroundRadius,
        endIconSecond = endIconSecond,
        endIconSecondColor = endIconSecondColor,
        endIconSecondPadding = endIconSecondPadding,
        endIconSecondBackgroundColor = endIconSecondBackgroundColor,
        endIconSecondBackgroundRadius = endIconSecondBackgroundRadius,
        title = title,
        titleMaxLines = titleMaxLines,
        titleColor = titleColor,
        titleStyle = titleStyle,
        description = description,
        descriptionMaxLines = descriptionMaxLines,
        descriptionColor = descriptionColor,
        descriptionStyle = descriptionStyle,
        description2 = description2,
        description2MaxLines = description2MaxLines,
        description3 = description3,
        description3MaxLines = description3MaxLines,
        description4 = description4,
        description4MaxLines = description4MaxLines,
        avatarBackgroundModifier = avatarBackgroundModifier,
        avatarBadgeModifier = avatarBadgeModifier,
        avatarContentModifier = avatarContentModifier,
        avatarType = avatarType,
        listItemStartAvatarSizeEnum = listItemStartAvatarSizeEnum,
        avatarBackgroundColor = avatarBackgroundColor,
        avatarBackgroundRadius = avatarBackgroundRadius,
        avatarIcon = avatarIcon,
        avatarIconColor = avatarIconColor,
        avatarIconPadding = avatarIconPadding,
        avatarImageUrl = avatarImageUrl,
        avatarClipPercent = avatarClipPercent,
        avatarImageCornerRadius = avatarImageCornerRadius,
        avatarContentScale = avatarContentScale,
        avatarText = avatarText,
        avatarTextColor = avatarTextColor,
        avatarBadgeType = avatarBadgeType,
        avatarBadgeIcon = avatarBadgeIcon,
        avatarBadgeBackgroundColor = avatarBadgeBackgroundColor,
        avatarBadgeIconColor = avatarBadgeIconColor,
        avatarBadgeBorderColor = avatarBadgeBorderColor,
        badgeType = badgeType,
        badgeIcon = badgeIcon,
        badgeBackgroundColor = badgeBackgroundColor,
        badgeIconColor = badgeIconColor,
        badgeBorderColor = badgeBorderColor,
        badgeModifier = badgeModifier,
        badgeTextColor = badgeTextColor,
        badgeText = badgeText,
        showDivider = showDivider,
        controllerType = controllerType,
        onCheckedChangeListener = onCheckedChangeListener,
        onClick = onClick
    )
    VerticalSpacer(20)
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        ListItemScreen()
    }
}
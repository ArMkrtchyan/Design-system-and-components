@file:OptIn(ExperimentalComposeUiApi::class)

package am.acba.compose.components.listItem

import am.acba.component.R
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.StatusBadge
import am.acba.compose.components.avatar.Avatar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.avatar.AvatarSizeEnum
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.controls.PrimaryCheckbox
import am.acba.compose.components.controls.PrimaryRadioButton
import am.acba.compose.components.controls.PrimarySwitch
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.id
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    listItemType: ListItemType = ListItemType.DEFAULT,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal1,
    backgroundRadius: Int = 12,

    showBorder: Boolean = false,
    borderColor: Color = DigitalTheme.colorScheme.borderNeutral,
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
    titleMaxLines: Int? = null,
    titleColor: Color? = null,
    titleStyle: TextStyle? = null,

    description: String = "",
    descriptionMaxLines: Int? = null,
    descriptionColor: Color? = null,
    descriptionStyle: TextStyle? = null,

    description2: String = "",
    description2MaxLines: Int? = null,
    description3: String = "",
    description3MaxLines: Int? = null,
    description4: String = "",

    avatarBackgroundModifier: Modifier = Modifier,
    avatarBadgeModifier: Modifier = Modifier,
    avatarContentModifier: Modifier = Modifier,
    avatarType: AvatarEnum = AvatarEnum.ICON,
    listItemStartAvatarSizeEnum: ListItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.ICON,
    avatarBackgroundColor: Color = Color.Transparent,
    avatarBackgroundRadius: Int = 0,
    avatarIcon: Int? = null,
    avatarIconColor: Color? = DigitalTheme.colorScheme.contentPrimary,
    avatarIconPadding: Dp = Dp.Unspecified,
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
    controllerSelected: Boolean = false,
    controllerEnabled: Boolean = true,
    onRadioButtonClick: () -> Unit = {},
    onCheckedChangeListener: (Boolean) -> Unit = {},

    statusModifier: Modifier = Modifier,
    statusTitle: String? = null,
    statusIcon: Int? = null,
    statusBackgroundColor: Color = DigitalTheme.colorScheme.backgroundPending,
    statusIconColor: Color = DigitalTheme.colorScheme.contentPending,
    statusTextColor: Color = DigitalTheme.colorScheme.contentPending,
    statusAlign: Alignment = Alignment.TopEnd,

    onClick: () -> Unit = {},
    onLongClick: (() -> Unit)? = null,
    onSecondEndIconClick: (() -> Unit)? = null,
    onEndIconClick: (() -> Unit)? = null
) {
    val borderModifier = if (showBorder) {
        Modifier.border(1.dp, borderColor, RoundedCornerShape(borderRadius.dp))
    } else {
        Modifier
    }
    Column(
        modifier = Modifier
            .then(modifier)
            .background(backgroundColor, RoundedCornerShape(backgroundRadius.dp))
            .then(borderModifier)
            .padding(top = 16.dp)
            .combinedClickable(
                onClick = { onClick.invoke() },
                onLongClick = { onLongClick?.invoke() })
    ) {
        val isTitleOnly =
            description.isEmpty() && description2.isEmpty() && description3.isEmpty() && description4.isEmpty()
        val showStartAvatar = avatarIcon != null || avatarImageUrl != null || avatarText != null
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .wrapContentHeight(),
            verticalAlignment = if (isTitleOnly) Alignment.CenterVertically else Alignment.Top
        ) {
            if (showStartAvatar) {
                Box(
                    modifier = Modifier.padding(top = if (isTitleOnly) 0.dp else 4.dp)
                ) {
                    Avatar(
                        backgroundModifier = avatarBackgroundModifier
                            .id("startIcon"),
                        badgeModifier = avatarBadgeModifier,
                        contentModifier = avatarContentModifier,
                        avatarType = avatarType,
                        avatarSize = listItemStartAvatarSizeEnum.avatarSize,
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
                HorizontalSpacer(16.dp)
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = if (isTitleOnly) Alignment.CenterVertically else Alignment.Top
                ) {
                    if (title.isNotEmpty()) {
                        PrimaryText(
                            modifier = Modifier
                                .weight(1f)
                                .align(if (isTitleOnly) Alignment.CenterVertically else Alignment.Top)
                                .id("title"),
                            text = title,
                            color = titleColor ?: listItemType.getTitleTextColor(),
                            style = titleStyle ?: listItemType.getTitleStyle(),
                            maxLines = titleMaxLines ?: listItemType.titleMaxLines,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (badgeType != BadgeEnum.NONE) {
                        HorizontalSpacer(12.dp)
                        Badge(
                            badgeType = badgeType,
                            icon = badgeIcon,
                            backgroundColor = badgeBackgroundColor,
                            iconColor = badgeIconColor,
                            badgeBorderColor = badgeBorderColor,
                            modifier = badgeModifier
                                .id("badge"),
                            text = badgeText,
                            textColor = badgeTextColor
                        )
                    }
                    if (endIcon != null) {
                        HorizontalSpacer(12.dp)
                        val endIconClickableModifier = if (onEndIconClick != null) {
                            Modifier.clickable(onClick = onEndIconClick)
                        } else {
                            Modifier
                        }
                        Avatar(
                            avatarType = AvatarEnum.ICON,
                            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24,
                            backgroundColor = endIconBackgroundColor,
                            backgroundRadius = endIconBackgroundRadius,
                            icon = endIcon,
                            iconColor = endIconColor,
                            iconPadding = endIconPadding.dp,
                            backgroundModifier = Modifier
                                .id("endIcon")
                                .then(endIconClickableModifier)
                        )
                    }
                    if (endIconSecond != null) {
                        HorizontalSpacer(12.dp)
                        val secondEndIconClickableModifier = if (onSecondEndIconClick != null) {
                            Modifier.clickable(onClick = onSecondEndIconClick)
                        } else {
                            Modifier
                        }
                        Avatar(
                            avatarType = AvatarEnum.ICON,
                            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24,
                            backgroundColor = endIconSecondBackgroundColor,
                            backgroundRadius = endIconSecondBackgroundRadius,
                            icon = endIconSecond,
                            iconColor = endIconSecondColor,
                            iconPadding = endIconSecondPadding.dp,
                            backgroundModifier = Modifier
                                .id("endIconSecond")
                                .then(secondEndIconClickableModifier),
                        )
                    }

                    when (controllerType) {
                        ControllerTypeEnum.NONE -> Unit
                        ControllerTypeEnum.CHECK_BOX -> PrimaryCheckbox(
                            enabled = controllerEnabled,
                            state = if (controllerSelected) ToggleableState.On else ToggleableState.Off,
                            onClick = { onCheckedChangeListener.invoke(it == ToggleableState.On) },
                            modifier = Modifier
                                .id("listItemCheckbox")
                        )

                        ControllerTypeEnum.RADIO_BUTTON -> PrimaryRadioButton(
                            enabled = controllerEnabled,
                            selected = controllerSelected,
                            onClick = onRadioButtonClick,
                            modifier = Modifier
                                .id("listItemRadioButton")
                        )

                        ControllerTypeEnum.SWITCH -> PrimarySwitch(
                            enabled = controllerEnabled,
                            checked = controllerSelected,
                            onCheckedChange = { onCheckedChangeListener.invoke(it) },
                            modifier = Modifier
                                .id("listItemSwitcher")
                        )
                    }
                }
                val newDescriptions = arrayListOf(
                    description,
                    description2,
                    description3,
                    description4
                ).filter { it.isNotEmpty() }
                val newDescription =
                    if (newDescriptions.isNotEmpty()) newDescriptions.first() else ""
                val newDescription2 = if (newDescriptions.size > 1) newDescriptions[1] else ""
                val newDescription3 = if (newDescriptions.size > 2) newDescriptions[2] else ""
                val newDescription4 = if (newDescriptions.size > 3) newDescriptions[3] else ""

                val descriptionsList =
                    arrayListOf(newDescription2, newDescription3, newDescription4)
                val newDescriptionMaxLines =
                    if (descriptionMaxLines == null || descriptionMaxLines < 1) null else descriptionMaxLines
                val newDescription2MaxLines =
                    if (description2MaxLines == null || description2MaxLines < 1) null else description2MaxLines
                val newDescription3MaxLines =
                    if (description3MaxLines == null || description3MaxLines < 1) null else description3MaxLines

                val firstDescriptionMaxLines = newDescriptionMaxLines
                    ?: (listItemType.descriptionMaxLines - descriptionsList.count { it.isNotEmpty() })
                val descriptions2List = arrayListOf(newDescription3, newDescription4)
                val secondDescriptionMaxLines =
                    newDescription2MaxLines
                        ?: (listItemType.descriptionMaxLines - firstDescriptionMaxLines - descriptions2List.count { it.isNotEmpty() })
                val descriptions3List = arrayListOf(newDescription4)
                val thirdDescriptionMaxLines = newDescription3MaxLines
                    ?: (listItemType.descriptionMaxLines - (firstDescriptionMaxLines + secondDescriptionMaxLines) - descriptions3List.count { it.isNotEmpty() })

                if (newDescription.isNotEmpty()) {
                    PrimaryText(
                        text = newDescription,
                        color = descriptionColor ?: listItemType.getDescriptionTextColor(),
                        style = descriptionStyle ?: listItemType.getDescriptionStyle(),
                        maxLines = firstDescriptionMaxLines,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .id("description")
                    )
                }
                if (newDescription2.isNotEmpty()) {
                    PrimaryText(
                        text = newDescription2,
                        color = descriptionColor ?: listItemType.getDescriptionTextColor(),
                        style = descriptionStyle ?: listItemType.getDescriptionStyle(),
                        maxLines = secondDescriptionMaxLines,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .id("description2")
                    )
                }
                if (newDescription3.isNotEmpty()) {
                    PrimaryText(
                        text = newDescription3,
                        color = descriptionColor ?: listItemType.getDescriptionTextColor(),
                        style = descriptionStyle ?: listItemType.getDescriptionStyle(),
                        maxLines = thirdDescriptionMaxLines,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .id("description3")
                    )
                }
                if (newDescription4.isNotEmpty()) {
                    PrimaryText(
                        text = newDescription4,
                        color = descriptionColor ?: listItemType.getDescriptionTextColor(),
                        style = descriptionStyle ?: listItemType.getDescriptionStyle(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .id("description4")
                    )
                }
            }
        }
        VerticalSpacer(16.dp)
        if (showDivider) {
            PrimaryDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
        if (!statusTitle.isNullOrEmpty()) {
            StatusBadge(
                title = statusTitle,
                icon = statusIcon,
                textColor = statusTextColor,
                iconColor = statusIconColor,
                backgroundColor = statusBackgroundColor,
                align = statusAlign,
                modifier = statusModifier
            )
        }
    }
}


@ExperimentalComposeUiApi
@Composable
@PreviewLightDark
fun PrimaryListItemPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            ListItem(
                title = "djcknsdkjbncsdbcsdbcjhds",
                avatarIcon = R.drawable.ic_info,
                description = "Description 1 Description 1 Description 1 Description 1 Description 1 Description 1 Description 1 Description 1 ",
                //     description2 = "Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 ",
                //    description3 = "Description 3 Description 3 Description 3 Description 3 Description 3 Description 3 Description 3 Description 3 ",
                description4 = "Description 4 Description 4 Description 4 Description 4 Description 4 Description 4 Description 4 Description 4",
                endIcon = R.drawable.ic_right,
                controllerType = ControllerTypeEnum.CHECK_BOX,
                statusTitle = "dhscbdsjhcbjhsdhcjdsjn",
                showBorder = true
            )
        }
    }
}
package am.acba.compose.components.listItem

import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryText
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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    description4MaxLines: Int? = null,

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

    onCheckedChangeListener: (Boolean) -> Unit = {},
    onClick: () -> Unit = {}
) {
    Column(
        modifier =
        if (showBorder) {
            modifier
                .background(backgroundColor, RoundedCornerShape(backgroundRadius.dp))
                .border(1.dp, borderColor, RoundedCornerShape(borderRadius.dp))
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .clickable { onClick.invoke() }
        } else {
            modifier
                .background(backgroundColor, RoundedCornerShape(backgroundRadius.dp))
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .clickable { onClick.invoke() }
        }
    ) {
        val isTitleOnly = description.isEmpty() && description2.isEmpty() && description3.isEmpty() && description4.isEmpty()
        val showStartAvatar = avatarIcon != null || avatarImageUrl != null || avatarText != null
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = if (isTitleOnly) Alignment.CenterVertically else Alignment.Top
        ) {
            if (showStartAvatar) {
                Box(
                    modifier = Modifier.padding(top = if (isTitleOnly) 0.dp else 4.dp)
                ) {
                    Avatar(
                        backgroundModifier = avatarBackgroundModifier,
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
                HorizontalSpacer(16)
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = if (controllerType == ControllerTypeEnum.SWITCH && isTitleOnly) Alignment.CenterVertically else Alignment.Top
                ) {
                    PrimaryText(
                        modifier = Modifier.weight(1f),
                        text = title,
                        color = titleColor ?: listItemType.getTitleTextColor(),
                        style = titleStyle ?: listItemType.getTitleStyle(),
                        maxLines = titleMaxLines ?: listItemType.titleMaxLines,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (badgeType != BadgeEnum.NONE) {
                        HorizontalSpacer(12)
                        Badge(
                            badgeType = badgeType,
                            icon = badgeIcon,
                            backgroundColor = badgeBackgroundColor,
                            iconColor = badgeIconColor,
                            badgeBorderColor = badgeBorderColor,
                            modifier = badgeModifier,
                            text = badgeText,
                            textColor = badgeTextColor
                        )
                    }
                    if (endIcon != null) {
                        HorizontalSpacer(12)
                        Avatar(
                            avatarType = AvatarEnum.ICON,
                            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24,
                            backgroundColor = endIconBackgroundColor,
                            backgroundRadius = endIconBackgroundRadius,
                            icon = endIcon,
                            iconColor = endIconColor,
                            iconPadding = endIconPadding.dp
                        )
                    }
                    if (endIconSecond != null) {
                        HorizontalSpacer(12)
                        Avatar(
                            avatarType = AvatarEnum.ICON,
                            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24,
                            backgroundColor = endIconSecondBackgroundColor,
                            backgroundRadius = endIconSecondBackgroundRadius,
                            icon = endIconSecond,
                            iconColor = endIconSecondColor,
                            iconPadding = endIconSecondPadding.dp
                        )
                    }
                    val isChecked = remember { mutableStateOf(false) }
                    when (controllerType) {
                        ControllerTypeEnum.NONE -> Unit
                        ControllerTypeEnum.CHECK_BOX -> PrimaryCheckbox(checked = isChecked.value, onCheckedChange = {
                            isChecked.value = it
                            onCheckedChangeListener.invoke(it)
                        })

                        ControllerTypeEnum.RADIO_BUTTON -> PrimaryRadioButton(selected = isChecked.value, onClick = {
                            isChecked.value = true
                            onCheckedChangeListener.invoke(true)
                        })

                        ControllerTypeEnum.SWITCH -> PrimarySwitch(checked = isChecked.value, onCheckedChange = {
                            isChecked.value = it
                            onCheckedChangeListener.invoke(it)
                        })
                    }
                }
                val newDescriptions = arrayListOf(description, description2, description3, description4).filter { it.isNotEmpty() }
                val newDescription = if (newDescriptions.isNotEmpty()) newDescriptions.first() else ""
                val newDescription2 = if (newDescriptions.size > 1) newDescriptions.first() else ""
                val newDescription3 = if (newDescriptions.size > 2) newDescriptions.first() else ""
                val newDescription4 = if (newDescriptions.size > 3) newDescriptions.first() else ""

                val descriptionsList = arrayListOf(newDescription2, newDescription3, newDescription4)
                val firstDescriptionMaxLines = listItemType.descriptionMaxLines - descriptionsList.count { it.isNotEmpty() }
                val descriptions2List = arrayListOf(newDescription3, newDescription4)
                val secondDescriptionMaxLines = listItemType.descriptionMaxLines - firstDescriptionMaxLines - descriptions2List.count { it.isNotEmpty() }
                val descriptions3List = arrayListOf(description4)
                val thirdDescriptionMaxLines = listItemType.descriptionMaxLines - secondDescriptionMaxLines - descriptions3List.count { it.isNotEmpty() }

                if (description.isNotEmpty()) {
                    PrimaryText(
                        text = newDescription,
                        color = descriptionColor ?: listItemType.getDescriptionTextColor(),
                        style = descriptionStyle ?: listItemType.getDescriptionStyle(),
                        maxLines = descriptionMaxLines ?: firstDescriptionMaxLines,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (description2.isNotEmpty()) {
                    PrimaryText(
                        text = newDescription2,
                        color = descriptionColor ?: listItemType.getDescriptionTextColor(),
                        style = descriptionStyle ?: listItemType.getDescriptionStyle(),
                        maxLines = description2MaxLines ?: secondDescriptionMaxLines,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (description3.isNotEmpty()) {
                    PrimaryText(
                        text = newDescription3,
                        color = descriptionColor ?: listItemType.getDescriptionTextColor(),
                        style = descriptionStyle ?: listItemType.getDescriptionStyle(),
                        maxLines = description3MaxLines ?: thirdDescriptionMaxLines,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (description4.isNotEmpty()) {
                    PrimaryText(
                        text = newDescription4,
                        color = descriptionColor ?: listItemType.getDescriptionTextColor(),
                        style = descriptionStyle ?: listItemType.getDescriptionStyle(),
                        maxLines = description4MaxLines ?: 1,
                        overflow = TextOverflow.Ellipsis
                    )
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
fun PrimaryListItemPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            ListItem(
                title = "djcknsdkjbncsdbcsdbcjhds"
            )
        }
    }
}
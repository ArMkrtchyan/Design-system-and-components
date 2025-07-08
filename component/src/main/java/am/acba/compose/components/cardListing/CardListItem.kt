package am.acba.compose.components.cardListing

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.StatusBadge
import am.acba.compose.components.avatar.Avatar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.avatar.AvatarSizeEnum
import am.acba.compose.components.controls.PrimaryCheckbox
import am.acba.compose.components.controls.PrimaryRadioButton
import am.acba.compose.components.controls.PrimarySwitch
import am.acba.compose.components.listItem.ControllerTypeEnum
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardListItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal1,
    backgroundRadius: Int = 12,

    showBorder: Boolean = false,
    borderColor: Color = DigitalTheme.colorScheme.borderNeutral,
    borderRadius: Int = 12,

    endIcon: Int? = null,
    endIconColor: Color = DigitalTheme.colorScheme.contentPrimary,

    startTitle: String = "",
    startTitleColor: Color = DigitalTheme.colorScheme.contentPrimary,
    startTitleStyle: TextStyle = DigitalTheme.typography.smallRegular,

    startDescription: String = "",
    startDescriptionColor: Color = DigitalTheme.colorScheme.contentPrimary,
    startDescriptionStyle: TextStyle = DigitalTheme.typography.body2Regular,


    endTitle: String = "",
    endTitleColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    endTitleStyle: TextStyle = DigitalTheme.typography.xSmallRegular,

    endDescription: String = "",
    endDescriptionColor: Color = DigitalTheme.colorScheme.contentPrimary,
    endDescriptionStyle: TextStyle = DigitalTheme.typography.body2Bold,

    currency: String? = null,
    currencyColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    currencyStyle: TextStyle = DigitalTheme.typography.body2Regular,

    avatarBackgroundModifier: Modifier = Modifier,
    avatarBadgeModifier: Modifier = Modifier,
    avatarContentModifier: Modifier = Modifier,
    avatarType: AvatarEnum = AvatarEnum.ICON,
    avatarBackgroundColor: Color = Color.Transparent,
    avatarBackgroundRadius: Int = 4,
    avatarIcon: Int? = null,
    avatarIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    avatarIconPadding: Dp = Dp.Unspecified,
    avatarImageUrl: String? = null,
    avatarClipPercent: Int = 0,
    avatarImageCornerRadius: Int? = null,
    avatarContentScale: ContentScale = ContentScale.Crop,

    controllerType: ControllerTypeEnum = ControllerTypeEnum.NONE,
    controllerSelected: Boolean = false,
    onRadioButtonClick: () -> Unit = {},
    onCheckedChangeListener: (Boolean) -> Unit = {},

    statusModifier: Modifier = Modifier,
    statusTitle: String? = null,
    statusIcon: Int? = null,
    statusBackgroundColor: Color = DigitalTheme.colorScheme.borderNeutral,
    statusIconColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    statusTextColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    statusAlign: Alignment = Alignment.TopEnd,

    onClick: () -> Unit = {}
) {
    val showStartAvatar = avatarIcon != null || avatarImageUrl != null
    val bgColor = if (selected) {
        DigitalTheme.colorScheme.backgroundTonal2
    } else backgroundColor
    Box(
        modifier =
        if (showBorder) {
            modifier
                .background(bgColor, RoundedCornerShape(backgroundRadius.dp))
                .border(1.dp, borderColor, RoundedCornerShape(borderRadius.dp))
                .padding(top = 16.dp)
                .clickable { onClick.invoke() }
        } else {
            modifier
                .background(bgColor, RoundedCornerShape(backgroundRadius.dp))
                .padding(top = 16.dp)
                .clickable { onClick.invoke() }
        }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                if (showStartAvatar) {
                    Avatar(
                        backgroundModifier = avatarBackgroundModifier,
                        badgeModifier = avatarBadgeModifier,
                        contentModifier = avatarContentModifier,
                        avatarType = avatarType,
                        avatarSize = AvatarSizeEnum.AVATAR_SIZE_36,
                        backgroundColor = avatarBackgroundColor,
                        backgroundRadius = avatarBackgroundRadius,
                        icon = avatarIcon,
                        iconColor = avatarIconColor,
                        iconPadding = avatarIconPadding,
                        imageUrl = avatarImageUrl,
                        clipPercent = avatarClipPercent,
                        imageCornerRadius = avatarImageCornerRadius,
                        contentScale = avatarContentScale,
                    )
                    HorizontalSpacer(16)
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    PrimaryText(
                        text = startTitle,
                        style = startTitleStyle,
                        color = startTitleColor,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    if (startDescription.isNotEmpty()) {
                        VerticalSpacer(2)
                        PrimaryText(
                            text = startDescription,
                            style = startDescriptionStyle,
                            color = startDescriptionColor,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
                HorizontalSpacer(8)
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    if (endTitle.isNotEmpty()) {
                        PrimaryText(
                            text = endTitle,
                            style = endTitleStyle,
                            color = endTitleColor,
                            maxLines = 1
                        )
                    }
                    if (endDescription.isNotEmpty()) {
                        VerticalSpacer(2)
                        Row {
                            PrimaryText(
                                text = endDescription,
                                style = endDescriptionStyle,
                                color = endDescriptionColor,
                                maxLines = 1
                            )
                            if (!currency.isNullOrEmpty()) {
                                HorizontalSpacer(2)
                                PrimaryText(
                                    text = currency,
                                    style = currencyStyle,
                                    color = currencyColor,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
                when (controllerType) {
                    ControllerTypeEnum.NONE -> Unit
                    ControllerTypeEnum.CHECK_BOX -> {
                        HorizontalSpacer(8)
                        PrimaryCheckbox(state = if (controllerSelected) ToggleableState.On else ToggleableState.Off, onClick = {
                            onCheckedChangeListener.invoke(it == ToggleableState.On)
                        })
                    }

                    ControllerTypeEnum.RADIO_BUTTON -> {
                        HorizontalSpacer(8)
                        PrimaryRadioButton(selected = controllerSelected, onClick = onRadioButtonClick)
                    }

                    ControllerTypeEnum.SWITCH -> {
                        HorizontalSpacer(8)
                        PrimarySwitch(checked = controllerSelected, onCheckedChange = {
                            onCheckedChangeListener.invoke(it)
                        })
                    }
                }
                endIcon?.let {
                    HorizontalSpacer(8)
                    PrimaryIcon(painter = painterResource(it), tint = endIconColor, modifier = Modifier.size(24.dp))
                }
            }
            VerticalSpacer(16)
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
}


@Composable
@PreviewLightDark
fun CardListItemPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            CardListItem(
                startTitle = "Դրամային",
                startDescription = "***3562 aaaaaa fddddddfd",
                endTitle = "հասանելի",
                endDescription = "400,000,000.00",
                currency = "AMD",
                endIcon = R.drawable.ic_down,
                statusTitle = "Հօգուտ`Անի Փ, Աստղիկ, Համո, Աննա, Լաուրա, Թիմային...",
                statusIcon = am.acba.component.R.drawable.ic_user
            )
        }
    }
}
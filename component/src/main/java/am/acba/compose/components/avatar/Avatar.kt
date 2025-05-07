package am.acba.compose.components.avatar

import am.acba.component.R
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun ActionButton(
    actionText: String,
    actionTextColor: Color = DigitalTheme.colorScheme.contentSecondary,
    backgroundModifier: Modifier = Modifier,
    badgeModifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    avatarType: AvatarEnum = AvatarEnum.ICON,
    avatarSize: AvatarSizeEnum = AvatarSizeEnum.AVATAR_SIZE_56,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal1,
    backgroundRadius: Int = 100,
    icon: Int? = null,
    iconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    iconPadding: Dp = 16.dp,
    imageUrl: String? = null,
    clipPercent: Int = 0,
    contentScale: ContentScale = ContentScale.Crop,
    text: String? = null,
    textColor: Color = DigitalTheme.colorScheme.contentSecondary,
    badgeType: BadgeEnum = BadgeEnum.NONE,
    badgeIcon: Int? = null,
    badgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    badgeIconColor: Color = DigitalTheme.colorScheme.contentSecondary,
    onActionButtonClick: () -> Unit = {},
    onBadgeClick: () -> Unit = {},
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
        onActionButtonClick.invoke()
    }) {
        Avatar(
            backgroundModifier,
            badgeModifier,
            contentModifier,
            avatarType,
            avatarSize,
            backgroundColor,
            backgroundRadius,
            icon,
            iconColor,
            iconPadding,
            imageUrl,
            clipPercent,
            contentScale,
            text,
            textColor,
            badgeType,
            badgeIcon,
            badgeBackgroundColor,
            badgeIconColor,
            onBadgeClick
        )
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryText(
            text = actionText,
            color = actionTextColor,
            style = DigitalTheme.typography.smallRegular
        )
    }
}

@Composable
fun Avatar(
    backgroundModifier: Modifier = Modifier,
    badgeModifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    avatarType: AvatarEnum = AvatarEnum.ICON,
    avatarSize: AvatarSizeEnum = AvatarSizeEnum.AVATAR_SIZE_24,
    backgroundColor: Color = Color.Transparent,
    backgroundRadius: Int = 0,
    icon: Int? = null,
    iconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    iconPadding: Dp = Dp.Unspecified,
    imageUrl: String? = null,
    clipPercent: Int = 0,
    contentScale: ContentScale = ContentScale.Crop,
    text: String? = null,
    textColor: Color = DigitalTheme.colorScheme.contentSecondary,
    badgeType: BadgeEnum = BadgeEnum.NONE,
    badgeIcon: Int? = null,
    badgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    badgeIconColor: Color = DigitalTheme.colorScheme.contentSecondary,
    onBadgeClick: () -> Unit = {},
) {
    Box(
        modifier = backgroundModifier
            .width(avatarSize.size.dp)
            .height(avatarSize.size.dp)
            .background(backgroundColor, RoundedCornerShape(backgroundRadius.dp)),
        contentAlignment = Alignment.Center
    ) {
        when (avatarType) {
            AvatarEnum.ICON -> AvatarIcon(modifier = contentModifier, icon = icon ?: R.drawable.default_icon, iconColor = iconColor, padding = iconPadding)
            AvatarEnum.IMAGE -> AvatarImage(modifier = contentModifier, imageRes = icon, clipPercent = clipPercent, imageUrl = imageUrl, contentScale = contentScale)
            AvatarEnum.TEXT -> AvatarText(modifier = contentModifier, avatarSize = avatarSize, text = text ?: "", textColor = textColor)
            AvatarEnum.LOTTIE -> AvatarLottie(modifier = contentModifier)
        }
        if (badgeType == BadgeEnum.DOT || badgeType == BadgeEnum.ICON) {
            val badgeSize = when {
                badgeType == BadgeEnum.DOT -> avatarSize.dotBadgeSize.dp
                else -> avatarSize.iconBadgeSize.dp
            }
            val padding = when {
                badgeType == BadgeEnum.DOT -> avatarSize.dotBadgePadding.dp
                else -> avatarSize.iconBadgePadding.dp
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clickable {
                        if (badgeType == BadgeEnum.ICON) {
                            onBadgeClick.invoke()
                        }
                    }
            ) {
                Badge(
                    modifier = badgeModifier
                        .width(badgeSize)
                        .height(badgeSize)
                        .padding(padding),
                    badgeType = badgeType,
                    icon = badgeIcon,
                    backgroundColor = badgeBackgroundColor,
                    iconColor = badgeIconColor
                )
            }

        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun AvatarImage(
    modifier: Modifier = Modifier,
    imageRes: Int? = null,
    imageUrl: String? = null,
    clipPercent: Int = 0,
    contentScale: ContentScale = ContentScale.Crop,
) {
    if (!imageUrl.isNullOrEmpty()) {
        GlideImage(
            model = imageUrl ?: imageRes ?: R.drawable.default_icon, contentDescription = "",
            contentScale = contentScale, modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(clipPercent))
        )
    } else {
        Image(
            painterResource(imageRes ?: R.drawable.default_icon), contentDescription = "",
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(clipPercent)),
            contentScale = contentScale
        )
    }
}

@Composable
private fun AvatarIcon(
    modifier: Modifier = Modifier,
    icon: Int,
    iconColor: Color,
    padding: Dp = Dp.Unspecified,
) {
    Icon(
        painterResource(icon),
        contentDescription = null,
        tint = iconColor,
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
    )
}

@Composable
private fun AvatarText(
    modifier: Modifier = Modifier,
    avatarSize: AvatarSizeEnum = AvatarSizeEnum.AVATAR_SIZE_24,
    text: String,
    textColor: Color
) {
    PrimaryText(
        text = text,
        style = avatarSize.getTextStyle(),
        color = textColor
    )
}

@Composable
private fun AvatarLottie(
    modifier: Modifier = Modifier,
) {
    val preloaderLottieComposition by rememberLottieComposition(LottieCompositionSpec.Asset("check_test.json"))

    LottieAnimation(
        composition = preloaderLottieComposition,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
@PreviewLightDark
fun AvatarScreenPreview() {
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            Avatar(avatarSize = AvatarSizeEnum.AVATAR_SIZE_24)
            Spacer(modifier = Modifier.height(16.dp))
            Avatar(avatarType = AvatarEnum.IMAGE, avatarSize = AvatarSizeEnum.AVATAR_SIZE_32)
            Spacer(modifier = Modifier.height(16.dp))
            Avatar(
                avatarType = AvatarEnum.ICON,
                avatarSize = AvatarSizeEnum.AVATAR_SIZE_56,
                icon = R.drawable.ic_phonebook,
                iconColor = DigitalTheme.colorScheme.contentBrand,
                backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
                backgroundRadius = 12,
                iconPadding = 16.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Avatar(
                avatarType = AvatarEnum.TEXT, avatarSize = AvatarSizeEnum.AVATAR_SIZE_36,
                text = "AA",
                textColor = DigitalTheme.colorScheme.contentAlternative5,
                backgroundColor = DigitalTheme.colorScheme.backgroundAlternative5,
                backgroundRadius = 100,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Avatar(
                avatarType = AvatarEnum.IMAGE,
                avatarSize = AvatarSizeEnum.AVATAR_SIZE_40,
                badgeType = BadgeEnum.DOT,
                icon = R.drawable.logo_amex_light,
                clipPercent = 50
            )
            Spacer(modifier = Modifier.height(16.dp))
            Avatar(avatarType = AvatarEnum.LOTTIE, avatarSize = AvatarSizeEnum.AVATAR_SIZE_56, badgeType = BadgeEnum.DOT)
            Spacer(modifier = Modifier.height(16.dp))
            Avatar(
                avatarSize = AvatarSizeEnum.AVATAR_SIZE_80, icon = R.drawable.ic_phonebook,
                backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
                backgroundRadius = 100,
            )
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(actionText = "Action button")
        }
    }
}
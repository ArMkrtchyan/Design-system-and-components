package am.acba.composeComponents.quickActionAndAvatar

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.avatar.ActionButton
import am.acba.compose.components.avatar.Avatar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.avatar.AvatarSizeEnum
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickActionAndAvatarScreen(title: String = "") {
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
                PrimaryDivider(text = "Avatar icon state")
                VerticalSpacer(16)
                AvatarIconStates()
                VerticalSpacer(16)
                PrimaryDivider(text = "Avatar icon state with square background")
                VerticalSpacer(16)
                AvatarIconWithRoundedStates()
                VerticalSpacer(16)
                PrimaryDivider(text = "Avatar Image state rounded")
                VerticalSpacer(16)
                AvatarImageStates()
                VerticalSpacer(16)
                PrimaryDivider(text = "Avatar Image state not rounded")
                VerticalSpacer(16)
                AvatarImageStates(0)
                VerticalSpacer(16)
                PrimaryDivider(text = "Avatar Text state")
                VerticalSpacer(16)
                AvatarTextStates()
                VerticalSpacer(16)
                PrimaryDivider(text = "Avatar Lottie state")
                VerticalSpacer(16)
                AvatarLottieStates()
                VerticalSpacer(16)
                PrimaryDivider(text = "Action buttons state")
                VerticalSpacer(16)
                ActionButtonStates()
                VerticalSpacer(40)
            }
        }
    }
}

@Composable
private fun AvatarIconStates() {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            iconPadding = 4.dp
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_32, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            iconPadding = 6.dp
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_36, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            iconPadding = 8.dp
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_40, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            iconPadding = 8.dp
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_56, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            badgeType = BadgeEnum.DOT,
            iconPadding = 16.dp
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_80, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            iconPadding = 16.dp, badgeType = BadgeEnum.ICON,
            badgeIcon = R.drawable.ic_camera, badgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
            badgeIconColor = DigitalTheme.colorScheme.contentBrand,
            onBadgeClick = { Toast.makeText(context, "On badge icon click", Toast.LENGTH_SHORT).show() }
        )
        HorizontalSpacer(16)
    }
}

@Composable
private fun AvatarIconWithRoundedStates() {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 4,
            iconPadding = 4.dp
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_32, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 4,
            iconPadding = 6.dp
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_36, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 4,
            iconPadding = 8.dp
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_40, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 6,
            iconPadding = 8.dp
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_56, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 12,
            badgeType = BadgeEnum.DOT,
            badgeBackgroundColor = DigitalTheme.colorScheme.backgroundWarning,
            iconPadding = 16.dp
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_80, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 16,
            iconPadding = 16.dp, badgeType = BadgeEnum.ICON,
            badgeIcon = R.drawable.ic_camera, badgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
            badgeIconColor = DigitalTheme.colorScheme.contentBrand,
            onBadgeClick = { Toast.makeText(context, "On badge icon click", Toast.LENGTH_SHORT).show() }
        )
        HorizontalSpacer(16)
    }
}

@Composable
private fun AvatarImageStates(clipPercent: Int = 50) {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_32, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_36, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_36, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType37_1_1.png",
            imageCornerRadius = 4,
            contentScale = ContentScale.FillWidth,

            )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_40, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_56, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_80, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        HorizontalSpacer(16)
    }
}

@Composable
private fun AvatarLottieStates() {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_32, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_36, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_40, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_56, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE, badgeType = BadgeEnum.DOT,
            badgeBackgroundColor = DigitalTheme.colorScheme.backgroundInfo,
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_80, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE, badgeType = BadgeEnum.ICON,
            badgeIcon = R.drawable.ic_camera, badgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
            badgeIconColor = DigitalTheme.colorScheme.contentBrand,
            onBadgeClick = { Toast.makeText(context, "On badge icon click", Toast.LENGTH_SHORT).show() }
        )
        HorizontalSpacer(16)
    }
}

@Composable
private fun AvatarTextStates() {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.TEXT, text = "AA",
            textColor = DigitalTheme.colorScheme.contentAlternative6,
            backgroundColor = DigitalTheme.colorScheme.backgroundAlternative6,
            backgroundRadius = 100,
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_32, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.TEXT, text = "AA",
            textColor = DigitalTheme.colorScheme.contentAlternative,
            backgroundColor = DigitalTheme.colorScheme.backgroundAlternative,
            backgroundRadius = 100,
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_36, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.TEXT, text = "AA",
            textColor = DigitalTheme.colorScheme.contentAlternative2,
            backgroundColor = DigitalTheme.colorScheme.backgroundAlternative2,
            backgroundRadius = 100,
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_40, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.TEXT, text = "AA",
            textColor = DigitalTheme.colorScheme.contentAlternative3,
            backgroundColor = DigitalTheme.colorScheme.backgroundAlternative3,
            backgroundRadius = 100,
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_56, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.TEXT, badgeType = BadgeEnum.DOT,
            badgeBackgroundColor = DigitalTheme.colorScheme.backgroundInfo, text = "AA",
            textColor = DigitalTheme.colorScheme.contentAlternative4,
            backgroundColor = DigitalTheme.colorScheme.backgroundAlternative4,
            backgroundRadius = 100,
        )
        HorizontalSpacer(16)
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_80, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.TEXT, badgeType = BadgeEnum.ICON,
            badgeIcon = R.drawable.ic_camera, badgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
            badgeIconColor = DigitalTheme.colorScheme.contentBrand, text = "AA",
            textColor = DigitalTheme.colorScheme.contentAlternative5,
            backgroundColor = DigitalTheme.colorScheme.backgroundAlternative5,
            backgroundRadius = 100,
            onBadgeClick = { Toast.makeText(context, "On badge icon click", Toast.LENGTH_SHORT).show() }
        )
        HorizontalSpacer(16)
    }
}


@Composable
private fun ActionButtonStates() {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.horizontalScroll(rememberScrollState())) {
        ActionButton(
            actionText = "Action button", badgeType = BadgeEnum.ICON, icon = R.drawable.ic_phonebook,
            badgeIcon = R.drawable.ic_camera, badgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
            badgeIconColor = DigitalTheme.colorScheme.contentBrand,
            onBadgeClick = { Toast.makeText(context, "On badge icon click", Toast.LENGTH_SHORT).show() },
            onActionButtonClick = { Toast.makeText(context, "On action button click", Toast.LENGTH_SHORT).show() }
        )
        HorizontalSpacer(16)
        ActionButton(
            actionText = "Action button", icon = R.drawable.ic_phonebook, backgroundRadius = 12, badgeType = BadgeEnum.DOT,
            badgeBackgroundColor = DigitalTheme.colorScheme.backgroundInfo,
            onActionButtonClick = { Toast.makeText(context, "On action button click", Toast.LENGTH_SHORT).show() }
        )
        HorizontalSpacer(16)
        ActionButton(
            actionText = "Action button",
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_56,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = 50,
            badgeType = BadgeEnum.ICON,
            badgeIcon = R.drawable.ic_camera, badgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
            badgeIconColor = DigitalTheme.colorScheme.contentBrand,
            onBadgeClick = { Toast.makeText(context, "On badge icon click", Toast.LENGTH_SHORT).show() },
            onActionButtonClick = { Toast.makeText(context, "On action button click", Toast.LENGTH_SHORT).show() }
        )
        HorizontalSpacer(16)
        ActionButton(
            actionText = "Action button",
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_56,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = 0,
            onActionButtonClick = { Toast.makeText(context, "On action button click", Toast.LENGTH_SHORT).show() }
        )
        HorizontalSpacer(16)
        ActionButton(
            actionText = "Action button",
            avatarType = AvatarEnum.TEXT, text = "AA",
            textColor = DigitalTheme.colorScheme.contentAlternative3,
            backgroundColor = DigitalTheme.colorScheme.backgroundAlternative3,
            badgeType = BadgeEnum.ICON,
            badgeIcon = R.drawable.ic_camera, badgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
            badgeIconColor = DigitalTheme.colorScheme.contentBrand,
            onBadgeClick = { Toast.makeText(context, "On badge icon click", Toast.LENGTH_SHORT).show() },
            onActionButtonClick = { Toast.makeText(context, "On action button click", Toast.LENGTH_SHORT).show() }
        )
        HorizontalSpacer(16)
        ActionButton(
            actionText = "Action button", avatarType = AvatarEnum.LOTTIE,
            backgroundColor = Color.Transparent,
            onActionButtonClick = { Toast.makeText(context, "On action button click", Toast.LENGTH_SHORT).show() }
        )
        HorizontalSpacer(16)

    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        QuickActionAndAvatarScreen()
    }
}
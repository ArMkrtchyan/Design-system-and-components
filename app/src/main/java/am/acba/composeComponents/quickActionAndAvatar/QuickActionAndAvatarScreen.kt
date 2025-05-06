package am.acba.composeComponents.quickActionAndAvatar

import am.acba.component.R
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.avatar.Avatar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.avatar.AvatarSizeEnum
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
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
                Spacer(modifier = Modifier.height(16.dp))
                AvatarIconStates()
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryDivider(text = "Avatar icon state with square background")
                Spacer(modifier = Modifier.height(16.dp))
                AvatarIconWithRoundedStates()
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryDivider(text = "Avatar Image state rounded")
                Spacer(modifier = Modifier.height(16.dp))
                AvatarImageStates()
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryDivider(text = "Avatar Image state not rounded")
                Spacer(modifier = Modifier.height(16.dp))
                AvatarImageStates(0)
                Spacer(modifier = Modifier.height(16.dp))
                PrimaryDivider(text = "Avatar Lottie state")
                Spacer(modifier = Modifier.height(16.dp))
                AvatarLottieStates()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun AvatarIconStates() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            iconPadding = 4.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_32, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            iconPadding = 6.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_36, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            iconPadding = 8.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_40, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            iconPadding = 8.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_56, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            badgeType = BadgeEnum.DOT,
            iconPadding = 16.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_80, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 100,
            iconPadding = 16.dp, badgeType = BadgeEnum.ICON,
            badgeIcon = R.drawable.ic_camera, badgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
            badgeIconColor = DigitalTheme.colorScheme.contentBrand
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
private fun AvatarIconWithRoundedStates() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 4,
            iconPadding = 4.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_32, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 4,
            iconPadding = 6.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_36, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 4,
            iconPadding = 8.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_40, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 6,
            iconPadding = 8.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_56, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 12,
            badgeType = BadgeEnum.DOT,
            badgeBackgroundColor = DigitalTheme.colorScheme.backgroundWarning,
            iconPadding = 16.dp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_80, icon = R.drawable.ic_phonebook,
            backgroundColor = DigitalTheme.colorScheme.backgroundTonal1,
            backgroundRadius = 16,
            iconPadding = 16.dp, badgeType = BadgeEnum.ICON,
            badgeIcon = R.drawable.ic_camera, badgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
            badgeIconColor = DigitalTheme.colorScheme.contentBrand
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
private fun AvatarImageStates(clipPercent: Int = 50) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_32, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_36, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_40, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_56, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_80, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.IMAGE, imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
            clipPercent = clipPercent
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
private fun AvatarLottieStates() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_24, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_32, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_36, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_40, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_56, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE, badgeType = BadgeEnum.DOT,
            badgeBackgroundColor = DigitalTheme.colorScheme.backgroundInfo,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Avatar(
            avatarSize = AvatarSizeEnum.AVATAR_SIZE_80, icon = R.drawable.ic_phonebook,
            avatarType = AvatarEnum.LOTTIE, badgeType = BadgeEnum.ICON,
            badgeIcon = R.drawable.ic_camera, badgeBackgroundColor = DigitalTheme.colorScheme.backgroundTonal3,
            badgeIconColor = DigitalTheme.colorScheme.contentBrand
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        QuickActionAndAvatarScreen()
    }
}
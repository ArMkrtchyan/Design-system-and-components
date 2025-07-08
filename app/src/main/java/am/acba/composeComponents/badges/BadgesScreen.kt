package am.acba.composeComponents.badges

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp


@Composable
private fun BadgesDefault() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Badge(badgeType = BadgeEnum.DOT)
        HorizontalSpacer(16)
        Badge(badgeType = BadgeEnum.ICON)
        HorizontalSpacer(16)
        Badge(badgeType = BadgeEnum.NUMBER, text = "2")
        HorizontalSpacer(16)
        Badge(badgeType = BadgeEnum.INFO, text = "Badge")
        HorizontalSpacer(16)
        Badge(badgeType = BadgeEnum.INFO, icon = R.drawable.ic_info, text = "With icon")
    }
}

@Composable
private fun BadgesNeutral() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Badge(badgeType = BadgeEnum.DOT, backgroundColor = DigitalTheme.colorScheme.backgroundPending)
        HorizontalSpacer(16)
        Badge(badgeType = BadgeEnum.ICON, backgroundColor = DigitalTheme.colorScheme.backgroundPending, iconColor = DigitalTheme.colorScheme.contentPending)
        HorizontalSpacer(16)
        Badge(badgeType = BadgeEnum.NUMBER, text = "2", backgroundColor = DigitalTheme.colorScheme.backgroundPending, textColor = DigitalTheme.colorScheme.contentPending)
        HorizontalSpacer(16)
        Badge(badgeType = BadgeEnum.INFO, text = "Badge", backgroundColor = DigitalTheme.colorScheme.backgroundPending, textColor = DigitalTheme.colorScheme.contentPending)
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.INFO,
            icon = R.drawable.ic_info,
            text = "With icon",
            backgroundColor = DigitalTheme.colorScheme.backgroundPending,
            textColor = DigitalTheme.colorScheme.contentPending
        )
    }
}

@Composable
private fun BadgesWarning() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Badge(badgeType = BadgeEnum.DOT, backgroundColor = DigitalTheme.colorScheme.backgroundWarning)
        HorizontalSpacer(16)
        Badge(badgeType = BadgeEnum.ICON, backgroundColor = DigitalTheme.colorScheme.backgroundWarning, iconColor = DigitalTheme.colorScheme.contentWarning)
        HorizontalSpacer(16)
        Badge(badgeType = BadgeEnum.NUMBER, text = "2", backgroundColor = DigitalTheme.colorScheme.backgroundWarning, textColor = DigitalTheme.colorScheme.contentWarning)
        HorizontalSpacer(16)
        Badge(badgeType = BadgeEnum.INFO, text = "Badge", backgroundColor = DigitalTheme.colorScheme.backgroundWarning, textColor = DigitalTheme.colorScheme.contentWarning)
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.INFO,
            icon = R.drawable.ic_info,
            text = "With icon",
            backgroundColor = DigitalTheme.colorScheme.backgroundWarning,
            textColor = DigitalTheme.colorScheme.contentWarning
        )
    }
}

@Composable
private fun OtherSizes() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Badge(
            badgeType = BadgeEnum.DOT,
            modifier = Modifier
                .width(8.dp)
                .height(8.dp)
        )
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.DOT,
            modifier = Modifier
                .width(9.dp)
                .height(9.dp)
        )
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.DOT
        )
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.DOT,
            modifier = Modifier
                .width(12.dp)
                .height(12.dp)
        )
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.DOT,
            modifier = Modifier
                .width(14.dp)
                .height(14.dp)
        )
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.DOT,
            modifier = Modifier
                .width(16.dp)
                .height(16.dp)
        )
        HorizontalSpacer(16)
    }
}

@Composable
private fun IconOtherSizes() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Badge(
            badgeType = BadgeEnum.ICON,
            icon = R.drawable.ic_camera,
        )
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.ICON,
            icon = R.drawable.ic_camera,
            modifier = Modifier
                .width(14.dp)
                .height(14.dp)
        )
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.ICON,
            icon = R.drawable.ic_camera,
            modifier = Modifier
                .width(16.dp)
                .height(16.dp)
        )
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.ICON,
            icon = R.drawable.ic_camera,
            modifier = Modifier
                .width(18.dp)
                .height(18.dp)
        )
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.ICON,
            icon = R.drawable.ic_camera,
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
        )
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.ICON,
            icon = R.drawable.ic_camera,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .padding(3.dp)
        )
        HorizontalSpacer(16)
        Badge(
            badgeType = BadgeEnum.ICON,
            icon = R.drawable.ic_camera,
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .padding(6.dp)
        )
        HorizontalSpacer(16)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgesScreen(title: String = "") {
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
            PrimaryToolbar(title = title)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                PrimaryDivider(text = "Badges default state")
                VerticalSpacer(16)
                BadgesDefault()
                VerticalSpacer(16)
                PrimaryDivider(text = "Badges pending state")
                VerticalSpacer(16)
                BadgesNeutral()
                VerticalSpacer(16)
                PrimaryDivider(text = "Badges warning state")
                VerticalSpacer(16)
                BadgesWarning()
                VerticalSpacer(16)
                PrimaryDivider(text = "Other sizes")
                VerticalSpacer(16)
                OtherSizes()
                VerticalSpacer(16)
                IconOtherSizes()
                VerticalSpacer(16)
            }
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        BadgesScreen()
    }
}
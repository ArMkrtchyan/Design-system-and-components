package am.acba.composeComponents.alerts

import am.acba.component.R
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.alerts.ComposeAlertTypes
import am.acba.compose.components.alerts.PrimaryAlert
import am.acba.compose.theme.DigitalTheme
import android.widget.Toast
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(title: String = "") {
    val context = LocalContext.current
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
                PrimaryAlert(
                    title = "Title",
                    description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
                    linkText = "Link"
                )
                VerticalSpacer(16)
                PrimaryAlert(
                    endIconPainter = painterResource(R.drawable.ic_close),
                    title = "Title",
                    description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
                    onCloseClick = { Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show() }
                )
                VerticalSpacer(16)
                PrimaryAlert(
                    alertType = ComposeAlertTypes.DANGER,
                    endIconPainter = painterResource(R.drawable.ic_close),
                    title = "Title",
                    description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
                    linkText = "Link"
                )
                VerticalSpacer(16)
                PrimaryAlert(
                    alertType = ComposeAlertTypes.WARNING,
                    endIconPainter = painterResource(R.drawable.ic_close),
                    title = "Title",
                    description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
                    linkText = "Link"
                )
                VerticalSpacer(16)
                PrimaryAlert(
                    alertType = ComposeAlertTypes.SUCCESS,
                    endIconPainter = painterResource(R.drawable.ic_close),
                    title = "Title",
                    description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
                    linkText = "Link"
                )
                VerticalSpacer(16)
                PrimaryAlert(
                    alertType = ComposeAlertTypes.NEUTRAL,
                    iconPainter = painterResource(R.drawable.ic_settings),
                    endIconPainter = painterResource(R.drawable.ic_close),
                    title = "Title",
                    description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
                    linkText = "Link"
                )
                VerticalSpacer(16)
                PrimaryAlert(
                    endIconPainter = painterResource(R.drawable.ic_close),
                    endIconTint = DigitalTheme.colorScheme.contentPrimary,
                    title = "Title",
                    description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
                    linkText = "Link",
                    isRounded = false
                )
                VerticalSpacer(16)
                PrimaryAlert(
                    alertType = ComposeAlertTypes.DANGER,
                    endIconPainter = painterResource(R.drawable.ic_close),
                    title = "Title",
                    description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
                    linkText = "Link",
                    isRounded = false
                )
                VerticalSpacer(16)
                PrimaryAlert(
                    alertType = ComposeAlertTypes.WARNING,
                    endIconPainter = painterResource(R.drawable.ic_close),
                    title = "Title",
                    description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
                    linkText = "Link",
                    isRounded = false
                )
                VerticalSpacer(16)
                PrimaryAlert(
                    alertType = ComposeAlertTypes.SUCCESS,
                    endIconPainter = painterResource(R.drawable.ic_close),
                    title = "Title",
                    description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
                    linkText = "Link",
                    isRounded = false
                )
                VerticalSpacer(16)
                PrimaryAlert(
                    alertType = ComposeAlertTypes.NEUTRAL,
                    iconPainter = painterResource(R.drawable.ic_settings),
                    endIconPainter = painterResource(R.drawable.ic_close),
                    title = "Title",
                    description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
                    linkText = "Link",
                    isRounded = false
                )
                VerticalSpacer(16)
            }
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        AlertsScreen()
    }
}
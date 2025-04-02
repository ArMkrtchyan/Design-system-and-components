package am.acba.compose.components

import am.acba.component.R
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryInfoMessage(
    title: String = "",
    description: String = "",
    link: String = "",
    closeIcon: Painter? = null,
    closeIconTint: Color = DigitalTheme.colorScheme.contentPrimary,
    onCloseClick: () -> Unit = {},
    onLinkClick: () -> Unit = {}
) {
    PrimaryMessage(
        type = 0,
        title = title,
        description = description,
        link = link,
        endIconPainter = closeIcon,
        endIconTint = closeIconTint,
        onCloseClick = onCloseClick,
        onLinkClick = onLinkClick
    )
}

@Composable
fun PrimaryErrorMessage(
    title: String = "",
    description: String = "",
    link: String = "",
    closeIcon: Painter? = null,
    closeIconTint: Color = DigitalTheme.colorScheme.contentPrimary,
    onCloseClick: () -> Unit = {},
    onLinkClick: () -> Unit = {}
) {
    PrimaryMessage(
        type = 1,
        title = title,
        description = description,
        link = link,
        endIconPainter = closeIcon,
        endIconTint = closeIconTint,
        onCloseClick = onCloseClick,
        onLinkClick = onLinkClick
    )
}

@Composable
fun PrimaryWarningMessage(
    title: String = "",
    description: String = "",
    link: String = "",
    closeIcon: Painter? = null,
    closeIconTint: Color = DigitalTheme.colorScheme.contentPrimary,
    onCloseClick: () -> Unit = {},
    onLinkClick: () -> Unit = {}
) {
    PrimaryMessage(
        type = 2,
        title = title,
        description = description,
        link = link,
        endIconPainter = closeIcon,
        endIconTint = closeIconTint,
        onCloseClick = onCloseClick,
        onLinkClick = onLinkClick
    )
}

@Composable
fun PrimarySuccessMessage(
    title: String = "",
    description: String = "",
    link: String = "",
    closeIcon: Painter? = null,
    closeIconTint: Color = DigitalTheme.colorScheme.contentPrimary,
    onCloseClick: () -> Unit = {},
    onLinkClick: () -> Unit = {}
) {
    PrimaryMessage(
        type = 3,
        title = title,
        description = description,
        link = link,
        endIconPainter = closeIcon,
        endIconTint = closeIconTint,
        onCloseClick = onCloseClick,
        onLinkClick = onLinkClick
    )
}

@Composable
fun PrimaryMessage(
    type: Int = 0,
    startIconPainter: Painter? = null,
    startIconTint: Color = Color.Transparent,
    endIconPainter: Painter? = null,
    endIconTint: Color = Color.Transparent,
    title: String = "",
    description: String = "",
    link: String = "",
    onCloseClick: () -> Unit = {},
    onLinkClick: () -> Unit = {}
) {
    val backgroundToken: Color
    val borderToken: Color
    val icon: Painter?
    val iconTint: Color
    when (type) {
        0 -> {
            backgroundToken = DigitalTheme.colorScheme.backgroundInfoTonal1
            borderToken = DigitalTheme.colorScheme.borderInfoTonal1
            icon = painterResource(id = R.drawable.ic_info)
            iconTint = DigitalTheme.colorScheme.contentInfoTonal1
        }

        1 -> {
            backgroundToken = DigitalTheme.colorScheme.backgroundDangerTonal1
            borderToken = DigitalTheme.colorScheme.borderDangerTonal1
            icon = painterResource(id = R.drawable.ic_close_round)
            iconTint = DigitalTheme.colorScheme.contentDangerTonal1
        }

        2 -> {
            backgroundToken = DigitalTheme.colorScheme.backgroundWarningTonal1
            borderToken = DigitalTheme.colorScheme.borderWarning
            icon = painterResource(id = R.drawable.ic_warning)
            iconTint = DigitalTheme.colorScheme.contentWarningTonal1
        }

        3 -> {
            backgroundToken = DigitalTheme.colorScheme.backgroundSuccessTonal1
            borderToken = DigitalTheme.colorScheme.borderSuccess
            icon = painterResource(id = R.drawable.ic_success)
            iconTint = DigitalTheme.colorScheme.contentSuccessTonal1
        }

        else -> {
            backgroundToken = DigitalTheme.colorScheme.backgroundTonal1
            borderToken = DigitalTheme.colorScheme.borderPrimaryTonal1
            icon = startIconPainter
            iconTint = startIconTint
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(ShapeTokens.shapePrimaryButton)
            .border(1.dp, borderToken, ShapeTokens.shapePrimaryButton)
            .background(backgroundToken)

    ) {
        icon?.let {
            Box(
                Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                PrimaryIcon(painter = icon, tint = iconTint)
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = icon?.let { 48.dp } ?: 16.dp, end = endIconPainter?.let { 48.dp } ?: 16.dp, top = 16.dp, bottom = 16.dp)
                .align(Alignment.Center)
        ) {
            if (title.isNotEmpty()) PrimaryText(text = title, style = DigitalTheme.typography.body2Bold)
            if (description.isNotEmpty()) {
                if (title.isNotEmpty()) Spacer(modifier = Modifier.height(4.dp))
                PrimaryText(text = description, style = DigitalTheme.typography.smallRegular)
            }
            if (link.isNotEmpty()) {
                Box(Modifier.clickable { onLinkClick.invoke() }) {
                    if (description.isNotEmpty() || title.isNotEmpty()) Spacer(modifier = Modifier.height(4.dp))
                    PrimaryText(text = link, style = DigitalTheme.typography.smallBold, textDecoration = TextDecoration.Underline)
                }
            }
        }
        endIconPainter?.let {
            Box(
                Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .align(Alignment.TopEnd)
                    .clickable { onCloseClick.invoke() }, contentAlignment = Alignment.Center
            ) {
                PrimaryIcon(
                    painter = endIconPainter, tint = endIconTint, modifier = Modifier
                        .height(16.dp)
                        .width(16.dp)
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun PrimaryInfoMessagePreview() {
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(20.dp)
        ) {
            PrimaryInfoMessage(
                title = "Title",
                description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam nunc adipiscing. Quis nec diam fames feugiat ac non.",
                link = "Link",
                closeIcon = painterResource(id = R.drawable.ic_close)
            )
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryErrorMessage(
                title = "Title",
                description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam nunc adipiscing. Quis nec diam fames feugiat ac non.",
                link = "Link"
            )
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryWarningMessage(
                title = "Title",
                description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam nunc adipiscing. Quis nec diam fames feugiat ac non.",
                link = "Link"
            )
            Spacer(modifier = Modifier.height(8.dp))
            PrimarySuccessMessage(
                title = "Title",
                description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam nunc adipiscing. Quis nec diam fames feugiat ac non.",
                link = "Link"
            )
        }
    }
}
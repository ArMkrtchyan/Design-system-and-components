package am.acba.compose.components.alerts

import am.acba.component.R
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import am.acba.utils.extensions.id
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryAlert(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    alertType: ComposeAlertTypes = ComposeAlertTypes.INFO,
    iconPainter: Painter? = null,
    endIconPainter: Painter? = null,
    endIconTint: Color = DigitalTheme.colorScheme.contentPrimary,
    linkText: String? = null,
    isRounded: Boolean = true,
    onClick: () -> Unit = {},
    onLinkClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
) {
    var startIconPainter = painterResource(R.drawable.ic_info)
    var startIconTint: Color
    var backgroundColor: Color
    var borderColor: Color
    when (alertType) {
        ComposeAlertTypes.INFO -> {
            startIconPainter = painterResource(R.drawable.ic_info)
            startIconTint = DigitalTheme.colorScheme.contentInfoTonal1
            backgroundColor = DigitalTheme.colorScheme.backgroundInfoTonal1
            borderColor = DigitalTheme.colorScheme.borderInfoTonal1
        }

        ComposeAlertTypes.DANGER -> {
            startIconPainter = painterResource(R.drawable.ic_close_round)
            startIconTint = DigitalTheme.colorScheme.contentDangerTonal1
            backgroundColor = DigitalTheme.colorScheme.backgroundDangerTonal1
            borderColor = DigitalTheme.colorScheme.borderDanger
        }

        ComposeAlertTypes.WARNING -> {
            startIconPainter = painterResource(R.drawable.ic_warning)
            startIconTint = DigitalTheme.colorScheme.contentWarningTonal1
            backgroundColor = DigitalTheme.colorScheme.backgroundWarningTonal1
            borderColor = DigitalTheme.colorScheme.borderWarning
        }

        ComposeAlertTypes.SUCCESS -> {
            startIconPainter = painterResource(R.drawable.ic_success)
            startIconTint = DigitalTheme.colorScheme.contentSuccessTonal1
            backgroundColor = DigitalTheme.colorScheme.backgroundSuccessTonal1
            borderColor = DigitalTheme.colorScheme.borderSuccess
        }

        ComposeAlertTypes.NEUTRAL -> {
            iconPainter?.let { startIconPainter = it }
            startIconTint = DigitalTheme.colorScheme.contentPrimaryTonal1
            backgroundColor = DigitalTheme.colorScheme.backgroundPendingTonal1
            borderColor = DigitalTheme.colorScheme.borderPrimary
        }
    }
    val roundModifier = if (isRounded) {
        Modifier
            .clip(ShapeTokens.shapePrimaryButton)
            .border(1.dp, borderColor, ShapeTokens.shapePrimaryButton)
    } else {
        Modifier
    }
    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(roundModifier)
                .background(backgroundColor)
                .padding(16.dp)
                .clickable {
                    onClick.invoke()
                }
                .then(modifier)
        ) {
            Row {
                PrimaryIcon(painter = startIconPainter, tint = startIconTint, modifier = Modifier.id("alertStartIcon"))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    title?.takeIf { it.isNotEmpty() }?.let {
                        PrimaryText(text = title, style = DigitalTheme.typography.body1Bold, maxLines = 3, modifier = Modifier.id("alertTitle"))
                        VerticalSpacer(4.dp)
                    }
                    description?.takeIf { it.isNotEmpty() }?.let {
                        PrimaryText(text = description, style = DigitalTheme.typography.smallRegular, modifier = Modifier.id("alertDescription"))
                    }
                    if (!linkText.isNullOrEmpty()) {
                        VerticalSpacer(4.dp)
                        val color = DigitalTheme.colorScheme.contentPrimary
                        PrimaryText(
                            modifier = Modifier
                                .id("alertLink")
                                .drawBehind {
                                    val strokeWidthPx = 1.dp.toPx()
                                    val verticalOffset = size.height - 2.sp.toPx()
                                    drawLine(
                                        color = color,
                                        strokeWidth = strokeWidthPx,
                                        start = Offset(0f, verticalOffset),
                                        end = Offset(size.width, verticalOffset)
                                    )
                                }
                                .clickable { onLinkClick.invoke() },
                            text = linkText,
                            style = DigitalTheme.typography.smallBold,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
                endIconPainter?.let {
                    PrimaryIcon(
                        modifier = Modifier
                            .size(16.dp)
                            .id("alertEndIcon"), painter = endIconPainter, tint = endIconTint
                    )
                }
            }

        }
        endIconPainter?.let {
            Box(
                modifier = Modifier
                    .id("alertEndIconBox")
                    .align(Alignment.TopEnd)
                    .size(40.dp)
                    .padding(top = 8.dp, end = 8.dp)
                    .clickable {
                        onCloseClick.invoke()
                    }) {
            }
        }
    }
}

@Composable
@PreviewLightDark
fun PrimaryAlertPreview() {
    DigitalTheme {
        PrimaryAlert(
            alertType = ComposeAlertTypes.NEUTRAL,
            endIconPainter = painterResource(R.drawable.ic_close),
            endIconTint = DigitalTheme.colorScheme.contentPrimary,
            title = "Title",
            description = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam.",
            linkText = "Link"
        )
    }
}
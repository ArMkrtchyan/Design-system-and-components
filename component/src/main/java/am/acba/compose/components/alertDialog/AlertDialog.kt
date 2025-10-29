package am.acba.compose.components.alertDialog

import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.GhostButton
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import am.acba.utils.extensions.id
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun PrimaryAlertDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    icon: Int? = null,
    iconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    title: String,
    titleColor: Color = DigitalTheme.colorScheme.contentPrimary,
    description: String? = null,
    descriptionColor: Color = DigitalTheme.colorScheme.contentPrimary,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    positiveButtonColor: Color = DigitalTheme.colorScheme.contentPrimary,
    negativeButtonColor: Color = DigitalTheme.colorScheme.contentPrimary,
    onPositiveButtonClick: () -> Unit = {},
    onNegativeButtonClick: () -> Unit = {},
    content: @Composable (() -> Unit)? = null
) {
    Dialog(
        onDismissRequest = onDismissRequest, properties = properties
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    DigitalTheme.colorScheme.backgroundTonal1, ShapeTokens.shapePrimaryButton
                )
        ) {
            Column(
                Modifier.padding(top = 24.dp)
            ) {
                icon?.let {
                    PrimaryAlertDialogIcon(icon = it, iconColor = iconColor)
                    VerticalSpacer(24.dp)
                }
                PrimaryAlertDialogTexts(
                    text = title, textId = "title", textColor = titleColor, style = DigitalTheme.typography.heading7Bold, 2
                )
                description?.let {
                    VerticalSpacer(8.dp)
                    PrimaryAlertDialogTexts(
                        text = it, textId = "description", textColor = descriptionColor, style = DigitalTheme.typography.subTitle2Regular, 3
                    )
                }
                content?.let {
                    VerticalSpacer(24.dp)
                    it.invoke()
                }
                VerticalSpacer(24.dp)
                positiveButtonText?.let {
                    PrimaryAlertDialogButton(
                        buttonId = "positiveButton", text = it, textId = "positiveButtonText", buttonColor = positiveButtonColor, onClick = onPositiveButtonClick
                    )
                }
                negativeButtonText?.let {
                    PrimaryAlertDialogButton(
                        buttonId = "negativeButton", text = it, textId = "negativeButtonText", buttonColor = negativeButtonColor, onClick = onNegativeButtonClick
                    )
                }
            }
        }
    }
}

@Composable
private fun PrimaryAlertDialogIcon(icon: Int, iconColor: Color) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier.align(Alignment.Center), painter = painterResource(icon), contentDescription = null, colorFilter = ColorFilter.tint(iconColor)
        )
    }
}

@Composable
private fun PrimaryAlertDialogTexts(
    text: String, textId: String, textColor: Color, style: TextStyle, maxLines: Int
) {
    PrimaryText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .id(textId),
        style = style,
        text = text,
        color = textColor,
        textAlign = TextAlign.Center,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun PrimaryAlertDialogButton(
    text: String, textId: String, buttonId: String, buttonColor: Color, onClick: () -> Unit
) {
    PrimaryDivider()
    GhostButton(
        modifier = Modifier
            .fillMaxWidth()
            .id(buttonId), text = text, textId = textId, textColor = buttonColor, onClick = onClick, shape = ShapeTokens.unspecified
    )
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        PrimaryAlertDialog(
            onDismissRequest = {},
            icon = am.acba.component.R.drawable.ic_block,
            title = "Օգտատերը բլոկավորված է",
            description = "Դուք կարող եք ապաբլոկավորել սեղմելով ապաբլոկավորման կոճակը:",
            positiveButtonText = "Ապաբլոկավորել",
            positiveButtonColor = DigitalTheme.colorScheme.contentBrandTonal1,
            negativeButtonText = "Չեղարկել",
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(125.dp)
                    .background(
                        DigitalTheme.colorScheme.backgroundTonal2, ShapeTokens.shapePrimaryButton
                    )
            ) {
                PrimaryText(modifier = Modifier.align(Alignment.Center), text = "Arshak .Mkrtchyan")
            }
        }
    }
}
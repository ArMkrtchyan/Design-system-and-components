@file:OptIn(ExperimentalMaterial3Api::class)

package am.acba.compose.components.dropDown

import am.acba.component.R
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.avatar.Avatar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.avatar.AvatarSizeEnum
import am.acba.compose.components.bottomSheet.PrimaryBottomSheet
import am.acba.compose.components.bottomSheet.closeBottomSheet
import am.acba.compose.components.dropDown.model.ContentProperties
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import am.acba.utils.Constants.EMPTY_STRING
import am.acba.utils.extensions.id
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope

@Composable
fun ComponentDropDown(
    label: String,
    value: TextFieldValue = TextFieldValue(EMPTY_STRING),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingImageUrl: String? = null,
    leadingIcon: Int? = null,
    leadingIconTint: Color? = DigitalTheme.colorScheme.contentPrimaryTonal1,
    labelStyle: TextStyle = DigitalTheme.typography.body1Regular,
    labelColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    textStyle: TextStyle = DigitalTheme.typography.body1Regular,
    textColor: Color = DigitalTheme.colorScheme.contentPrimary,
    contentProperties: ContentProperties = ContentProperties(),
    onDismissRequest: () -> Unit = {},
    content: @Composable (sheetState: SheetState, coroutineScope: CoroutineScope, onItemClick: () -> Unit) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    val showBottomSheet = remember { mutableStateOf(false) }

    LaunchedEffect(showBottomSheet.value) {
        isFocused = showBottomSheet.value
    }

    val borderAlpha by animateFloatAsState(
        targetValue = if (isFocused) 1f else 0f,
        animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
    )
    val animatedFocusColor = DigitalTheme.colorScheme.borderPrimary.copy(alpha = borderAlpha)

    val isLabelFocused = value.text.isNotEmpty()

    val labelOffsetY by animateDpAsState(
        targetValue = if (isLabelFocused) (-16).dp else 0.dp,
        label = "labelOffsetY"
    )
    val labelFontSize by animateFloatAsState(
        targetValue = if (isLabelFocused) 12f else 16f,
        label = "labelFontSize"
    )

    val arrowRotation by animateFloatAsState(
        targetValue = if (showBottomSheet.value) 180f else 0f,
        label = "arrow-rotation",
        animationSpec = tween(
            easing = LinearOutSlowInEasing
        )
    )

    val newModifier = when {
        !enabled -> Modifier.background(shape = ShapeTokens.shapePrimaryInput, color = DigitalTheme.colorScheme.backgroundTonal2Disable)
        isFocused -> Modifier
            .background(shape = ShapeTokens.shapePrimaryInput, color = DigitalTheme.colorScheme.backgroundTonal2)
            .border(1.dp, animatedFocusColor, ShapeTokens.shapePrimaryInput)

        else -> Modifier.background(shape = ShapeTokens.shapePrimaryInput, color = DigitalTheme.colorScheme.backgroundTonal2)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .heightIn(58.dp)
            .id("component_drop_down")
            .then(newModifier)
    ) {
        LeadingAvatar(
            iconRes = leadingIcon,
            iconColor = leadingIconTint,
            iconSize = AvatarSizeEnum.AVATAR_SIZE_24,
            spaceStart = 16.dp,
            imageUrl = leadingImageUrl,
            enabled = enabled
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    if (enabled)
                        showBottomSheet.value = true
                },
            contentAlignment = Alignment.CenterStart
        ) {
            PrimaryText(
                text = label,
                fontSize = labelFontSize.sp,
                color = textColors(enabled, labelColor),
                style = labelStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .offset(y = labelOffsetY)
                    .padding(start = 16.dp)
            )

            if (isLabelFocused) {
                PrimaryText(
                    value.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColors(enabled, textColor),
                    style = textStyle,
                    modifier = Modifier.padding(top = 12.dp, start = 16.dp)
                )
            }
        }
        PrimaryIcon(
            modifier = Modifier
                .padding(end = 16.dp)
                .rotate(arrowRotation)
                .clickable {
                    if (enabled)
                        showBottomSheet.value = true
                },
            tint = textColors(enabled, DigitalTheme.colorScheme.contentPrimaryTonal1),
            painter = painterResource(R.drawable.ic_down)
        )
    }

    if (showBottomSheet.value) {
        androidx.compose.ui.window.Popup {
            PrimaryBottomSheet(
                title = contentProperties.title,
                dismiss = {
                    showBottomSheet.value = false
                    onDismissRequest.invoke()
                },
                properties = contentProperties.modalBottomSheetProperties,
                contentHorizontalPadding = contentProperties.horizontalPadding,
                contentBottomPadding = contentProperties.bottomPadding,
                calculatePercentForOpenFullScreen = contentProperties.calculatePercentForOpenFullScreen,
                bottomSheetVisible = showBottomSheet.value,
            ) { sheetState, coroutineScope ->
                content(sheetState, coroutineScope) {
                    closeBottomSheet(state = sheetState, scope = coroutineScope) {
                        showBottomSheet.value = false
                        onDismissRequest.invoke()
                    }
                }
            }
        }
    }
}

@Composable
private fun LeadingAvatar(
    imageUrl: String?,
    iconRes: Int?,
    iconColor: Color?,
    iconSize: AvatarSizeEnum = AvatarSizeEnum.AVATAR_SIZE_24,
    spaceStart: Dp,
    enabled: Boolean
) {
    if (iconRes != null || imageUrl != null) {
        Row {
            val color = if (iconRes != null && imageUrl == null) {
                if (enabled) iconColor else DigitalTheme.colorScheme.contentPrimaryTonal1Disable
            } else iconColor
            HorizontalSpacer(spaceStart)
            Avatar(
                avatarType = imageUrl?.let { AvatarEnum.IMAGE } ?: AvatarEnum.ICON,
                avatarSize = iconSize,
                icon = iconRes,
                iconColor = color,
                imageUrl = imageUrl,
            )
        }
    }
}


@NonRestartableComposable
@Composable
private fun textColors(enabled: Boolean, color: Color) =
    if (enabled) color else DigitalTheme.colorScheme.contentPrimaryTonal1Disable

@Composable
@PreviewLightDark
fun PrimaryDatePickerPreview() {
    DigitalTheme {
        ComponentDropDown(
            label = "DropDown",
            leadingIcon = R.drawable.ic_close,
            value = TextFieldValue("DropDown"),
            leadingImageUrl = "https://online1-test.acba.am/Shared/Currencies/US.svg",
            content = { _, _, _ -> })
    }
}
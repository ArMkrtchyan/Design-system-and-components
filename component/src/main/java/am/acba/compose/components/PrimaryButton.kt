package am.acba.compose.components

import am.acba.component.R
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import android.view.Gravity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = DigitalTheme.typography.body1Bold,
    icon: Int = -1,
    iconGravity: Int = Gravity.START,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) DigitalTheme.colorScheme.backgroundBrandPressed else DigitalTheme.colorScheme.backgroundBrand
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = ShapeTokens.shapePrimaryButton,
        colors = ButtonColors(
            contentColor = DigitalTheme.colorScheme.contentSecondary,
            containerColor = color,
            disabledContentColor = DigitalTheme.colorScheme.contentSecondaryDisable,
            disabledContainerColor = DigitalTheme.colorScheme.backgroundBrandDisable
        ),
        contentPadding = contentPadding,
        elevation = null,
        interactionSource = interactionSource
    ) { SetIconWithText(icon = icon, iconGravity = iconGravity, text = text, style = textStyle) }
}

@Composable
fun PrimaryButtonSmall(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = DigitalTheme.typography.body2Bold,
    icon: Int = -1,
    iconGravity: Int = Gravity.START,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) DigitalTheme.colorScheme.backgroundBrandPressed else DigitalTheme.colorScheme.backgroundBrand
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = ShapeTokens.shapePrimaryButtonSmall,
        colors = ButtonColors(
            contentColor = DigitalTheme.colorScheme.contentSecondary,
            containerColor = color,
            disabledContentColor = DigitalTheme.colorScheme.contentSecondaryDisable,
            disabledContainerColor = DigitalTheme.colorScheme.backgroundBrandDisable
        ),
        contentPadding = contentPadding,
        elevation = null,
        interactionSource = interactionSource
    ) { SetIconWithText(icon = icon, iconGravity = iconGravity, iconSize = 16, text = text, style = textStyle, iconAndTextSpacing = 4) }
}

@Composable
fun GhostButton(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = DigitalTheme.typography.body1Bold,
    icon: Int = -1,
    iconGravity: Int = Gravity.START,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) DigitalTheme.colorScheme.backgroundTonal2 else Color.Transparent
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = ShapeTokens.shapePrimaryButton,
        colors = ButtonColors(
            contentColor = DigitalTheme.colorScheme.contentBrandTonal1,
            containerColor = color,
            disabledContentColor = DigitalTheme.colorScheme.contentBrandTonal1Disable,
            disabledContainerColor = DigitalTheme.colorScheme.backgroundBrandDisable
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        elevation = null,
        interactionSource = interactionSource
    ) { SetIconWithText(icon = icon, iconGravity = iconGravity, text = text, textStyle) }
}


@Composable
fun SecondaryButtonGreen(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = DigitalTheme.typography.body1Bold,
    icon: Int = -1,
    iconGravity: Int = Gravity.START,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = if (isPressed) DigitalTheme.colorScheme.backgroundTonal2 else Color.Transparent
    val borderColor = if (enabled) DigitalTheme.colorScheme.borderBrandTonal1 else DigitalTheme.colorScheme.borderBrandTonal1Disable
    SecondaryButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        icon = icon,
        iconGravity = iconGravity,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = ButtonColors(
            contentColor = DigitalTheme.colorScheme.borderBrandTonal1,
            containerColor = backgroundColor,
            disabledContentColor = DigitalTheme.colorScheme.contentBrandTonal1Disable,
            disabledContainerColor = Color.Transparent
        ),
        borderColor = borderColor,
        onClick = onClick
    )
}

@Composable
fun SecondaryButtonGrey(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = DigitalTheme.typography.body1Bold,
    icon: Int = -1,
    iconGravity: Int = Gravity.START,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = if (isPressed) DigitalTheme.colorScheme.backgroundTonal2 else Color.Transparent
    val borderColor = if (enabled) DigitalTheme.colorScheme.borderBase else DigitalTheme.colorScheme.borderBrandTonal1Disable
    SecondaryButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        icon = icon,
        iconGravity = iconGravity,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = ButtonColors(
            contentColor = DigitalTheme.colorScheme.contentPrimary,
            containerColor = backgroundColor,
            disabledContentColor = DigitalTheme.colorScheme.contentBrandTonal1Disable,
            disabledContainerColor = Color.Transparent
        ),
        borderColor = borderColor,
        onClick = onClick
    )
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = DigitalTheme.typography.body1Bold,
    icon: Int = -1,
    iconGravity: Int = Gravity.START,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    borderColor: Color = Color.Transparent,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = ShapeTokens.shapePrimaryButton,
        border = BorderStroke(1.dp, borderColor),
        colors = colors,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        elevation = null,
        interactionSource = interactionSource
    ) { SetIconWithText(icon = icon, iconGravity = iconGravity, text = text, textStyle) }
}

@Composable
fun EmojiButton(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = DigitalTheme.typography.body1Bold,
    emojiIcon: Int = -1,
    onClick: () -> Unit,
    iconGravity: Int = Gravity.END
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) DigitalTheme.colorScheme.backgroundSecondaryPressed else DigitalTheme.colorScheme.backgroundSecondary
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = ShapeTokens.shapePrimaryButton,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        elevation = null,
        interactionSource = interactionSource,

        colors = ButtonColors(
            contentColor = DigitalTheme.colorScheme.contentInverse,
            containerColor = color,
            disabledContentColor = ButtonDefaults.buttonColors().disabledContainerColor,
            disabledContainerColor = DigitalTheme.colorScheme.backgroundBrandDisable
        ),
    ) {
        SetIconWithText(icon = emojiIcon, iconGravity = iconGravity, text = text, textStyle)
    }
}

@Composable
private fun SetIconWithText(icon: Int, iconGravity: Int, text: String, style: TextStyle, iconSize: Int = 24, iconAndTextSpacing: Int = 8) {
    if (icon > -1) {
        when (iconGravity) {
            Gravity.START -> SetStartIconWithText(icon, text, style, iconSize = iconSize, iconAndTextSpacing = iconAndTextSpacing)
            Gravity.TOP -> SetTopIconWithText(icon, text, style, iconSize = iconSize, iconAndTextSpacing = iconAndTextSpacing)
            Gravity.BOTTOM -> SetBottomIconWithText(icon, text, style, iconSize = iconSize, iconAndTextSpacing = iconAndTextSpacing)
            Gravity.END -> SetEndIconWithText(icon, text, style, iconSize = iconSize, iconAndTextSpacing = iconAndTextSpacing)
        }
    } else {
        PrimaryButtonText(text = text, style)
    }
}

@Composable
private fun SetStartIconWithText(icon: Int, text: String, style: TextStyle, iconSize: Int = 24, iconAndTextSpacing: Int = 8) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        PrimaryButtonIcon(icon = icon, iconSize = iconSize)
        PrimaryButtonSpacer(iconAndTextSpacing)
        PrimaryButtonText(text = text, style)
    }
}

@Composable
private fun SetEndIconWithText(icon: Int, text: String, style: TextStyle, iconSize: Int = 24, iconAndTextSpacing: Int = 8) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        PrimaryButtonText(text = text, style)
        PrimaryButtonSpacer(iconAndTextSpacing)
        PrimaryButtonIcon(icon = icon, iconSize = iconSize)
    }
}

@Composable
private fun SetTopIconWithText(icon: Int, text: String, style: TextStyle, iconSize: Int = 24, iconAndTextSpacing: Int = 8) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        PrimaryButtonIcon(icon = icon, iconSize = iconSize)
        PrimaryButtonSpacer(iconAndTextSpacing)
        PrimaryButtonText(text = text, style)
    }
}

@Composable
private fun SetBottomIconWithText(icon: Int, text: String, style: TextStyle, iconSize: Int = 24, iconAndTextSpacing: Int = 8) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        PrimaryButtonText(text = text, style)
        PrimaryButtonSpacer(iconAndTextSpacing)
        PrimaryButtonIcon(icon = icon, iconSize = iconSize)
    }
}

@Composable
private fun PrimaryButtonIcon(icon: Int, iconSize: Int = 24) {
    Icon(
        painter = painterResource(id = icon), contentDescription = "", modifier = Modifier
            .requiredHeight(iconSize.dp)
            .requiredWidth(iconSize.dp)
    )
}

@Composable
private fun PrimaryButtonText(text: String, style: TextStyle) {
    Text(text = text, style = style)
}

@Composable
private fun PrimaryButtonSpacer(width: Int = 8) {
    Spacer(
        Modifier
            .width(width.dp)
            .height(4.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@PreviewLightDark
fun PrimaryButtonPreview() {
    DigitalTheme {
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
                PrimaryToolbar(title = "Compose Buttons", actions = {
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
                    Spacer(modifier = Modifier.height(16.dp))
                    EmojiButton(
                        onClick = {

                        },
                        text = "Emoji button",
                        iconGravity = Gravity.END,
                        emojiIcon = am.acba.component.R.drawable.ic_add_small,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PrimaryButton(
                        onClick = {

                        },
                        text = "Primary button",
                        icon = am.acba.component.R.drawable.ic_add_small,
                        iconGravity = Gravity.START,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PrimaryButtonSmall(
                        onClick = {

                        },
                        text = "Primary button",
                        icon = am.acba.component.R.drawable.ic_add_small,
                        iconGravity = Gravity.START,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PrimaryButton(
                        onClick = {

                        },
                        text = "Primary button disabled",
                        icon = am.acba.component.R.drawable.ic_car_accident,
                        iconGravity = Gravity.END,
                        enabled = false
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SecondaryButtonGreen(
                        onClick = {

                        },
                        text = "Secondary button",
                        icon = am.acba.component.R.drawable.ic_add_small,
                        iconGravity = Gravity.START,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SecondaryButtonGreen(
                        onClick = {

                        },
                        text = "Secondary button disabled",
                        icon = am.acba.component.R.drawable.ic_car_accident,
                        iconGravity = Gravity.END,
                        enabled = false
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SecondaryButtonGrey(
                        onClick = {

                        },
                        text = "Secondary button grey",
                        icon = am.acba.component.R.drawable.ic_add_small,
                        iconGravity = Gravity.START,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SecondaryButtonGrey(
                        onClick = {

                        },
                        text = "Secondary button grey disabled",
                        icon = am.acba.component.R.drawable.ic_car_accident,
                        iconGravity = Gravity.END,
                        enabled = false
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    GhostButton(
                        onClick = {

                        },
                        text = "Ghost button",
                        icon = am.acba.component.R.drawable.ic_add_small,
                        iconGravity = Gravity.START,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    GhostButton(
                        onClick = {

                        },
                        text = "Ghost button disabled",
                        icon = am.acba.component.R.drawable.ic_car_accident,
                        iconGravity = Gravity.END,
                        enabled = false
                    )
                }
            }
        }
    }
}
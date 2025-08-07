@file:OptIn(ExperimentalFoundationApi::class)

package am.acba.compose.components.inputs

import am.acba.component.R
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.Constants.EMPTY_STRING
import am.acba.utils.Constants.STAR_MASK
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PinInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    mask: String = STAR_MASK,
    showMask: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    length: Int = 4,
    width: Dp = 48.dp,
    height: Dp = 60.dp,
    spacing: Int = 8,
    focusRequester: FocusRequester = FocusRequester(),
    error: String = EMPTY_STRING
) {

    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .width((width * length + (length * (spacing - 1)).dp))
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                when {
                    it.length > length -> onValueChange.invoke(it.substring(0, length))
                    it.length == length -> {
                        onValueChange.invoke(it)
                        focusManager.clearFocus()
                    }

                    else -> onValueChange.invoke(it)
                }
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                }
                .semantics {
                    contentDescription = "PIN code input"
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = imeAction
            ),
            interactionSource = remember { MutableInteractionSource() },
            cursorBrush = SolidColor(Color.Transparent),
            enabled = enabled,
            readOnly = readOnly,
            decorationBox = { innerTextField ->
                CompositionLocalProvider(
                    LocalTextSelectionColors provides TextSelectionColors(
                        handleColor = Color.Transparent,
                        backgroundColor = DigitalTheme.colorScheme.contentPrimary
                    )
                ) {
                    PinItemRow(value, length, width, height, spacing, mask, showMask, isFocused, error, innerTextField)
                }
            }
        )
        error.takeIf { it.isNotEmpty() }?.let {
            VerticalSpacer(8.dp)
            ErrorRow(it)
        }
    }
}

@Composable
private fun PinItemRow(
    value: String,
    length: Int,
    width: Dp,
    height: Dp,
    spacing: Int,
    mask: String,
    showMask: Boolean,
    isFocused: Boolean,
    error: String,
    innerTextField: @Composable () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(length) { index ->
            val char = value.getOrNull(index)?.toString().orEmpty()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(width)
                    .height(height)
                    .let {
                        if (error.isNotEmpty())
                            it.border(1.dp, DigitalTheme.colorScheme.borderDanger, RoundedCornerShape(12.dp))
                        else it
                    }
                    .background(DigitalTheme.colorScheme.backgroundTonal2, RoundedCornerShape(12.dp))
            ) {
                PinItem(char, mask, showMask, value.length == index && isFocused)
            }
        }
    }
    Box(
        modifier = Modifier
            .alpha(0f)
    ) {
        innerTextField()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun PinItem(
    char: String,
    mask: String,
    showMask: Boolean,
    showCursor: Boolean,
) {

    val infiniteTransition = rememberInfiniteTransition(label = "Cursor")
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Cursor-Alpha"
    )

    var showValueBeforeMask by remember(char) { mutableStateOf(true) }

    LaunchedEffect(char) {
        showValueBeforeMask = char.isNotEmpty()
        if (showValueBeforeMask) delay(200)
        showValueBeforeMask = false
    }

    when {
        char.isNotEmpty() -> {
            AnimatedContent(
                targetState = when {
                    !showMask -> char
                    showValueBeforeMask -> char
                    else -> mask
                },
                transitionSpec = {
                    fadeIn(tween(100)) togetherWith fadeOut(tween(100))
                },
                label = "PinCharAnimation"
            ) { animatedText ->
                PrimaryText(
                    text = animatedText,
                    style = DigitalTheme.typography.heading3Bold
                )
            }
        }

        showCursor -> {
            Box {
                PrimaryText(
                    text = "|",
                    color = DigitalTheme.colorScheme.contentBrand.copy(alpha = cursorAlpha)
                )
            }
        }
    }
}

@Composable
private fun ErrorRow(error: String) {
    Row {
        PrimaryIcon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(R.drawable.ic_info),
            tint = DigitalTheme.colorScheme.contentDangerTonal1
        )
        HorizontalSpacer(4.dp)
        PrimaryText(
            error,
            style = DigitalTheme.typography.smallRegular,
            color = DigitalTheme.colorScheme.contentDangerTonal1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterVertically),
        )
    }
}

@Composable
@PreviewLightDark
fun PinInputPreview() {
    DigitalTheme {
        PinInput("1", {})
    }
}
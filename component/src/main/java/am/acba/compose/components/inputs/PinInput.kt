package am.acba.compose.components.inputs

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PinInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    mask: String = "*",
    showMask: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    length: Int = 4,
    width: Int = 48,
    height: Int = 60,
    error: String = "Մուտքագրված կոդը սխալ է մի քիչ երկար"
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

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

    Column(modifier = modifier.width((width * length + (32)).dp)) {
        BasicTextField(
            value = value,
            onValueChange = {
                onValueChange.invoke(it)
                if (it.length == length) {
                    focusManager.clearFocus()
                }
            },
            modifier = modifier
                .focusRequester(focusRequester)
                .focusable()
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = imeAction
            ),
            enabled = enabled,
            readOnly = readOnly,
            decorationBox = {
                PinItemRow(length, showMask, value, mask, isFocused, cursorAlpha, width, height, error)
            }
        )
        error.takeIf { it.isNotEmpty() }?.let {
            VerticalSpacer(8)
            ErrorRow(it)
        }
    }
}

@Composable
private fun PinItemRow(
    length: Int,
    showMask: Boolean,
    value: String,
    mask: String,
    isFocused: Boolean,
    cursorAlpha: Float,
    width: Int,
    height: Int,
    error: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(length) { index ->
            val char = value.getOrNull(index)?.toString().orEmpty()
            val modifier = if (error.isEmpty()) {
                Modifier
                    .width(width.dp)
                    .height(height.dp)
                    .background(DigitalTheme.colorScheme.backgroundTonal2, shape = RoundedCornerShape(12.dp))
            } else {
                Modifier
                    .width(width.dp)
                    .height(height.dp)
                    .border(1.dp, DigitalTheme.colorScheme.borderDanger, RoundedCornerShape(12.dp))
                    .background(DigitalTheme.colorScheme.backgroundTonal2, shape = RoundedCornerShape(12.dp))
            }
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                PinCharItem(showMask, value, char, mask, index, isFocused, cursorAlpha)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun PinCharItem(
    showMask: Boolean,
    value: String,
    char: String,
    mask: String,
    index: Int,
    isFocused: Boolean,
    cursorAlpha: Float,
    visibleDuration: Long = 200L,
) {

    var showValueBeforeMask by remember(char) { mutableStateOf(true) }

    LaunchedEffect(char) {
        if (char.isNotEmpty()) {
            showValueBeforeMask = true
            delay(visibleDuration)
            showValueBeforeMask = false
        } else {
            showValueBeforeMask = false
        }
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

        value.length == index && isFocused -> {
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
    Row(verticalAlignment = Alignment.CenterVertically) {
        PrimaryIcon(painter = painterResource(R.drawable.ic_info), tint = DigitalTheme.colorScheme.contentDangerTonal1)
        HorizontalSpacer(4)
        PrimaryText(error, style = DigitalTheme.typography.smallRegular, color = DigitalTheme.colorScheme.contentDangerTonal1)
    }
}

@Composable
@PreviewLightDark
fun PinInputPreview() {
    DigitalTheme {
        PinInput("1", {})
    }
}
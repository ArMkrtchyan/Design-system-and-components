package am.acba.compose.components.inputs

import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PinInput(
    modifier: Modifier = Modifier,
    length: Int = 4,
    width: Int = 48,
    height: Int = 60,
    onValueChange: (String) -> Unit = {},
    onDone: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    var pinValue by remember { mutableStateOf("") } // todo EH change to empty string from constants

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

    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
    }

    BasicTextField(
        value = pinValue,
        onValueChange = {
            onValueChange.invoke(it)
            if (it.length <= length && it.all { char -> char.isDigit() }) {
                pinValue = it
                if (it.length == length) {
                    onDone(it)
                }
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
            imeAction = ImeAction.Done
        ),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(length) { index ->
                    val char = pinValue.getOrNull(index)?.toString() ?: "" // todo EH change to empty string from constants

                    Box(
                        modifier = modifier
                            .width(width.dp)
                            .height(height.dp)
                            .background(DigitalTheme.colorScheme.backgroundTonal2, shape = RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            char.isNotEmpty() -> PrimaryText(
                                text = char,
                                style = DigitalTheme.typography.heading3Bold
                            )

                            pinValue.length == index && isFocused -> Box {
                                PrimaryText(
                                    text = "|",
                                    style = DigitalTheme.typography.heading3Bold,
                                    color = DigitalTheme.colorScheme.contentPrimary.copy(alpha = cursorAlpha)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        PinInput {

        }
    }
}
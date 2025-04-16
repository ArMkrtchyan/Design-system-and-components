package am.acba.compose.components.inputs

import am.acba.component.R
import am.acba.component.extensions.numberDeFormatting
import am.acba.component.extensions.numberFormattingWithOutDot
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.inputs.visualTransformations.AmountFormattingVisualTransformation
import am.acba.compose.components.inputs.visualTransformations.MaxLengthVisualTransformation
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryInput(
    modifier: Modifier = Modifier,
    value: TextFieldValue = TextFieldValue(""),
    onValueChange: (TextFieldValue) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: String? = null,
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    leadingIconTint: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    trailingTint: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    onLeadingIconClick: (() -> Unit)? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    errorText: String? = null,
    helpText: String? = null,
    label: String? = null,
    autoFormatting: Boolean = false,
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val newModifier = when {
        isError -> {
            Modifier
                .border(
                    1.dp,
                    DigitalTheme.colorScheme.contentDangerTonal1,
                    ShapeTokens.shapePrimaryInput
                )
        }

        isFocused -> {
            Modifier
                .border(1.dp, DigitalTheme.colorScheme.borderPrimary, ShapeTokens.shapePrimaryInput)
        }

        else -> {
            Modifier
        }
    }
    Column(
        modifier = modifier
    ) {
        var maxLength = Integer.MAX_VALUE
        if (visualTransformation is MaxLengthVisualTransformation) {
            maxLength = visualTransformation.maxLength
        } else if (visualTransformation is AmountFormattingVisualTransformation) {
            maxLength = visualTransformation.maxLength
        }
        TextField(
            value = value,
            onValueChange = {
                if (value.text.length != maxLength || it.text.length <= maxLength)
                    onValueChange(it)
            },
            modifier = newModifier
                .fillMaxWidth()
                .heightIn(min = 58.dp),
            enabled = enabled,
            readOnly = readOnly,
            placeholder = placeholder?.let { { Label(text = placeholder) } },
            leadingIcon = leadingOrTrailingIcon(
                iconRes = leadingIcon,
                tint = leadingIconTint,
                isEnabled = enabled,
                onClick = onLeadingIconClick
            ),
            trailingIcon = leadingOrTrailingIcon(
                iconRes = trailingIcon,
                tint = trailingTint,
                isEnabled = enabled,
                onClick = onTrailingIconClick
            ),
            prefix = prefix,
            suffix = suffix,
            shape = ShapeTokens.shapePrimaryInput,
            textStyle = DigitalTheme.typography.body1Regular,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            colors = createStateColors(),
            isError = isError,
            label = label?.let { { Label(text = label, isError = isError, isEnabled = enabled) } },
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (isError) {
            if (!errorText.isNullOrEmpty()) {
                SupportRow(
                    iconRes = R.drawable.ic_info,
                    text = errorText,
                    color = DigitalTheme.colorScheme.contentDangerTonal1
                )
            }
        } else if (!helpText.isNullOrEmpty()) {
            val textColor = when {
                !enabled -> DigitalTheme.colorScheme.contentPrimaryTonal1Disable
                else -> DigitalTheme.colorScheme.contentPrimaryTonal1
            }
            SupportRow(text = helpText, color = textColor)
        }
    }
}

fun TextFieldValue.checkMaxLength(
    maxLength: Int,
    currentValue: TextFieldValue? = null
): TextFieldValue? {
    if (currentValue != null && currentValue.text.length == maxLength && text.length > maxLength) return null
    if (text.length <= maxLength) {
        return this
    } else {
        val newText = text.substring(0, maxLength)
        val newSelection = when {
            currentValue == null -> {
                TextRange(newText.length)
            }

            abs(text.length - currentValue.text.length) == 1 -> {
                selection
            }

            else -> {
                TextRange(newText.length)
            }
        }
        return TextFieldValue(newText, newSelection)
    }
}

fun TextFieldValue.checkOnlyNumbers(
    keyboardOptions: KeyboardOptions,
    currentValue: TextFieldValue? = null,
    autoFormatting: Boolean = false
): TextFieldValue? {
    when (keyboardOptions.keyboardType) {
        KeyboardType.Number, KeyboardType.Decimal -> {
            if (text.isDigitsOnly()) {
                val formatedText = text.numberFormattingWithOutDot()
                return if (autoFormatting) {
                    TextFieldValue(formatedText, selection)
                } else {
                    this
                }
            } else {
                val filteredText = text.numberDeFormatting().filter { it.isDigit() }
                val formatedText = filteredText.numberFormattingWithOutDot()
                val newSelection = when {
                    currentValue == null -> {
                        if (autoFormatting) {
                            TextRange(formatedText.length)
                        } else {
                            TextRange(text.length)
                        }
                    }

                    abs(text.length - currentValue.text.length) == 1 -> {
                        selection
                    }

                    else -> {
                        if (autoFormatting) {
                            TextRange(formatedText.length)
                        } else {
                            TextRange(text.length)
                        }
                    }
                }
                return if (autoFormatting) {
                    TextFieldValue(formatedText, newSelection)
                } else {
                    TextFieldValue(filteredText, newSelection)
                }
            }
        }

        else -> return this
    }
}


@Composable
@PreviewLightDark
fun PrimaryInputPreview(
    modifier: Modifier = Modifier,
) {
    val textNormal =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            PrimaryInput(
                value = textNormal.value,
                onValueChange = { textNormal.value = it },
                label = "Label",
                leadingIcon = R.drawable.ic_close,
                onLeadingIconClick = {
                    textNormal.value = TextFieldValue("jcndskjcndk")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(hint = "Search...", modifier = modifier)
        }
    }
}
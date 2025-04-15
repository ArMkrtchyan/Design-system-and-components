package am.acba.compose.components

import am.acba.component.extensions.numberFormatting
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
    maxLength: Int = Integer.MAX_VALUE,
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
                .border(1.dp, DigitalTheme.colorScheme.contentDangerTonal1, ShapeTokens.shapePrimaryInput)
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
        TextField(
            value = value.checkOnlyNumbers(keyboardOptions, value, autoFormatting)?.checkMaxLength(maxLength) ?: value,
            onValueChange = {
                it.checkOnlyNumbers(keyboardOptions, value, autoFormatting)?.checkMaxLength(maxLength, value)?.let { ifNotNulValue -> onValueChange(ifNotNulValue) }
            },
            modifier = newModifier
                .fillMaxWidth()
                .heightIn(min = 58.dp),
            enabled = enabled,
            readOnly = readOnly,
            placeholder = placeholder?.let { { Label(text = placeholder) } },
            leadingIcon = leadingOrTrailingIcon(iconRes = leadingIcon, tint = leadingIconTint, isEnabled = enabled, onClick = onLeadingIconClick),
            trailingIcon = leadingOrTrailingIcon(iconRes = trailingIcon, tint = trailingTint, isEnabled = enabled, onClick = onTrailingIconClick),
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
                SupportRow(iconRes = am.acba.component.R.drawable.ic_info, text = errorText, color = DigitalTheme.colorScheme.contentDangerTonal1)
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

fun TextFieldValue.checkMaxLength(maxLength: Int, currentValue: TextFieldValue? = null): TextFieldValue? {
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

fun TextFieldValue.checkOnlyNumbers(keyboardOptions: KeyboardOptions, currentValue: TextFieldValue? = null, autoFormatting: Boolean = false): TextFieldValue? {
    when (keyboardOptions.keyboardType) {
        KeyboardType.Number, KeyboardType.Decimal -> {
            if (text.isDigitsOnly()) return this
            else {
                val filteredText = text.filter { it.isDigit() }
                val formatedText = filteredText.numberFormatting()
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
fun SearchBar(
    hint: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    height: Dp = 40.dp,
    cornerShape: Shape = ShapeTokens.shapePrimaryInput,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal2,
    onSearchClicked: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    onComponentClick: (() -> Unit)? = null,
) {
    onComponentClick?.let {
        modifier.clickable {

        }
    }
    var text by remember { mutableStateOf(TextFieldValue()) }
    Row(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .background(color = backgroundColor, shape = cornerShape),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .size(40.dp)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                painter = painterResource(id = am.acba.component.R.drawable.ic_search),
                contentDescription = "search",
                tint = DigitalTheme.colorScheme.contentPrimaryTonal1,
            )
        }
        BasicTextField(
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth(),
            value = text,
            cursorBrush = SolidColor(DigitalTheme.colorScheme.contentBrand),
            onValueChange = {
                text = it
                onTextChange(it.text)
            },
            enabled = isEnabled,
            textStyle = TextStyle(
                color = DigitalTheme.colorScheme.contentPrimary,
                fontSize = 16.sp
            ),
            decorationBox = { innerTextField ->
                if (text.text.isEmpty()) {
                    Text(
                        text = hint,
                        color = DigitalTheme.colorScheme.contentPrimaryTonal1,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
            singleLine = true
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if (text.text.isNotEmpty()) {
                        text = TextFieldValue(text = "")
                        onTextChange("")
                    }
                }
        ) {
            if (text.text.isNotEmpty()) {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    painter = painterResource(id = am.acba.component.R.drawable.ic_close),
                    contentDescription = "Search",
                    tint = DigitalTheme.colorScheme.contentPrimaryTonal1,
                )
            }
        }
    }
}


@Composable
fun SupportRow(iconRes: Int? = null, text: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(15.dp, 0.dp, 0.dp, 0.dp)
    ) {
        iconRes?.let {
            SupportIcon(it)
            Spacer(modifier = Modifier.width(4.dp))
        }
        SupportText(text = text, color = color)
    }
}

@Composable
fun Label(text: String?, isError: Boolean = false, isEnabled: Boolean = true) {
    val textColor = when {
        isError -> DigitalTheme.colorScheme.contentDangerTonal1
        !isEnabled -> DigitalTheme.colorScheme.contentPrimaryTonal1Disable
        else -> DigitalTheme.colorScheme.contentPrimaryTonal1
    }
    Text(
        text = text ?: "",
        color = textColor
    )
}

@Composable
fun leadingOrTrailingIcon(iconRes: Int? = null, tint: Color, isEnabled: Boolean = true, onClick: (() -> Unit)? = null): @Composable (() -> Unit)? {
    val iconTint = when {
        !isEnabled -> DigitalTheme.colorScheme.contentPrimaryTonal1Disable
        else -> tint
    }
    return if (iconRes != null) {
        {
            Icon(
                painter = painterResource(id = iconRes),
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clickable { onClick?.invoke() },
                contentDescription = "",
                tint = iconTint
            )
        }
    } else null
}

@Composable
fun SupportIcon(iconRes: Int) {
    Icon(
        painter = painterResource(id = iconRes),
        modifier = Modifier
            .width(18.dp)
            .height(18.dp),
        contentDescription = "",
        tint = DigitalTheme.colorScheme.contentDangerTonal1
    )
}

@Composable
fun SupportText(text: String, color: Color) {
    PrimaryText(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        style = DigitalTheme.typography.smallRegular,
        color = color,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun createStateColors() = TextFieldDefaults.colors(
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    errorIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    errorPlaceholderColor = DigitalTheme.colorScheme.contentPrimary,
    errorTextColor = DigitalTheme.colorScheme.contentPrimary,
    errorLabelColor = DigitalTheme.colorScheme.contentDangerTonal1,
    errorContainerColor = DigitalTheme.colorScheme.backgroundTonal2,
    errorTrailingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    errorLeadingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    focusedLabelColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    unfocusedLabelColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    focusedTextColor = DigitalTheme.colorScheme.contentPrimary,
    unfocusedTextColor = DigitalTheme.colorScheme.contentPrimary,
    unfocusedContainerColor = DigitalTheme.colorScheme.backgroundTonal2,
    focusedContainerColor = DigitalTheme.colorScheme.backgroundTonal2,
    focusedTrailingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    focusedLeadingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    unfocusedTrailingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    unfocusedLeadingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    cursorColor = DigitalTheme.colorScheme.contentBrand,
    errorCursorColor = DigitalTheme.colorScheme.contentDangerTonal1,
    disabledContainerColor = DigitalTheme.colorScheme.backgroundTonal2Disable,
    disabledPrefixColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledPlaceholderColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledSuffixColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledLabelColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledTextColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledLeadingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledTrailingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledSupportingTextColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
)

@Composable
@PreviewLightDark
fun PrimaryInputPreview(
    modifier: Modifier = Modifier,
) {
    val textNormal = rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
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
                leadingIcon = am.acba.component.R.drawable.ic_close,
                onLeadingIconClick = {
                    textNormal.value = TextFieldValue("jcndskjcndk")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(hint = "Search...", modifier = modifier)
        }
    }
}
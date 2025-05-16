package am.acba.compose.components.inputs

import am.acba.component.R
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.inputs.visualTransformations.CardFormattingVisualTransformation
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardInput(
    modifier: Modifier = Modifier,
    value: TextFieldValue = TextFieldValue(""),
    onValueChange: (TextFieldValue) -> Unit,
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    leadingIconTint: Color? = DigitalTheme.colorScheme.contentPrimaryTonal1,
    trailingTint: Color? = DigitalTheme.colorScheme.contentPrimaryTonal1,
    leadingIconSize: Dp = 36.dp,
    trailingIconSize: Dp = 24.dp,
    label: String? = null,
    placeholder: String? = null,
    helpText: String? = null,
    errorText: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    onFocusChanged: ((hasFocus: Boolean) -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val showSecondaryIcon = remember { mutableStateOf(false) }
    val isFocused by interactionSource.collectIsFocusedAsState()
    onFocusChanged?.invoke(isFocused)
    val pattern = Regex("^[0-9]*\$")

    PrimaryInput(
        value = value,
        onValueChange = {
            val maxLength = if (it.text.startsWith("37")) 15 else 16
            if (checkInputValidation(value, maxLength, pattern, it)) {
                onValueChange(it)
                showSecondaryIcon.value = it.text.isNotEmpty()
            }
        },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        placeholder = placeholder,
        visualTransformation = CardFormattingVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = 1,
        interactionSource = interactionSource,
        isError = isError,
        label = label,
        errorText = errorText,
        helpText = helpText,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        leadingIconTint = leadingIconTint,
        trailingTint = trailingTint,
        secondaryTrailingIcon = if (showSecondaryIcon.value) R.drawable.ic_close_small_2 else null,
        secondaryTrailingTint = DigitalTheme.colorScheme.contentPrimary,
        onSecondaryTrailingIconClick = {
            onValueChange(TextFieldValue(""))
            showSecondaryIcon.value = false
        },
        leadingIconSize = leadingIconSize,
        trailingIconSize = trailingIconSize,
    )
}


private fun checkInputValidation(value: TextFieldValue, maxLength: Int, pattern: Regex, textFieldValue: TextFieldValue): Boolean {
    return (value.text.length != maxLength || textFieldValue.text.length <= maxLength)
        && textFieldValue.text.matches(pattern)
}


@Composable
@PreviewLightDark
fun CardInputPreview() {
    val textNormal =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            CardInput(
                value = textNormal.value,
                onValueChange = { textNormal.value = it },
                placeholder = "Քարտի համար",
                trailingIcon = R.drawable.ic_scan,
                trailingTint = DigitalTheme.colorScheme.contentPrimary,
                leadingIcon = DigitalTheme.themedResources.defaultCardIcon,
                leadingIconTint = null,
            )
            VerticalSpacer(20)
            CardInput(
                value = TextFieldValue("1456236558963658"),
                onValueChange = { textNormal.value = it },
                placeholder = "Քարտի համար",
                trailingIcon = R.drawable.ic_scan,
                enabled = false,
                trailingTint = DigitalTheme.colorScheme.contentPrimary,
                leadingIcon = DigitalTheme.themedResources.defaultCardIcon,
                leadingIconTint = null,
            )
            VerticalSpacer(20)
            CardInput(
                value = textNormal.value,
                onValueChange = { textNormal.value = it },
                placeholder = "Քարտի համար",
                isError = true,
                errorText = "Error text",
                trailingIcon = R.drawable.ic_scan,
                leadingIcon = DigitalTheme.themedResources.defaultCardIcon,
                leadingIconTint = null,
                trailingTint = DigitalTheme.colorScheme.contentPrimary
            )
        }
    }
}
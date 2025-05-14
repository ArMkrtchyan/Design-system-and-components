package am.acba.compose.components

import am.acba.component.R
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.inputs.PrimaryInput
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryDropDown(
    modifier: Modifier = Modifier,
    value: TextFieldValue = TextFieldValue(""),
    onValueChange: (TextFieldValue) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
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
    leadingIcon: Int? = null,
    leadingIconTint: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        PrimaryInput(
            modifier,
            value,
            onValueChange,
            enabled,
            readOnly,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            errorText = errorText,
            helpText = helpText,
            label = label,
            trailingIcon = R.drawable.ic_down,
            leadingIcon = leadingIcon,
            leadingIconTint = leadingIconTint
        )
        Button(
            modifier = Modifier.fillMaxSize(),
            onClick = onClick,
            shape = ShapeTokens.shapePrimaryButton,
            colors = ButtonColors(
                contentColor = Color.Transparent,
                containerColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {}

    }
}

@Composable
@PreviewLightDark
fun PrimaryDropDownPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
        ) {
            PrimaryDropDown(onValueChange = {}, value = TextFieldValue("Username"), onClick = {})
            VerticalSpacer(8)
            PrimaryDropDown(onValueChange = {}, value = TextFieldValue("Username"), enabled = false, onClick = {})
        }
    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package am.acba.compose.components.dropDown

import am.acba.component.R
import am.acba.compose.components.bottomSheet.PrimaryBottomSheet
import am.acba.compose.components.inputs.PrimaryInput
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

@Composable
fun ComponentDropDown(
    value: TextFieldValue = TextFieldValue(EMPTY_STRING),
    dropDownModifier: Modifier = Modifier,
    label: String,
    helpText: String? = null,
    isError: Boolean = false,
    errorText: String? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: Int? = null,
    leadingIconTint: Color? = DigitalTheme.colorScheme.contentPrimaryTonal1,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    bottomSheetTitle: String = "",
    bottomSheetProperties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    bottomSheetContentHorizontalPadding: Int = 16,
    bottomSheetContentBottomPadding: Int = 16,
    bottomSheetCalculatePercentForOpenFullScreen: Boolean = true,
    bottomSheetContent: @Composable (sheetState: SheetState, coroutineScope: CoroutineScope) -> Unit,
    openDropDown: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(openDropDown.value) {
        if (openDropDown.value) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    Box {
        PrimaryInput(
            value = value,
            modifier = dropDownModifier.focusRequester(focusRequester),
            label = label,
            helpText = helpText,
            isError = isError,
            errorText = errorText,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            leadingIcon = leadingIcon,
            leadingIconTint = leadingIconTint,
            trailingIcon = if (openDropDown.value) R.drawable.ic_up else R.drawable.ic_down,
            singleLine = true,
            maxLines = maxLines,
        )
        TransparentButton(enabled, openDropDown)
        if (openDropDown.value) {
            PrimaryBottomSheet(
                title = bottomSheetTitle,
                dismiss = {
                    openDropDown.value = false
                },
                properties = bottomSheetProperties,
                contentHorizontalPadding = bottomSheetContentHorizontalPadding.dp,
                contentBottomPadding = bottomSheetContentBottomPadding.dp,
                calculatePercentForOpenFullScreen = bottomSheetCalculatePercentForOpenFullScreen,
                content = bottomSheetContent,
                bottomSheetVisible = true,
            )
        }
    }
}

@Composable
@NonRestartableComposable
private fun TransparentButton(enabled: Boolean, openDropdown: MutableState<Boolean>) {
    Button(
        modifier = Modifier.fillMaxSize(),
        onClick = {
            openDropdown.value = true
        },
        enabled = enabled,
        shape = ShapeTokens.shapePrimaryButton,
        colors = ButtonColors(
            contentColor = Color.Transparent,
            containerColor = Color.Transparent,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {}
}

@Composable
@PreviewLightDark
fun PrimaryDatePickerPreview() {
    DigitalTheme {
        ComponentDropDown(TextFieldValue("DropDown"), Modifier.fillMaxSize(), "DropDown", bottomSheetContent = { _, _ -> })
    }
}

@file:OptIn(ExperimentalMaterial3Api::class)

package am.acba.compose.components.dropDown

import am.acba.component.R
import am.acba.compose.components.bottomSheet.PrimaryBottomSheet
import am.acba.compose.components.bottomSheet.closeBottomSheet
import am.acba.compose.components.dropDown.model.ContentProperties
import am.acba.compose.components.inputs.PrimaryInput
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import kotlinx.coroutines.CoroutineScope

@Composable
fun ComponentDropDown(
    label: String,
    value: TextFieldValue = TextFieldValue(EMPTY_STRING),
    modifier: Modifier = Modifier,
    helpText: String? = null,
    isError: Boolean = false,
    errorText: String? = null,
    enabled: Boolean = true,
    leadingIcon: Int? = null,
    leadingIconTint: Color? = DigitalTheme.colorScheme.contentPrimaryTonal1,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    contentProperties: ContentProperties = ContentProperties(),
    onDismissRequest: () -> Unit = {},
    content: @Composable (sheetState: SheetState, coroutineScope: CoroutineScope, onItemClick: () -> Unit) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val showBottomSheet: MutableState<Boolean> = remember { mutableStateOf(false) }

    LaunchedEffect(showBottomSheet.value) {
        if (showBottomSheet.value) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    Box {
        PrimaryInput(
            value = value,
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showBottomSheet.value = true
                    }
                }
                .then(modifier),
            label = label,
            helpText = helpText,
            isError = isError,
            errorText = errorText,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            leadingIcon = leadingIcon,
            leadingIconTint = leadingIconTint,
            trailingIcon = if (showBottomSheet.value) R.drawable.ic_up else R.drawable.ic_down,
            onTrailingIconClick = { showBottomSheet.value = true },
            singleLine = true,
            maxLines = maxLines,
            durationMillis = 350
        )
    }
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
        content = { sheetState, coroutineScope ->
            content(sheetState, coroutineScope) {
                closeBottomSheet(state = sheetState, scope = coroutineScope) {
                    showBottomSheet.value = false
                    onDismissRequest.invoke()
                }
            }
        },
        bottomSheetVisible = showBottomSheet.value,
    )
}

@Composable
@PreviewLightDark
fun PrimaryDatePickerPreview() {
    DigitalTheme {
        ComponentDropDown("DropDown", TextFieldValue("DropDown"), content = { _, _, _ -> })
    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package am.acba.compose.components.datePicker

import am.acba.component.R
import am.acba.compose.components.datePicker.calendar.PrimaryCalendar
import am.acba.compose.components.datePicker.calendar.model.CalendarMode
import am.acba.compose.components.inputs.PrimaryInput
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark

@Composable
fun PrimaryDatePicker(
    label: String,
    selectedDate: String,
    datePickerState: DatePickerState,
    onDateSelected: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    trailingTint: Color? = DigitalTheme.colorScheme.contentPrimaryTonal1,
    error: String = EMPTY_STRING,
    helpText: String = EMPTY_STRING,
    mode: CalendarMode = CalendarMode.POPUP,
) {
    val showCalendar = remember { mutableStateOf(false) }
    Box {
        PrimaryInput(
            value = TextFieldValue(selectedDate),
            modifier = modifier,
            label = label,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            trailingIcon = R.drawable.ic_calendar,
            trailingTint = trailingTint,
            singleLine = true,
            errorText = error,
            helpText = helpText,
            isError = error.isNotEmpty()
        )
        TransparentButton(enabled, showCalendar)
        if (showCalendar.value) {
            PrimaryCalendar(
                state = datePickerState,
                onDateSelected = onDateSelected,
                mode = mode,
                onDismissRequest = {
                    showCalendar.value = false
                })
        }
    }
}

@Composable
@NonRestartableComposable
private fun TransparentButton(enabled: Boolean, showCalendar: MutableState<Boolean>) {
    Button(
        modifier = Modifier.fillMaxSize(),
        onClick = {
            showCalendar.value = true
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
    PrimaryDatePicker("Choose date", "", rememberDatePickerState(), { mills, string -> })
}
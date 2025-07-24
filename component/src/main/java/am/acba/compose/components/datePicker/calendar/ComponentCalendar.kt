@file:OptIn(ExperimentalMaterial3Api::class)

package am.acba.compose.components.datePicker.calendar

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.bottomSheet.PrimaryBottomSheet
import am.acba.compose.components.bottomSheet.closeBottomSheet
import am.acba.compose.components.datePicker.calendar.model.CalendarMode
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import am.acba.utils.Constants.DATE_FORMAT_DD_MM_YYYY
import am.acba.utils.extensions.orEmpty
import am.acba.utils.extensions.toDateStringFrom
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentCalendar(
    state: DatePickerState,
    onDismissRequest: () -> Unit,
    onDateSelected: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
    mode: CalendarMode = CalendarMode.POPUP,
) {
    Box(modifier = modifier) {
        when (mode) {
            CalendarMode.POPUP -> PopUp(state, onDismissRequest, onDateSelected)
            CalendarMode.MODAL -> BottomSheet(state, onDismissRequest, onDateSelected)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PopUp(state: DatePickerState, onDismissRequest: () -> Unit, onDateSelected: (Long, String) -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            CalendarViewWithActions(state, onDismissRequest, onDateSelected)
        }
    }
}

@Composable
private fun BottomSheet(state: DatePickerState, onDismissRequest: () -> Unit, onDateSelected: (Long, String) -> Unit) {
    PrimaryBottomSheet(
        bottomSheetVisible = true,
        calculatePercentForOpenFullScreen = false,
        contentHorizontalPadding = 0.dp,
        icon = null,
        dismiss = {
            onDismissRequest.invoke()
        }) { sheetState, scope ->
        CalendarViewWithActions(
            state,
            onDismissRequest = {
                closeBottomSheet(state = sheetState, scope = scope) {
                    onDismissRequest.invoke()
                }
            },
            onDateSelected
        )
    }
}

@Composable
private fun CalendarViewWithActions(state: DatePickerState, onDismissRequest: () -> Unit, onDateSelected: (Long, String) -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(DigitalTheme.colorScheme.backgroundTonal1, shape = ShapeTokens.shapeCalendar),
    ) {
        Column(modifier = Modifier.wrapContentSize()) {
            Calendar(state)
            ActionRow(state, onDismissRequest, onDateSelected)
        }
    }
}

@Composable
private fun Calendar(state: DatePickerState) {
    DatePicker(
        state = state,
        showModeToggle = false,
        modifier = Modifier.clip(ShapeTokens.shapeCalendar),
        colors = DatePickerDefaults.colors(
            containerColor = DigitalTheme.colorScheme.backgroundTonal1,
            titleContentColor = DigitalTheme.colorScheme.contentPrimary,
            headlineContentColor = DigitalTheme.colorScheme.contentPrimary,
            weekdayContentColor = DigitalTheme.colorScheme.contentPrimaryTonal2,
            yearContentColor = DigitalTheme.colorScheme.contentPrimary,
            selectedYearContentColor = DigitalTheme.colorScheme.contentSecondary,
            selectedYearContainerColor = DigitalTheme.colorScheme.contentBrand,
            dayContentColor = DigitalTheme.colorScheme.contentPrimary,
            selectedDayContentColor = DigitalTheme.colorScheme.contentSecondary,
            selectedDayContainerColor = DigitalTheme.colorScheme.contentBrand,
            todayContentColor = DigitalTheme.colorScheme.contentBrand,
            todayDateBorderColor = DigitalTheme.colorScheme.transparent,
            navigationContentColor = DigitalTheme.colorScheme.contentPrimary,
            disabledDayContentColor = DigitalTheme.colorScheme.contentPrimaryTonal2,
            dividerColor = DigitalTheme.colorScheme.contentPrimaryTonal2,
        )
    )
}

@Composable
@NonRestartableComposable
private fun ActionRow(state: DatePickerState, onDismissRequest: () -> Unit, onDateSelected: (Long, String) -> Unit) {
    var confirmDateMills by remember { mutableLongStateOf(state.selectedDateMillis.orEmpty()) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = {
            state.selectedDateMillis = confirmDateMills
            onDismissRequest.invoke()
        }) {
            PrimaryText(
                stringResource(R.string.cancel),
                color = DigitalTheme.colorScheme.contentBrand,
                style = DigitalTheme.typography.body1Regular
            )
        }
        HorizontalSpacer(8)
        TextButton(onClick = {
            val selectedDateMills = state.selectedDateMillis.orEmpty()
            val selectedDateString = selectedDateMills toDateStringFrom DATE_FORMAT_DD_MM_YYYY
            confirmDateMills = selectedDateMills
            onDateSelected.invoke(selectedDateMills, selectedDateString)
            onDismissRequest.invoke()
        }) {
            PrimaryText(
                stringResource(R.string.ok),
                color = DigitalTheme.colorScheme.contentBrand,
                style = DigitalTheme.typography.body1Regular
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@PreviewLightDark
private fun AcbaCalendarPreview() {
    DigitalTheme {
        ComponentCalendar(rememberDatePickerState(), onDismissRequest = {}, onDateSelected = { _, _ -> })
    }
}
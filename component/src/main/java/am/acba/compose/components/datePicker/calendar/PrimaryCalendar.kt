@file:OptIn(ExperimentalMaterial3Api::class)

package am.acba.compose.components.datePicker.calendar

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryCalendar(
    state: DatePickerState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    mode: CalendarMode = CalendarMode.POPUP,
) {
    Box(modifier = modifier) {
        when (mode) {
            CalendarMode.POPUP -> PopUp(state, onDismissRequest)
            CalendarMode.MODAL -> TODO()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BoxScope.PopUp(state: DatePickerState, onDismissRequest: () -> Unit) {
    Popup(
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
                    .background(DigitalTheme.colorScheme.backgroundTonal1, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Calendar(state)
                    ActionRow(onDismissRequest)
                }
            }
        }
    }
}

@Composable
private fun Calendar(state: DatePickerState) {
    DatePicker(
        state = state,
        showModeToggle = false,
        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
        colors = DatePickerDefaults.colors(
            containerColor = DigitalTheme.colorScheme.backgroundTonal1,
            titleContentColor = DigitalTheme.colorScheme.contentPrimary,
            headlineContentColor = DigitalTheme.colorScheme.contentPrimary,
            weekdayContentColor = DigitalTheme.colorScheme.contentPrimaryTonal2,
            yearContentColor = DigitalTheme.colorScheme.contentPrimary,
            selectedYearContentColor = DigitalTheme.colorScheme.contentPrimary,
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
private fun ActionRow(onDismissRequest: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = onDismissRequest) {
            PrimaryText(
                "Cancel",
                color = DigitalTheme.colorScheme.contentBrand,
                style = DigitalTheme.typography.body1Regular
            )
        }
        HorizontalSpacer(8)
        TextButton(onClick = onDismissRequest) {
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
    PrimaryCalendar(rememberDatePickerState(), onDismissRequest = {})
}

enum class CalendarMode {
    POPUP,
    MODAL
}
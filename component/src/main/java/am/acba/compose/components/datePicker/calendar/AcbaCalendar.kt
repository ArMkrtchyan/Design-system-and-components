package am.acba.compose.components.datePicker.calendar

import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcbaCalendar(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    mode: CalendarMode = CalendarMode.POPUP,
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(modifier = modifier) {
        if (isVisible) {
            when (mode) {
                CalendarMode.POPUP -> PopUp(onDismissRequest)
                CalendarMode.MODAL -> TODO()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PopUp(onDismissRequest: () -> Unit) {
    Popup(
        onDismissRequest = onDismissRequest,
        alignment = Alignment.TopCenter,
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(DigitalTheme.colorScheme.backgroundTonal1, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            DatePicker(
                state = rememberDatePickerState(),
                showModeToggle = false,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .padding(8.dp),
                colors = DatePickerDefaults.colors(
                    containerColor = DigitalTheme.colorScheme.backgroundTonal1,
                    titleContentColor = DigitalTheme.colorScheme.contentPrimary,
                    headlineContentColor = DigitalTheme.colorScheme.contentPrimary,
                    weekdayContentColor = DigitalTheme.colorScheme.contentPrimaryTonal2,
                    yearContentColor = DigitalTheme.colorScheme.contentPrimary,
                    selectedYearContentColor = DigitalTheme.colorScheme.contentPrimary,
                    selectedYearContainerColor = DigitalTheme.colorScheme.contentBrand,
                    dayContentColor = DigitalTheme.colorScheme.contentPrimary,
                    selectedDayContentColor = DigitalTheme.colorScheme.contentPrimary,
                    selectedDayContainerColor = DigitalTheme.colorScheme.contentBrand,
                    todayContentColor = DigitalTheme.colorScheme.contentBrand,
                    todayDateBorderColor = DigitalTheme.colorScheme.transparent,
                    navigationContentColor = DigitalTheme.colorScheme.contentPrimary,
                    disabledDayContentColor = DigitalTheme.colorScheme.contentPrimaryTonal2,
                    dividerColor = DigitalTheme.colorScheme.contentPrimaryTonal2,
                )
            )
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
@PreviewLightDark
private fun AcbaCalendarPreview() {
    AcbaCalendar(true, onDismissRequest = {})
}

enum class CalendarMode {
    POPUP,
    MODAL
}
@file:OptIn(ExperimentalMaterial3Api::class)

package am.acba.compose.components.datePicker

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.datePicker.calendar.PrimaryCalendar
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryDatePicker(
    label: String,
    selectedDate: String,
    datePickerState: DatePickerState,
    onDateSelected: (Long, String) -> Unit,
    modifier: Modifier = Modifier,
    labelStyleInitial: TextStyle = DigitalTheme.typography.body1Regular,
    labelStyleFloating: TextStyle = DigitalTheme.typography.smallRegular,
    labelColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    dateTextStyle: TextStyle = DigitalTheme.typography.body1Regular,
    dateTextColor: Color = DigitalTheme.colorScheme.contentPrimary,
    error: String = EMPTY_STRING,
    errorTextStyle: TextStyle = DigitalTheme.typography.smallRegular,
    errorTextColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1
) {
    val labelStyle = if (selectedDate.isEmpty()) labelStyleInitial else labelStyleFloating
    val labelColor = if (error.isEmpty()) labelColor else DigitalTheme.colorScheme.contentDangerTonal1
    val showCalendar = remember { mutableStateOf(false) }
    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            DatePickerCard(label, selectedDate, labelStyle, labelColor, dateTextStyle, dateTextColor, error, showCalendar)
            error.takeIf { it.isNotEmpty() }?.let {
                VerticalSpacer(8)
                ErrorRow(it, errorTextStyle, errorTextColor)
            }
        }
        if (showCalendar.value) {
            PrimaryCalendar(
                state = datePickerState,
                onDateSelected = onDateSelected,
                onDismissRequest = {
                    showCalendar.value = false
                })
        }
    }
}

@Composable
private fun DatePickerCard(
    label: String,
    selectedDate: String,
    labelStyle: TextStyle,
    labelColor: Color,
    dateTextStyle: TextStyle,
    dateTextColor: Color,
    error: String,
    showCalendar: MutableState<Boolean>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                DigitalTheme.colorScheme.backgroundTonal2,
                shape = RoundedCornerShape(12)
            ).let {
                if (error.isNotEmpty())
                    it.border(1.dp, DigitalTheme.colorScheme.borderDanger, RoundedCornerShape(12.dp))
                else it
            }
            .padding(16.dp)
            .clickable {
                showCalendar.value = true
            }
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            PrimaryText(label, style = labelStyle, color = labelColor)
            selectedDate.takeIf { it.isNotEmpty() }?.let {
                VerticalSpacer(4)
                PrimaryText(selectedDate, style = dateTextStyle, color = dateTextColor)
            }
        }
        PrimaryIcon(painterResource(R.drawable.ic_calendar))
    }
}

@Composable
private fun ErrorRow(error: String, style: TextStyle, color: Color) {
    Row {
        PrimaryIcon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(R.drawable.ic_info),
            tint = DigitalTheme.colorScheme.contentDangerTonal1
        )
        HorizontalSpacer(4)
        PrimaryText(
            error,
            style = style,
            color = color,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterVertically),
        )
    }
}

@Composable
@PreviewLightDark
fun PrimaryDatePickerPreview() {
    PrimaryDatePicker("Choose date", "", rememberDatePickerState(), { mills, string -> })
}
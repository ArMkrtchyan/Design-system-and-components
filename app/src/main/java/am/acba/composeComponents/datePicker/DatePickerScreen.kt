package am.acba.composeComponents.datePicker

import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.datePicker.PrimaryDatePicker
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerScreen(title: String = EMPTY_STRING) {
    var selectedDate by remember { mutableStateOf(EMPTY_STRING) }
    Box(
        modifier = Modifier
            .background(DigitalTheme.colorScheme.backgroundBase)
            .fillMaxSize()
            .padding(
                bottom = TopAppBarDefaults.windowInsets
                    .only(WindowInsetsSides.Bottom)
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
    ) {
        Column(Modifier.fillMaxSize()) {
            PrimaryToolbar(title = title)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                DescriptionText()
                VerticalSpacer(24)
                PrimaryDatePicker("ChooseDate", selectedDate, onDateSelected = { dateMills, dateString ->
                    selectedDate = dateString
                })
            }
        }
    }
}

@Composable
@NonRestartableComposable
private fun DescriptionText() {
    PrimaryText(
        "Date Picker allows users to pick single or multiple dates. " +
            "They typically appear in forms, filters and etc. Use this component " +
            " if you need to collect some information from the user or filter out a range."
    )
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        DatePickerScreen()
    }
}
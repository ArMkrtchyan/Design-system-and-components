package am.acba.composeComponents.controls

import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.controls.PrimaryCheckbox
import am.acba.compose.components.controls.PrimaryRadioButton
import am.acba.compose.components.controls.PrimarySwitch
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlsScreen(title: String = "") {
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
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    val checkboxState = remember { mutableStateOf(ToggleableState.Off) }
                    val checkbox2State = remember { mutableStateOf(ToggleableState.Off) }
                    PrimaryCheckbox(checkboxState.value, onClick = { checkboxState.value = it })
                    VerticalSpacer(8)
                    PrimaryCheckbox(
                        state = checkbox2State.value,
                        useIndeterminateState = true,
                        onClick = { checkbox2State.value = it }, text = "sd s ds "
                    )
                    VerticalSpacer(8)
                    PrimaryCheckbox(enabled = false)
                    VerticalSpacer(8)
                    PrimaryCheckbox(state = ToggleableState.Off, enabled = false)
                    VerticalSpacer(8)
                    PrimaryCheckbox(state = ToggleableState.Indeterminate, enabled = false)
                    VerticalSpacer(8)
                }
                HorizontalSpacer(20)
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    val isChecked = remember { mutableStateOf(false) }
                    PrimaryRadioButton(selected = isChecked.value, onClick = {
                        isChecked.value = !isChecked.value
                    })
                    VerticalSpacer(16)
                    val isChecked2 = remember { mutableStateOf(true) }
                    PrimaryRadioButton(selected = isChecked2.value, onClick = {
                        isChecked2.value = !isChecked2.value
                    })
                    VerticalSpacer(16)
                    val isChecked3 = remember { mutableStateOf(false) }
                    PrimaryRadioButton(selected = isChecked3.value, enabled = false, onClick = {
                        isChecked3.value = !isChecked3.value
                    })
                    VerticalSpacer(16)
                    val isChecked4 = remember { mutableStateOf(true) }
                    PrimaryRadioButton(selected = isChecked4.value, enabled = false, onClick = {
                        isChecked4.value = !isChecked4.value
                    })
                    VerticalSpacer(16)
                }
                HorizontalSpacer(20)
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    val isChecked = remember { mutableStateOf(false) }
                    PrimarySwitch(checked = isChecked.value, onCheckedChange = {
                        isChecked.value = it
                    })
                    VerticalSpacer(16)
                    val isChecked2 = remember { mutableStateOf(true) }
                    PrimarySwitch(checked = isChecked2.value, onCheckedChange = {
                        isChecked2.value = it
                    })
                    VerticalSpacer(16)
                    val isChecked3 = remember { mutableStateOf(false) }
                    PrimarySwitch(checked = isChecked3.value, enabled = false, onCheckedChange = {
                        isChecked3.value = it
                    })
                    VerticalSpacer(16)
                    val isChecked4 = remember { mutableStateOf(true) }
                    PrimarySwitch(checked = isChecked4.value, enabled = false, onCheckedChange = {
                        isChecked4.value = it
                    })
                    VerticalSpacer(16)
                }
                HorizontalSpacer(20)
            }
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        ControlsScreen()
    }
}
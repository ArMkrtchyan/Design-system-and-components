package am.acba.compose.components.controls

import am.acba.compose.VerticalSpacer
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PrimarySwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.height(32.dp),
        thumbContent = thumbContent,
        enabled = enabled,
        colors = SwitchDefaults.colors().copy(
            checkedThumbColor = DigitalTheme.colorScheme.contentSecondary,
            checkedTrackColor = DigitalTheme.colorScheme.contentBrand,
            checkedBorderColor = DigitalTheme.colorScheme.contentBrand,
            checkedIconColor = DigitalTheme.colorScheme.contentSecondary,
            uncheckedThumbColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
            uncheckedTrackColor = DigitalTheme.colorScheme.backgroundTonal2,
            uncheckedBorderColor = DigitalTheme.colorScheme.borderBase,
            uncheckedIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
            disabledCheckedThumbColor = DigitalTheme.colorScheme.contentSecondaryDisable,
            disabledCheckedTrackColor = DigitalTheme.colorScheme.backgroundBrandDisable,
            disabledCheckedBorderColor = DigitalTheme.colorScheme.backgroundBrandDisable,
            disabledCheckedIconColor = DigitalTheme.colorScheme.contentSecondaryDisable,
            disabledUncheckedThumbColor = DigitalTheme.colorScheme.contentBrandTonal1Disable,
            disabledUncheckedTrackColor = DigitalTheme.colorScheme.backgroundBrandDisable,
            disabledUncheckedBorderColor = DigitalTheme.colorScheme.borderBrandTonal1Disable,
            disabledUncheckedIconColor = DigitalTheme.colorScheme.contentBrandTonal1Disable,
        ),
        interactionSource = interactionSource
    )
}


@Composable
@PreviewLightDark
fun PrimarySwitchPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
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
    }
}
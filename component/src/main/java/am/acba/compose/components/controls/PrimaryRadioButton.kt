package am.acba.compose.components.controls

import am.acba.compose.common.VerticalSpacer
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null
) {
    RadioButton(
        selected = selected,
        onClick = onClick,
        modifier = modifier.size(24.dp),
        enabled = enabled,
        colors = RadioButtonDefaults.colors().copy(
            selectedColor = DigitalTheme.colorScheme.contentBrand,
            unselectedColor = DigitalTheme.colorScheme.borderBase,
            disabledSelectedColor = DigitalTheme.colorScheme.contentBrandTonal1Disable,
            disabledUnselectedColor = DigitalTheme.colorScheme.contentBrandTonal1Disable
        ),
        interactionSource = interactionSource
    )
}


@Composable
@PreviewLightDark
fun PrimaryRadioButtonPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            val isChecked = remember { mutableStateOf(false) }
            PrimaryRadioButton(selected = isChecked.value, onClick = {
                isChecked.value = !isChecked.value
            })
            VerticalSpacer(16.dp)
            val isChecked2 = remember { mutableStateOf(true) }
            PrimaryRadioButton(selected = isChecked2.value, onClick = {
                isChecked2.value = !isChecked2.value
            })
            VerticalSpacer(16.dp)
            val isChecked3 = remember { mutableStateOf(false) }
            PrimaryRadioButton(selected = isChecked3.value, enabled = false, onClick = {
                isChecked3.value = !isChecked3.value
            })
            VerticalSpacer(16.dp)
            val isChecked4 = remember { mutableStateOf(true) }
            PrimaryRadioButton(selected = isChecked4.value, enabled = false, onClick = {
                isChecked4.value = !isChecked4.value
            })
            VerticalSpacer(16.dp)
        }
    }
}
package am.acba.compose.components.controls

import am.acba.component.R
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import am.acba.utils.extensions.id
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryCheckbox(
    state: ToggleableState = ToggleableState.Off,
    onClick: (ToggleableState) -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    useIndeterminateState: Boolean = false,
    text: String? = null,
    textStyle: TextStyle = DigitalTheme.typography.subTitle1Regular,
    textColor: Color = DigitalTheme.colorScheme.contentPrimary,
) {
    val states: List<ToggleableState> = if (useIndeterminateState) {
        arrayListOf(ToggleableState.Off, ToggleableState.Indeterminate, ToggleableState.On)
    } else {
        arrayListOf(ToggleableState.Off, ToggleableState.On)
    }
    val currentState = remember { mutableIntStateOf(states.indexOf(state)) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor: Color by animateColorAsState(if (isPressed) DigitalTheme.colorScheme.backgroundTonal2 else Color.Transparent)
    Row(modifier = modifier.wrapContentWidth().id("checkbox")) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(backgroundColor, ShapeTokens.shapeRound)
                .triStateToggleable(
                    state = state,
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Checkbox,
                    enabled = enabled,
                    onClick = {
                        currentState.intValue = (currentState.intValue + 1) % states.size
                        onClick.invoke(states[currentState.intValue])
                    }
                )
        ) {
            when (state) {
                ToggleableState.On -> CheckboxSelectedState(enabled)
                ToggleableState.Off -> CheckboxUnSelectedState(enabled)
                ToggleableState.Indeterminate -> CheckboxIndeterminateState(enabled)
            }
        }
        if (!text.isNullOrEmpty()) {
            HorizontalSpacer(8.dp)
            Column(modifier = Modifier.wrapContentWidth()) {
                PrimaryText(text = text, color = textColor, style = textStyle, modifier = Modifier.wrapContentWidth())
            }
        }
    }
}

@Composable
private fun BoxScope.CheckboxSelectedState(isEnabled: Boolean) {
    val backgroundColor = if (isEnabled) {
        DigitalTheme.colorScheme.backgroundBrand
    } else {
        DigitalTheme.colorScheme.backgroundBrandDisable
    }
    val iconColor = if (isEnabled) {
        DigitalTheme.colorScheme.contentSecondary
    } else {
        DigitalTheme.colorScheme.contentSecondaryDisable
    }
    Box(
        modifier = Modifier
            .size(24.dp)
            .align(Alignment.Center)
            .padding(3.dp)
            .background(backgroundColor, ShapeTokens.shapeCheckbox),
    ) {

    }

    PrimaryIcon(
        modifier = Modifier
            .size(24.dp)
            .align(Alignment.Center),
        painter = painterResource(R.drawable.ic_success_small),
        tint = iconColor
    )
}

@Composable
private fun BoxScope.CheckboxUnSelectedState(isEnabled: Boolean) {
    val borderColor = if (isEnabled) {
        DigitalTheme.colorScheme.borderBase
    } else {
        DigitalTheme.colorScheme.borderBrandTonal1Disable
    }
    Spacer(
        modifier = Modifier
            .size(18.dp)
            .align(Alignment.Center)
            .border(1.5.dp, borderColor, ShapeTokens.shapeCheckbox),
    )
}

@Composable
private fun BoxScope.CheckboxIndeterminateState(isEnabled: Boolean) {
    val backgroundColor = if (isEnabled) {
        DigitalTheme.colorScheme.backgroundBrand
    } else {
        DigitalTheme.colorScheme.backgroundBrandDisable
    }
    val iconColor = if (isEnabled) {
        DigitalTheme.colorScheme.contentSecondary
    } else {
        DigitalTheme.colorScheme.contentSecondaryDisable
    }
    Box(
        modifier = Modifier
            .size(24.dp)
            .align(Alignment.Center)
            .padding(3.dp)
            .background(backgroundColor, ShapeTokens.shapeCheckbox),
    ) {
        Spacer(
            modifier = Modifier
                .width(13.dp)
                .height(2.dp)
                .align(Alignment.Center)
                .background(iconColor, ShapeTokens.shapeRound),
        )
    }
}

@Composable
@PreviewLightDark
fun PrimaryCheckboxPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            val checkboxState = remember { mutableStateOf(ToggleableState.Off) }
            PrimaryCheckbox(
                checkboxState.value,
                useIndeterminateState = true,
                onClick = { checkboxState.value = it },
                text = "Checkbox title Checkbox title Checkbox title Checkbox title"
            )
            VerticalSpacer(20.dp)
            PrimaryCheckbox(state = ToggleableState.On)
            VerticalSpacer(20.dp)
            PrimaryCheckbox(state = ToggleableState.Indeterminate)
            VerticalSpacer(20.dp)
            PrimaryCheckbox(enabled = false)
            VerticalSpacer(20.dp)
            PrimaryCheckbox(state = ToggleableState.Off, enabled = false)
            VerticalSpacer(20.dp)
            PrimaryCheckbox(state = ToggleableState.Indeterminate, enabled = false)
            VerticalSpacer(20.dp)
        }
    }
}
package am.acba.compose.components.inputs.phoneInput

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.inputs.Label
import am.acba.compose.components.inputs.SupportAndErrorTexts
import am.acba.compose.components.inputs.createStateColors
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PhoneNumberInput(
    modifier: Modifier = Modifier,
    value: TextFieldValue = TextFieldValue(""),
    onValueChange: (TextFieldValue) -> Unit,
    helpText: String? = null,
    label: String? = null,
    placeholder: String? = null,
    errorText: String? = null,
    regionCode: String? = null,
    isValidNumber: Boolean = true,
    showArrow: Boolean = true,
    isError: Boolean = false,
    onRegionClick: (() -> Unit)? = null,
    onPickContactClick: (() -> Unit)? = null,
    onFocusChanged: ((hasFocus: Boolean) -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    var inputModifier: Modifier = Modifier
    var regionModifier: Modifier = Modifier
    when {
        isError -> {
            inputModifier = errorBorderForPhoneInput()
            regionModifier = errorBorderForRegionCode()
        }

        isFocused -> {
            inputModifier = focusedBorderForPhoneInput()
            regionModifier = focusedBorderForRegion()
        }

        !isFocused && value.text.isNotEmpty() -> {
            onValueChange(TextFieldValue(value.text))

        }
    }
    onFocusChanged?.invoke(isFocused)
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RegionCode(regionModifier, showArrow, regionCode = regionCode ?: "", onRegionClick)
            HorizontalSpacer(width = 1)
            PhoneTextField(
                modifier = inputModifier,
                value = value,
                onValueChange = onValueChange,
                onPickContactClick = onPickContactClick,
                label = label,
                placeholder = placeholder,
                isError = !isValidNumber,
                maxLength = 10
            )
        }
        SupportAndErrorTexts(isError, true, errorText, helpText)
    }
}

@Composable
fun RegionCode(
    modifier: Modifier = Modifier,
    showArrow: Boolean,
    regionCode: String,
    onRegionClick: (() -> Unit)?
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .height(58.dp)
            .background(shape = ShapeTokens.shapeCurrencyInput, color = DigitalTheme.colorScheme.backgroundTonal2)
            .padding(horizontal = 12.dp)
            .clickable {
                onRegionClick?.invoke()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painterResource(R.drawable.ic_flag_am), contentDescription = "", modifier = Modifier
                .width(20.dp)
                .height(20.dp),
            alpha = 1f
        )
        PrimaryText(
            modifier = Modifier.padding(start = 4.dp),
            text = regionCode,
            style = DigitalTheme.typography.body1Regular,
            color = DigitalTheme.colorScheme.contentPrimaryTonal1
        )
        if (showArrow) {
            PrimaryIcon(
                painterResource(R.drawable.ic_down), modifier = Modifier
                    .padding(start = 4.dp)
                    .width(16.dp)
                    .height(16.dp),
                tint = DigitalTheme.colorScheme.contentPrimary
            )
        }
    }
}

@Composable
fun RowScope.PhoneTextField(
    modifier: Modifier,
    value: TextFieldValue,
    label: String? = null,
    placeholder: String?,
    onValueChange: (TextFieldValue) -> Unit,
    onPickContactClick: (() -> Unit)?,
    maxLength: Int,
    isError: Boolean,
) {
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = placeholder?.let { { Text(text = it) } },
        shape = ShapeTokens.shapeCurrency,
        modifier = modifier
            .height(58.dp)
            .weight(1f),
        label = label?.let { { Label(text = label, isError = isError, isEnabled = true) } },
        colors = createStateColors(),

        trailingIcon = {
            IconButton(onClick = { onPickContactClick?.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_contacts),
                    contentDescription = "Contacts Icon",
                    tint = DigitalTheme.colorScheme.contentPrimary
                )
            }
        }

    )
}

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun errorBorderForRegionCode() =
    Modifier.border(
        1.dp,
        DigitalTheme.colorScheme.contentDangerTonal1,
        ShapeTokens.shapeCurrencyInput
    )

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun errorBorderForPhoneInput() =
    Modifier.border(
        1.dp,
        DigitalTheme.colorScheme.contentDangerTonal1,
        ShapeTokens.shapeCurrency
    )

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun focusedBorderForPhoneInput() =
    Modifier.border(
        1.dp,
        DigitalTheme.colorScheme.borderPrimary,
        ShapeTokens.shapeCurrency
    )

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun focusedBorderForRegion() =
    Modifier.border(
        1.dp,
        DigitalTheme.colorScheme.borderPrimary,
        ShapeTokens.shapeCurrencyInput
    )


@Composable
@PreviewLightDark
fun PhoneInputPreview() {
    val textNormal =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            PhoneNumberInput(
                value = textNormal.value,
                onValueChange = { textNormal.value = it },
                regionCode = "+374",
                helpText = "help text",
                errorText = "error text",
                label = "Phone",
                placeholder = "Phone Number",
                isError = false,
                isValidNumber = true,
            )
        }
    }
}

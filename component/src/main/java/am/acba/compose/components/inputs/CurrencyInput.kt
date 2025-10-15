package am.acba.compose.components.inputs


import am.acba.component.R
import am.acba.component.extensions.numberFormatting
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.inputs.visualTransformations.AmountFormattingVisualTransformation
import am.acba.compose.components.inputs.visualTransformations.MaxLengthVisualTransformation
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyInput(
    modifier: Modifier = Modifier,
    value: TextFieldValue = TextFieldValue(""),
    onValueChange: (TextFieldValue) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    helpText: String? = null,
    errorText: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    maxLength: Int = 15,
    returnTextWhenValueZero: String = "",
    labelMaxLines: Int = 1,
    formatDecimal: Boolean = false,
    showArrow: Boolean = false,
    autoFormatting: Boolean = true,
    onCurrencyClick: (() -> Unit)? = null,
    onFocusChanged: ((hasFocus: Boolean) -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    var inputModifier: Modifier = Modifier
    var currencyModifier: Modifier = Modifier
    when {
        isError -> {
            inputModifier = errorBorderForAmountInput()
            currencyModifier = errorBorderForCurrency()
        }

        isFocused -> {
            inputModifier = focusedBorderForAmountInput()
            currencyModifier = focusedBorderForCurrency()
        }

        !isFocused && formatDecimal && value.text.isNotEmpty() -> {
            onValueChange(
                TextFieldValue(
                    value.text.replace(",", "").numberFormatting(returnTextWhenValueZero).replace(",", "")
                )
            )

        }
    }
    onFocusChanged?.invoke(isFocused)
    Column(
        modifier = modifier
    ) {
        Row {
            AmountTextField(
                value,
                onValueChange,
                maxLength,
                inputModifier,
                enabled,
                readOnly,
                placeholder,
                formatDecimal,
                autoFormatting,
                keyboardOptions,
                keyboardActions,
                interactionSource,
                isError,
                label,
                labelMaxLines
            )
            HorizontalSpacer(1.dp)
            CurrencyField(currencyModifier, enabled, showArrow, onCurrencyClick)
        }
        SupportAndErrorTexts(isError, enabled, errorText, helpText)
    }
}

@Composable
private fun RowScope.AmountTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    maxLength: Int,
    modifier: Modifier,
    enabled: Boolean,
    readOnly: Boolean,
    placeholder: String?,
    formatDecimal: Boolean,
    autoFormatting: Boolean = true,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    interactionSource: MutableInteractionSource,
    isError: Boolean,
    label: String?,
    labelMaxLines: Int = 1
) {
    val pattern = remember {
        if (formatDecimal) Regex("^\\d*\\.?\\d*\$")
        else Regex("^[0-9]*\$")
    }
    val visualTransformation = if (autoFormatting) {
        AmountFormattingVisualTransformation(formatDecimal = formatDecimal)
    } else {
        MaxLengthVisualTransformation(maxLength)
    }
    TextField(
        value = value,
        onValueChange = {
            if (checkInputValidation(value, maxLength, pattern, it))
                onValueChange(it)
        },
        modifier = modifier
            .weight(1f)
            .height(58.dp),
        enabled = enabled,
        readOnly = readOnly,
        placeholder = placeholder?.let { { Label(text = placeholder, maxLines = labelMaxLines) } },
        shape = ShapeTokens.shapeCurrencyInput,
        textStyle = DigitalTheme.typography.body1Regular,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = 1,
        interactionSource = interactionSource,
        colors = createStateColors(),
        isError = isError,
        label = label?.let { { Label(text = label, isError = isError, isEnabled = enabled, maxLines = labelMaxLines) } },
    )
}

private fun checkInputValidation(
    value: TextFieldValue,
    maxLength: Int,
    pattern: Regex,
    textFieldValue: TextFieldValue
): Boolean {
    val splitTextArray = textFieldValue.text.split(".")
    val isDecimal = splitTextArray.size == 2
    val isDotPositionValid = !isDecimal || splitTextArray[1].length <= 2
    return (value.text.length != maxLength || textFieldValue.text.length <= maxLength)
        && textFieldValue.text.matches(pattern)
        && isDotPositionValid
        && !textFieldValue.text.startsWith(".")
        && !(textFieldValue.text.length > 1 && textFieldValue.text.startsWith("0"))
}

@Composable
private fun CurrencyField(
    modifier: Modifier,
    enabled: Boolean,
    showArrow: Boolean,
    onCurrencyClick: (() -> Unit)?
) {
    var currencyBackgroundColor: Color
    var currencyTextColor: Color
    var flagOpacity: Float
    if (enabled) {
        currencyBackgroundColor = DigitalTheme.colorScheme.backgroundTonal2
        currencyTextColor = DigitalTheme.colorScheme.contentPrimaryTonal1
        flagOpacity = 1f
    } else {
        currencyBackgroundColor = DigitalTheme.colorScheme.backgroundTonal2Disable
        currencyTextColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable
        flagOpacity = 0.5f
    }
    modifier

    Row(
        modifier = modifier
            .wrapContentWidth()
            .height(58.dp)
            .background(shape = ShapeTokens.shapeCurrency, color = currencyBackgroundColor)
            .padding(horizontal = 12.dp)
            .clickable(enabled = showArrow && enabled) {
                onCurrencyClick?.invoke()
            },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Image(
            painterResource(R.drawable.ic_flag_am), contentDescription = "", modifier = Modifier
                .width(20.dp)
                .height(20.dp),
            alpha = flagOpacity
        )
        HorizontalSpacer(4.dp)
        PrimaryText("AMD", style = DigitalTheme.typography.body1Regular, color = currencyTextColor)
        HorizontalSpacer(2.dp)
        if (showArrow) {
            PrimaryIcon(
                painterResource(R.drawable.ic_down), modifier = Modifier
                    .width(16.dp)
                    .height(16.dp),
                tint = currencyTextColor
            )
        }
    }
}

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun errorBorderForAmountInput() = Modifier.border(
    1.dp,
    DigitalTheme.colorScheme.contentDangerTonal1,
    ShapeTokens.shapeCurrencyInput
)

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun errorBorderForCurrency() =
    Modifier.border(
        1.dp,
        DigitalTheme.colorScheme.contentDangerTonal1,
        ShapeTokens.shapeCurrency
    )


@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun focusedBorderForAmountInput() =
    Modifier.border(
        1.dp,
        DigitalTheme.colorScheme.borderPrimary,
        ShapeTokens.shapeCurrencyInput
    )


@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
private fun focusedBorderForCurrency() =
    Modifier.border(
        1.dp,
        DigitalTheme.colorScheme.borderPrimary,
        ShapeTokens.shapeCurrency
    )


@Composable
@PreviewLightDark
fun CurrencyInputPreview() {
    val textNormal =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            CurrencyInput(
                value = textNormal.value,
                onValueChange = { textNormal.value = it },
                label = "Amount",
                isError = true,
                helpText = "Min. 50,000.00 AMD",
            )
        }
    }
}
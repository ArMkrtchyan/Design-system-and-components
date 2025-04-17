package am.acba.compose.components.inputs


import am.acba.component.R
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.inputs.visualTransformations.AmountFormattingVisualTransformation
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
import androidx.compose.foundation.layout.Spacer
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
    formatDecimal: Boolean = false,
    showArrow: Boolean = false,
    onCurrencyClick: (() -> Unit)? = null,
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
    }
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
                keyboardOptions,
                keyboardActions,
                interactionSource,
                isError,
                label
            )
            Spacer(modifier = Modifier.width(1.dp))
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
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    interactionSource: MutableInteractionSource,
    isError: Boolean,
    label: String?
) {
    val pattern = remember {
        if (formatDecimal) Regex("^\\d*\\.?\\d*\$")
        else Regex("^[0-9]*\$")
    }
    TextField(
        value = value,
        onValueChange = {
            if ((value.text.length != maxLength || it.text.length <= maxLength) && it.text.matches(pattern))
                onValueChange(it)
        },
        modifier = modifier
            .weight(1f)
            .height(58.dp),
        enabled = enabled,
        readOnly = readOnly,
        placeholder = placeholder?.let { { Label(text = placeholder) } },
        shape = ShapeTokens.shapeCurrencyInput,
        textStyle = DigitalTheme.typography.body1Regular,
        visualTransformation = AmountFormattingVisualTransformation(
            maxLength = maxLength,
            formatDecimal = formatDecimal
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = 1,
        interactionSource = interactionSource,
        colors = createStateColors(),
        isError = isError,
        label = label?.let { { Label(text = label, isError = isError, isEnabled = enabled) } },
    )
}

@Composable
private fun CurrencyField(modifier: Modifier, enabled: Boolean, showArrow: Boolean, onCurrencyClick: (() -> Unit)?) {
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
        Spacer(modifier = Modifier.width(4.dp))
        PrimaryText("AMD", style = DigitalTheme.typography.body1Regular, color = currencyTextColor)
        Spacer(modifier = Modifier.width(2.dp))
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
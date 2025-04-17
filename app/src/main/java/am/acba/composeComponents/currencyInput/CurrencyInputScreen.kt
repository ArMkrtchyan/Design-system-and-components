package am.acba.composeComponents.currencyInput

import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.inputs.CurrencyInput
import am.acba.compose.theme.DigitalTheme
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyInputScreen(title: String = "") {
    val currencyText1 =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val currencyText2 =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val currencyText3 =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val currencyText4 =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
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
            PrimaryToolbar(title = title, actions = {
                IconButton(onClick = {

                }) {

                }
            })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                CurrencyInput(
                    value = currencyText1.value,
                    onValueChange = { currencyText1.value = it },
                    label = "Amount",
                    helpText = "Min. 50,000.00 AMD",
                    showArrow = true,
                    onCurrencyClick = { Toast.makeText(context, "On currency click", Toast.LENGTH_SHORT).show() }
                )
                Spacer(modifier = Modifier.height(16.dp))
                CurrencyInput(
                    value = currencyText2.value,
                    onValueChange = { currencyText2.value = it },
                    label = "Amount",
                    formatDecimal = true,
                    helpText = "Min. 50,000.00 AMD",
                )
                Spacer(modifier = Modifier.height(16.dp))
                CurrencyInput(
                    value = currencyText3.value,
                    onValueChange = { currencyText3.value = it },
                    label = "Amount",
                    isError = true,
                    helpText = "Min. 50,000.00 AMD",
                    showArrow = true,
                    onCurrencyClick = { Toast.makeText(context, "On currency click", Toast.LENGTH_SHORT).show() }
                )
                Spacer(modifier = Modifier.height(16.dp))
                CurrencyInput(
                    value = currencyText4.value,
                    onValueChange = { currencyText4.value = it },
                    label = "Amount",
                    enabled = false,
                    helpText = "Min. 50,000.00 AMD",
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        CurrencyInputScreen()
    }
}
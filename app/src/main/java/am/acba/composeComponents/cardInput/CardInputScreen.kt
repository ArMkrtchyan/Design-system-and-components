package am.acba.composeComponents.cardInput

import am.acba.component.R
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.inputs.CardInput
import am.acba.compose.theme.DigitalTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardInputScreen(title: String = "") {
    val textNormal =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val textNormal2 =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val textNormal3 =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val textNormal4 =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("9556236558963658")) }
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
                CardInput(
                    value = textNormal.value,
                    onValueChange = { textNormal.value = it },
                    placeholder = "Քարտի համար",
                    trailingIcon = R.drawable.ic_scan,
                    trailingTint = DigitalTheme.colorScheme.contentPrimary,
                )
                VerticalSpacer(16.dp)
                CardInput(
                    value = textNormal2.value,
                    onValueChange = { textNormal2.value = it },
                    placeholder = "Քարտի համար",
                    helpText = "Enter card number",
                )
                VerticalSpacer(16.dp)
                CardInput(
                    value = textNormal3.value,
                    onValueChange = { textNormal3.value = it },
                    placeholder = "Քարտի համար",
                    trailingIcon = R.drawable.ic_scan,
                    isError = true,
                    errorText = "Wrong card number",
                    trailingTint = DigitalTheme.colorScheme.contentPrimary,
                )
                VerticalSpacer(16.dp)
                CardInput(
                    value = textNormal4.value,
                    onValueChange = { textNormal4.value = it },
                    placeholder = "Քարտի համար",
                    trailingIcon = R.drawable.ic_scan,
                    enabled = false,
                    trailingTint = DigitalTheme.colorScheme.contentPrimary,
                )
                VerticalSpacer(16.dp)
            }
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        CardInputScreen()
    }
}
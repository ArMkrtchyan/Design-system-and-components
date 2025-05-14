package am.acba.composeComponents.inputs

import am.acba.component.R
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.inputs.PrimaryInput
import am.acba.compose.components.inputs.SearchBar
import am.acba.compose.components.inputs.visualTransformations.MaxLengthVisualTransformation
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
import androidx.compose.material3.IconButton
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
fun InputsScreen(title: String = "") {
    val textNormal =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val textNormal2 =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val textNormal3 =
        rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
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
                PrimaryInput(
                    value = textNormal.value,
                    onValueChange = { textNormal.value = it },
                    label = "Label",
                    helpText = "Some help text",
                    visualTransformation = MaxLengthVisualTransformation(15),
                    autoFormatting = true,
                    leadingIcon = R.drawable.ic_close,
                )
                VerticalSpacer(16)
                PrimaryInput(
                    value = textNormal3.value,
                    onValueChange = { textNormal3.value = it },
                    label = "Label",
                    enabled = false,
                    errorText = "Some error text",
                    helpText = "Some help text",
                    leadingIcon = R.drawable.ic_close,
                )
                VerticalSpacer(16)
                SearchBar(hint = "Search...")
                VerticalSpacer(16)
            }
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        InputsScreen()
    }
}
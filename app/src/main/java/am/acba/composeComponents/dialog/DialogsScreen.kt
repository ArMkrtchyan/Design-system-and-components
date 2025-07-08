package am.acba.composeComponents.dialog

import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryButton
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.alertDialog.PrimaryAlertDialog
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen(title: String = "") {
    val shouldShowDialog = remember { mutableIntStateOf(-1) }
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
                PrimaryDivider(text = "Dialog with all components")
                VerticalSpacer(20)
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Show dialog",
                ) {
                    shouldShowDialog.intValue = 1
                }
                VerticalSpacer(20)
                PrimaryDivider(text = "Dialog without icon and content")
                VerticalSpacer(20)
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Show dialog",
                ) {
                    shouldShowDialog.intValue = 2
                }
                VerticalSpacer(20)
                PrimaryDivider(text = "Dialog with title and one button")
                VerticalSpacer(20)
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Show dialog",
                ) {
                    shouldShowDialog.intValue = 3
                }
                VerticalSpacer(20)
                PrimaryDivider(text = "Dialog with title, description and button")
                VerticalSpacer(20)
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Show dialog",
                ) {
                    shouldShowDialog.intValue = 4
                }
                VerticalSpacer(20)
                PrimaryDivider(text = "Dialog with title, description and two button")
                VerticalSpacer(20)
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Show dialog",
                ) {
                    shouldShowDialog.intValue = 5
                }
                VerticalSpacer(20)
                PrimaryDivider(text = "Dialog with title, icon and button")
                VerticalSpacer(20)
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Show dialog",
                ) {
                    shouldShowDialog.intValue = 6
                }
                VerticalSpacer(20)
                PrimaryDivider(text = "Non cancelable dialog")
                VerticalSpacer(20)
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Show dialog",
                ) {
                    shouldShowDialog.intValue = 7
                }
                VerticalSpacer(20)
            }
        }
    }
    when (shouldShowDialog.intValue) {
        1 -> AllComponentsDialog(shouldShowDialog)
        2 -> WithoutIconAndContentDialog(shouldShowDialog)
        3 -> TitleAndButtonDialog(shouldShowDialog)
        4 -> TitleDescriptionAndButtonDialog(shouldShowDialog)
        5 -> TitleDescriptionAndButtonsDialog(shouldShowDialog)
        6 -> IconAndTitleDialog(shouldShowDialog)
        7 -> NonCancelableDialog(shouldShowDialog)
    }
}

@Composable
fun AllComponentsDialog(shouldShowDialog: MutableState<Int>) {
    PrimaryAlertDialog(
        onDismissRequest = { shouldShowDialog.value = -1 },
        icon = am.acba.component.R.drawable.ic_block,
        title = "Օգտատերը բլոկավորված է",
        description = "Դուք կարող եք ապաբլոկավորել սեղմելով ապաբլոկավորման կոճակը:",
        positiveButtonText = "Ապաբլոկավորել",
        positiveButtonColor = DigitalTheme.colorScheme.contentBrandTonal1,
        negativeButtonText = "Չեղարկել",
        onPositiveButtonClick = { shouldShowDialog.value = -1 },
        onNegativeButtonClick = { shouldShowDialog.value = -1 }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(125.dp)
                .background(DigitalTheme.colorScheme.backgroundTonal2, ShapeTokens.shapePrimaryButton)
        ) {
            PrimaryText(modifier = Modifier.align(Alignment.Center), text = "Arshak .Mkrtchyan")
        }
    }
}

@Composable
fun WithoutIconAndContentDialog(shouldShowDialog: MutableState<Int>) {
    PrimaryAlertDialog(
        onDismissRequest = { shouldShowDialog.value = -1 },
        title = "Օգտատերը բլոկավորված է",
        description = "Դուք կարող եք ապաբլոկավորել սեղմելով ապաբլոկավորման կոճակը:",
        positiveButtonText = "Ապաբլոկավորել",
        positiveButtonColor = DigitalTheme.colorScheme.contentBrandTonal1,
        negativeButtonText = "Չեղարկել",
        onPositiveButtonClick = { shouldShowDialog.value = -1 },
        onNegativeButtonClick = { shouldShowDialog.value = -1 }
    )
}

@Composable
fun TitleAndButtonDialog(shouldShowDialog: MutableState<Int>) {
    PrimaryAlertDialog(
        onDismissRequest = { shouldShowDialog.value = -1 },
        title = "Օգտատերը բլոկավորված է",
        positiveButtonText = "Ապաբլոկավորել",
        positiveButtonColor = DigitalTheme.colorScheme.contentBrandTonal1,
        onPositiveButtonClick = { shouldShowDialog.value = -1 },
    )
}

@Composable
fun TitleDescriptionAndButtonDialog(shouldShowDialog: MutableState<Int>) {
    PrimaryAlertDialog(
        onDismissRequest = { shouldShowDialog.value = -1 },
        title = "Օգտատերը բլոկավորված է",
        description = "Դուք կարող եք ապաբլոկավորել սեղմելով ապաբլոկավորման կոճակը:",
        negativeButtonText = "Չեղարկել",
        onNegativeButtonClick = { shouldShowDialog.value = -1 }
    )
}

@Composable
fun TitleDescriptionAndButtonsDialog(shouldShowDialog: MutableState<Int>) {
    PrimaryAlertDialog(
        onDismissRequest = { shouldShowDialog.value = -1 },
        title = "Օգտատերը բլոկավորված է",
        description = "Դուք կարող եք ապաբլոկավորել սեղմելով ապաբլոկավորման կոճակը:",
        positiveButtonText = "Ապաբլոկավորել",
        positiveButtonColor = DigitalTheme.colorScheme.contentBrandTonal1,
        negativeButtonText = "Չեղարկել",
        onPositiveButtonClick = { shouldShowDialog.value = -1 },
        onNegativeButtonClick = { shouldShowDialog.value = -1 }
    )
}


@Composable
fun IconAndTitleDialog(shouldShowDialog: MutableState<Int>) {
    PrimaryAlertDialog(
        onDismissRequest = { shouldShowDialog.value = -1 },
        icon = am.acba.component.R.drawable.ic_block,
        title = "Օգտատերը բլոկավորված է",
        positiveButtonText = "Ապաբլոկավորել",
        positiveButtonColor = DigitalTheme.colorScheme.contentDangerTonal1,
        onPositiveButtonClick = { shouldShowDialog.value = -1 }
    )
}

@Composable
fun NonCancelableDialog(shouldShowDialog: MutableState<Int>) {
    PrimaryAlertDialog(
        onDismissRequest = { shouldShowDialog.value = -1 },
        icon = am.acba.component.R.drawable.ic_block,
        title = "Օգտատերը բլոկավորված է",
        description = "Դուք կարող եք ապաբլոկավորել սեղմելով ապաբլոկավորման կոճակը:",
        positiveButtonText = "Ապաբլոկավորել",
        positiveButtonColor = DigitalTheme.colorScheme.contentBrandTonal1,
        negativeButtonText = "Չեղարկել",
        onPositiveButtonClick = { shouldShowDialog.value = -1 },
        onNegativeButtonClick = { shouldShowDialog.value = -1 },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        DialogsScreen()
    }
}
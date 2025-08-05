package am.acba.composeComponents.dropDown

import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.bottomSheet.closeBottomSheet
import am.acba.compose.components.dropDown.ComponentDropDown
import am.acba.compose.components.listItem.ListItem
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownScreen(title: String = "") {
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
                DropDownWithSpacer(
                    value = TextFieldValue("Drop Down Value"),
                    dropDownModifier = Modifier.fillMaxSize(),
                    label = "Drop Down Label",
                    helpText = "Help text",
                    bottomSheetTitle = "Bottom Sheet1",
                    bottomSheetContent = { sheetState, scope ->
                        TextField(value = "a", onValueChange = {}, modifier = Modifier.fillMaxWidth())
                    })

                var text by remember { mutableStateOf(TextFieldValue("Drop Down Value")) }
                val openDropDown = remember { mutableStateOf(false) }

                DropDownWithSpacer(
                    value = text,
                    dropDownModifier = Modifier.fillMaxSize(),
                    label = "Drop Down Label",
                    bottomSheetTitle = "Bottom Sheet3",
                    bottomSheetCalculatePercentForOpenFullScreen = false,
                    bottomSheetContent = { sheetState, scope ->
                        val list = listOf(
                            "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Standard.svg" to "Հերթական մարում",
                            "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Partial.svg" to "Մասնակի մարում",
                            "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Total.svg" to "Ամբողջական մարում",
                            "https://online1-test.acba.am/Shared/LeasingImages/PaymentTypes/Other.svg" to "Այլ վճարումներ",
                        )
                        LazyColumn {
                            items(list) {
                                ListItem(
                                    title = it.second,
                                    titleStyle = DigitalTheme.typography.body1Regular,
                                    backgroundColor = Color.Transparent,
                                    avatarType = AvatarEnum.IMAGE,
                                    avatarImageUrl = it.first,
                                    avatarIconColor = DigitalTheme.colorScheme.contentPrimary,
                                    showDivider = true,
                                    onClick = {
                                        closeBottomSheet(state = sheetState, scope = scope) {
                                            openDropDown.value = false
                                            text = TextFieldValue(it.second)
                                        }
                                    }
                                )
                            }
                        }
                    },
                    openDropDown = openDropDown,
                )

                DropDownWithSpacer(
                    value = TextFieldValue("Drop Down Value"),
                    dropDownModifier = Modifier.fillMaxSize(),
                    label = "Drop Down Label",
                    isError = true,
                    errorText = "Some error",
                    bottomSheetTitle = "Bottom Sheet 2",
                    bottomSheetCalculatePercentForOpenFullScreen = false,
                    bottomSheetContent = { sheetState, scope ->
                        TextField(value = "a", onValueChange = {}, modifier = Modifier.fillMaxWidth())
                    })

                DropDownWithSpacer(
                    value = TextFieldValue("Drop Down Value"),
                    dropDownModifier = Modifier.fillMaxSize(),
                    label = "Drop Down Label",
                    enabled = false,
                    bottomSheetTitle = "Bottom Sheet",
                    bottomSheetCalculatePercentForOpenFullScreen = false,
                    bottomSheetContent = { sheetState, scope ->
                        TextField(value = "a", onValueChange = {})
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownWithSpacer(
    value: TextFieldValue = TextFieldValue(""),
    dropDownModifier: Modifier = Modifier,
    label: String,
    helpText: String? = null,
    isError: Boolean = false,
    errorText: String? = null,
    enabled: Boolean = true,
    leadingIcon: Int? = null,
    leadingIconTint: Color? = DigitalTheme.colorScheme.contentPrimaryTonal1,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    bottomSheetTitle: String = "",
    bottomSheetProperties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    bottomSheetContentHorizontalPadding: Int = 16,
    bottomSheetContentBottomPadding: Int = 16,
    bottomSheetCalculatePercentForOpenFullScreen: Boolean = true,
    bottomSheetContent: @Composable (sheetState: SheetState, coroutineScope: CoroutineScope) -> Unit,
    openDropDown: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    VerticalSpacer(16)
    ComponentDropDown(
        value = value,
        dropDownModifier = dropDownModifier,
        label = label,
        helpText = helpText,
        isError = isError,
        errorText = errorText,
        enabled = enabled,
        leadingIcon = leadingIcon,
        leadingIconTint = leadingIconTint,
        singleLine = true,
        maxLines = maxLines,
        bottomSheetTitle = bottomSheetTitle,
        bottomSheetProperties = bottomSheetProperties,
        bottomSheetContentHorizontalPadding = bottomSheetContentHorizontalPadding,
        bottomSheetContentBottomPadding = bottomSheetContentBottomPadding,
        bottomSheetCalculatePercentForOpenFullScreen = bottomSheetCalculatePercentForOpenFullScreen,
        bottomSheetContent = bottomSheetContent,
        openDropDown = openDropDown
    )
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        DropDownScreen()
    }
}
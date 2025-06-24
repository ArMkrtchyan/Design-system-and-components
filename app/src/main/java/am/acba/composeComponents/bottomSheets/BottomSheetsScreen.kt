@file:OptIn(ExperimentalMaterial3Api::class)

package am.acba.composeComponents.bottomSheets

import am.acba.compose.components.PrimaryButton
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.bottomSheet.PrimaryBottomSheet
import am.acba.compose.components.bottomSheet.closeBottomSheet
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

@Composable
fun BottomSheetsScreen(title: String = "") {
    val bottomSheetVisible = remember { mutableStateOf(false) }
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
                PrimaryButton(
                    text = "Show bottom sheet",
                    modifier = Modifier.fillMaxWidth()
                ) {
                    bottomSheetVisible.value = true
                }
            }
        }
    }
    PrimaryBottomSheet(
        title = "Ընտրել հաշվեհամարը",
        bottomSheetVisible = bottomSheetVisible.value,
        contentHorizontalPadding = 0.dp,
        dismiss = {
            bottomSheetVisible.value = false
        }
    ) { state: SheetState, coroutineScope: CoroutineScope ->
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
                        closeBottomSheet(state = state, scope = coroutineScope) {
                            bottomSheetVisible.value = false
                        }
                    }
                )
            }
        }
    }

}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        BottomSheetsScreen()
    }
}
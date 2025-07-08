package am.acba.composeComponents.tables

import am.acba.component.R
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.listItem.ListItemStartAvatarSizeEnum
import am.acba.compose.components.tableComponent.TableComponent
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TablesScreen(title: String = "") {
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
                    .padding(top = 16.dp)
                    .padding(bottom = 50.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                TableComponent(
                    title = "5G վարկ",
                    avatarIcon = R.drawable.ic_phonebook,
                    tableItems = arrayListOf(
                        Pair("Անուն Ազգանուն", "Արշակ Մկրտչյան"),
                        Pair("Վարկային կոդ", "234567890"),
                        Pair("Հաշվեհամար", "123454354553224"),
                        Pair("Հերթական մարում", "4,000.00 AMD"),
                        Pair("Ընթացիկ", "4,000.00 AMD"),
                        Pair("Տոկոսագումար", "4,000.00 AMD"),
                        Pair("Միջնորդավճար", "4,000.00 AMD"),
                        Pair("Միջնորդավճար 2", "4,000.00 AMD"),
                        Pair("Միջնորդավճար 3", "4,000.00 AMD"),
                        Pair("Ընդամենը", "4,000.00 AMD")
                    ),
                    minimumVisibleItemsCount = 3
                )
                VerticalSpacer(20)
                TableComponent(
                    title = "5G վարկ",
                    avatarIcon = R.drawable.ic_phonebook,
                    tableItems = arrayListOf(
                        Pair("Անուն Ազգանուն", "Արշակ Մկրտչյան"),
                        Pair("Վարկային կոդ", "234567890"),
                        Pair("Հաշվեհամար", "123454354553224"),
                        Pair("Հերթական մարում", "4,000.00 AMD"),
                        Pair("Ընթացիկ", "4,000.00 AMD"),
                        Pair("Տոկոսագումար", "4,000.00 AMD"),
                        Pair("Միջնորդավճար", "4,000.00 AMD")
                    )
                )
                VerticalSpacer(20)
                TableComponent(
                    title = "5G վարկ",
                    avatarIcon = R.drawable.ic_phonebook,
                    tableItems = arrayListOf(
                        Pair("Անուն Ազգանուն", "Արշակ Մկրտչյան")
                    )
                )
                VerticalSpacer(20)
                TableComponent(
                    title = "5G վարկ",
                    avatarIcon = R.drawable.default_avatar,
                    avatarBackgroundColor = Color.Transparent,
                    avatarType = AvatarEnum.IMAGE,
                    listItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.AVATAR,
                    tableItems = arrayListOf(
                        Pair("Անուն Ազգանուն", "Արշակ Մկրտչյան")
                    )
                )
                VerticalSpacer(20)
                TableComponent(
                    title = "5G վարկ",
                    tableItems = arrayListOf(
                        Pair("Անուն Ազգանուն", "Արշակ Մկրտչյան")
                    )
                )
                VerticalSpacer(20)
                TableComponent(
                    tableItems = arrayListOf(
                        Pair("Վարկային կոդ", "234567890"),
                        Pair("Հաշվեհամար", "123454354553224"),
                        Pair("Հերթական մարում", "4,000.00 AMD"),
                    )
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        TablesScreen()
    }
}
package am.acba.composeComponents.expandableView

import am.acba.component.R
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.accordion.Accordion
import am.acba.compose.components.listItem.ControllerTypeEnum
import am.acba.compose.components.listItem.ListItem
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableViewScreen(title: String = "") {
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
                val expanded = remember { mutableStateOf(false) }
                Accordion(avatarIcon = am.acba.component.R.drawable.ic_income, expanded = expanded, onClick = { expanded.value = !expanded.value }) {
                    ListItem(
                        title = "djcknsdkjbncsdbcsdbcjhds",
                        avatarIcon = R.drawable.ic_info,
                        description = "Description 1 Description 1 Description 1 Description 1 Description 1 Description 1 Description 1 Description 1 ",
                        //     description2 = "Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 Description 2 ",
                        //    description3 = "Description 3 Description 3 Description 3 Description 3 Description 3 Description 3 Description 3 Description 3 ",
                        description4 = "Description 4 Description 4 Description 4 Description 4 Description 4 Description 4 Description 4 Description 4",
                        endIcon = R.drawable.ic_right,
                        controllerType = ControllerTypeEnum.CHECK_BOX
                    )
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        ExpandableViewScreen()
    }
}
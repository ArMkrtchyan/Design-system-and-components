package am.acba.composeComponents.toolbar

import am.acba.compose.components.collapsingToolbar.CollapsingTitle
import am.acba.compose.components.collapsingToolbar.CollapsingToolbar
import am.acba.compose.components.collapsingToolbar.rememberToolbarScrollBehavior
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarScreen(title: String = "") {
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
        val scrollBehavior = rememberToolbarScrollBehavior()
        Column(
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            CollapsingToolbar(
                collapsingTitle = CollapsingTitle("Toolbar title Toolbar title Toolbar title", DigitalTheme.typography.heading5Bold),
                scrollBehavior = scrollBehavior
            )
            LazyColumn(modifier = Modifier) {
                items((0..20).toList()) {
                    ListItem(
                        title = "List item $it",
                        titleStyle = DigitalTheme.typography.body1Regular,
                        backgroundColor = Color.Transparent,
                        showDivider = true,
                        onClick = {

                        }
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
        ToolbarScreen()
    }
}
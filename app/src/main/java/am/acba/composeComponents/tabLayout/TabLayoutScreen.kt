package am.acba.composeComponents.tabLayout

import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.tabs.PrimaryTabRow
import am.acba.compose.components.tabs.TabBarWithPager
import am.acba.compose.components.tabs.model.TabItem
import am.acba.compose.components.tabs.model.TabModeEnum
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.Constants.EMPTY_STRING
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabLayoutScreen(title: String = EMPTY_STRING) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
        ) {
            PrimaryToolbar(title = title)
            TabLayout()
            VerticalSpacer(48.dp)
            TabLayoutWithPager(TabModeEnum.FIXED)
            TabLayoutWithPager(TabModeEnum.SCROLLABLE)
        }
    }
}

@Composable
private fun TabLayout() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val items = listOf(TabItem("Tab1", "2"), TabItem("Tab2", "5"), TabItem("Tab3", ""))
    PrimaryTabRow(
        items = items,
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier.fillMaxWidth()
    ) { index ->
        selectedTabIndex = index
    }
}

@Composable
private fun TabLayoutWithPager(mode: TabModeEnum) {
    val items = mutableListOf(
        TabItem("Tab1", ""),
        TabItem("Tab2", ""),
        TabItem("Tab3", ""),
    ).apply {
        if (mode == TabModeEnum.SCROLLABLE) {
            addAll(listOf(TabItem("Tab4", ""), TabItem("Tab5", ""), TabItem("Tab6", "")))
        }
    }
    val pagerState = rememberPagerState { items.size }

    TabBarWithPager(
        tabs = items,
        pagerState = pagerState,
        mode = mode
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            PrimaryText("Content $page")
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        TabLayoutScreen()
    }
}
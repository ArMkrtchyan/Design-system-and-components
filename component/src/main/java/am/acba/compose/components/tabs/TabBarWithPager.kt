package am.acba.compose.components.tabs

import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.tabs.model.TabItem
import am.acba.compose.components.tabs.model.TabModeEnum
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun TabBarWithPager(
    tabs: List<TabItem>,
    pagerState: PagerState,
    tabModifier: Modifier = Modifier,
    indicatorColor: Color = DigitalTheme.colorScheme.contentBrandTonal1,
    tabPagerSpacing: Dp = 8.dp,
    mode: TabModeEnum = TabModeEnum.FIXED,
    pageContent: @Composable (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    PrimaryTabRow(
        items = tabs,
        selectedTabIndex = pagerState.currentPage,
        modifier = tabModifier.fillMaxWidth(),
        mode = mode,
        indicatorColor = indicatorColor,
    ) { index ->
        coroutineScope.launch {
            pagerState.animateScrollToPage(index)
        }
    }
    VerticalSpacer(tabPagerSpacing)
    HorizontalPager(state = pagerState) { page ->
        pageContent.invoke(page)
    }
}
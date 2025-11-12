package am.acba.compose.components.tabs

import am.acba.compose.components.tabs.model.TabItem
import am.acba.compose.components.tabs.model.TabModeEnum
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.id
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryTabRow(
    items: List<TabItem>,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    indicatorColor: Color = DigitalTheme.colorScheme.contentBrandTonal1,
    mode: TabModeEnum = TabModeEnum.FIXED,
    id: String = "tabRow",
    edgePadding: Dp = 0.dp,
    onTabClick: (Int) -> Unit = {}
) {

    @Composable
    fun FixedTabRow() {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier.id(id),
            containerColor = DigitalTheme.colorScheme.transparent,
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .height(1.dp),
                        color = indicatorColor
                    )
                }
            },
            divider = { HorizontalDivider(color = DigitalTheme.colorScheme.transparent) }
        ) {
            items.forEachIndexed { index, item ->
                PrimaryTab(selectedTabIndex == index, item, onClick = {
                    onTabClick.invoke(index)
                })
            }
        }
    }

    @Composable
    fun ScrollableTabRow() {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier.id(id),
            containerColor = DigitalTheme.colorScheme.transparent,
            edgePadding = edgePadding,
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .height(1.dp),
                        color = indicatorColor
                    )
                }
            },
            divider = { HorizontalDivider(color = DigitalTheme.colorScheme.transparent) }
        ) {
            items.forEachIndexed { index, item ->
                PrimaryTab(selectedTabIndex == index, item, onClick = {
                    onTabClick.invoke(index)
                })
            }
        }
    }

    when (mode) {
        TabModeEnum.FIXED -> FixedTabRow()
        TabModeEnum.SCROLLABLE -> ScrollableTabRow()
    }
}

@Composable
@PreviewLightDark
private fun PrimaryTabRowPreview() {
    DigitalTheme {
        PrimaryTabRow(listOf(TabItem("Tab1", ""), TabItem("Ta2", ""), TabItem("Tab3", "")), 0)
    }
}
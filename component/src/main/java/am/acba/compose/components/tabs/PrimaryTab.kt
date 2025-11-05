package am.acba.compose.components.tabs

import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.tabs.model.TabItem
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.id
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryTab(
    selected: Boolean,
    tabItem: TabItem,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedContentColor: Color = DigitalTheme.colorScheme.contentBrandTonal1,
    unselectedContentColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    interactionSource: MutableInteractionSource? = null,
    selectedBadgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    unselectedBadgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundPending,
    selectedBadgeTextColor: Color = DigitalTheme.colorScheme.contentSecondary,
    unselectedBadgeTextColor: Color = DigitalTheme.colorScheme.contentPending,
    id: String = "tab",
    onClick: () -> Unit = {},
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier.id(id),
        enabled = enabled,
        unselectedContentColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
        selectedContentColor = DigitalTheme.colorScheme.contentBrand,
        interactionSource = interactionSource,
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PrimaryText(
                    tabItem.title,
                    style = if (selected) DigitalTheme.typography.body2Bold else DigitalTheme.typography.subTitle2Regular,
                    color = if (selected) selectedContentColor else unselectedContentColor,
                    modifier = Modifier.id("${id}Text")
                )
                if (!tabItem.badge.isNullOrEmpty()) {
                    HorizontalSpacer(8.dp)
                    Badge(
                        badgeType = BadgeEnum.NUMBER,
                        text = tabItem.badge,
                        id = "tabBadge",
                        backgroundColor = if (selected) selectedBadgeBackgroundColor else unselectedBadgeBackgroundColor,
                        textColor = if (selected) selectedBadgeTextColor else unselectedBadgeTextColor
                    )
                }
            }
        }
    )
}

@Composable
@PreviewLightDark
private fun ComponentTabPreview() {
    DigitalTheme {
        PrimaryTab(true, TabItem("Tab1")) {}
    }
}
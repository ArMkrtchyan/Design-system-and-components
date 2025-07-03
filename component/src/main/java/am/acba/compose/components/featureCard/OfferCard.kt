package am.acba.compose.components.featureCard

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.featureCard.model.IOfferCardItem
import am.acba.compose.components.featureCard.model.OfferCardItem
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun <T : IOfferCardItem> OfferCard(
    title: String,
    items: List<T>,
    onClick: () -> Unit = {},
    onItemClick: (T) -> Unit = {},
    seeAllTitle: String = EMPTY_STRING,
    onSeeAllClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    badge: String = EMPTY_STRING,
    badgeBackground: Color = DigitalTheme.colorScheme.backgroundSuccess,
    badgeTextColor: Color = DigitalTheme.colorScheme.contentSecondary,
    cardBackground: Color = DigitalTheme.colorScheme.backgroundTonal1,
    cardRadius: Int = 12,
    cardItemPadding: Int = 32,
    trailingIcon: Int? = R.drawable.ic_down,
    trailingIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    isExpanded: Boolean = false,
) {

    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "arrow-rotation",
        animationSpec = tween(easing = LinearOutSlowInEasing)
    )

    Column(
        modifier = modifier
            .background(cardBackground, RoundedCornerShape(cardRadius.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick.invoke() }
        ) {
            PrimaryText(
                text = title,
                style = DigitalTheme.typography.body2Regular,
            )
            badge.takeIf { it.isNotEmpty() }?.let {
                HorizontalSpacer(8)
                Badge(badgeType = BadgeEnum.INFO, text = badge, backgroundColor = badgeBackground, textColor = badgeTextColor)
            }
            Spacer(modifier = Modifier.weight(1f))
            trailingIcon.takeIf { it != null }?.let {
                HorizontalSpacer(8)
                PrimaryIcon(painter = painterResource(it), modifier = Modifier.rotate(arrowRotation), tint = trailingIconColor)
            }
        }
        VerticalSpacer(12)
        ItemsPager(items, isExpanded, onItemClick, cardItemPadding)
        seeAllTitle.takeIf { it.isNotEmpty() }?.let {
            SeeAllText(seeAllTitle, onSeeAllClick)
        }
    }
}

@Composable
private fun <T : IOfferCardItem> ItemsPager(items: List<T>, isExpanded: Boolean, onItemClick: (T) -> Unit, cardItemPadding: Int) {
    val pagerState = rememberPagerState { items.size }
    var startPadding by remember { mutableStateOf(cardItemPadding.dp) }
    var endPadding by remember { mutableStateOf(cardItemPadding.dp) }

    LaunchedEffect(pagerState.currentPage) {
        if (items.size == 1) {
            startPadding = 0.dp
            endPadding = 0.dp
            return@LaunchedEffect
        }
        when (pagerState.currentPage) {
            0 -> {
                startPadding = 0.dp
                endPadding = (cardItemPadding * 2).dp
            }

            items.size - 1 -> {
                endPadding = 0.dp
                startPadding = (cardItemPadding * 2).dp
            }

            else -> {
                startPadding = cardItemPadding.dp
                endPadding = cardItemPadding.dp
            }
        }
    }

    BoxWithConstraints {
        val itemWidth = if (items.size == 1) this.maxWidth else this.maxWidth * 0.8f
        HorizontalPager(
            state = pagerState,
            pageSpacing = 8.dp,
            contentPadding = PaddingValues(start = startPadding, end = endPadding),
            userScrollEnabled = items.size != 1,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            OfferCardContent(
                modifier = Modifier.width(itemWidth),
                item = items[page],
                isExpanded = isExpanded,
                onClick = onItemClick
            )
        }
    }
}

@Composable
@NonRestartableComposable
private fun SeeAllText(title: String, onClick: () -> Unit) {
    PrimaryText(
        title,
        style = TextStyle(textDecoration = TextDecoration.Underline),
        modifier = Modifier
            .clickable {
                onClick.invoke()
            }
            .padding(top = 12.dp, bottom = 8.dp, end = 8.dp)
    )
}

@PreviewLightDark
@Composable
fun OfferCardPreview() {
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            val offer = OfferCardItem(
                amount = "10,000,000.00 AMD",
                creditLimitTitle = "վարկային սահմանաչափ",
                expirationDate = "Վերջնաժամկետ 12/09/2024",
                badge = "նոր",
            )

            OfferCard(
                title = "duq uneq nor arajark",
                items = listOf(offer),
                seeAllTitle = "",
                isExpanded = false,
                onClick = {},
                onItemClick = {},
                onSeeAllClick = {}
            )
        }
    }
}
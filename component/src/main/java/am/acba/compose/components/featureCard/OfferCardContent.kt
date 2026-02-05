package am.acba.compose.components.featureCard

import am.acba.component.R
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.featureCard.model.IOfferCardItem
import am.acba.compose.components.featureCard.model.OfferCardItem
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T : IOfferCardItem> OfferCardContent(
    item: T,
    modifier: Modifier = Modifier,
    cardRadius: Int = 8,
    isExpanded: Boolean = true,
    onClick: (T) -> Unit = {}
) {
    val cardPadding by animateDpAsState(
        targetValue = if (isExpanded) 16.dp else 8.dp,
        animationSpec = tween(durationMillis = 400),
        label = "cardPadding"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(item.background ?: DigitalTheme.colorScheme.backgroundAlternative6, RoundedCornerShape(cardRadius.dp))
            .padding(cardPadding)
            .clickable {
                onClick.invoke(item)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AmountText(item.amount, isExpanded)
                CreditLimitText(item.creditLimitTitle)
                CircleBox(isExpanded)
            }
            val animatedHeight by animateDpAsState(
                targetValue = if (isExpanded) 53.dp else 0.dp,
                animationSpec = tween(durationMillis = 300),
                label = "height-animation"
            )
            val animatedAlpha by animateFloatAsState(
                targetValue = if (isExpanded) 1f else 0f,
                animationSpec = tween(durationMillis = 300),
                label = "alpha-animation"
            )
            Column(
                modifier = Modifier
                    .height(animatedHeight)
                    .alpha(animatedAlpha)
                    .fillMaxWidth()
            ) {
                ExpandedAmountText(item.amount)
                VerticalSpacer(8.dp)
                ExpirationDateText(item.expirationDate)
            }
        }

        item.badge.takeIf { it.isNotEmpty() }?.let {
            Badge(
                modifier = Modifier.align(Alignment.BottomEnd),
                badgeType = BadgeEnum.INFO,
                text = item.badge,
                backgroundColor = item.badgeBackground ?: DigitalTheme.colorScheme.backgroundSuccess,
                textColor = item.badgeTextColor ?: DigitalTheme.colorScheme.contentSecondary
            )
        }
    }
}

@Composable
private fun AmountText(offerAmount: String, isExpanded: Boolean) {
    val density = LocalDensity.current
    var fullWidth by remember { mutableStateOf(0.dp) }
    var measured by remember { mutableStateOf(false) }

    if (!measured) {
        PrimaryText(
            offerAmount,
            modifier = Modifier
                .alpha(0f)
                .onGloballyPositioned { layoutCoordinates ->
                    val widthPx = layoutCoordinates.size.width
                    fullWidth = with(density) { widthPx.toDp() }
                    measured = true
                },
            color = DigitalTheme.colorScheme.contentInverse,
            style = DigitalTheme.typography.smallBold,
            maxLines = 1
        )
    }

    if (measured) {
        val animatedWidth by animateDpAsState(
            targetValue = if (isExpanded) 0.dp else fullWidth,
            animationSpec = tween(durationMillis = 300),
            label = "width-animation"
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            PrimaryText(
                offerAmount,
                modifier = Modifier.width(animatedWidth),
                color = DigitalTheme.colorScheme.contentInverse,
                style = DigitalTheme.typography.smallBold,
                maxLines = 1
            )
            if (!isExpanded) HorizontalSpacer(4.dp)
        }
    }
}

@Composable
private fun RowScope.CreditLimitText(creditLimitText: String) {
    PrimaryText(
        creditLimitText,
        color = DigitalTheme.colorScheme.contentInverse,
        style = DigitalTheme.typography.xSmallRegular,
        modifier = Modifier
            .padding(end = 8.dp)
            .weight(1f),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
@NonRestartableComposable
private fun CircleBox(isExpanded: Boolean) {
    val transition = updateTransition(targetState = isExpanded, label = "expand-transition")

    val scale by transition.animateFloat(
        label = "box-scale",
        transitionSpec = {
            tween(
                durationMillis = 200,
                delayMillis = 100
            )
        }
    ) { isExpanded ->
        if (isExpanded) 1f else 0f
    }
    Box(
        modifier = Modifier
            .size(24.dp)
            .scale(scale)
            .background(DigitalTheme.colorScheme.contentSecondary, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        PrimaryIcon(
            painter = painterResource(R.drawable.ic_right),
            tint = DigitalTheme.colorScheme.contentInverse,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
private fun ExpandedAmountText(offerAmount: String) {
    PrimaryText(
        offerAmount,
        color = DigitalTheme.colorScheme.contentInverse,
        style = DigitalTheme.typography.heading6Bold,
        lineHeight = 30.sp
    )
}

@Composable
private fun ExpirationDateText(expirationDate: String) {
    PrimaryText(
        expirationDate,
        color = DigitalTheme.colorScheme.contentInverse,
        style = DigitalTheme.typography.xSmallRegular,
        lineHeight = 22.sp
    )
}

@PreviewLightDark
@Composable
private fun FeatureCardAnimatedContentPreview() {
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            OfferCardContent(
                item = OfferCardItem(
                    amount = "10,000,000.00 AMD",
                    creditLimitTitle = "վարկային սահմանաչափ",
                    expirationDate = "Վերջնաժամկետ 12/09/2024",
                    badge = "նոր"
                ),
            )
        }
    }
}
package am.acba.compose.components.featureCard

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun OfferCardAnimatedContent(
    offerAmount: String,
    offerCreditLimitTitle: String,
    offerExpirationDate: String,
    modifier: Modifier = Modifier,
    badge: String = "",
    cardWidth: Int? = null,
    cardRadius: Int = 8,
    isExpanded: Boolean = true,
    cardBackground: Color = DigitalTheme.colorScheme.backgroundAlternative6,
    badgeBackground: Color = DigitalTheme.colorScheme.backgroundSuccess,
    onClick: () -> Unit = {}
) {
    val transition = updateTransition(targetState = isExpanded, label = "expand-transition")

    val cardHeight by transition.animateDp(label = "cardHeight") { isExpanded ->
        if (isExpanded) 108.dp else 38.dp
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 8.dp)
            .height(cardHeight)
            .clickable {
                onClick.invoke()
            },
        shape = RoundedCornerShape(cardRadius.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground)
    ) {
        val columnModifier = if (cardWidth != null) Modifier.width(cardWidth.dp) else Modifier.fillMaxWidth()
        Column(
            modifier = columnModifier
                .padding(vertical = 8.dp, horizontal = if (isExpanded) 16.dp else 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                AmountText(offerAmount, isExpanded)
                CreditLimitText(offerCreditLimitTitle, isExpanded)
                CircleBox(isExpanded)
                AnimatedBadge(badge, badgeBackground, isExpanded)
            }
            if (isExpanded) {
                ExpandedAmountText(offerAmount)
                Spacer(modifier = Modifier.height(8.dp))
                ExpirationDateRow(offerExpirationDate, badge, badgeBackground)
            }
        }
    }
}

@Composable
private fun RowScope.AmountText(offerAmount: String, isExpanded: Boolean) {
    var fullWidth by remember { mutableStateOf(400.dp) }

    val animatedWidth by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else fullWidth,
        animationSpec = tween(durationMillis = 300),
        label = "width-animation"
    )
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .then(
                if (fullWidth == 400.dp) {
                    Modifier.onGloballyPositioned { layoutCoordinates ->
                        val widthPx = layoutCoordinates.size.width
                        fullWidth = with(density) { widthPx.toDp() }
                    }
                } else {
                    Modifier.width(animatedWidth)
                }
            )
            .padding(top = 3.dp)
    ) {
        PrimaryText(
            offerAmount,
            color = DigitalTheme.colorScheme.contentInverse,
            style = DigitalTheme.typography.smallBold,
            maxLines = 1
        )
    }
    if (!isExpanded) HorizontalSpacer(4)
}

@Composable
private fun RowScope.CreditLimitText(creditLimitText: String, isExpanded: Boolean) {
    PrimaryText(
        creditLimitText,
        color = DigitalTheme.colorScheme.contentInverse,
        style = DigitalTheme.typography.body2Regular,
        modifier = Modifier
            .weight(1f)
            .padding(top = if (isExpanded) 8.dp else 0.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
@NonRestartableComposable
private fun CircleBox(isExpanded: Boolean) {
    val transition = updateTransition(targetState = isExpanded, label = "expand-transition")

    val boxSize by transition.animateDp(label = "box-size") { isExpanded ->
        if (isExpanded) 24.dp else 0.dp
    }
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .size(boxSize)
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
private fun AnimatedBadge(badge: String, badgeBackground: Color, isExpanded: Boolean) {
    val transition = updateTransition(targetState = isExpanded, label = "expand-transition")

    val badgeWidth by transition.animateDp(label = "bade-width") { isExpanded ->
        if (isExpanded) 0.dp else 38.dp
    }

    val badgeHeight by transition.animateDp(label = "badge-height") { isExpanded ->
        if (isExpanded) 0.dp else 22.dp
    }

    badge.takeIf { it.isNotEmpty() }?.let {
        Badge(
            badgeType = BadgeEnum.INFO,
            text = badge,
            backgroundColor = badgeBackground,
            modifier = Modifier
                .width(badgeWidth)
                .height(badgeHeight),
        )
    }
}

@Composable
private fun ExpandedAmountText(offerAmount: String) {
    PrimaryText(
        offerAmount,
        color = DigitalTheme.colorScheme.contentInverse,
        style = DigitalTheme.typography.heading6Bold
    )
}

@Composable
private fun ExpirationDateRow(expirationDate: String, badge: String, badgeBackground: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        ExpirationDateText(expirationDate)
        badge.takeIf { it.isNotEmpty() }?.let {
            Badge(
                badgeType = BadgeEnum.INFO,
                text = badge,
                backgroundColor = badgeBackground,
            )
        }
    }
}

@Composable
private fun ExpirationDateText(expirationDate: String) {
    PrimaryText(
        expirationDate,
        color = DigitalTheme.colorScheme.contentInverse,
        style = DigitalTheme.typography.xSmallRegular,
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
            OfferCardAnimatedContent(
                offerAmount = "10,000,000.00 AMD",
                offerCreditLimitTitle = "վարկային սահմանաչափ",
                offerExpirationDate = "Վերջնաժամկետ 12/09/2024",
                badge = "նոր",
            )
        }
    }
}
package am.acba.compose.components.featureCard

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun FeatureCardAnimatedContent(
    offerAmount: String,
    creditLimitTitle: String,
    offerExpirationDate: String,
    modifier: Modifier = Modifier,
    badgeTitle: String = "",
    width: Int? = null,
    isExpanded: Boolean = false,
    cardBackground: Color = DigitalTheme.colorScheme.backgroundAlternative6,
    badgeBackground: Color = DigitalTheme.colorScheme.backgroundSuccess,
    onIconClick: () -> Unit = {}
) {
    val transition = updateTransition(targetState = isExpanded, label = "expand-transition")

    val cardHeight by transition.animateDp(label = "cardHeight") { isExpanded ->
        if (isExpanded) 118.dp else 38.dp
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 4.dp)
            .height(cardHeight),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground)
    ) {
        val columnModifier = if (width != null) Modifier.width(width.dp) else Modifier.fillMaxWidth()
        Column(
            modifier = columnModifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                CollapsedAmountText(offerAmount, isExpanded, transition)
                if (!isExpanded) HorizontalSpacer(4)
                CollapsedCreditLimitText(creditLimitTitle)
                if (isExpanded) CircleBox(onIconClick) else Badge(badgeTitle, badgeBackground)
            }
            if (isExpanded) ExpandedView(offerAmount, offerExpirationDate, badgeTitle, badgeBackground)
        }
    }
}

@Composable
private fun RowScope.CollapsedAmountText(offerAmount: String, isExpanded: Boolean, transition: Transition<Boolean>) {
    val enter = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = 1000)
    ) + fadeIn(animationSpec = tween(1000))

    val exit = slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(durationMillis = 1000)
    ) + fadeOut(animationSpec = tween(1000))

    val width by transition.animateDp(label = "amountWidth") { isExpanded ->
        if (!isExpanded) 100.dp else 0.dp
    }

    PrimaryText(
        offerAmount,
        modifier = Modifier.width(width),
        color = DigitalTheme.colorScheme.contentInverse,
        style = DigitalTheme.typography.smallBold,
        maxLines = 1
    )
}

@Composable
private fun RowScope.CollapsedCreditLimitText(creditLimitText: String) {
    PrimaryText(
        creditLimitText,
        color = DigitalTheme.colorScheme.contentInverse,
        style = DigitalTheme.typography.body2Regular,
        modifier = Modifier.weight(1f),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun Badge(badgeText: String, badgeBackground: Color) {
    if (badgeText.isNotEmpty()) {
        Badge(
            badgeType = BadgeEnum.INFO,
            text = badgeText,
            backgroundColor = badgeBackground,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
private fun ExpandedView(
    offerAmount: String,
    offerExpirationDate: String,
    badgeText: String,
    badgeBackground: Color
) {
    PrimaryText(
        offerAmount,
        color = DigitalTheme.colorScheme.contentInverse,
        style = DigitalTheme.typography.heading6Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        PrimaryText(
            offerExpirationDate,
            color = DigitalTheme.colorScheme.contentInverse,
            style = DigitalTheme.typography.xSmallRegular,
            modifier = Modifier.padding(top = 8.dp)
        )
        Badge(badgeText, badgeBackground)
    }
}

@Composable
@NonRestartableComposable
private fun CircleBox(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(DigitalTheme.colorScheme.contentSecondary, shape = CircleShape)
            .clickable {
                onClick.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        PrimaryIcon(
            painter = painterResource(R.drawable.ic_right),
            tint = DigitalTheme.colorScheme.contentInverse,
            modifier = Modifier.padding(4.dp)
        )
    }
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
            FeatureCardAnimatedContent(
                offerAmount = "10,000,000.00 AMD",
                creditLimitTitle = "վարկային սահմանաչափ",
                offerExpirationDate = "Վերջնաժամկետ 12/09/2024",
                badgeTitle = "նոր",
            )
        }
    }
}
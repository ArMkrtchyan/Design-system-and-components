package am.acba.compose.components.featureCard

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.featureCard.model.FeatureCardItem
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun FeatureCard(
    title: String,
    items: List<FeatureCardItem>,
    modifier: Modifier = Modifier,
    badge: String = "",
    badgeBackground: Color = DigitalTheme.colorScheme.backgroundSuccess,
    cardBackground: Color = DigitalTheme.colorScheme.backgroundTonal1,
    cardRadius: Int = 12,
    trailingIcon: Int? = R.drawable.ic_down,
    trailingIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    isExpanded: Boolean = false,
    onClick: (() -> Unit)? = null,
    onSnippedIconClick: () -> Unit = {},
) {

    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "accordion-arrow",
        animationSpec = tween(
            easing = LinearOutSlowInEasing
        )
    )

    Column(
        modifier = modifier
            .background(cardBackground, RoundedCornerShape(cardRadius.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick?.invoke() }
        ) {
            PrimaryText(
                text = title,
                style = DigitalTheme.typography.body2Regular,
            )
            badge.takeIf { it.isNotEmpty() }?.let {
                HorizontalSpacer(8)
                Badge(badgeType = BadgeEnum.INFO, text = badge, backgroundColor = badgeBackground)
            }
            Spacer(modifier = Modifier.weight(1f))
            trailingIcon.takeIf { it != null }?.let {
                HorizontalSpacer(8)
                PrimaryIcon(painter = painterResource(it), modifier = Modifier.rotate(arrowRotation), tint = trailingIconColor)
            }
        }
        VerticalSpacer(8)
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(items) {
                FeatureCardAnimatedContent(
                    offerAmount = it.offerAmount,
                    creditLimitTitle = it.creditLimitTitle,
                    offerExpirationDate = it.offerExpirationDate,
                    badgeTitle = it.badge,
                    width = 250,
                    isExpanded = isExpanded,
                    onIconClick = onSnippedIconClick
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun FeatureCardPreview() {
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            val expanded by remember { mutableStateOf(false) }

            val a = FeatureCardItem(
                offerAmount = "10,000,000.00 AMD",
                creditLimitTitle = "վարկային սահմանաչափ",
                offerExpirationDate = "Վերջնաժամկետ 12/09/2024",
                badge = "նոր",
            )

            FeatureCard(
                title = "duq uneq nor arajark",
                items = listOf(a, a),
                isExpanded = expanded
            )
        }
    }
}


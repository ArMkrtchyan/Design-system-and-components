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
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
    modifier: Modifier = Modifier,
    title: String,
    cardBadgeText: String = "+1",
    items: List<FeatureCardItem> = emptyList(),
    snippetBadgeText: String = "",
    onSnippedIconClick: () -> Unit = {},
    background: Color = DigitalTheme.colorScheme.backgroundTonal1,
    radius: Int = 12,
    endIcon: Int? = R.drawable.ic_down,
    endIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    isExpanded: Boolean = false,
    onClick: (() -> Unit)? = null,
) {

    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "accordion-arrow",
        animationSpec = tween(
            easing = LinearOutSlowInEasing
        )
    )

    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 100,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(
                delayMillis = 100,
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        )
    }

    Column(
        modifier = modifier
            .background(background, RoundedCornerShape(radius.dp))
            .padding(16.dp)
            .clickable { onClick?.invoke() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            PrimaryText(
                text = title,
                style = DigitalTheme.typography.body2Regular,
            )
            cardBadgeText.takeIf { it.isNotEmpty() }?.let {
                HorizontalSpacer(8)
                Badge(badgeType = BadgeEnum.INFO, text = "+1", backgroundColor = DigitalTheme.colorScheme.backgroundSuccess)
            }
            Spacer(modifier = Modifier.weight(1f))
            endIcon.takeIf { it != null }?.let {
                HorizontalSpacer(8)
                PrimaryIcon(painter = painterResource(it), modifier = Modifier.rotate(arrowRotation), tint = endIconColor)
            }
        }
        VerticalSpacer(8)
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(items) {
                if (isExpanded) {
                    SnippetExpanded(
                        text = it.text,
                        secondaryText = it.secondaryText,
                        tertiaryText = it.tertiaryText,
                        badgeText = snippetBadgeText,
                        width = 250,
                        onIconClick = onSnippedIconClick
                    )
                } else {
                    if (it.text.isNotEmpty() || it.secondaryText.isNotEmpty()) {
                        SnippetCollapsed(
                            text = it.text,
                            secondaryText = it.secondaryText,
                            badgeText = snippetBadgeText,
                            width = 250
                        )
                    }
                }
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
                text = "10,000,000.00 AMD",
                secondaryText = "վարկային սահմանաչափ",
                tertiaryText = "Վերջնաժամկետ 12/09/2024",
                badgeText = "նոր",
            )

            FeatureCard(
                title = "duq uneq nor arajark",
                snippetBadgeText = "նոր",
                items = listOf(a, a),
                isExpanded = expanded
            )
        }
    }
}


package am.acba.compose.components.featureCard

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
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

    Column(
        modifier = modifier
            .background(background, RoundedCornerShape(radius.dp))
            .animateContentSize()
            .padding(16.dp)
            .clickable {
                onClick?.invoke()
            }
    ) {
        Column {
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
                            onIconClick = onSnippedIconClick
                        )
                    } else {
                        if (it.text.isNotEmpty() || it.secondaryText.isNotEmpty()) {
                            SnippetCollapsed(text = it.text, secondaryText = it.secondaryText, badgeText = snippetBadgeText)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SnippetCollapsed(
    modifier: Modifier = Modifier,
    text: String,
    secondaryText: String = "",
    badgeText: String = "",
    cardBackground: Color = DigitalTheme.colorScheme.backgroundAlternative6,
    badgeBackground: Color = DigitalTheme.colorScheme.backgroundSuccess
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(38.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PrimaryText(
                text,
                color = DigitalTheme.colorScheme.contentInverse,
                style = DigitalTheme.typography.smallBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            HorizontalSpacer(8)
            PrimaryText(
                secondaryText,
                color = DigitalTheme.colorScheme.contentInverse,
                style = DigitalTheme.typography.xSmallRegular,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            if (badgeText.isNotEmpty()) {
                Badge(
                    badgeType = BadgeEnum.INFO,
                    text = badgeText,
                    backgroundColor = badgeBackground,
                )
            }
        }
    }
}

@Composable
fun SnippetExpanded(
    modifier: Modifier = Modifier,
    text: String,
    secondaryText: String,
    tertiaryText: String,
    badgeText: String = "",
    cardBackground: Color = DigitalTheme.colorScheme.backgroundAlternative6,
    badgeBackground: Color = DigitalTheme.colorScheme.backgroundSuccess,
    onIconClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                PrimaryText(
                    text,
                    color = DigitalTheme.colorScheme.contentInverse,
                    style = DigitalTheme.typography.body2Regular,
                    modifier = Modifier.weight(1f)
                )
                CircleBox(onIconClick)
            }
            PrimaryText(
                secondaryText,
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
                    tertiaryText,
                    color = DigitalTheme.colorScheme.contentInverse,
                    style = DigitalTheme.typography.xSmallRegular,
                    modifier = Modifier.padding(top = 8.dp)
                )

                if (badgeText.isNotEmpty()) {
                    Badge(
                        badgeType = BadgeEnum.INFO,
                        text = badgeText,
                        backgroundColor = badgeBackground,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
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

data class FeatureCardItem(
    val text: String = "",
    val secondaryText: String = "",
    val tertiaryText: String = "",
    val badgeText: String = "",
    val background: Color? = null,
    val badgeBackground: Color? = null
)


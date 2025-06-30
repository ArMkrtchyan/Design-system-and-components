package am.acba.compose.components.featureCard

import am.acba.component.R
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun OfferCardExpanded(
    modifier: Modifier = Modifier,
    offerAmount: String,
    creditLimitTitle: String,
    offerExpirationDate: String,
    badge: String = "",
    width: Int? = null,
    cardBackground: Color = DigitalTheme.colorScheme.backgroundAlternative6,
    badgeBackground: Color = DigitalTheme.colorScheme.backgroundSuccess,
    onIconClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground)
    ) {
        val columnModifier = if (width != null) Modifier.width(width.dp) else Modifier.fillMaxWidth()
        Column(
            modifier = columnModifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                PrimaryText(
                    creditLimitTitle,
                    color = DigitalTheme.colorScheme.contentInverse,
                    style = DigitalTheme.typography.body2Regular,
                    modifier = Modifier.weight(1f)
                )
                CircleBox(onIconClick)
            }
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
                badge.takeIf { it.isNotEmpty() }?.let {
                    Badge(
                        badgeType = BadgeEnum.INFO,
                        text = it,
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
private fun OfferCardExpandedPreview() {
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            OfferCardExpanded(
                offerAmount = "10,000,000.00 AMD",
                creditLimitTitle = "վարկային սահմանաչափ",
                offerExpirationDate = "Վերջնաժամկետ 12/09/2024",
                badge = "նոր",
            )
        }
    }
}
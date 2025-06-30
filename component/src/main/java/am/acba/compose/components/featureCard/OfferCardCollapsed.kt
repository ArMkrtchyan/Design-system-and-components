package am.acba.compose.components.featureCard

import am.acba.compose.HorizontalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun OfferCardCollapsed(
    modifier: Modifier = Modifier,
    offerAmount: String,
    creditLimitTitle: String = "",
    badgeTitle: String = "",
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
            modifier = Modifier.fillMaxWidth()
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(38.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PrimaryText(
                offerAmount,
                color = DigitalTheme.colorScheme.contentInverse,
                style = DigitalTheme.typography.smallBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            HorizontalSpacer(8)
            PrimaryText(
                creditLimitTitle,
                color = DigitalTheme.colorScheme.contentInverse,
                style = DigitalTheme.typography.xSmallRegular,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            if (badgeTitle.isNotEmpty()) {
                Badge(
                    badgeType = BadgeEnum.INFO,
                    text = badgeTitle,
                    backgroundColor = badgeBackground,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun OfferCardCollapsedPreview() {
    DigitalTheme {
        Column(
            Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(10.dp)
        ) {
            OfferCardCollapsed(
                offerAmount = "10,000,000.00 AMD",
                creditLimitTitle = "վարկային սահմանաչափ",
                badgeTitle = "նոր",
            )
        }
    }
}
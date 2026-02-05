package am.acba.compose.components.stockListItem

import am.acba.component.R
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.avatar.Avatar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.avatar.AvatarImage
import am.acba.compose.components.avatar.AvatarSizeEnum
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.id
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StockListItem(
    modifier: Modifier = Modifier,

    infoText: String,
    infoTitle: String,
    infoSubTitle: String? = null,

    middleTitle: String? = null,
    middleValue: String? = null,

    endTitle: String,
    endValue: String,
    endPercentText: String,
    endPercent: Double,
    endPercentIconUrl: String? = null,

    avatarBackgroundModifier: Modifier = Modifier,
    avatarBadgeModifier: Modifier = Modifier,
    avatarContentModifier: Modifier = Modifier,
    avatarType: AvatarEnum = AvatarEnum.ICON,
    avatarBackgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal2,
    avatarBackgroundRadius: Int = 8,
    avatarIcon: Int? = R.drawable.ic_stock,
    avatarIconColor: Color = DigitalTheme.colorScheme.contentInfoTonal1,
    avatarIconPadding: Dp = 8.dp,
    avatarImageUrl: String? = null,
    avatarClipPercent: Int = 0,
    avatarImageCornerRadius: Int? = null,
    avatarContentScale: ContentScale = ContentScale.Crop,
    avatarSize: AvatarSizeEnum = AvatarSizeEnum.AVATAR_SIZE_36,

    showDivider: Boolean = true,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            }
            .then(modifier)
    ) {
        Column {
            Row {
                if (avatarIcon != null || avatarImageUrl != null) {
                    StartIcon(
                        avatarBackgroundModifier,
                        avatarBadgeModifier,
                        avatarContentModifier,
                        avatarType,
                        avatarBackgroundColor,
                        avatarBackgroundRadius,
                        avatarIcon,
                        avatarIconColor,
                        avatarIconPadding,
                        avatarImageUrl,
                        avatarClipPercent,
                        avatarImageCornerRadius,
                        avatarContentScale,
                        avatarSize
                    )
                }
                InfoContent(infoText, infoTitle, infoSubTitle)
                HorizontalSpacer(8.dp)
                if (middleValue?.isNotEmpty() == true) {
                    MiddleContent(middleTitle ?: "", middleValue)
                    HorizontalSpacer(8.dp)
                }
                EndContent(endTitle, endValue, endPercentText, endPercent, endPercentIconUrl)
            }
            VerticalSpacer(6.dp)
            if (showDivider) {
                PrimaryDivider()
            }
        }
    }
}

@Composable
private fun StartIcon(
    avatarBackgroundModifier: Modifier = Modifier,
    avatarBadgeModifier: Modifier = Modifier,
    avatarContentModifier: Modifier = Modifier,
    avatarType: AvatarEnum = AvatarEnum.ICON,
    avatarBackgroundColor: Color = Color.Transparent,
    avatarBackgroundRadius: Int = 4,
    avatarIcon: Int? = null,
    avatarIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    avatarIconPadding: Dp = Dp.Unspecified,
    avatarImageUrl: String? = null,
    avatarClipPercent: Int = 0,
    avatarImageCornerRadius: Int? = null,
    avatarContentScale: ContentScale = ContentScale.Crop,
    avatarSize: AvatarSizeEnum = AvatarSizeEnum.AVATAR_SIZE_36,
) {
    Column {
        Avatar(
            backgroundModifier = avatarBackgroundModifier,
            badgeModifier = avatarBadgeModifier,
            contentModifier = avatarContentModifier,
            avatarType = avatarType,
            avatarSize = avatarSize,
            backgroundColor = avatarBackgroundColor,
            backgroundRadius = avatarBackgroundRadius,
            icon = avatarIcon,
            iconColor = avatarIconColor,
            iconPadding = avatarIconPadding,
            imageUrl = avatarImageUrl,
            clipPercent = avatarClipPercent,
            imageCornerRadius = avatarImageCornerRadius,
            contentScale = avatarContentScale,
        )
    }
    HorizontalSpacer(8.dp)
}

@Composable
private fun RowScope.InfoContent(infoText: String, infoTitle: String, infoSubTitle: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        PrimaryText(
            text = infoText,
            style = DigitalTheme.typography.xSmallRegular,
            color = DigitalTheme.colorScheme.contentPrimaryTonal1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .id("stockListItemInfo")
        )
        PrimaryText(
            text = infoTitle,
            style = DigitalTheme.typography.smallRegular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .id("stockListItemTitle")
        )
        if (infoSubTitle != null) {
            VerticalSpacer(2.dp)
            PrimaryText(
                text = infoSubTitle,
                style = DigitalTheme.typography.xSmallRegular,
                color = DigitalTheme.colorScheme.contentPrimaryTonal1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .id("stockListItemSubTitle")
            )
        }
    }
}

@Composable
private fun MiddleContent(middleTitle: String, middleValue: String) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        PrimaryText(
            text = middleTitle,
            style = DigitalTheme.typography.xSmallRegular,
            color = DigitalTheme.colorScheme.contentPrimaryTonal1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .id("stockListItemMiddleTitle")
        )
        PrimaryText(
            text = middleValue,
            style = DigitalTheme.typography.smallRegular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .id("stockListItemMiddleValue")
        )
    }
}

@Composable
private fun EndContent(
    endTitle: String,
    endValue: String,
    endPercentText: String,
    endPercent: Double,
    endPercentIconUrl: String?,
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        PrimaryText(
            text = endTitle,
            style = DigitalTheme.typography.xSmallRegular,
            color = DigitalTheme.colorScheme.contentPrimaryTonal1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .id("stockListItemEndTitle")
        )
        PrimaryText(
            text = endValue,
            style = DigitalTheme.typography.smallBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .id("stockListItemEndValue")
        )
        VerticalSpacer(2.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val endPercentColor: Color
            if (endPercent < 0) {
                endPercentColor = DigitalTheme.colorScheme.contentDangerTonal1
            } else if (endPercent > 0) {
                endPercentColor = DigitalTheme.colorScheme.contentBrand
            } else {
                endPercentColor = DigitalTheme.colorScheme.contentPrimaryTonal1
            }
            if (endPercent != 0.0 && endPercentIconUrl != null) {
                Box(
                    Modifier
                        .size(14.dp)
                ) {
                    AvatarImage(
                        imageUrl = endPercentIconUrl,
                        iconColor = endPercentColor,
                        modifier = Modifier
                            .size(14.dp)
                            .id("stockListItemEndPercentIcon")
                    )
                }
                HorizontalSpacer(4.dp)
            }
            PrimaryText(
                text = endPercentText,
                style = DigitalTheme.typography.xSmallRegular,
                color = endPercentColor,
                maxLines = 1,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .id("stockListItemEndPercent")
            )
        }
    }
}

@Composable
@PreviewLightDark
fun Preview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            StockListItem(
                avatarIcon = R.drawable.ic_stock,
                infoText = "APPL",
                infoTitle = "Apple",

                endTitle = "Քանակ։ 25",
                endValue = "2,122,233.12 USD",
                endPercent = 0.36,
                endPercentText = "+0.36%"
            )
            VerticalSpacer(16.dp)
            StockListItem(
                avatarIcon = R.drawable.ic_stock,
                infoText = "AMACBAS10ER7",
                infoTitle = "Ակբա բաց բաժնետիրական",
                infoSubTitle = "Մարումը՝ 01/03/2025",

                middleTitle = "Շուկ. եկամ",
                middleValue = "8,321%",

                endTitle = "Քանակ։ 25",
                endValue = "2,122,233.12 USD",
                endPercent = 0.0,
                endPercentText = "0.00%"
            )
            VerticalSpacer(16.dp)
            StockListItem(
                avatarIcon = R.drawable.ic_stock,
                infoText = "AMACBAS10ER7",
                infoTitle = "Ակբա բաց բաժնետիրական",
                infoSubTitle = "Մարումը՝ 01/03/2025",

                middleTitle = "Շուկ. եկամ",
                middleValue = "8,321%",

                endTitle = "Քանակ։ 25",
                endValue = "2,122,233.12 USD",
                endPercent = -0.5,
                endPercentText = "-0.5%"
            )
        }
    }
}
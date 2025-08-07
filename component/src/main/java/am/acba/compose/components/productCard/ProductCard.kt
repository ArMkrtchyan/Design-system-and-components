package am.acba.compose.components.productCard

import am.acba.component.R
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.StatusBadge
import am.acba.compose.components.avatar.Avatar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.avatar.AvatarSizeEnum
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import am.acba.utils.extensions.id
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    startAvatarBackgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal2,
    startAvatarBackgroundRadius: Int = 8,
    startAvatarIcon: Int? = null,
    startAvatarIconColor: Color = DigitalTheme.colorScheme.contentBrand,
    startAvatarIconPadding: Dp = 8.dp,
    startAvatarImageUrl: String? = null,
    startAvatarClipPercent: Int = 0,
    startAvatarImageCornerRadius: Int? = 4,
    startAvatarContentScale: ContentScale = ContentScale.Crop,
    title: String,
    description: String? = null,
    bottomRowTitle1: String = "",
    bottomRowValue1: String? = null,
    bottomRowTitle2: String = "",
    bottomRowValue2: String? = null,
    rowItems: List<Pair<String, String>> = emptyList(),
    statusModifier: Modifier = Modifier,
    statusTitle: String? = null,
    statusIcon: Int? = null,
    statusBackgroundColor: Color = DigitalTheme.colorScheme.borderNeutral,
    statusIconColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    statusTextColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    statusAlign: Alignment = Alignment.TopEnd,
    onClick: () -> Unit,
) {
    val showBottomRow1 = remember { mutableStateOf(!bottomRowValue1.isNullOrEmpty()) }
    val showBottomRow2 = remember { mutableStateOf(!bottomRowValue2.isNullOrEmpty()) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(DigitalTheme.colorScheme.backgroundTonal1, shape = ShapeTokens.shapePrimaryButton)
            .padding(top = 16.dp)
            .clickable { onClick.invoke() }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                ProductCardHeader(
                    title = title, description = description,
                    startAvatarBackgroundColor = startAvatarBackgroundColor,
                    startAvatarBackgroundRadius = startAvatarBackgroundRadius,
                    startAvatarIcon = startAvatarIcon,
                    startAvatarIconColor = startAvatarIconColor,
                    startAvatarIconPadding = startAvatarIconPadding,
                    startAvatarImageUrl = startAvatarImageUrl,
                    startAvatarClipPercent = startAvatarClipPercent,
                    startAvatarImageCornerRadius = startAvatarImageCornerRadius,
                    startAvatarContentScale = startAvatarContentScale
                )
                if (rowItems.isNotEmpty()) {
                    VerticalSpacer(16.dp)
                    ProductCardDynamicRows(rowItems, showBottomRow1.value || showBottomRow2.value)
                }
                if (showBottomRow1.value || showBottomRow2.value) {
                    VerticalSpacer(16.dp)
                }
                if (showBottomRow1.value) {
                    ProductRowTexts(bottomRowTitle1, bottomRowValue1 ?: "", index = 4)
                }
                if (showBottomRow1.value && showBottomRow2.value) {
                    VerticalSpacer(8.dp)
                }
                if (showBottomRow2.value) {
                    ProductRowTexts(
                        bottomRowTitle2,
                        bottomRowValue2 ?: "",
                        endTextStyle = DigitalTheme.typography.body2Bold,
                        endTextColor = DigitalTheme.colorScheme.contentPrimary, index = 5
                    )
                }
                if (showBottomRow1.value || showBottomRow2.value) {
                    VerticalSpacer(16.dp)
                }
            }
            if (!statusTitle.isNullOrEmpty()) {
                StatusBadge(
                    title = statusTitle,
                    icon = statusIcon,
                    textColor = statusTextColor,
                    iconColor = statusIconColor,
                    backgroundColor = statusBackgroundColor,
                    align = statusAlign,
                    modifier = statusModifier
                )
            }
        }
    }
}

@Composable
private fun ProductCardHeader(
    title: String,
    description: String? = null,
    startAvatarBackgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal2,
    startAvatarBackgroundRadius: Int = 0,
    startAvatarIcon: Int? = null,
    startAvatarIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    startAvatarIconPadding: Dp = Dp.Unspecified,
    startAvatarImageUrl: String? = null,
    startAvatarClipPercent: Int = 0,
    startAvatarImageCornerRadius: Int? = null,
    startAvatarContentScale: ContentScale = ContentScale.Crop,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = description?.let { Alignment.Top } ?: Alignment.CenterVertically
    ) {
        if (startAvatarIcon != null || !startAvatarImageUrl.isNullOrEmpty()) {
            Avatar(
                avatarType = startAvatarIcon?.let { AvatarEnum.ICON } ?: AvatarEnum.IMAGE,
                avatarSize = AvatarSizeEnum.AVATAR_SIZE_36,
                icon = startAvatarIcon,
                iconColor = startAvatarIconColor,
                backgroundColor = startAvatarBackgroundColor,
                backgroundRadius = startAvatarBackgroundRadius,
                iconPadding = startAvatarIconPadding,
                imageUrl = startAvatarImageUrl,
                clipPercent = startAvatarClipPercent,
                imageCornerRadius = startAvatarImageCornerRadius,
                contentScale = startAvatarContentScale,
                backgroundModifier = Modifier.id("productCardIcon")
            )
            HorizontalSpacer(16.dp)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .height(IntrinsicSize.Max)
        ) {
            PrimaryText(
                modifier = Modifier
                    .fillMaxWidth()
                    .id("productCardTitle"),
                text = title,
                maxLines = 1,
                style = DigitalTheme.typography.body2Bold,
            )
            description?.let {
                PrimaryText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .id("productCardDescription"),
                    text = description,
                    style = DigitalTheme.typography.xSmallRegular,
                    color = DigitalTheme.colorScheme.contentPrimaryTonal1,
                )
            }
        }
        HorizontalSpacer(16.dp)
        Avatar(icon = R.drawable.ic_right, iconColor = DigitalTheme.colorScheme.contentPrimaryTonal1, backgroundModifier = Modifier.id("productCardEndIcon"))
    }
}

@Composable
private fun ProductCardDynamicRows(items: List<Pair<String, String>>, showLastLine: Boolean) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        items.forEachIndexed { index, item ->
            ProductCardDynamicRowItem(item, index == items.size - 1, showLastLine, index + 1)
        }
    }
}

@Composable
private fun ProductCardDynamicRowItem(item: Pair<String, String>, isLast: Boolean, showLastLine: Boolean, index: Int) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        ProductRowTexts(
            item.first,
            item.second,
            modifier = Modifier.padding(vertical = 12.dp),
            endTextStyle = DigitalTheme.typography.smallBold,
            endTextColor = DigitalTheme.colorScheme.contentPrimary,
            index = index
        )
        if (!isLast) {
            PrimaryDivider()
        } else if (showLastLine) {
            PrimaryDivider()
        }
    }
}

@Composable
private fun ProductRowTexts(
    startText: String,
    endText: String,
    startTextStyle: TextStyle = DigitalTheme.typography.smallRegular,
    endTextStyle: TextStyle = DigitalTheme.typography.smallRegular,
    startTextColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    endTextColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    modifier: Modifier = Modifier,
    index: Int
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PrimaryText(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .id("productCardRowTitle${index}"),
            text = startText,
            style = startTextStyle,
            color = startTextColor,
        )
        PrimaryText(
            modifier = Modifier
                .weight(1f)
                .id("productCardRowValue${index}"),
            text = endText,
            style = endTextStyle,
            color = endTextColor,
            textAlign = TextAlign.End
        )
    }
}

@Composable
@PreviewLightDark
fun ProductCardPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            ProductCard(
                title = "Ավանդի գրավորվ վարկային",
                startAvatarImageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType37_1_1.png",
                startAvatarContentScale = ContentScale.FillWidth,
                startAvatarImageCornerRadius = 4,
                bottomRowTitle1 = "Վճարման օր",
                bottomRowValue1 = "14/սեպ/2024",
                bottomRowTitle2 = "Վճարման ենթակա գումար",
                bottomRowValue2 = "40,000.00 AMD",
            ) {}
            VerticalSpacer(20.dp)
            ProductCard(
                title = "Ավանդի գրավորվ վարկային", description = "Վերջ - 12/սեպ/2024", rowItems = arrayListOf(
                    "Պայմանագրի համար" to "1231456789",
                    "Սկզբնական գումար" to "200,000.00 AMD",
                    "Ընթացիկ պարտք" to "200,000.00 AMD"
                )
            ) {}
            VerticalSpacer(20.dp)
            ProductCard(
                title = "Ավանդի գրավորվ վարկային", description = "Վերջ - 12/սեպ/2024",
                rowItems = arrayListOf(
                    "Պայմանագրի համար" to "1231456789",
                    "Սկզբնական գումար" to "200,000.00 AMD",
                    "Ընթացիկ պարտք" to "200,000.00 AMD"
                ),
                bottomRowTitle1 = "Վճարման օր",
                bottomRowValue1 = "14/սեպ/2024",
                bottomRowTitle2 = "Վճարման ենթակա գումար",
                bottomRowValue2 = "40,000.00 AMD",
            ) {}
            VerticalSpacer(20.dp)
            ProductCard(
                title = "Ավանդի գրավորվ վարկային", description = "Վերջ - 12/սեպ/2024",
                rowItems = arrayListOf(
                    "Պայմանագրի համար" to "1231456789",
                    "Սկզբնական գումար" to "200,000.00 AMD",
                    "Ընթացիկ պարտք" to "200,000.00 AMD"
                ),
                bottomRowTitle1 = "Վճարման օր",
                bottomRowValue1 = "14/սեպ/2024",
            ) {}
        }
    }
}
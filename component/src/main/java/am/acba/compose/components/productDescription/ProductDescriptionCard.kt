@file:OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)

package am.acba.compose.components.productDescription

import am.acba.component.R
import am.acba.component.extensions.dpToPx
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.timeLine.ProductDescriptionBadge
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun <T : IProductDescription> ProductDescriptionCard(
    modifier: Modifier = Modifier,
    productDescription: T,
    onClick: (T) -> Unit
) {
    val contentMinHeight = remember { mutableIntStateOf(100) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 190.dp)
            .background(DigitalTheme.colorScheme.backgroundTonal1, ShapeTokens.shapePrimaryButton)
            .padding(top = 12.dp, start = 16.dp)
            .clickable { onClick.invoke(productDescription) }
    ) {
        ProductDescriptionHeader(productDescription.title, productDescription.subTitle, contentMinHeight)
        ProductDescriptionContent(productDescription, contentMinHeight)
    }
}

@Composable
@NonRestartableComposable
private fun ProductDescriptionHeader(title: String, subTitle: String, contentMinHeight: MutableIntState) {
    Column(
        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
            contentMinHeight.intValue = 190.dpToPx() - layoutCoordinates.size.height
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)

        ) {
            ProductDescriptionHeaderTitle(title)
            HorizontalSpacer(8.dp)
            ProductDescriptionHeaderIcon()
        }
        VerticalSpacer(12.dp)
        ProductDescriptionSubTitle(subTitle)
    }
}

@Composable
@NonRestartableComposable
private fun RowScope.ProductDescriptionHeaderTitle(title: String) {
    VerticalSpacer(8.dp)
    PrimaryText(
        modifier = Modifier
            .weight(1f)
            .padding(end = 16.dp),
        text = title,
        style = DigitalTheme.typography.body1Bold,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
@NonRestartableComposable
private fun ProductDescriptionHeaderIcon() {
    PrimaryIcon(painter = painterResource(R.drawable.ic_right))
}

@Composable
@NonRestartableComposable
private fun ProductDescriptionSubTitle(subTitle: String) {
    if (subTitle.isNotEmpty()) {
        PrimaryText(
            modifier = Modifier.fillMaxWidth(),
            text = subTitle,
            style = DigitalTheme.typography.body2Regular
        )
    }
}

@Composable
@NonRestartableComposable
private fun <T : IProductDescription> ProductDescriptionContent(productDescription: T, contentMinHeight: MutableIntState) {
    val mediaHeight = remember { mutableIntStateOf(200) }
    val density = LocalDensity.current
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .heightIn(min = with(density) { contentMinHeight.intValue.toDp() })
                .onGloballyPositioned { layoutCoordinates ->
                    mediaHeight.intValue = layoutCoordinates.size.height
                }
        ) {
            VerticalSpacer(12.dp)
            ProductDescriptionSecondTitle(productDescription.secondTitle)
            ProductDescriptionBadges(productDescription.badges, productDescription.badgeBackgroundColor, productDescription.badgeTextColor)
            ProductDescriptionSecondSubTitle(productDescription.secondSubTitle)
            ProductDescriptionBullets(productDescription.bullets)
        }
        ProductDescriptionMedia(productDescription.mediaImage, with(density) { mediaHeight.intValue.toDp() })
    }
}

@Composable
@NonRestartableComposable
private fun ProductDescriptionSecondTitle(secondTitle: String) {
    if (secondTitle.isNotEmpty()) {
        PrimaryText(
            modifier = Modifier.fillMaxWidth(),
            text = secondTitle,
            style = DigitalTheme.typography.xSmallBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        VerticalSpacer(8.dp)
    }
}

@Composable
@NonRestartableComposable
private fun ProductDescriptionBadges(badges: List<ProductDescriptionBadge>, badgeBackgroundColor: Color?, badgeTextColor: Color?) {
    if (badges.isNotEmpty()) {
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            badges.forEach { badge ->
                Badge(
                    text = badge.title,
                    backgroundColor = badgeBackgroundColor ?: DigitalTheme.colorScheme.backgroundTonal2,
                    textColor = badgeTextColor ?: DigitalTheme.colorScheme.contentPrimary,
                    imageUrl = badge.iconUrl
                )
            }
        }
        VerticalSpacer(8.dp)
    }
}

@Composable
@NonRestartableComposable
private fun ProductDescriptionSecondSubTitle(secondSubTitle: String) {
    if (secondSubTitle.isNotEmpty()) {
        PrimaryText(
            modifier = Modifier.fillMaxWidth(),
            text = secondSubTitle,
            style = DigitalTheme.typography.smallRegular
        )
        VerticalSpacer(8.dp)
    }
}

@Composable
@NonRestartableComposable
private fun ProductDescriptionBullets(bullets: List<String>) {
    if (bullets.isNotEmpty()) {
        bullets.forEach { bullet ->
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                PrimaryIcon(modifier = Modifier.size(20.dp), painter = painterResource(R.drawable.ic_success_small), tint = DigitalTheme.colorScheme.contentBrand)
                HorizontalSpacer(4.dp)
                PrimaryText(text = bullet, style = DigitalTheme.typography.smallRegular, modifier = Modifier.weight(1f))
            }
            VerticalSpacer(4.dp)
        }
    }
    VerticalSpacer(12.dp)
}

@Composable
@NonRestartableComposable
private fun ProductDescriptionMedia(imageUrl: String, height: Dp) {
    Box(
        modifier = Modifier
            .height(height)
            .fillMaxWidth(fraction = 0.38f)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .background(DigitalTheme.colorScheme.backgroundTonal2, RoundedCornerShape(12.dp))
        )

        GlideImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(ClipRightBottomRoundedShape()),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}

private class ClipRightBottomRoundedShape : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f,
                    right = size.width - 16.dpToPx(),
                    top = 0f,
                    bottom = size.height - 16.dpToPx(),
                    bottomRightCornerRadius = CornerRadius(12.dpToPx().toFloat(), 12.dpToPx().toFloat())
                )
            )
        }
        return Outline.Generic(path)
    }

}

@Composable
@PreviewLightDark
fun ProductDescriptionCardPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            ProductDescriptionCard(productDescription = createMockState(MockState(1))) {}
            VerticalSpacer(20.dp)
            ProductDescriptionCard(productDescription = createMockState(MockState(1))) {}
        }
    }
}
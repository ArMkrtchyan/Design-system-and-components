package am.acba.compose.components.guide


import am.acba.component.R
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryButtonSmall
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GuidePopUpLayer(
    modifier: Modifier = Modifier,
    currentCoordinatePosition: MutableIntState,
    isAnchorTop: Boolean = true,
    anchorXPosition: Dp = 0.dp,
    guides: List<IGuide> = emptyList(),
    onFinished: () -> Unit = {}
) {
    val guide = guides[currentCoordinatePosition.intValue]
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(fraction = 0.8f)
    ) {
        if (isAnchorTop) {
            Anchor(true, -anchorXPosition)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(DigitalTheme.colorScheme.backgroundTonal1, ShapeTokens.shapePrimaryButton)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                PrimaryText(text = "Ռեֆերալ կոդ", modifier = Modifier.weight(1f), style = DigitalTheme.typography.subTitle2Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                if (guides.size > 1) {
                    PrimaryIcon(painter = painterResource(R.drawable.ic_close_small_2),
                        tint = DigitalTheme.colorScheme.contentPrimaryTonal1,
                        modifier = Modifier.clickable { onFinished.invoke() })
                }
            }
            VerticalSpacer(12)
            PrimaryText(
                text = "Ստացեք դրամական քեշբեք հավելվածի միջոցով կատարված վճարումների համար",
                modifier = Modifier.fillMaxWidth(),
                style = DigitalTheme.typography.body2Regular,
                color = DigitalTheme.colorScheme.contentPrimaryTonal1,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            VerticalSpacer(16)
            guide.Content()
            PrimaryButtonSmall(
                text = "Next",
                modifier = Modifier
                    .wrapContentSize(),
            ) {
                if (currentCoordinatePosition.intValue < guides.size - 1) {
                    currentCoordinatePosition.intValue++
                } else {
                    onFinished.invoke()
                }
            }
        }
        if (!isAnchorTop) {
            Anchor(false, anchorXPosition)
        }

    }
}

@Composable
private fun Anchor(isAnchorTop: Boolean, anchorXPosition: Dp) {
    val rotate = if (isAnchorTop) 180f else 0f
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PrimaryIcon(
            painter = painterResource(R.drawable.ic_anchor),
            tint = DigitalTheme.colorScheme.backgroundTonal1,
            modifier = Modifier
                .rotate(rotate)
                .offset(x = anchorXPosition)
        )
    }
}

@Composable
@PreviewLightDark
fun GuidePopUpLayerPreview() {
    val currentCoordinatePosition = remember { mutableIntStateOf(0) }
    DigitalTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            GuidePopUpLayer(currentCoordinatePosition = currentCoordinatePosition, guides = listOf(GuideItem()))
        }
    }
}
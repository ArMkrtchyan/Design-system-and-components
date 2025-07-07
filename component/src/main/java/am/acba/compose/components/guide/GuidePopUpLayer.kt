package am.acba.compose.components.guide


import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    completeButtonText: String = stringResource(R.string.ok),
    onFinished: () -> Unit = {}
) {
    val guide = guides[currentCoordinatePosition.intValue]
    val size = guides.size
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(fraction = 0.8f)
    ) {
        if (isAnchorTop) Anchor(true, -anchorXPosition)
        CardContent(guide, size, currentCoordinatePosition, completeButtonText, onFinished)
        if (!isAnchorTop) Anchor(false, anchorXPosition)
    }
}

@Composable
private fun CardContent(guide: IGuide, size: Int, currentCoordinatePosition: MutableIntState, completeButtonText: String, onFinished: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DigitalTheme.colorScheme.backgroundTonal1, ShapeTokens.shapePrimaryButton)
            .padding(16.dp)
    ) {
        TitleWithCloseIcon(guide.title, size > 1, onFinished)
        VerticalSpacer(12)

        Description(guide.description)
        VerticalSpacer(16)

        guide.content?.let {
            it.invoke()
            VerticalSpacer(16)
        }

        NavigationButtonsWithText(currentCoordinatePosition, size, completeButtonText, onFinished)
    }
}

@Composable
private fun TitleWithCloseIcon(title: String, showCloseIcon: Boolean, onFinished: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        PrimaryText(text = title, modifier = Modifier.weight(1f), style = DigitalTheme.typography.subTitle2Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
        if (showCloseIcon) {
            PrimaryIcon(painter = painterResource(R.drawable.ic_close_small_2),
                tint = DigitalTheme.colorScheme.contentPrimaryTonal1,
                modifier = Modifier.clickable { onFinished.invoke() })
        }
    }
}

@Composable
private fun Description(description: String) {
    PrimaryText(
        text = description,
        modifier = Modifier.fillMaxWidth(),
        style = DigitalTheme.typography.body2Regular,
        color = DigitalTheme.colorScheme.contentPrimaryTonal1,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun NavigationButtonsWithText(currentCoordinatePosition: MutableIntState, size: Int, completeButtonText: String, onFinished: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val count = if (size > 1) "${currentCoordinatePosition.intValue + 1}/${size}" else ""
        PageCounter(count)
        NavigationButtons(currentCoordinatePosition, size, completeButtonText, onFinished)
    }
}

@Composable
private fun RowScope.PageCounter(count: String) {
    PrimaryText(
        text = count,
        modifier = Modifier.weight(1f),
        style = DigitalTheme.typography.subTitle2Regular,
        color = DigitalTheme.colorScheme.contentPrimaryTonal1,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun NavigationButtons(currentCoordinatePosition: MutableIntState, size: Int, completeButtonText: String, onFinished: () -> Unit) {
    if (size > 1) {
        when (currentCoordinatePosition.intValue) {
            0 -> NavigationButtonsStartState(currentCoordinatePosition)
            size - 1 -> NavigationButtonsEndState(currentCoordinatePosition, completeButtonText, onFinished)
            else -> NavigationButtonsMiddleState(currentCoordinatePosition)
        }
    } else {
        CompleteButton(completeButtonText, onFinished)
    }
}

@Composable
private fun NavigationButtonsStartState(currentCoordinatePosition: MutableIntState) {
    LeftRightIcon(false, currentCoordinatePosition)
}

@Composable
private fun NavigationButtonsMiddleState(currentCoordinatePosition: MutableIntState) {
    LeftRightIcon(true, currentCoordinatePosition)
    HorizontalSpacer(8)
    LeftRightIcon(false, currentCoordinatePosition)
}

@Composable
private fun NavigationButtonsEndState(currentCoordinatePosition: MutableIntState, completeButtonText: String, onFinished: () -> Unit) {
    LeftRightIcon(true, currentCoordinatePosition)
    HorizontalSpacer(8)
    CompleteButton(completeButtonText, onFinished)
}

@Composable
@NonRestartableComposable
private fun LeftRightIcon(isLeft: Boolean, currentCoordinatePosition: MutableIntState) {
    val paintRes = if (isLeft) R.drawable.ic_left else R.drawable.ic_right
    Box(
        modifier = Modifier
            .width(40.dp)
            .height(34.dp)
            .border(1.dp, DigitalTheme.colorScheme.borderPrimaryTonal1, ShapeTokens.shapeBadge)
            .clickable {
                if (isLeft) {
                    currentCoordinatePosition.intValue--
                } else {
                    currentCoordinatePosition.intValue++
                }
            }
    ) {
        PrimaryIcon(
            painter = painterResource(paintRes),
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
@NonRestartableComposable
private fun CompleteButton(text: String, onFinished: () -> Unit) {
    Box(
        modifier = Modifier
            .background(DigitalTheme.colorScheme.backgroundBrand, ShapeTokens.shapeBadge)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onFinished.invoke() }
    ) {
        PrimaryText(
            text = text,
            style = DigitalTheme.typography.smallRegular,
            color = DigitalTheme.colorScheme.contentSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun Anchor(isAnchorTop: Boolean, anchorXPosition: Dp) {
    val rotate = if (isAnchorTop) 180f else 0f
    Box(modifier = Modifier.fillMaxWidth()) {
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
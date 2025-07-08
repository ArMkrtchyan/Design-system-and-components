package am.acba.compose.components.timeLine

import am.acba.component.R
import am.acba.component.primaryTimeLine.TimeLineType
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun <T : ITimeLineCompose> PrimaryTimeLine(
    modifier: Modifier = Modifier,
    endIconPainter: Painter? = null,
    endIconTint: Color = DigitalTheme.colorScheme.contentPrimary,
    title: String = "",
    titleMaxLines: Int = 2,
    timeLineItems: List<T>,
    timeLineType: TimeLineType = TimeLineType.TIMELINE,
    onClick: () -> Unit = {},
    onItemClick: (T) -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(ShapeTokens.shapePrimaryButton)
            .background(DigitalTheme.colorScheme.backgroundTonal1)
            .padding(top = 16.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Column {
            if (endIconPainter != null || title.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable {
                            onClick.invoke()
                        }, verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    PrimaryText(modifier = Modifier.weight(1f), text = title, maxLines = titleMaxLines, style = DigitalTheme.typography.body2Bold)
                    endIconPainter?.let {
                        HorizontalSpacer(8)
                        PrimaryIcon(modifier = Modifier.size(20.dp), painter = endIconPainter, tint = endIconTint)
                    }
                }
                VerticalSpacer(16)
            }

            timeLineItems.forEachIndexed { index, timeLineItem ->
                TimeLineItem(timeLineItem, timeLineItems, index, timeLineItems.size, timeLineType, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
private fun <T : ITimeLineCompose> TimeLineItem(item: T, timeLineItems: List<T>, index: Int, itemCount: Int, timeLineType: TimeLineType, onItemClick: (T) -> Unit = {}) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .fillMaxHeight()
            ) {
                if (itemCount > 1) {
                    TimeLineItemProgressLines(item, timeLineItems, index, itemCount, timeLineType)
                }
                TimeLineItemIcon(item)
            }
            HorizontalSpacer(8)
            TimeLineItemContent(item, onItemClick = onItemClick)
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(16.dp)
                .padding(start = 25.5.dp)
        ) {
            if (itemCount > 1 && (timeLineType == TimeLineType.TIMELINE || index != itemCount - 1)) {
                var color = DigitalTheme.colorScheme.backgroundTonal3
                if (timeLineType == TimeLineType.PROGRESS && item.getStatus() == TimeLineStatusComposeEnum.SUCCESS) {
                    color = DigitalTheme.colorScheme.backgroundSuccess
                }
                Box(
                    Modifier
                        .width(1.dp)
                        .background(color)
                        .height(16.dp)
                )
            }
        }
    }
}

@Composable
private fun BoxScope.TimeLineItemIcon(item: ITimeLineCompose) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(DigitalTheme.colorScheme.backgroundTonal1, ShapeTokens.shapeRound)
            .align(Alignment.Center)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(item.getIconBackgroundColor(), ShapeTokens.shapeRound)
                .border(1.dp, item.getIconBorderColor(), ShapeTokens.shapeRound)
                .align(Alignment.Center)
        ) {
            if (item.getStatus() != TimeLineStatusComposeEnum.NONE) {
                PrimaryIcon(
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.Center),
                    painter = painterResource(item.getStartIcon()),
                    tint = item.getIconColor()
                )
            }
        }
    }
}


@Composable
private fun <T : ITimeLineCompose> TimeLineItemProgressLines(item: T, timeLineItems: List<T>, index: Int, itemCount: Int, timeLineType: TimeLineType) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .width(20.dp)
            .fillMaxHeight()
    ) {
        val topLineColor = when {
            index == 0 -> Color.Transparent
            timeLineType == TimeLineType.PROGRESS && timeLineItems[index - 1].getStatus() == TimeLineStatusComposeEnum.SUCCESS -> DigitalTheme.colorScheme.backgroundSuccess
            else -> DigitalTheme.colorScheme.backgroundTonal3
        }
        val bottomLineColor = when {
            index == itemCount - 1 && timeLineType == TimeLineType.PROGRESS -> {
                Color.Transparent
            }

            timeLineType == TimeLineType.PROGRESS && item.getStatus() == TimeLineStatusComposeEnum.SUCCESS -> {
                DigitalTheme.colorScheme.backgroundSuccess
            }

            else -> {
                DigitalTheme.colorScheme.backgroundTonal3
            }
        }
        Box(
            Modifier
                .width(1.dp)
                .background(topLineColor)
                .fillMaxHeight(fraction = 0.5f)
        )
        Box(
            Modifier
                .width(1.dp)
                .background(bottomLineColor)
                .fillMaxHeight()
        )
    }
}

@Composable
private fun <T : ITimeLineCompose> TimeLineItemContent(item: T, onItemClick: (T) -> Unit = {}) {
    Column(
        modifier = Modifier
            .background(item.getContentBackgroundColor(), ShapeTokens.shapePrimaryButtonSmall)
            .padding(vertical = item.getStatus().verticalPadding.dp, horizontal = 8.dp)
            .clickable {
                onItemClick.invoke(item)
            }
    ) {
        val titleStyle = if (item.getStatus() == TimeLineStatusComposeEnum.NONE) DigitalTheme.typography.smallRegular else DigitalTheme.typography.body2Regular
        val endTextStyle = if (item.getStatus() == TimeLineStatusComposeEnum.NONE) DigitalTheme.typography.smallRegular else DigitalTheme.typography.subTitle2Bold
        Row {
            PrimaryText(modifier = Modifier.weight(1f), text = item.getTitle(), style = titleStyle, color = item.getTitleColor())
            item.getEndText()?.let {
                HorizontalSpacer(8)
                PrimaryText(modifier = Modifier.wrapContentWidth(), text = it, style = endTextStyle, color = item.getEndTextColor())
            }
        }
        item.getDescription()?.let {
            PrimaryText(modifier = Modifier.fillMaxWidth(), text = it, style = DigitalTheme.typography.xSmallRegular, color = item.getDescriptionColor())
        }
        item.getLinkText()?.let {
            VerticalSpacer(2)
            PrimaryText(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                style = DigitalTheme.typography.subTitle2Bold,
                textAlign = TextAlign.End,
                color = item.getLinkTextColor()
            )
        }
    }
}

@Composable
@PreviewLightDark
fun PrimaryListItemPreview() {
    DigitalTheme {
        val list = arrayListOf(
            TimeLineComposeModel(
                "14/10/2024",
                "400,000.00 AMD",
                "Մարումը՝ այսօր",
                "LinkText",
                timeLineStatus = TimeLineStatusComposeEnum.PENDING
            ),
//            TimeLineComposeModel(
//                "14/10/2024",
//                "400,000.00 AMD",
//                "Մարումը՝ այսօր",
//                "LinkText",
//                timeLineStatus = TimeLineStatusComposeEnum.PENDING_2
//            ),
//            TimeLineComposeModel(
//                "14/10/2024",
//                "400,000.00 AMD",
//                "Մարումը՝ այսօր",
//                "LinkText",
//                timeLineStatus = TimeLineStatusComposeEnum.WARNING
//            ),
//            TimeLineComposeModel(
//                "14/10/2024",
//                "400,000.00 AMD",
//                "Մարումը՝ այսօր",
//                "LinkText",
//                timeLineStatus = TimeLineStatusComposeEnum.SUCCESS
//            ),
//            TimeLineComposeModel(
//                "14/10/2024",
//                "400,000.00 AMD",
//                "Մարումը՝ այսօր",
//                "LinkText",
//                timeLineStatus = TimeLineStatusComposeEnum.DANGER,
//            ),
//            TimeLineComposeModel(
//                "14/10/2024",
//                "400,000.00 AMD",
//                "Մարումը՝ այսօր",
//                "LinkText",
//                timeLineStatus = TimeLineStatusComposeEnum.DANGER,
//                timeLineStartIcon = R.drawable.ic_close_small_2,
//                timeLineContentBackgroundColor = Color.Transparent,
//                timeLineLinkTextColor = DigitalTheme.colorScheme.contentInverse
//            ),
//            TimeLineComposeModel(
//                "14/10/2024",
//                timeLineStatus = TimeLineStatusComposeEnum.NONE
//            ),
//            TimeLineComposeModel(
//                "14/10/2024",
//                timeLineStatus = TimeLineStatusComposeEnum.NONE
//            ),
        )
        val progressList = arrayListOf(
            TimeLineComposeModel(
                timeLineTitle = "Դիմումի ուղարկում",
                timeLineDescription = "Ուղղարկվել է՝ 20.02.2024",
                timeLineStatus = TimeLineStatusComposeEnum.SUCCESS,
                timeLineContentBackgroundColor = Color.Transparent
            ),
            TimeLineComposeModel(
                timeLineTitle = "Դիմումի դիտարկում",
                timeLineDescription = "Դիտարկվել է՝ 20.02.2024",
                timeLineStatus = TimeLineStatusComposeEnum.SUCCESS,
                timeLineContentBackgroundColor = Color.Transparent
            ),
            TimeLineComposeModel(
                timeLineTitle = "ՀԴՄ սարքի  գրանցում բանկում",
                timeLineDescription = "Մինչև՝ 20.02.2024",
                timeLineStatus = TimeLineStatusComposeEnum.SUCCESS,
                timeLineContentBackgroundColor = Color.Transparent
            ),
            TimeLineComposeModel(
                timeLineTitle = "Պայմանագրի կնքում",
                timeLineDescription = "Մինչև ՝ 20.04.2023",
                timeLineStatus = TimeLineStatusComposeEnum.PENDING
            ),
            TimeLineComposeModel(
                timeLineTitle = "Սարքի ակտիվացում",
                timeLineDescription = "Պայմանագրի կնքումից հետո 2-3 աշխ. օրվա ընթացքում",
                timeLineStatus = TimeLineStatusComposeEnum.DEFAULT,
                timeLineContentBackgroundColor = Color.Transparent
            )
        )
        Column {
//            PrimaryTimeLine(
//                title = "Մարման գրաֆիկ",
//                endIconPainter = painterResource(R.drawable.ic_right),
//                timeLineItems = list,
//            )
//            VerticalSpacer(30)
//            PrimaryTimeLine(
//                timeLineItems = progressList,
//                timeLineType = TimeLineType.PROGRESS
//            )
            PrimaryTimeLine(
                title = "Մարման գրաֆիկ",
                endIconPainter = painterResource(R.drawable.ic_right),
                timeLineItems = list,
            )
        }
    }
}
package am.acba.compose.components.expandableCard

import am.acba.component.R
import am.acba.component.viewUtil.ViewUtil.copyWithVibration
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.StatusBadge
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal1,
    backgroundRadius: Int = 12,
    statusModifier: Modifier = Modifier,
    statusTitle: String? = null,
    statusIcon: Int? = null,
    statusBackgroundColor: Color = DigitalTheme.colorScheme.backgroundPending,
    statusIconColor: Color = DigitalTheme.colorScheme.contentPending,
    statusTextColor: Color = DigitalTheme.colorScheme.contentPending,
    statusAlign: Alignment = Alignment.TopEnd,
    expanded: Boolean = false,
    onCardClick: () -> Unit = {},
    pinedContent: @Composable () -> Unit,
    animatedContent: @Composable () -> Unit,
) {
    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "accordion-arrow",
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(backgroundRadius.dp))
            .padding(top = 16.dp)
            .clickable { onCardClick.invoke() }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                pinedContent.invoke()
                AnimatedContent(expanded, animatedContent)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .height(28.dp)

            ) {
                PrimaryIcon(
                    painter = painterResource(R.drawable.ic_down_2), modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.Center)
                        .graphicsLayer(rotationX = arrowRotation)
                )
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
fun AnimatedContent(expanded: Boolean, animatedContent: @Composable () -> Unit) {
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

    AnimatedVisibility(
        visible = expanded,
        enter = enterTransition,
        exit = exitTransition
    ) {
        animatedContent.invoke()
    }
}


@Composable
@PreviewLightDark
fun ExpandableCardPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            ExpandableCard(

                pinedContent = {
                    PrimaryText(
                        text = "Ընթացիկ պարտք",
                        style = DigitalTheme.typography.xSmallRegular,
                        color = DigitalTheme.colorScheme.contentPrimaryTonal1
                    )
                    VerticalSpacer(4.dp)
                    PrimaryText(
                        text = "400,000.00 AMD",
                        style = DigitalTheme.typography.heading6Bold,
                    )
                },
                animatedContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        VerticalSpacer(24.dp)
                        PrimaryDivider()
                        VerticalSpacer(12.dp)
                        AmountRow(
                            title = "Սկզբնական գումար",
                            value = "400,000.00 AMD"
                        )
                        AmountRow(
                            title = "Պայմանագրի համար",
                            value = "400,000.00 AMD"
                        )
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AmountRow(title: String, value: String) {
    val context = LocalContext.current
    Row(modifier = Modifier.padding(vertical = 12.dp)) {
        PrimaryText(
            text = title,
            modifier = Modifier.weight(0.5f),
            style = DigitalTheme.typography.smallRegular,
            color = DigitalTheme.colorScheme.contentPrimaryTonal1
        )
        HorizontalSpacer(8.dp)
        PrimaryText(text = value, modifier = Modifier
            .weight(0.5f)
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    context.copyWithVibration(value)
                }
            ), textAlign = TextAlign.End, style = DigitalTheme.typography.smallBold)
    }
}
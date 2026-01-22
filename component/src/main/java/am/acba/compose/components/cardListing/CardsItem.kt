package am.acba.compose.components.cardListing

import am.acba.component.R
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.StatusBadge
import am.acba.compose.components.avatar.AvatarImage
import am.acba.compose.components.badges.Badge
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.Constants.EMPTY_STRING
import am.acba.utils.extensions.id
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun CardsItem(
    modifier: Modifier = Modifier,
    cardStatusIcon: Int? = null,
    statusTitle: String? = null,
    statusIcon: Int? = null,
    endIcon: Int? = null,
    onEndIconClick: () -> Unit = {},
    swipeActionIcon: Int = R.drawable.ic_flake,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal1,
    endIconColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    statusBackgroundColor: Color = DigitalTheme.colorScheme.backgroundInfoTonal1,
    statusIconColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    statusTextColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    badgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundPending,
    badgeTextColor: Color = DigitalTheme.colorScheme.contentPending,
    actionBackgroundColor: Color = DigitalTheme.colorScheme.backgroundInfo,
    titleStyle: TextStyle = DigitalTheme.typography.body1Regular,
    subTitleStyle: TextStyle = DigitalTheme.typography.smallRegular,
    cardNumberStyle: TextStyle = DigitalTheme.typography.smallRegular,
    imageUrl: String = EMPTY_STRING,
    title: String = EMPTY_STRING,
    subTitle: String = EMPTY_STRING,
    cardNumber: String = EMPTY_STRING,
    badgeText: String = EMPTY_STRING,
    swipeActionText: String = EMPTY_STRING,
    id: String = "cardsItem",
    badgeType: BadgeEnum = BadgeEnum.NONE,
    backgroundRadius: Dp = 12.dp,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
    onSwipeAction: (Boolean) -> Unit = {},
    isEditingInitial: Boolean = false,
    isSwipEnabled: Boolean = true,
    isOpen: Boolean = false,
    isReordering: Boolean = false,
) {
    val density = LocalDensity.current
    var swipePx by remember { mutableFloatStateOf(0f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var startAnimation by remember { mutableStateOf(false) }
    val animatedOffsetX by animateFloatAsState(
        targetValue = if (offsetX < -swipePx / 2) offsetX else 0f,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing),
        label = "animated-offset"
    )

    val dragState = rememberDraggableState { delta ->
        if (!isEditingInitial) {
            offsetX = (offsetX + delta).coerceIn(-swipePx, 0f)
        }
    }
    val transition = updateTransition(targetState = isEditingInitial, label = "scale-transition")
    val scale by transition.animateFloat(
        label = "scale",
        transitionSpec = {
            tween(
                durationMillis = 200,
                delayMillis = 100
            )
        }
    ) { isEditingInitial ->
        if (isEditingInitial) 0f else 1f
    }

    val animatedWidth by animateDpAsState(
        targetValue = if (isEditingInitial) 24.dp else 0.dp,
        animationSpec = tween(durationMillis = 300),
        label = "width-animation"
    )

    LaunchedEffect(isEditingInitial, isOpen) {
        if (isEditingInitial) {
            delay(Random.nextInt(0, 200).toLong())
            offsetX = 0f
            startAnimation = true
        } else {
            startAnimation = false
        }
        if (!isOpen) offsetX = 0f
    }

    val rotation = editStateRotation(startAnimation)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .graphicsLayer { rotationZ = rotation }
            .background(actionBackgroundColor, RoundedCornerShape(backgroundRadius + 1.dp))
            .id(id)
            .then(modifier.combinedClickable(onClick = onClick, onLongClick = onLongClick))
    ) {
        SwipeableCardItem(
            id = id,
            onOffsetChange = { offsetX = it },
            onSwipePxChange = { swipePx = it },
            density = density,
            swipeActionIcon = swipeActionIcon,
            swipeActionText = swipeActionText,
            onActionClick = onActionClick
        )
        CardsItemContent(
            id = id,
            animatedOffsetX = animatedOffsetX,
            dragState = dragState,
            offsetX = offsetX,
            onOffsetChange = { offsetX = it },
            swipePx = swipePx,
            isEditingInitial = isEditingInitial,
            onSwipeAction = onSwipeAction,
            backgroundColor = backgroundColor,
            backgroundRadius = backgroundRadius,
            endIcon = endIcon,
            endIconColor = endIconColor,
            onEndIconClick = onEndIconClick,
            scale = scale,
            imageUrl = imageUrl,
            title = title,
            subTitle = subTitle,
            cardNumber = cardNumber,
            badgeText = badgeText,
            badgeType = badgeType,
            badgeBackgroundColor = badgeBackgroundColor,
            badgeTextColor = badgeTextColor,
            statusTitle = statusTitle,
            statusIcon = statusIcon,
            cardStatusIcon = cardStatusIcon,
            statusBackgroundColor = statusBackgroundColor,
            statusIconColor = statusIconColor,
            statusTextColor = statusTextColor,
            cardNumberStyle = cardNumberStyle,
            titleStyle = titleStyle,
            subTitleStyle = subTitleStyle,
            animatedWidth = animatedWidth,
            isSwipEnabled = isSwipEnabled,
            isReordering = isReordering
        )
    }
}

@Composable
private fun SwipeableCardItem(
    id: String,
    onOffsetChange: (Float) -> Unit,
    onSwipePxChange: (Float) -> Unit,
    density: Density,
    swipeActionIcon: Int,
    swipeActionText: String,
    onActionClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .id("${id}Box"),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .widthIn(min = 70.dp)
                .id("${id}Action")
                .clickable {
                    onActionClick.invoke()
                    onOffsetChange(0f)
                }
                .onGloballyPositioned { coordinates ->
                    val extraPaddingPx = with(density) { 28.dp.toPx() }
                    onSwipePxChange(coordinates.size.width.toFloat() + extraPaddingPx)
                }
        ) {
            PrimaryIcon(
                painter = painterResource(swipeActionIcon),
                tint = DigitalTheme.colorScheme.contentSecondary,
                modifier = Modifier
                    .size(28.dp)
                    .id("${id}ActionIcon")
            )
            PrimaryText(
                modifier = Modifier.id("${id}ActionText"),
                color = DigitalTheme.colorScheme.contentSecondary,
                text = swipeActionText,
                style = DigitalTheme.typography.smallRegular
            )
        }
    }
}

@Composable
private fun CardsItemContent(
    id: String,
    animatedOffsetX: Float,
    dragState: DraggableState,
    offsetX: Float,
    onOffsetChange: (Float) -> Unit,
    swipePx: Float,
    isEditingInitial: Boolean,
    onSwipeAction: (Boolean) -> Unit,
    backgroundColor: Color,
    backgroundRadius: Dp,
    endIcon: Int?,
    endIconColor: Color,
    onEndIconClick: () -> Unit,
    scale: Float,
    imageUrl: String,
    title: String,
    subTitle: String,
    cardNumber: String,
    badgeText: String,
    badgeType: BadgeEnum,
    badgeBackgroundColor: Color,
    badgeTextColor: Color,
    statusTitle: String?,
    statusIcon: Int?,
    cardStatusIcon: Int?,
    statusBackgroundColor: Color,
    statusIconColor: Color,
    statusTextColor: Color,
    cardNumberStyle: TextStyle,
    titleStyle: TextStyle,
    subTitleStyle: TextStyle,
    animatedWidth: Dp,
    isSwipEnabled: Boolean,
    isReordering: Boolean
) {
    val elevation by animateDpAsState(
        targetValue = if (isReordering) 8.dp else 0.dp,
        label = "shadow-animation"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(backgroundRadius)
            )
            .draggable(
                state = dragState,
                orientation = Orientation.Horizontal,
                enabled = !isEditingInitial && isSwipEnabled,
                onDragStopped = {
                    if (!isEditingInitial) {
                        val shouldOpen = offsetX < -swipePx / 2
                        onSwipeAction.invoke(shouldOpen)
                        onOffsetChange(if (shouldOpen) -swipePx else 0f)
                    }
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    backgroundColor,
                    shape = RoundedCornerShape(backgroundRadius)
                )
                .id("${id}MainColumn")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box {
                    AvatarImage(
                        modifier = Modifier
                            .width(100.dp)
                            .height(64.dp), clipPercent = 14, imageUrl = imageUrl
                    )
                    if (cardStatusIcon != null)
                        PrimaryIcon(
                            painter = painterResource(cardStatusIcon),
                            tint = DigitalTheme.colorScheme.contentSecondary,
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center)
                        )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .height(24.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PrimaryText(
                            modifier = Modifier.id("${id}Title"),
                            text = title,
                            style = titleStyle
                        )
                        if (endIcon != null && !isEditingInitial) {
                            HorizontalSpacer(8.dp)
                            PrimaryIcon(
                                painter = painterResource(endIcon),
                                tint = endIconColor,
                                modifier = Modifier
                                    .size(24.dp)
                                    .scale(scale)
                                    .clickable { onEndIconClick.invoke() }
                            )
                        }
                    }
                    if (subTitle.isNotEmpty()) {
                        PrimaryText(
                            modifier = Modifier.id("${id}SubTitle"),
                            text = subTitle,
                            maxLines = 1,
                            style = subTitleStyle,
                            overflow = TextOverflow.Ellipsis
                        )
                        VerticalSpacer(4.dp)
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PrimaryText(
                            modifier = Modifier
                                .id("${id}CardNumber")
                                .padding(top = 2.dp), text = cardNumber, style = cardNumberStyle
                        )
                    }
                }
                if (isEditingInitial) {
                    HorizontalSpacer(8.dp)
                }
                PrimaryIcon(
                    painter = painterResource(R.drawable.ic_drag_indicator),
                    tint = endIconColor,
                    modifier = Modifier
                        .size(animatedWidth)
                        .align(Alignment.CenterVertically)
                )
            }
            if (!statusTitle.isNullOrEmpty()) {
                StatusBadge(
                    title = statusTitle,
                    icon = statusIcon,
                    id = "${id}StatusBadge",
                    textColor = statusTextColor,
                    iconColor = statusIconColor,
                    backgroundColor = statusBackgroundColor,
                    align = Alignment.BottomEnd
                )
            }
        }
        if (badgeText.isNotEmpty())
            Badge(
                badgeType = badgeType, text = badgeText, backgroundColor = badgeBackgroundColor,
                textColor = badgeTextColor,
                modifier = Modifier
                    .padding(
                        end = if (isEditingInitial) 16.dp + animatedWidth + 8.dp else 16.dp,
                        bottom = if (!statusTitle.isNullOrEmpty()) 32.dp else 16.dp
                    )
                    .align(Alignment.BottomEnd)
                    .id("${id}Badge")
            )
    }
}

@Composable
private fun editStateRotation(startAnimation: Boolean): Float {
    val rotation: Float = if (startAnimation) {
        val infiniteTransition = rememberInfiniteTransition(label = "card_wobble")
        val randomStart = remember { if (Random.nextBoolean()) -0.5f else 0.5f }
        val randomEnd = randomStart * -1f
        val animatedRotation by infiniteTransition.animateFloat(
            initialValue = randomStart, targetValue = randomEnd, animationSpec = infiniteRepeatable(
                animation = tween(150, easing = LinearEasing), repeatMode = RepeatMode.Reverse
            ), label = "rotation"
        )
        animatedRotation
    } else {
        0f
    }
    return rotation
}

@Composable
@PreviewLightDark
fun CardsItemPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            CardsItem(
                title = "Mastercard Standard",
                cardNumber = "**** 5678",
                badgeText = "Badge",
                endIcon = R.drawable.ic_info,
                badgeType = BadgeEnum.INFO,
                imageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType41_1_1.png",
                isEditingInitial = false,
                statusTextColor = DigitalTheme.colorScheme.contentInfoTonal1,
                statusIcon = R.drawable.ic_info,
                statusTitle = "Քարտը պատրաստ է ակտիվացման",
                statusIconColor = DigitalTheme.colorScheme.contentInfoTonal1,
                statusBackgroundColor = DigitalTheme.colorScheme.backgroundInfoTonal1
            )
        }
    }
}


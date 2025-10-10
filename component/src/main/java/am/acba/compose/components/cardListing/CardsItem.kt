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
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CardsItem(
    modifier: Modifier = Modifier,
    backgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal1,
    backgroundRadius: Int = 12,
    imageUrl: String = "",
    statusModifier: Modifier = Modifier,
    statusTitle: String? = null,
    statusIcon: Int? = null,
    endIcon: Int? = null,
    endIconColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    statusBackgroundColor: Color = DigitalTheme.colorScheme.backgroundInfoTonal1,
    statusIconColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    statusTextColor: Color = DigitalTheme.colorScheme.contentPrimaryTonal1,
    statusAlign: Alignment = Alignment.TopEnd,
    title: String = "",
    titleStyle: TextStyle = DigitalTheme.typography.body1Regular,
    subTitle: String = "",
    subTitleStyle: TextStyle = DigitalTheme.typography.smallRegular,
    cardNumber: String = "",
    cardNumberStyle: TextStyle = DigitalTheme.typography.smallRegular,
    badgeText: String = "",
    badgeTextColor: Color = DigitalTheme.colorScheme.contentPending,
    badgeType: BadgeEnum = BadgeEnum.NONE,
    badgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundPending,
    isDraggableModeOn: Boolean = false,
    maxSwipe: Float = 100f,
    onClick: () -> Unit = {},
    isEditingInitial: Boolean = false,
    onDeleteClick: () -> Unit = {}
) {
    val swipePx = with(LocalDensity.current) { maxSwipe.dp.toPx() }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var isOpen by remember { mutableStateOf(false) }

    val rotation: Float = if (isEditingInitial) {
        val infiniteTransition = rememberInfiniteTransition(label = "card_wobble")
        val animatedRotation by infiniteTransition.animateFloat(
            initialValue = -1f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(150, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "rotation"
        )
        animatedRotation
    } else {
        0f
    }

    Box(
        modifier = modifier.graphicsLayer { rotationZ = rotation }
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                    },
                    onTap = { onClick() }
                )
            }
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    DigitalTheme.colorScheme.backgroundInfo,
                    RoundedCornerShape(backgroundRadius.dp)
                ),
            contentAlignment = Alignment.CenterEnd
        ) {
            PrimaryIcon(
                painter = painterResource(R.drawable.ic_flake),
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 24.dp)
                    .size(28.dp)
                    .clickable {
                        onDeleteClick()
                        offsetX = 0f
                        isOpen = false
                    }
            )
        }

        // Foreground card
        Column(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            val newOffset = (offsetX + dragAmount).coerceIn(-swipePx, 0f)
                            offsetX = newOffset
                        },
                        onDragEnd = {
                            isOpen = offsetX < -swipePx / 2
                            offsetX = if (isOpen) -swipePx else 0f
                        }
                    )
                }
                .background(backgroundColor, RoundedCornerShape(backgroundRadius.dp))
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                AvatarImage(
                    modifier = Modifier
                        .width(100.dp)
                        .height(64.dp),
                    clipPercent = 10,
                    imageUrl = imageUrl
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp)
                        .padding(start = 12.dp)
                ) {
                    Row {
                        PrimaryText(
                            modifier = Modifier.weight(1f),
                            text = title,
                            style = titleStyle
                        )
                        if (endIcon != null && !isDraggableModeOn) {
                            HorizontalSpacer(8.dp)
                            PrimaryIcon(
                                painter = painterResource(endIcon),
                                tint = endIconColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    if (subTitle.isNotEmpty()) {
                        PrimaryText(text = subTitle, style = subTitleStyle)
                    }

                    VerticalSpacer(4.dp)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        PrimaryText(
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 2.dp),
                            text = cardNumber,
                            style = cardNumberStyle
                        )

                        Badge(
                            badgeType = badgeType,
                            text = badgeText,
                            backgroundColor = badgeBackgroundColor,
                            textColor = badgeTextColor,
                            modifier = Modifier.align(Alignment.Bottom)
                        )
                    }
                }

                if (isDraggableModeOn) {
                    HorizontalSpacer(8.dp)
                    PrimaryIcon(
                        painter = painterResource(R.drawable.ic_drag_indicator),
                        tint = endIconColor,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterVertically)
                    )
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
                subTitle = "Aramayis Ter-Stepanyan",
                cardNumber = "**** 5678",
                badgeText = "Badge",
                endIcon = R.drawable.ic_info,
                badgeType = BadgeEnum.INFO,
                imageUrl =
                    "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType41_1_1.png",
                isEditingInitial = false,
                onDeleteClick = { println("Delete clicked") }
            )
        }
    }
}

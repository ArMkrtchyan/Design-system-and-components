package am.acba.utils.extensions

import am.acba.utils.ReorderableState
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerId
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.isOutOfBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAll
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.util.fastFirstOrNull
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

fun Context.composableView(
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    content: @Composable @UiComposable () -> Unit = {}
): ComposeView =
    ComposeView(
        context = this,
        attrs = attrs,
        defStyleAttr = defStyleAttr
    ).apply {
        setContent(content)
    }

fun TextStyle.textStyleToSpanStyle(): SpanStyle {
    return SpanStyle(
        fontSize = this.fontSize,
        fontFamily = this.fontFamily,
        fontWeight = this.fontWeight,
        fontStyle = this.fontStyle,
        fontSynthesis = this.fontSynthesis,
        fontFeatureSettings = this.fontFeatureSettings,
        letterSpacing = this.letterSpacing,
        baselineShift = this.baselineShift,
        textGeometricTransform = this.textGeometricTransform,
        localeList = this.localeList,
        drawStyle = this.drawStyle
    )
}

fun Modifier.detectReorder(state: ReorderableState<*>) =
    this.then(
        Modifier.pointerInput(Unit) {
            forEachGesture {
                awaitPointerEventScope {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    var drag: PointerInputChange?
                    var overSlop = Offset.Zero
                    do {
                        drag = awaitPointerSlopOrCancellation(down.id, down.type) { change, over ->
                            change.consume()
                            overSlop = over
                        }
                    } while (drag != null && !drag.isConsumed)
                    if (drag != null) {
                        state.interactions.trySend(StartDrag(down.id, overSlop))
                    }
                }
            }
        }
    )

fun Modifier.detectReorderAfterLongPress(state: ReorderableState<*>) =
    this.then(
        Modifier.pointerInput(Unit) {
            forEachGesture {
                val down = awaitPointerEventScope {
                    awaitFirstDown(requireUnconsumed = false)
                }
                awaitLongPressOrCancellation(down)?.also {
                    state.interactions.trySend(StartDrag(down.id))
                }
            }
        }
    )

@SuppressLint("ReturnFromAwaitPointerEventScope")
fun Modifier.reorderable(
    state: ReorderableState<*>
) = then(
    Modifier.pointerInput(Unit) {
        forEachGesture {
            val dragStart = state.interactions.receive()
            val down = awaitPointerEventScope {
                currentEvent.changes.fastFirstOrNull { it.id == dragStart.id }
            }
            if (down != null && state.onDragStart(down.position.x.toInt(), down.position.y.toInt())) {
                dragStart.offset?.apply {
                    state.onDrag(x.toInt(), y.toInt())
                }
                detectDrag(
                    down.id,
                    onDragEnd = {
                        state.onDragCanceled()
                    },
                    onDragCancel = {
                        state.onDragCanceled()
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        state.onDrag(dragAmount.x.toInt(), dragAmount.y.toInt())
                    })
            }
        }
    })

suspend fun PointerInputScope.detectDrag(
    down: PointerId,
    onDragEnd: () -> Unit = { },
    onDragCancel: () -> Unit = { },
    onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit,
) {
    awaitPointerEventScope {
        if (
            drag(down) {
                onDrag(it, it.positionChange())
                it.consume()
            }
        ) {
            currentEvent.changes.forEach {
                if (it.changedToUp()) it.consume()
            }
            onDragEnd()
        } else {
            onDragCancel()
        }
    }
}

suspend fun AwaitPointerEventScope.awaitPointerSlopOrCancellation(
    pointerId: PointerId,
    pointerType: PointerType,
    onPointerSlopReached: (change: PointerInputChange, overSlop: Offset) -> Unit
): PointerInputChange? {
    if (currentEvent.isPointerUp(pointerId)) {
        return null
    }
    var offset = Offset.Zero
    val touchSlop = viewConfiguration.pointerSlop(pointerType)

    var pointer = pointerId

    while (true) {
        val event = awaitPointerEvent()
        val dragEvent = event.changes.fastFirstOrNull { it.id == pointer } ?: return null
        if (dragEvent.isConsumed) {
            return null
        } else if (dragEvent.changedToUpIgnoreConsumed()) {
            val otherDown = event.changes.fastFirstOrNull { it.pressed }
            if (otherDown == null) {
                return null
            } else {
                pointer = otherDown.id
            }
        } else {
            offset += dragEvent.positionChange()
            val distance = offset.getDistance()
            var acceptedDrag = false
            if (distance >= touchSlop) {
                val touchSlopOffset = offset / distance * touchSlop
                onPointerSlopReached(dragEvent, offset - touchSlopOffset)
                if (dragEvent.isConsumed) {
                    acceptedDrag = true
                } else {
                    offset = Offset.Zero
                }
            }

            if (acceptedDrag) {
                return dragEvent
            } else {
                awaitPointerEvent(PointerEventPass.Final)
                if (dragEvent.isConsumed) {
                    return null
                }
            }
        }
    }
}

@SuppressLint("ReturnFromAwaitPointerEventScope")
internal suspend fun PointerInputScope.awaitLongPressOrCancellation(
    initialDown: PointerInputChange
): PointerInputChange? {
    var longPress: PointerInputChange? = null
    var currentDown = initialDown
    val longPressTimeout = viewConfiguration.longPressTimeoutMillis
    return try {
        withTimeout(longPressTimeout) {
            awaitPointerEventScope {
                var finished = false
                while (!finished) {
                    val event = awaitPointerEvent(PointerEventPass.Main)
                    if (event.changes.fastAll { it.changedToUpIgnoreConsumed() }) {
                        finished = true
                    }

                    if (
                        event.changes.fastAny {
                            it.isConsumed || it.isOutOfBounds(size, extendedTouchPadding)
                        }
                    ) {
                        finished = true
                    }

                    val consumeCheck = awaitPointerEvent(PointerEventPass.Final)
                    if (consumeCheck.changes.fastAny { it.isConsumed }) {
                        finished = true
                    }
                    if (!event.isPointerUp(currentDown.id)) {
                        longPress = event.changes.fastFirstOrNull { it.id == currentDown.id }
                    } else {
                        val newPressed = event.changes.fastFirstOrNull { it.pressed }
                        if (newPressed != null) {
                            currentDown = newPressed
                            longPress = currentDown
                        } else {
                            finished = true
                        }
                    }
                }
            }
        }
        null
    } catch (_: TimeoutCancellationException) {
        longPress ?: initialDown
    }
}

private fun PointerEvent.isPointerUp(pointerId: PointerId): Boolean = changes.fastFirstOrNull { it.id == pointerId }?.pressed != true

private val mouseSlop = 0.125.dp
private val defaultTouchSlop = 18.dp
private val mouseToTouchSlopRatio = mouseSlop / defaultTouchSlop

private fun ViewConfiguration.pointerSlop(pointerType: PointerType): Float {
    return when (pointerType) {
        PointerType.Mouse -> touchSlop * mouseToTouchSlopRatio
        else -> touchSlop
    }
}

data class StartDrag(val id: PointerId, val offset: Offset? = null)
data class DraggableItemPosition(val index: Int, val key: Any?)

fun Modifier.id(id: String, prefix: String = "android:id/"): Modifier {
    return this then Modifier
        .semantics { testTagsAsResourceId = true }
        .testTag("$prefix$id")
}
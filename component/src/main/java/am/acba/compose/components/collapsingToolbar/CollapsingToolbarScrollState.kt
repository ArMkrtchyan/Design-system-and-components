﻿package am.acba.compose.components.collapsingToolbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

/**
 * A state object that can be hoisted to control and observe the top app bar state. The state is
 * read and updated by a [CollapsingToolbarScrollBehavior] implementation.
 *
 * In most cases, this state will be created via [rememberToolbarScrollBehavior].
 *
 * @param initialHeightOffsetLimit the initial value for [CollapsingToolbarScrollState.heightOffsetLimit]
 * @param initialHeightOffset the initial value for [CollapsingToolbarScrollState.heightOffset]
 * @param initialContentOffset the initial value for [CollapsingToolbarScrollState.contentOffset]
 */
@Stable
class CollapsingToolbarScrollState(
    initialHeightOffsetLimit: Float,
    initialHeightOffset: Float,
    initialContentOffset: Float,
) {
    companion object {
        val Saver: Saver<CollapsingToolbarScrollState, *> = listSaver(
            save = { listOf(it.heightOffsetLimit, it.heightOffset, it.contentOffset) },
            restore = {
                CollapsingToolbarScrollState(
                    initialHeightOffsetLimit = it[0],
                    initialHeightOffset = it[1],
                    initialContentOffset = it[2]
                )
            }
        )
    }

    /**
     * The top app bar's height offset limit in pixels, which represents the limit that a top app
     * bar is allowed to collapse to.
     *
     * Use this limit to coerce the [heightOffset] value when it's updated.
     */
    var heightOffsetLimit by mutableStateOf(initialHeightOffsetLimit)

    /**
     * The top app bar's current height offset in pixels. This height offset is applied to the fixed
     * height of the app bar to control the displayed height when content is being scrolled.
     *
     * Updates to the [heightOffset] value are coerced between zero and [heightOffsetLimit].
     */
    var heightOffset: Float
        get() = _heightOffset.value
        set(newOffset) {
            _heightOffset.value = newOffset.coerceIn(
                minimumValue = heightOffsetLimit,
                maximumValue = 0f
            )
        }

    /**
     * The total offset of the content scrolled under the top app bar.
     *
     * This value is updated by a [CollapsingToolbarScrollBehavior] whenever a nested scroll connection
     * consumes scroll events. A common implementation would update the value to be the sum of all
     * [NestedScrollConnection.onPostScroll] `consumed.y` values.
     */
    var contentOffset by mutableStateOf(initialContentOffset)

    /**
     * A value that represents the collapsed height percentage of the app bar.
     *
     * A `0.0` represents a fully expanded bar, and `1.0` represents a fully collapsed bar (computed
     * as [heightOffset] / [heightOffsetLimit]).
     */
    val collapsedFraction: Float
        get() = if (heightOffsetLimit != 0f) {
            heightOffset / heightOffsetLimit
        } else {
            0f
        }

    private var _heightOffset = mutableStateOf(initialHeightOffset)

}

@Composable
internal fun rememberToolbarScrollState(
    initialHeightOffsetLimit: Float = -Float.MAX_VALUE,
    initialHeightOffset: Float = 0f,
    initialContentOffset: Float = 0f
) = rememberSaveable(saver = CollapsingToolbarScrollState.Saver) {
    CollapsingToolbarScrollState(
        initialHeightOffsetLimit,
        initialHeightOffset,
        initialContentOffset
    )
}
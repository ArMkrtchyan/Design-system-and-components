@file:OptIn(ExperimentalComposeUiApi::class)

package am.acba.compose

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.UiComposable
import androidx.compose.ui.platform.ComposeView

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
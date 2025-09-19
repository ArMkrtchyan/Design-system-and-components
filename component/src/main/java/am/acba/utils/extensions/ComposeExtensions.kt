package am.acba.utils.extensions

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle

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

fun Modifier.id(id: String, prefix: String = "android:id/"): Modifier {
    return this then Modifier
        .semantics { testTagsAsResourceId = true }
        .testTag("$prefix$id")
}
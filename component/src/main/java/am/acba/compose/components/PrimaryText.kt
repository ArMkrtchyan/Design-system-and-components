package am.acba.compose.components

import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun PrimaryText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = DigitalTheme.colorScheme.contentPrimary,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = DigitalTheme.typography.body1Medium,
    autoSize: TextAutoSize? = null
) {
    val textColor = color.takeOrElse { style.color.takeOrElse { LocalContentColor.current } }

    BasicText(
        text = text,
        modifier = modifier,
        style =
            style.merge(
                color = textColor,
                fontSize = fontSize,
                fontWeight = fontWeight,
                textAlign = textAlign ?: TextAlign.Unspecified,
                lineHeight = lineHeight,
                fontFamily = fontFamily,
                textDecoration = textDecoration,
                fontStyle = fontStyle,
                letterSpacing = letterSpacing
            ),
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        autoSize = autoSize
    )
}

@Composable
fun PrimaryText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = DigitalTheme.colorScheme.contentPrimary,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = DigitalTheme.typography.body1Medium,
    autoSize: TextAutoSize? = null
) {
    val textColor = color.takeOrElse { style.color.takeOrElse { LocalContentColor.current } }

    BasicText(
        text = text,
        modifier = modifier,
        style =
            style.merge(
                color = textColor,
                fontSize = fontSize,
                fontWeight = fontWeight,
                textAlign = textAlign ?: TextAlign.Unspecified,
                lineHeight = lineHeight,
                fontFamily = fontFamily,
                textDecoration = textDecoration,
                fontStyle = fontStyle,
                letterSpacing = letterSpacing
            ),
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        autoSize = autoSize
    )
}
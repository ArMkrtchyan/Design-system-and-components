package am.acba.compose.components.inputs

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun SupportRow(iconRes: Int? = null, text: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(15.dp, 0.dp, 0.dp, 0.dp)
    ) {
        iconRes?.let {
            SupportIcon(it)
            HorizontalSpacer(4)
        }
        SupportText(text = text, color = color)
    }
}

@Composable
fun Label(text: String?, isError: Boolean = false, isEnabled: Boolean = true) {
    val textColor = when {
        isError -> DigitalTheme.colorScheme.contentDangerTonal1
        !isEnabled -> DigitalTheme.colorScheme.contentPrimaryTonal1Disable
        else -> DigitalTheme.colorScheme.contentPrimaryTonal1
    }
    Text(
        text = text ?: "",
        color = textColor
    )
}

@Composable
fun leadingOrTrailingIcon(
    iconRes: Int? = null,
    tint: Color?,
    secondaryIconRes: Int? = null,
    secondaryTint: Color? = DigitalTheme.colorScheme.contentPrimaryTonal1,
    isEnabled: Boolean = true,
    secondaryIconSize: Dp = 24.dp,
    iconSize: Dp = 24.dp,
    isLeading: Boolean = true,
    onSecondaryIconClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
): @Composable (() -> Unit)? {
    val iconColorFilter = when {
        tint == null -> null
        !isEnabled -> ColorFilter.tint(DigitalTheme.colorScheme.contentPrimaryTonal1Disable)
        else -> ColorFilter.tint(tint)
    }
    val secondaryIconColorFilter = when {
        secondaryTint == null -> null
        !isEnabled -> ColorFilter.tint(DigitalTheme.colorScheme.contentPrimaryTonal1Disable)
        else -> ColorFilter.tint(secondaryTint)
    }
    return when {
        iconRes != null && secondaryIconRes != null -> {
            {
                Row {
                    ImageIcon(secondaryIconRes, secondaryIconColorFilter, secondaryIconSize, spaceStart = 12, spaceEnd = 12, onSecondaryIconClick)
                    ImageIcon(iconRes, iconColorFilter, iconSize, spaceStart = 0, spaceEnd = 16, onClick)
                }
            }
        }

        iconRes != null -> {
            {
                if (isLeading) {
                    ImageIcon(iconRes, iconColorFilter, iconSize, spaceStart = 16, spaceEnd = 8, onClick)
                } else {
                    ImageIcon(iconRes, iconColorFilter, iconSize, spaceStart = 12, spaceEnd = 16, onClick)
                }
            }
        }

        secondaryIconRes != null -> {
            {
                ImageIcon(secondaryIconRes, secondaryIconColorFilter, secondaryIconSize, spaceStart = 12, spaceEnd = 16, onSecondaryIconClick)
            }
        }

        else -> null
    }
}

@Composable
private fun ImageIcon(iconRes: Int, colorFilter: ColorFilter?, iconSize: Dp = 24.dp, spaceStart: Int, spaceEnd: Int, onClick: (() -> Unit)? = null) {
    Row {
        HorizontalSpacer(spaceStart)
        Image(
            painter = painterResource(id = iconRes),
            modifier = Modifier
                .width(iconSize)
                .height(iconSize)
                .clickable { onClick?.invoke() },
            contentDescription = "",
            colorFilter = colorFilter
        )
        HorizontalSpacer(spaceEnd)
    }
}

@Composable
fun SupportIcon(iconRes: Int) {
    Icon(
        painter = painterResource(id = iconRes),
        modifier = Modifier
            .width(18.dp)
            .height(18.dp),
        contentDescription = "",
        tint = DigitalTheme.colorScheme.contentDangerTonal1
    )
}

@Composable
fun SupportText(text: String, color: Color) {
    PrimaryText(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        style = DigitalTheme.typography.smallRegular,
        color = color,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun SupportAndErrorTexts(isError: Boolean, enabled: Boolean, errorText: String?, helpText: String?) {
    Column {
        VerticalSpacer(4)
        if (isError) {
            if (!errorText.isNullOrEmpty()) {
                SupportRow(
                    iconRes = R.drawable.ic_info,
                    text = errorText,
                    color = DigitalTheme.colorScheme.contentDangerTonal1
                )
            }
        } else if (!helpText.isNullOrEmpty()) {
            val textColor = when {
                !enabled -> DigitalTheme.colorScheme.contentPrimaryTonal1Disable
                else -> DigitalTheme.colorScheme.contentPrimaryTonal1
            }
            SupportRow(text = helpText, color = textColor)
        }
    }
}

@Composable
fun createStateColors() = TextFieldDefaults.colors(
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    errorIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    errorPlaceholderColor = DigitalTheme.colorScheme.contentPrimary,
    errorTextColor = DigitalTheme.colorScheme.contentPrimary,
    errorLabelColor = DigitalTheme.colorScheme.contentDangerTonal1,
    errorContainerColor = DigitalTheme.colorScheme.backgroundTonal2,
    errorTrailingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    errorLeadingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    focusedLabelColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    unfocusedLabelColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    focusedTextColor = DigitalTheme.colorScheme.contentPrimary,
    unfocusedTextColor = DigitalTheme.colorScheme.contentPrimary,
    unfocusedContainerColor = DigitalTheme.colorScheme.backgroundTonal2,
    focusedContainerColor = DigitalTheme.colorScheme.backgroundTonal2,
    focusedTrailingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    focusedLeadingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    unfocusedTrailingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    unfocusedLeadingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
    cursorColor = DigitalTheme.colorScheme.contentBrand,
    errorCursorColor = DigitalTheme.colorScheme.contentDangerTonal1,
    disabledContainerColor = DigitalTheme.colorScheme.backgroundTonal2Disable,
    disabledPrefixColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledPlaceholderColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledSuffixColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledLabelColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledTextColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledLeadingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledTrailingIconColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
    disabledSupportingTextColor = DigitalTheme.colorScheme.contentPrimaryTonal1Disable,
)

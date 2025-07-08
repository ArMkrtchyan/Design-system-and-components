package am.acba.compose.components.listItem

import am.acba.compose.theme.DigitalTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

enum class ListItemType(
    val titleMaxLines: Int,
    val descriptionMaxLines: Int
) {
    DEFAULT(2, 4),
    OVERLINE(1, 2);

    @Composable
    fun getTitleStyle(): TextStyle {
        return when (this) {
            DEFAULT -> DigitalTheme.typography.body1Bold
            OVERLINE -> DigitalTheme.typography.smallRegular
        }
    }

    @Composable
    fun getTitleTextColor(): Color {
        return when (this) {
            DEFAULT -> DigitalTheme.colorScheme.contentPrimary
            OVERLINE -> DigitalTheme.colorScheme.contentPrimaryTonal1
        }
    }

    @Composable
    fun getDescriptionStyle(): TextStyle {
        return when (this) {
            DEFAULT -> DigitalTheme.typography.body2Regular
            OVERLINE -> DigitalTheme.typography.body1Regular
        }
    }

    @Composable
    fun getDescriptionTextColor(): Color {
        return when (this) {
            DEFAULT -> DigitalTheme.colorScheme.contentPrimaryTonal1
            OVERLINE -> DigitalTheme.colorScheme.contentPrimary
        }
    }
}
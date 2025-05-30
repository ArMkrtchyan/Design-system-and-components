package am.acba.compose.components.timeLine

import am.acba.component.R
import am.acba.compose.theme.DigitalTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class TimeLineStatusComposeEnum(
    val type: Int,
    val icon: Int,
    val verticalPadding: Int = 8
) {
    NONE(
        type = 0,
        icon = R.drawable.ic_ellipse,
        verticalPadding = 0
    ),
    DEFAULT(
        type = 1,
        icon = R.drawable.ic_ellipse,
    ),
    PENDING(
        type = 2,
        icon = R.drawable.ic_info_1,
    ),
    PENDING_2(
        type = 3,
        icon = R.drawable.ic_info_1,
    ),
    WARNING(
        type = 4,
        icon = R.drawable.ic_attention_1,
    ),
    DANGER(
        type = 5,
        icon = R.drawable.ic_attention_1,
    ),
    SUCCESS(
        type = 6,
        icon = R.drawable.ic_success_small,
    );


    @Composable
    fun getIconColor() = when (this) {
        NONE -> Color.Transparent
        DEFAULT -> DigitalTheme.colorScheme.backgroundTonal3
        PENDING -> DigitalTheme.colorScheme.borderInfoTonal1
        PENDING_2 -> DigitalTheme.colorScheme.contentSecondary
        WARNING -> DigitalTheme.colorScheme.contentInverse
        SUCCESS -> DigitalTheme.colorScheme.contentSecondary
        DANGER -> DigitalTheme.colorScheme.contentSecondary
    }

    @Composable
    fun getIconBackgroundColor() = when (this) {
        NONE -> DigitalTheme.colorScheme.backgroundTonal1
        DEFAULT -> Color.Transparent
        PENDING -> Color.Transparent
        PENDING_2 -> DigitalTheme.colorScheme.borderInfoTonal1
        WARNING -> DigitalTheme.colorScheme.backgroundWarning
        SUCCESS -> DigitalTheme.colorScheme.backgroundSuccess
        DANGER -> DigitalTheme.colorScheme.backgroundDanger
    }

    @Composable
    fun getIconBorderColor() = when (this) {
        NONE -> DigitalTheme.colorScheme.backgroundTonal3
        DEFAULT -> Color.Transparent
        PENDING -> DigitalTheme.colorScheme.borderInfoTonal1
        PENDING_2 -> Color.Transparent
        WARNING -> Color.Transparent
        SUCCESS -> Color.Transparent
        DANGER -> Color.Transparent
    }

    @Composable
    fun getContentBackgroundColor() = when (this) {
        NONE -> Color.Transparent
        DEFAULT -> DigitalTheme.colorScheme.backgroundTonal2
        PENDING -> DigitalTheme.colorScheme.backgroundTonal2
        PENDING_2 -> DigitalTheme.colorScheme.backgroundInfoTonal1
        WARNING -> DigitalTheme.colorScheme.backgroundWarningTonal1
        SUCCESS -> DigitalTheme.colorScheme.backgroundSuccessTonal1
        DANGER -> DigitalTheme.colorScheme.backgroundDangerTonal1
    }

    @Composable
    fun getLinkTextColor() = when (this) {
        NONE -> DigitalTheme.colorScheme.contentPrimary
        DEFAULT -> DigitalTheme.colorScheme.contentInfoTonal1
        PENDING -> DigitalTheme.colorScheme.contentInfoTonal1
        PENDING_2 -> DigitalTheme.colorScheme.contentInfoTonal1
        WARNING -> DigitalTheme.colorScheme.contentWarningTonal1
        SUCCESS -> DigitalTheme.colorScheme.contentSuccessTonal1
        DANGER -> DigitalTheme.colorScheme.contentDangerTonal1
    }
}
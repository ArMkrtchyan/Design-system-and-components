package am.acba.compose.components.timeLine

import am.acba.compose.theme.DigitalTheme
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface ITimeLineCompose {

    fun getTitle(): String
    fun getEndText(): String? = null
    fun getDescription(): String? = null
    fun getLinkText(): String? = null
    fun getStatus(): TimeLineStatusComposeEnum = TimeLineStatusComposeEnum.NONE

    @DrawableRes
    fun getStartIcon(): Int = getStatus().icon

    @Composable
    fun getIconColor(): Color = getStatus().getIconColor()

    @Composable
    fun getIconBackgroundColor(): Color = getStatus().getIconBackgroundColor()

    @Composable
    fun getIconBorderColor(): Color = getStatus().getIconBorderColor()

    @Composable
    fun getContentBackgroundColor(): Color = getStatus().getContentBackgroundColor()

    @Composable
    fun getTitleColor(): Color = DigitalTheme.colorScheme.contentPrimary

    @Composable
    fun getDescriptionColor(): Color = DigitalTheme.colorScheme.contentPrimaryTonal1

    @Composable
    fun getEndTextColor(): Color = DigitalTheme.colorScheme.contentPrimary

    @Composable
    fun getLinkTextColor(): Color = getStatus().getLinkTextColor()
}
package am.acba.compose.components.timeLine

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class TimeLineComposeModel(
    val timeLineTitle: String,
    val timeLineEndText: String? = null,
    val timeLineDescription: String? = null,
    val timeLineLinkText: String? = null,
    val timeLineStatus: TimeLineStatusComposeEnum = TimeLineStatusComposeEnum.NONE,
    val timeLineStartIcon: Int? = null,
    val timeLineIconColor: Color? = null,
    val timeLineIconBackgroundColor: Color? = null,
    val timeLineIconBorderColor: Color? = null,
    val timeLineContentBackgroundColor: Color? = null,
    val timeLineTitleColor: Color? = null,
    val timeLineDescriptionColor: Color? = null,
    val timeLineEndTextColor: Color? = null,
    val timeLineLinkTextColor: Color? = null,
    val timeLineStartIconTint: Color? = null
) : ITimeLineCompose {
    override fun getTitle(): String {
        return timeLineTitle
    }

    override fun getEndText(): String? {
        return timeLineEndText
    }

    override fun getDescription(): String? {
        return timeLineDescription
    }

    override fun getLinkText(): String? {
        return timeLineLinkText
    }

    override fun getStatus(): TimeLineStatusComposeEnum {
        return timeLineStatus
    }

    override fun getStartIcon(): Int {
        return timeLineStartIcon ?: super.getStartIcon()
    }

    @Composable
    override fun getIconColor(): Color {
        return timeLineIconColor ?: super.getIconColor()
    }

    @Composable
    override fun getIconBackgroundColor(): Color {
        return timeLineIconBackgroundColor ?: super.getIconBackgroundColor()
    }

    @Composable
    override fun getIconBorderColor(): Color {
        return timeLineIconBorderColor ?: super.getIconBorderColor()
    }

    @Composable
    override fun getContentBackgroundColor(): Color {
        return timeLineContentBackgroundColor ?: super.getContentBackgroundColor()
    }

    @Composable
    override fun getTitleColor(): Color {
        return timeLineTitleColor ?: super.getTitleColor()
    }

    @Composable
    override fun getDescriptionColor(): Color {
        return timeLineDescriptionColor ?: super.getDescriptionColor()
    }

    @Composable
    override fun getEndTextColor(): Color {
        return timeLineEndTextColor ?: super.getEndTextColor()
    }

    @Composable
    override fun getLinkTextColor(): Color {
        return timeLineLinkTextColor ?: super.getLinkTextColor()
    }
}

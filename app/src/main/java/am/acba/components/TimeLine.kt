package am.acba.components

import am.acba.component.primaryTimeLine.ITimeLineInfo
import am.acba.component.primaryTimeLine.TimeLineStatusEnum
import androidx.annotation.AttrRes

data class TimeLine(
    val timeLineTitle: String,
    @AttrRes val timeLineTitleColorAttr: Int? = null,
    val timeLineStatusEnum: TimeLineStatusEnum = TimeLineStatusEnum.NONE,
    val timeLineDescription: String? = null,
    @AttrRes val timeLineDescriptionColorAttr: Int? = null,
    val timeLineEndText: String? = null,
    @AttrRes val timeLineEndTextColorAttr: Int? = null,
    val timeLineLinkText: String? = null,
    @AttrRes val timeLineLinkTextColorAttr: Int? = null,
    val timeLineStartIcon: Int? = null,
    @AttrRes val timeLineStartIconTintAttr: Int? = null,
    val timeLineStartIconBackground: Int? = null,
    @AttrRes val timeLineStartIconBackgroundTintAttr: Int? = null,
    @AttrRes val timeLineContentBackgroundTintAttr: Int? = null,
) : ITimeLineInfo {
    override fun getTimeLineStatus(): TimeLineStatusEnum {
        return timeLineStatusEnum
    }

    override fun getTitle(): String {
        return timeLineTitle
    }

    override fun getTitleColorAttr(): Int {
        return timeLineTitleColorAttr ?: super.getTitleColorAttr()
    }

    override fun getDescriptionColorAttr(): Int {
        return timeLineDescriptionColorAttr ?: super.getDescriptionColorAttr()
    }

    override fun getDescription(): String? {
        return timeLineDescription
    }

    override fun getEndTextColorAttr(): Int {
        return timeLineEndTextColorAttr ?: super.getEndTextColorAttr()
    }

    override fun getEndText(): String? {
        return timeLineEndText
    }

    override fun getLinkTextColorAttr(): Int? {
        return timeLineLinkTextColorAttr
    }

    override fun getLinkText(): String? {
        return timeLineLinkText
    }

    override fun getStartIcon(): Int? {
        return timeLineStartIcon
    }

    override fun getStartIconTint(): Int? {
        return timeLineStartIconTintAttr
    }

    override fun getStartIconBackground(): Int? {
        return timeLineStartIconBackground
    }

    override fun getStartIconBackgroundTint(): Int? {
        return timeLineStartIconBackgroundTintAttr
    }

    override fun getContentBackgroundAttr(): Int? {
        return timeLineContentBackgroundTintAttr
    }
}

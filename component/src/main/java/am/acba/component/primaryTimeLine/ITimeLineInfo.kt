package am.acba.component.primaryTimeLine

import am.acba.component.R
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes

interface ITimeLineInfo {
    fun getTimeLineStatus(): TimeLineStatusEnum = TimeLineStatusEnum.NONE

    @AttrRes
    fun getTitleColorAttr(): Int = R.attr.contentPrimary
    fun getTitle(): String


    @AttrRes
    fun getDescriptionColorAttr(): Int = R.attr.contentPrimaryTonal1
    fun getDescription(): String? = null


    @AttrRes
    fun getEndTextColorAttr(): Int = R.attr.contentPrimary
    fun getEndText(): String? = null


    @AttrRes
    fun getLinkTextColorAttr(): Int? = null
    fun getLinkText(): String? = null

    @DrawableRes
    fun getStartIcon(): Int? = null

    @AttrRes
    fun getStartIconTint(): Int? = null

    @DrawableRes
    fun getStartIconBackground(): Int? = null

    @AttrRes
    fun getStartIconBackgroundTint(): Int? = null

    @AttrRes
    fun getContentBackgroundAttr(): Int? = null
}
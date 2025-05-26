package am.acba.component.primaryTimeLine

import am.acba.component.R
import am.acba.component.databinding.ItemTimeLineBinding
import am.acba.component.databinding.PrimaryTimeLineBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.view.isVisible

class PrimaryTimeLine : FrameLayout {
    private val binding by lazy {
        PrimaryTimeLineBinding.inflate(context.inflater(), this, false)
    }
    private var timeLineType: TimeLineType = TimeLineType.TIMELINE

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PrimaryTimeLine).apply {
            setTitle(getString(R.styleable.PrimaryTimeLine_timeLineTitle))
            setEndIconVisibility(getBoolean(R.styleable.PrimaryTimeLine_timeLineEndIconVisibility, false))
            val timeLineType = TimeLineType.findByType(getInt(R.styleable.PrimaryTimeLine_timeLineType, 0))
            setTimeLineType(timeLineType)
            addView(binding.root)
            recycle()
        }
    }

    fun setTitle(@StringRes textResId: Int) {
        val text = try {
            context.getString(textResId)
        } catch (e: Exception) {
            null
        }
        setTitle(text)
    }

    fun setTitle(title: String?) {
        binding.title.text = title
        binding.title.isVisible = !title.isNullOrEmpty()
        checkHeaderLayoutVisibility()
    }

    fun setEndIconVisibility(isVisible: Boolean) {
        binding.endIcon.isVisible = isVisible
        checkHeaderLayoutVisibility()
    }

    fun setTimeLineType(timeLineType: TimeLineType) {
        this.timeLineType = timeLineType
    }

    fun <T : ITimeLineInfo> setTimelineItems(timeLineItems: List<T>, onItemClickListener: ((T) -> Unit)? = null) {
        binding.timeLines.removeAllViews()
        val bindingList = arrayListOf<Pair<ItemTimeLineBinding, TimeLineStatusEnum>>()
        timeLineItems.forEachIndexed { index, timeLineInfo ->
            val itemBinding = ItemTimeLineBinding.inflate(context.inflater(), this, false)
            val status = timeLineInfo.getTimeLineStatus()
            val itemCount = timeLineItems.size
            itemBinding.root.setTag(R.id.title, status.type)
            setIconAndContentColors(
                itemBinding,
                timeLineInfo.getStartIcon() ?: status.icon,
                timeLineInfo.getStartIconBackground() ?: status.iconBackground,
                timeLineInfo.getStartIconBackgroundTint() ?: status.iconBackgroundTint,
                timeLineInfo.getStartIconTint() ?: status.iconTint,
                timeLineInfo.getContentBackgroundAttr() ?: status.contentBackgroundTint,
                timeLineInfo.getLinkTextColorAttr() ?: status.linkTextColor,
            )
            if (status == TimeLineStatusEnum.NONE) {
                setTextsStylesSmall(itemBinding)
            } else {
                setTextsStylesLarge(itemBinding, timeLineInfo)
            }
            itemBinding.topLine.isVisible = index != 0 && itemCount > 1
            if (timeLineType == TimeLineType.PROGRESS) {
                itemBinding.bottomLine.isVisible = index != itemCount - 1 && itemCount > 1
                bindingList.add(itemBinding to status)
            } else {
                itemBinding.bottomLine.isVisible = itemCount > 1
            }
            itemBinding.title.text = timeLineInfo.getTitle()
            itemBinding.endText.text = timeLineInfo.getEndText()
            itemBinding.description.text = timeLineInfo.getDescription()
            itemBinding.linkText.text = timeLineInfo.getLinkText()

            itemBinding.endText.isVisible = !timeLineInfo.getEndText().isNullOrEmpty()
            itemBinding.description.isVisible = !timeLineInfo.getDescription().isNullOrEmpty()
            itemBinding.linkText.isVisible = !timeLineInfo.getLinkText().isNullOrEmpty()
            onItemClickListener?.let {
                itemBinding.cardContainer.setOnClickListener { onItemClickListener.invoke(timeLineInfo) }
            }
            binding.timeLines.addView(itemBinding.root)
        }
        if (timeLineType == TimeLineType.PROGRESS) {
            bindingList.forEachIndexed { index, pair ->
                val status = pair.second
                val itemBinding = pair.first
                if (status == TimeLineStatusEnum.SUCCESS) {
                    itemBinding.bottomLine.setBackgroundColor(context.getColorFromAttr(status.iconBackgroundTint ?: R.attr.borderPrimaryTonal3))
                    if (index + 1 <= bindingList.size) {
                        val nextItemBinding = bindingList[index + 1].first
                        nextItemBinding.topLine.setBackgroundColor(context.getColorFromAttr(status.iconBackgroundTint ?: R.attr.borderPrimaryTonal3))
                    }
                }
            }
        }
    }

    private fun setIconAndContentColors(
        binding: ItemTimeLineBinding,
        icon: Int,
        iconBackground: Int,
        iconBackgroundTint: Int?,
        iconTint: Int?,
        contentBackgroundTint: Int?,
        linkTextColorAttr: Int,
    ) {
        val context = binding.root.context
        binding.startIcon.setImageResource(icon)
        binding.startIcon.setBackgroundResource(iconBackground)
        val colorStateList = iconBackgroundTint?.let(context::getColorStateListFromAttr)
        binding.startIcon.backgroundTintList = colorStateList
        val iconColorStateList = iconTint?.let(context::getColorStateListFromAttr)
        binding.startIcon.imageTintList = iconColorStateList
        val cardBackgroundColorStateList = contentBackgroundTint?.let(context::getColorStateListFromAttr)
        binding.contentCardContainer.setCardBackgroundColor(cardBackgroundColorStateList)
        binding.linkText.setTextColor(context.getColorStateListFromAttr(linkTextColorAttr))
    }

    private fun setTextsStylesLarge(binding: ItemTimeLineBinding, timeLineInfo: ITimeLineInfo) {
        val context = binding.root.context
        binding.title.setTextAppearance(R.style.Body2_Regular)
        binding.endText.setTextAppearance(R.style.Subtitle2_Bold)
        binding.title.setTextColor(context.getColorStateListFromAttr(timeLineInfo.getTitleColorAttr()))
        binding.endText.setTextColor(context.getColorStateListFromAttr(timeLineInfo.getEndTextColorAttr()))
        binding.cardContainer.setPadding(8.dpToPx(), 8.dpToPx(), 8.dpToPx(), 8.dpToPx())
    }

    private fun setTextsStylesSmall(binding: ItemTimeLineBinding) {
        val context = binding.root.context
        binding.title.setTextAppearance(R.style.Small_Regular)
        binding.endText.setTextAppearance(R.style.Small_Regular)
        binding.title.setTextColor(context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1))
        binding.endText.setTextColor(context.getColorStateListFromAttr(R.attr.contentPrimaryTonal1))
        binding.cardContainer.setPadding(8.dpToPx(), 0.dpToPx(), 8.dpToPx(), 0.dpToPx())
    }

    private fun checkHeaderLayoutVisibility() {
        binding.headerLayout.isVisible = binding.title.isVisible || binding.endIcon.isVisible
    }
}
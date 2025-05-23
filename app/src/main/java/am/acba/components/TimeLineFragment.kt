package am.acba.components

import am.acba.component.primaryTimeLine.TimeLineStatusEnum
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentTimeLineBinding
import android.widget.Toast

class TimeLineFragment : BaseViewBindingFragment<FragmentTimeLineBinding>() {
    override val inflate: Inflater<FragmentTimeLineBinding>
        get() = FragmentTimeLineBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentTimeLineBinding.initView() {
        timeLineExample1.setTimelineItems(example1) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample2.setTimelineItems(example2) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample3.setTimelineItems(example3) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample4.setTimelineItems(example4) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample5.setTimelineItems(example5) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample6.setTimelineItems(example6) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample7.setTimelineItems(example7) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample8.setTimelineItems(example8) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample9.setTimelineItems(example9) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample10.setTimelineItems(example10) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample11.setTimelineItems(example11) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample12.setTimelineItems(example12) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample13.setTimelineItems(example13) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample14.setTimelineItems(example14) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample15.setTimelineItems(example15) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample16.setTimelineItems(example16) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample17.setTimelineItems(example17) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample18.setTimelineItems(example18) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample19.setTimelineItems(example19) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample20.setTimelineItems(example20) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample21.setTimelineItems(example21) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample22.setTimelineItems(example22) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample23.setTimelineItems(example23) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }
        timeLineExample24.setTimelineItems(example24) { Toast.makeText(requireContext(), it.timeLineTitle, Toast.LENGTH_SHORT).show() }

        timeLineExample1.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample2.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample3.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample4.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample5.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample6.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample7.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample8.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample9.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample10.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample11.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample12.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample13.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample14.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample15.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample16.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample17.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample18.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample19.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample20.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample21.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample22.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample23.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
        timeLineExample24.setOnClickListener { Toast.makeText(requireContext(), "On Time line click", Toast.LENGTH_SHORT).show() }
    }

    private val example1 = arrayListOf(
        TimeLine(timeLineTitle = "14/10/2024", timeLineEndText = "400,000.00 AMD"),
        TimeLine(timeLineTitle = "20/11/2024", timeLineEndText = "550,000.00 AMD"),
        TimeLine(timeLineTitle = "22/12/2024", timeLineEndText = "100,000.00 AMD")
    )
    private val example2 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineStatusEnum = TimeLineStatusEnum.DEFAULT
        ),
        TimeLine(timeLineTitle = "20/11/2024", timeLineEndText = "550,000.00 AMD"),
        TimeLine(timeLineTitle = "22/12/2024", timeLineEndText = "100,000.00 AMD")
    )
    private val example3 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.PENDING
        ),
        TimeLine(timeLineTitle = "20/11/2024", timeLineEndText = "550,000.00 AMD"),
        TimeLine(timeLineTitle = "22/12/2024", timeLineEndText = "100,000.00 AMD")
    )
    private val example4 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.PENDING_2
        ),
        TimeLine(timeLineTitle = "20/11/2024", timeLineEndText = "550,000.00 AMD"),
        TimeLine(timeLineTitle = "22/12/2024", timeLineEndText = "100,000.00 AMD")
    )
    private val example5 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineStatusEnum = TimeLineStatusEnum.SUCCESS
        ),
        TimeLine(timeLineTitle = "20/11/2024", timeLineEndText = "550,000.00 AMD"),
        TimeLine(timeLineTitle = "22/12/2024", timeLineEndText = "100,000.00 AMD")
    )
    private val example6 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.DANGER
        ),
        TimeLine(timeLineTitle = "20/11/2024", timeLineEndText = "550,000.00 AMD"),
        TimeLine(timeLineTitle = "22/12/2024", timeLineEndText = "100,000.00 AMD")
    )
    private val example7 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.WARNING
        ),
        TimeLine(timeLineTitle = "20/11/2024", timeLineEndText = "550,000.00 AMD"),
        TimeLine(timeLineTitle = "22/12/2024", timeLineEndText = "100,000.00 AMD")
    )
    private val example8 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.DANGER
        ),
        TimeLine(
            timeLineTitle = "20/11/2024",
            timeLineEndText = "550,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.PENDING_2
        ),
        TimeLine(timeLineTitle = "22/12/2024", timeLineEndText = "100,000.00 AMD")
    )
    private val example9 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.DANGER
        ),
        TimeLine(
            timeLineTitle = "20/11/2024",
            timeLineEndText = "550,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.DANGER
        ),
        TimeLine(timeLineTitle = "22/12/2024", timeLineEndText = "100,000.00 AMD")
    )
    private val example10 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.DANGER
        ),
        TimeLine(
            timeLineTitle = "20/11/2024",
            timeLineEndText = "550,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.WARNING
        ),
        TimeLine(timeLineTitle = "22/12/2024", timeLineEndText = "100,000.00 AMD")
    )
    private val example13 = arrayListOf(
        TimeLine(timeLineTitle = "14/10/2024", timeLineEndText = "400,000.00 AMD"),
        TimeLine(timeLineTitle = "20/11/2024", timeLineEndText = "550,000.00 AMD")
    )
    private val example14 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineStatusEnum = TimeLineStatusEnum.DEFAULT
        ),
        TimeLine(timeLineTitle = "20/11/2024", timeLineEndText = "550,000.00 AMD")
    )
    private val example15 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.PENDING
        ),
        TimeLine(timeLineTitle = "20/11/2024", timeLineEndText = "550,000.00 AMD")
    )

    private val example16 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineStatusEnum = TimeLineStatusEnum.DEFAULT
        )
    )
    private val example17 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineDescription = "Մարումը` 5 օրից",
            timeLineStatusEnum = TimeLineStatusEnum.PENDING
        )
    )
    private val example18 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineStatusEnum = TimeLineStatusEnum.SUCCESS
        )
    )
    private val example19 = arrayListOf(
        TimeLine(
            timeLineTitle = "Դիմումի ուղարկում",
            timeLineDescription = "Ուղղարկվել է՝ 20.02.2024",
            timeLineStatusEnum = TimeLineStatusEnum.SUCCESS,
            timeLineContentBackgroundTintAttr = am.acba.component.R.attr.transparent
        ),
        TimeLine(
            timeLineTitle = "Դիմումի դիտարկում",
            timeLineDescription = "Դիտարկվել է՝ 20.02.2024",
            timeLineStatusEnum = TimeLineStatusEnum.SUCCESS,
            timeLineContentBackgroundTintAttr = am.acba.component.R.attr.transparent
        ),
        TimeLine(
            timeLineTitle = "ՀԴՄ սարքի  գրանցում բանկում",
            timeLineDescription = "Մինչև՝ 20.02.2024",
            timeLineStatusEnum = TimeLineStatusEnum.PENDING
        ),
        TimeLine(
            timeLineTitle = "Պայմանագրի կնքում",
            timeLineDescription = "Մինչև ՝ 20.04.2023",
            timeLineStatusEnum = TimeLineStatusEnum.DEFAULT,
            timeLineContentBackgroundTintAttr = am.acba.component.R.attr.transparent
        ),
        TimeLine(
            timeLineTitle = "Սարքի ակտիվացում",
            timeLineDescription = "Պայմանագրի կնքումից հետո 2-3 աշխ. օրվա ընթացքում",
            timeLineStatusEnum = TimeLineStatusEnum.DEFAULT,
            timeLineContentBackgroundTintAttr = am.acba.component.R.attr.transparent
        ),
    )

    private val example11 = arrayListOf(
        TimeLine(
            timeLineTitle = "Դիմումի դիտարկում",
            timeLineDescription = "Մինչև ՝ 20.04.2023",
            timeLineStatusEnum = TimeLineStatusEnum.PENDING
        )
    )
    private val example12 = arrayListOf(
        TimeLine(
            timeLineTitle = "Պայմանագիրը պատրաստ է:",
            timeLineDescription = "Կնքել  մինչև ՝ 20.04.2023",
            timeLineLinkText = "Դիտարկել և կնքել",
            timeLineStatusEnum = TimeLineStatusEnum.PENDING_2
        )
    )

    private val example20 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineStatusEnum = TimeLineStatusEnum.PENDING,
            timeLineLinkText = "Link text"
        )
    )

    private val example21 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineStatusEnum = TimeLineStatusEnum.PENDING_2,
            timeLineLinkText = "Link text"
        )
    )

    private val example22 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineStatusEnum = TimeLineStatusEnum.WARNING,
            timeLineLinkText = "Link text"
        )
    )

    private val example23 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineStatusEnum = TimeLineStatusEnum.SUCCESS,
            timeLineLinkText = "Link text"
        )
    )

    private val example24 = arrayListOf(
        TimeLine(
            timeLineTitle = "14/10/2024",
            timeLineEndText = "400,000.00 AMD",
            timeLineStatusEnum = TimeLineStatusEnum.DANGER,
            timeLineLinkText = "Link text"
        )
    )
}
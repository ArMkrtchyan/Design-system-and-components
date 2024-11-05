package am.acba.components

import am.acba.component.extensions.log
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentDatePickerBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DatePickerFragment : BaseViewBindingFragment<FragmentDatePickerBinding>() {
    override val inflate: Inflater<FragmentDatePickerBinding>
        get() = FragmentDatePickerBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentDatePickerBinding.initView() {
        datePicker.disabledDays = arrayListOf(25, 26, 27, 28, 29, 30, 31)
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] + 30
        if (calendar[Calendar.DAY_OF_MONTH] in 25..31) {
            calendar[Calendar.DAY_OF_MONTH] = 1
            calendar[Calendar.MONTH] = calendar[Calendar.MONTH] + 1
        }
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time) ?: ""
        datePicker.setText(date)
        datePicker.currentDate = calendar.timeInMillis
        setFirstRepaymentDayRestrictions(1)
        datePicker.setOnDateSetListener { dateFormatted, date ->
            date?.log()
            dateFormatted?.log()
        }
    }

    private fun setFirstRepaymentDayRestrictions(repaymentFrequencyId: Int) {
        mBinding.apply {
            val nextMinAvailableDay = if (repaymentFrequencyId == 1) 20 else 30
            val nextMaxAvailableDay = if (repaymentFrequencyId == 1) 44 else 90
            val minDateCalendar = Calendar.getInstance()
            minDateCalendar[Calendar.DAY_OF_MONTH] = minDateCalendar[Calendar.DAY_OF_MONTH] + nextMinAvailableDay
            val maxDateCalendar = Calendar.getInstance()
            maxDateCalendar[Calendar.DAY_OF_MONTH] = minDateCalendar[Calendar.DAY_OF_MONTH] + nextMaxAvailableDay
            datePicker.minDate = minDateCalendar.timeInMillis
            datePicker.maxDate = maxDateCalendar.timeInMillis
        }
    }
}
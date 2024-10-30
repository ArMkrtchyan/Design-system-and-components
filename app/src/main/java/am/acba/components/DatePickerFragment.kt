package am.acba.components

import am.acba.component.extensions.log
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentDatePickerBinding
import java.util.Calendar

class DatePickerFragment : BaseViewBindingFragment<FragmentDatePickerBinding>() {
    override val inflate: Inflater<FragmentDatePickerBinding>
        get() = FragmentDatePickerBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentDatePickerBinding.initView() {
        datePicker.disabledDays = arrayListOf(25, 26, 27, 28, 29, 30, 31)
        datePicker.minDate = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.MONTH, 9)
        }.timeInMillis
        datePicker.setOnDateSetListener { dateFormatted, date ->
            date?.log()
            dateFormatted?.log()
        }
    }
}
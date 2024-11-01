package am.acba.component.datePicker

import android.content.Context
import android.os.Parcel
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.apply

class MaterialDatePickerDialog(
    private val context: Context? = null,
    private val fragmentManager: FragmentManager? = null,
    private val cancelable: Boolean = true,
    private val currentDate: Long = Date().time,
    private val minDate: Long? = null,
    private val maxDate: Long? = null,
    private val disabledDays: List<Int> = emptyList(),
    private val formattingPattern: String = "dd/MM/yyyy",
    private val onDateSet: ((dateFormatted: String?, date: Long?) -> Unit)? = null,
) {


    private val theme: Int = am.acba.component.R.style.MaterialCalendar
    private val calendar: Calendar = Calendar.getInstance()


    private constructor(builder: Builder) : this(
        builder.context,
        builder.fragmentManager,
        builder.cancelable,
        builder.currentDate,
        builder.minDate,
        builder.maxDate,
        builder.disabledDays,
        builder.formattingPattern,
        builder.onDateSet,
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block)
            .build()
    }

    data class Builder(
        var context: Context? = null,
        var fragmentManager: FragmentManager? = null,
        var cancelable: Boolean = true,
        var currentDate: Long = Date().time,
        var minDate: Long? = null,
        var maxDate: Long? = null,
        var disabledDays: List<Int> = emptyList(),
        var formattingPattern: String = "dd/MM/yyyy",
        var onDateSet: ((dateFormatted: String?, date: Long?) -> Unit)? = null,
    ) {

        fun setFragmentManager(fragmentManager: FragmentManager) = apply { this.fragmentManager = fragmentManager }
        fun setContext(context: Context) = apply { this.context = context }
        fun setCancelable(isCancelable: Boolean) = apply { this.cancelable = isCancelable }
        fun setCurrentDate(currentDate: Long) = apply { this.currentDate = currentDate }
        fun setMinDate(minDate: Long) = apply { this.minDate = minDate }
        fun setMaxDate(maxDate: Long) = apply { this.maxDate = maxDate }
        fun setFormattingPattern(formattingPattern: String) = apply { this.formattingPattern = formattingPattern }
        fun setDisabledDays(disabledDays: List<Int>) = apply { this.disabledDays = disabledDays }
        fun onDateSet(onDateSet: (dateFormatted: String?, date: Long?) -> Unit) = apply { this.onDateSet = onDateSet }

        fun build(): MaterialDatePickerDialog {
            return MaterialDatePickerDialog(this)
        }
    }

    val datePicker: MaterialDatePicker<*>
    val calendarConstraintBuilder: CalendarConstraints.Builder

    init {
        calendarConstraintBuilder = CalendarConstraints.Builder()
        minDate?.let { calendarConstraintBuilder.setStart(it) }
        maxDate?.let { calendarConstraintBuilder.setEnd(it) }
        calendarConstraintBuilder.setFirstDayOfWeek(Calendar.MONDAY)
        if (disabledDays.isNotEmpty()) {
            calendarConstraintBuilder.setValidator(object : CalendarConstraints.DateValidator {
                override fun isValid(date: Long): Boolean {
                    val calendar = Calendar.getInstance().apply { timeInMillis = date }
                    val isDayIsNotInDisables = calendar[Calendar.DAY_OF_MONTH] !in disabledDays
                    val isDisableBeforeMinDate = minDate == null || date >= minDate
                    val isDisableAfterMaxDate = maxDate == null || date <= maxDate
                    return isDayIsNotInDisables && isDisableBeforeMinDate && isDisableAfterMaxDate
                }

                override fun describeContents(): Int {
                    return 0
                }

                override fun writeToParcel(dest: Parcel, flags: Int) {

                }

            })
        }
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(context?.getString(am.acba.component.R.string.date_picker_title_text))
            .setCalendarConstraints(calendarConstraintBuilder.build())
            .setSelection(currentDate)
            .setTheme(theme)
            .build()
        datePicker.addOnPositiveButtonClickListener { date ->
            val calendar = Calendar.getInstance().apply { timeInMillis = date }
            val formattedDate = SimpleDateFormat(formattingPattern, Locale.getDefault()).format(calendar.time) ?: ""
            onDateSet?.invoke(formattedDate, calendar.timeInMillis)
        }
        datePicker.isCancelable = cancelable
        fragmentManager?.let { datePicker.show(it, "DatePicker") }
    }
}
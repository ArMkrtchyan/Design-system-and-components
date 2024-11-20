package am.acba.component.datePicker

import am.acba.component.R
import am.acba.component.input.PrimaryDropDown
import android.content.ContentValues.TAG
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import java.util.Date
import kotlin.let

class PrimaryDatePicker @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : PrimaryDropDown(context, attrs) {
    var currentDate: Long = Date().time
    var minDate: Long? = null
    var maxDate: Long? = null
    var disabledDays: List<Int> = emptyList()
    var formattingPattern: String = "dd/MM/yyyy"
    private var onDateSet: ((dateFormatted: String?, date: Long?) -> Unit)? = null

    init {
        binding.inputDropDown.endIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_calendar)
        binding.frame.setOnClickListener {
            MaterialDatePickerDialog.build {
                this.context = context
                fragmentManager = getFragmentManager()
                currentDate = this@PrimaryDatePicker.currentDate
                minDate = this@PrimaryDatePicker.minDate
                maxDate = this@PrimaryDatePicker.maxDate
                disabledDays = this@PrimaryDatePicker.disabledDays
                formattingPattern = this@PrimaryDatePicker.formattingPattern
                onDateSet = { dateFormatted, date ->
                    binding.inputDropDown.setText(dateFormatted)
                    this@PrimaryDatePicker.currentDate = date ?: this@PrimaryDatePicker.currentDate
                    this@PrimaryDatePicker.onDateSet?.invoke(dateFormatted, date)
                }
            }
        }
    }

    fun setOnDateSetListener(onDateSet: (dateFormatted: String?, date: Long?) -> Unit) {
        this.onDateSet = onDateSet
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.frame.setOnClickListener(l)
    }

    private fun getFragmentManager(): FragmentManager {
        try {
            val fragmentActivity = context as? FragmentActivity
            fragmentActivity?.let {
                val fragmentManager = it.supportFragmentManager
                return fragmentManager
            }
        } catch (e: ClassCastException) {
            Log.d(TAG, "Can't get the fragment manager with this")
        }
        return getFragmentManager()
    }
}
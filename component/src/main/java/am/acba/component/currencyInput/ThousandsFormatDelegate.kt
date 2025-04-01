package am.acba.component.currencyInput


import am.acba.component.extensions.log
import am.acba.component.input.PrimaryEditText
import android.os.Handler
import android.os.Looper
import android.service.autofill.CharSequenceTransformation
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.KeyEvent
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ThousandsFormatDelegate(private val editText: EditText) : ReadWriteProperty<Any?, String> {
    private var isEditing = false
    private var rawInput = ""
    var previousText = ""

    init {
        editText.doAfterTextChanged { editable ->
            if (isEditing || editable.isNullOrEmpty()) return@doAfterTextChanged
            val currentText = editable.toString()
            if (currentText.length < previousText.length) {
                previousText = currentText
                editText.setText(getOriginalText(currentText).toThousandsWithoutDecimals())
                editText.setSelection(editText.text.length)
                return@doAfterTextChanged
            }
            isEditing = true
            rawInput = getOriginalText(editable.toString())
            val formattedString = rawInput.toThousandsWithoutDecimals()
            val ed = SpannableStringBuilder(formattedString)
            editText.text = ed
            editText.setSelection(formattedString.length)
            isEditing = false
            previousText = formattedString
        }
        editText.setOnKeyListener { _, keyCode, event ->
            keyCode.log()
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (event.action == KeyEvent.ACTION_DOWN) {

                } else if (event.action == KeyEvent.ACTION_UP) {

                }
            }
            false
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return editText.text.toString()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        editText.setText(value.toThousands())
        editText.setSelection(editText.text.length)
    }

    private var deleteStartTime = 0L
    private val handler = Handler(Looper.getMainLooper())
    private val deleteRunnable = Runnable {
        if (System.currentTimeMillis() - deleteStartTime >= 500) { // Long press detected
            val length = editText.text.length
            if (length > 0) {
                editText.text.delete(0, length)
            }
        }
    }

}

fun getOriginalText(text: String): String {
    val originalString = text.replace(",", "")
    if (originalString.all { it.isDigit() }) {
        return originalString
    }
    return ""
}


fun String.toThousands(): String =
    kotlin.runCatching {
        DecimalFormat("#,##0.00").apply {
            decimalFormatSymbols = DecimalFormatSymbols(Locale.ENGLISH)
        }.format(this.toDoubleOrNull() ?: 0.0)
    }.getOrDefault("")


fun String?.toThousandsWithoutDecimals(): String {
    return runCatching {
        DecimalFormat("#,##0").apply {
            decimalFormatSymbols = DecimalFormatSymbols(Locale.ENGLISH)
        }.format(this?.toLongOrNull() ?: 0L)
    }.getOrDefault("")
}

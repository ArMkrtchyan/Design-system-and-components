package am.acba.component.phoneNumberInput

//noinspection SuspiciousImport
import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat.getSystemService


@SuppressLint("UseCompatLoadingForDrawables")
class CutCopyPasteEditText : AppCompatEditText {

    private var onDoneButtonClick: (() -> Unit)? = null
    private var onActionButtonClick: ((Int) -> Unit)? = null

    fun setOnCutCopyPasteListener(action: ((Int) -> Unit)? = null) {
        mAction = action
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        @RequiresApi(Build.VERSION_CODES.Q)
        this.textCursorDrawable = context.getDrawable(am.acba.component.R.drawable.cursor_drawable)

        setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(context, InputMethodManager::class.java)
                imm?.hideSoftInputFromWindow(windowToken, 0)
                clearFocus()

                onDoneButtonClick?.invoke()
                true
            } else {
                onActionButtonClick?.invoke(actionId)
            }
            false
        }
    }

    fun onKeyboardDoneButtonClick(onDoneButtonClick: () -> Unit) {
        this.onDoneButtonClick = onDoneButtonClick
    }

    fun onKeyboardActionButtonClick(onActionButtonClick: (Int) -> Unit) {
        this.onActionButtonClick = onActionButtonClick
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        val consumed = super.onTextContextMenuItem(id)
        when (id) {
            R.id.cut -> mAction?.invoke(id)
            R.id.copy -> mAction?.invoke(id)
            R.id.paste -> mAction?.invoke(id)
        }
        return consumed
    }

    private var mAction: ((Int) -> Unit?)? = null
}
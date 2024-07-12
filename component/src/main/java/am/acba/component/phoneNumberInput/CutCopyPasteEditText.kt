package am.acba.component.phoneNumberInput

//noinspection SuspiciousImport
import android.R
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class CutCopyPasteEditText : AppCompatEditText {

    fun setOnCutCopyPasteListener(action: ((Int) -> Unit)? = null) {
        mAction = action
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


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
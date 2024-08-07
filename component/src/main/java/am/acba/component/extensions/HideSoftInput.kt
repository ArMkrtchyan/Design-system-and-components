package am.acba.component.extensions

import android.graphics.Rect
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService

fun EditText.hideSoftInput(){
    setOnEditorActionListener { textView, i, _ ->
        if (i == EditorInfo.IME_ACTION_DONE) {
            val imm = getSystemService(context, InputMethodManager::class.java)
            imm?.hideSoftInputFromWindow(textView.windowToken, 0)
            true
        } else {
            false
        }
    }
}

fun View.addKeyboardVisibilityListener(editText: EditText) {
    this.viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        this@addKeyboardVisibilityListener.getWindowVisibleDisplayFrame(rect)
        val screenHeight = this@addKeyboardVisibilityListener.rootView.height
        val keypadHeight = screenHeight - rect.bottom
        if (keypadHeight < screenHeight * 0.15) {
            editText.clearFocus()
        }
    }
}
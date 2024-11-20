package am.acba.component.viewUtil

import am.acba.component.textView.PrimaryTextView
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object ViewUtil {
    fun copyText(view: PrimaryTextView) {
        val clipboardManager =
            view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", view.text)
        clipboardManager.setPrimaryClip(clipData)
    }
}
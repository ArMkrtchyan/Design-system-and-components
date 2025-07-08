package am.acba.component.viewUtil

import am.acba.component.extensions.vibrate
import am.acba.component.input.PrimaryInput.Companion.VIBRATION_AMPLITUDE
import am.acba.component.textView.PrimaryTextView
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object ViewUtil {
    fun copyWithVibration(view: PrimaryTextView) {
        copyText(view)
        view.context.vibrate(VIBRATION_AMPLITUDE)
    }

    fun copyText(view: PrimaryTextView) {
        val clipboardManager = view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", view.text)
        clipboardManager.setPrimaryClip(clipData)
    }


    fun Context.copyWithVibration(text: String) {
        copyText(text)
        vibrate(VIBRATION_AMPLITUDE)
    }

    fun Context.copyText(text: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)
    }
}
package am.acba.component

import android.os.SystemClock
import android.view.View

class PreventDoubleClickListener(
    private val onClickListener: View.OnClickListener?,
    private val clickInterval: Int = 1000,
) : View.OnClickListener {
    private var mClickedTime = 0L
    override fun onClick(view: View?) {
        if (SystemClock.elapsedRealtime() - mClickedTime > clickInterval) {
            mClickedTime = SystemClock.elapsedRealtime()
            onClickListener?.onClick(view)
        }
    }
}
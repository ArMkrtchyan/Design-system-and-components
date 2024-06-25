package am.acba.components.base

import am.acba.components.mainScreen.MainActivity
import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected lateinit var mActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context
        }
    }

    protected fun onScreenHeightChange(onChange: (Int) -> Unit) {
        val mRootWindow = requireActivity().window
        val mRootView: View = mRootWindow.decorView.findViewById(android.R.id.content)
        mRootView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            val view: View = mRootWindow.decorView
            view.getWindowVisibleDisplayFrame(r)
            // r.left, r.top, r.right, r.bottom
            onChange.invoke(mRootView.height - r.bottom)
        }
    }

    override fun onPause() {
        mActivity.hideSoftInput()
        super.onPause()
    }

}
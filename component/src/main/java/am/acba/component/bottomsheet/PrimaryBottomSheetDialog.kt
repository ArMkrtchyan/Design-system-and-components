package am.acba.component.bottomsheet


import am.acba.component.R
import am.acba.component.databinding.BottomSheetBaseContainerBinding
import am.acba.component.extensions.Inflater
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class PrimaryBottomSheetDialog<VB : ViewBinding> : BottomSheetDialogFragment() {
    protected abstract val inflate: Inflater<VB>
    abstract fun VB.initView()
    protected open val draggable: Boolean = true
    protected open val cancelable: Boolean = true
    protected open val state: Int = BottomSheetBehavior.STATE_COLLAPSED
    protected open val parentBackgroundColorAttr: Int? = null
    protected open val contentPaddingStart: Int? = null
    protected open val contentPaddingTop: Int? = null
    protected open val contentPaddingEnd: Int? = null
    protected open val contentPaddingBottom: Int? = null
    protected lateinit var mActivity: AppCompatActivity
    protected var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private val containerBinding by lazy { BottomSheetBaseContainerBinding.inflate(requireContext().inflater()) }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCompatActivity) {
            mActivity = context
        }
    }

    @SuppressLint("ResourceType")
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog = BottomSheetDialog(requireContext(), theme).apply {
        bottomSheetBehavior = behavior
        behavior.isDraggable = draggable
        behavior.state = state
    }

    private lateinit var _binding: VB
    protected val mBinding: VB
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        if (!::_binding.isInitialized) {
            _binding = inflate(inflater, container, false)
            _binding.initView()
            setTitle(arguments?.getString("title"))
            containerBinding.container.addView(_binding.root)
            containerBinding.divider.isVisible = draggable
        }
        isCancelable = cancelable
        containerBinding.close.setOnClickListener { dismiss() }
        containerBinding.parent.backgroundTintList = requireContext().getColorStateListFromAttr(parentBackgroundColorAttr ?: am.acba.component.R.attr.backgroundTonal1)
        containerBinding.container.setPadding(
            contentPaddingStart ?: 16.dpToPx(),
            contentPaddingTop ?: 0.dpToPx(),
            contentPaddingEnd ?: 16.dpToPx(),
            contentPaddingBottom ?: 24.dpToPx(),
        )
        return containerBinding.root
    }

    protected fun setTitle(title: String?) {
        containerBinding.title.text = title
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

    private fun hideSoftInput() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val currentFocusedView = dialog?.currentFocus
        currentFocusedView?.let {
            inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        hideSoftInput()
        super.onDismiss(dialog)
    }
}
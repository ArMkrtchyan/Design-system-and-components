package am.acba.component.bottomsheet

import am.acba.component.R
import am.acba.component.databinding.ModalBottomSheetBinding
import am.acba.component.databinding.PersistentBottomSheetBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.StableState


class PersistentBottomSheet : CoordinatorLayout {
    private val binding by lazy {
        PersistentBottomSheetBinding.inflate(
            context.inflater(),
            this,
            false
        )
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.PersistentBottomSheet).apply {
            addView(binding.root)
            try {
                binding.layout.initListeners()

                val title = getString(R.styleable.PersistentBottomSheet_title)
                setTitle(title)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            recycle()
            invalidate()
        }
    }

    private fun ModalBottomSheetBinding.initListeners() {
        ivClose.setOnClickListener {
            BottomSheetBehavior.from(binding.root).isHideable = true
            setState(BottomSheetBehavior.STATE_HIDDEN)

            BottomSheetBehavior.from(binding.root).isHideable = false
        }
    }

    private fun addContainer(child: View) {
        binding.layout.dynamicContainer.addView(child)
    }

    fun setTitle(title: String?) {
        binding.layout.tvTitle.apply {
            text = title
            viewTreeObserver.addOnGlobalLayoutListener {
                val behavior = BottomSheetBehavior.from(binding.root)
                behavior.peekHeight = height + 72.dpToPx()
            }
        }
    }

    fun setDynamicContainer(dynamicContainer: View) {
        addContainer(dynamicContainer)
    }

    fun setState(@StableState state: Int) {
        binding.root.isVisible = true
        BottomSheetBehavior.from(binding.root).state = state
    }
}
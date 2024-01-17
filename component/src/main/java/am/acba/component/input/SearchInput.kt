package am.acba.component.input

import am.acba.component.R
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updateMarginsRelative

class SearchInput : PrimaryInput {
    private var withBackIcon = false

    constructor(context: Context) : super(context, null, R.attr.searchInputStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.searchInputStyle) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.SearchInput).apply {
            editText?.background = ContextCompat.getDrawable(context, R.color.Transparent)
            try {
                if (hasValueOrEmpty(R.styleable.SearchInput_withBackIcon)) {
                    withBackIcon = getBoolean(R.styleable.SearchInput_withBackIcon, false)
                }
                editText?.hint = getString(R.styleable.SearchInput_android_hint)
                editText?.setText(getString(R.styleable.SearchInput_android_text))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            updateEndIconBackgroundState()
            updateStartIconBackgroundState()
            recycle()
        }
    }

    private fun updateEndIconBackgroundState() {
        val endIcon = findViewById<ImageButton>(com.google.android.material.R.id.text_input_end_icon)
        endIcon.background = ContextCompat.getDrawable(context, R.drawable.background_ghost_brand_cycle)
        endIcon.updateLayoutParams<FrameLayout.LayoutParams> {
            updateMarginsRelative(0, 0, 0, 0)
            updateMargins(0, 0, 0, 0)
        }
    }

    private fun updateStartIconBackgroundState() {
        val startIcon = findViewById<ImageButton>(com.google.android.material.R.id.text_input_start_icon)
        startIcon.background = ContextCompat.getDrawable(context, R.drawable.background_ghost_brand_cycle)
        startIcon.updateLayoutParams<LayoutParams> {
            updateMarginsRelative(0, 0, 0, 0)
            updateMargins(0, 0, 0, 0)
        }
    }
}
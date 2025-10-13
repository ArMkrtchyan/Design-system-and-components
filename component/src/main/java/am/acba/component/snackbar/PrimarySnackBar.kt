package am.acba.component.snackbar

import am.acba.component.R
import am.acba.component.databinding.PrimarySnackBarBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import am.acba.component.extensions.playLottieAnimation
import am.acba.component.imageView.PrimaryImageView
import am.acba.component.textView.PrimaryTextView
import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PrimarySnackBar(
    val context: Activity,
    @DrawableRes private val icon: Int? = null,
    private val lottieIcon: String? = null,
    private val title: String = "",
    private val isUserClosable: Boolean = false,
    private val lifecycleOwner: LifecycleOwner? = null,
) {
    private val binding by lazy { PrimarySnackBarBinding.inflate(context.inflater(), context.window.decorView.findViewById(android.R.id.content), false) }

    private constructor(builder: Builder) : this(
        builder.context,
        builder.icon,
        builder.lottieIcon,
        builder.title,
        builder.isUserClosable,
        builder.lifecycleOwner,
    )

    companion object {
        inline fun build(required: Activity, block: Builder.() -> Unit) =
            Builder(required).apply(block)
                .build()
    }

    data class Builder(
        val context: Activity,
        @DrawableRes var icon: Int? = null,
        var lottieIcon: String? = null,
        var title: String = "",
        var isUserClosable: Boolean = false,
        var lifecycleOwner: LifecycleOwner? = null,
    ) {

        fun build(): PrimarySnackBar {
            val v = PrimarySnackBar(this)
            v.show()
            return v
        }
    }


    fun show() {
        val rootView = context.window.decorView.findViewById<FrameLayout>(android.R.id.content)
        var coordinatorLayout: CoordinatorLayout? = rootView.findViewById(R.id.snackbar_coordinator_layout)
        if (coordinatorLayout == null) {
            coordinatorLayout = binding.snackbarCoordinatorLayout
            rootView.addView(binding.root)
        }

        val bottomSheet = coordinatorLayout.findViewById<FrameLayout>(R.id.bottom_sheet)
        val sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        sheetBehavior.isDraggable = isUserClosable

        (coordinatorLayout.getTag(R.id.snackbar_coordinator_layout) as? Runnable)?.let {
            coordinatorLayout.removeCallbacks(it)
            coordinatorLayout.setTag(R.id.snackbar_coordinator_layout, null)
        }

        val lottie = coordinatorLayout.findViewById<LottieAnimationView>(R.id.lottie)
        val startIcon = coordinatorLayout.findViewById<PrimaryImageView>(R.id.start_icon)
        val endIcon = coordinatorLayout.findViewById<PrimaryImageView>(R.id.end_icon)
        val snackBarTitle = coordinatorLayout.findViewById<PrimaryTextView>(R.id.title)

        snackBarTitle.text = ""
        lottie.cancelAnimation()
        startIcon.setImageDrawable(null)

        lottie.isVisible = lottieIcon != null
        startIcon.isVisible = icon != null
        endIcon.isVisible = isUserClosable
        snackBarTitle.text = title
        snackBarTitle.setPadding(0, 0, if (isUserClosable) 0 else 16.dpToPx(), 0)
        icon?.let(startIcon::setImageResource)

        if (lottieIcon != null) {
            lottie.playLottieAnimation(500) {
                lottie.setAnimation(lottieIcon)
            }
        }

        endIcon.setOnClickListener { swipeDown(sheetBehavior, coordinatorLayout) }
        swipeDown(sheetBehavior)
        bottomSheet.postDelayed({ swipeUp(sheetBehavior) }, 200)

        val appearTime = (3000 + title.split(" ").size * 200).coerceAtMost(15000)
        val handlerCallback = Runnable { swipeDown(sheetBehavior, coordinatorLayout) }
        coordinatorLayout.setTag(R.id.snackbar_coordinator_layout, handlerCallback)
        coordinatorLayout.postDelayed(handlerCallback, appearTime.toLong())

        lifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                endIcon.callOnClick()
            }
        })
    }

    private fun swipeUp(sheetBehavior: BottomSheetBehavior<*>) {
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun swipeDown(sheetBehavior: BottomSheetBehavior<*>, coordinatorLayout: CoordinatorLayout? = null) {
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        if (coordinatorLayout != null) {
            val snackBarTitle = coordinatorLayout.findViewById<PrimaryTextView>(R.id.title)
            val lottie = coordinatorLayout.findViewById<LottieAnimationView>(R.id.lottie)
            val startIcon = coordinatorLayout.findViewById<PrimaryImageView>(R.id.start_icon)
            coordinatorLayout.postDelayed({
                snackBarTitle.text = ""
                lottie.clearAnimation()
                startIcon.setImageDrawable(null)
            }, 400)
        }
    }
}
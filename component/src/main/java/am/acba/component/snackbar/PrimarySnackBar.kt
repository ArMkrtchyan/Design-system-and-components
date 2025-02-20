package am.acba.component.snackbar

import am.acba.component.R
import am.acba.component.databinding.PrimarySnackBarBinding
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
        var sheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<View>(binding.bottomSheet)
        if (coordinatorLayout != null) {
            val bottomSheet = coordinatorLayout.findViewById<FrameLayout>(R.id.bottom_sheet)
            sheetBehavior = BottomSheetBehavior.from<View>(bottomSheet!! as View)
            bottomSheet.postDelayed({ swipeUp(sheetBehavior) }, 400)
        } else {
            coordinatorLayout = binding.snackbarCoordinatorLayout

            rootView.addView(binding.root)

            swipeDown(sheetBehavior)
            binding.bottomSheet.postDelayed({ swipeUp(sheetBehavior) }, 400)
        }
        val lottie = coordinatorLayout.findViewById<LottieAnimationView>(R.id.lottie)
        val startIcon = coordinatorLayout.findViewById<PrimaryImageView>(R.id.start_icon)
        val endIcon = coordinatorLayout.findViewById<PrimaryImageView>(R.id.end_icon)
        val snackBarTitle = coordinatorLayout.findViewById<PrimaryTextView>(R.id.title)
        lottie.isVisible = lottieIcon != null
        startIcon.isVisible = icon != null
        snackBarTitle.text = title
        endIcon.isVisible = isUserClosable
        icon?.let(startIcon::setImageResource)
        sheetBehavior.isDraggable = isUserClosable
        if (lottieIcon != null) {
            lottie.playLottieAnimation(500) {
                lottie.setAnimation(lottieIcon)
            }
        }
        coordinatorLayout.invalidate()
        endIcon.setOnClickListener { swipeDown(sheetBehavior, coordinatorLayout) }
        var handlerCallback: Runnable? = coordinatorLayout.getTag(R.id.snackbar_coordinator_layout) as? Runnable
        handlerCallback?.let(coordinatorLayout::removeCallbacks)
        if (!isUserClosable) {
            val appearTime = (title.split(" ").size / 4) * 1000
            handlerCallback = Runnable { swipeDown(sheetBehavior, coordinatorLayout) }
            coordinatorLayout.setTag(R.id.snackbar_coordinator_layout, handlerCallback)
            coordinatorLayout.postDelayed(handlerCallback, (if (appearTime > 2000) appearTime else 2000).toLong())
        } else {
            lifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                override fun onStop(owner: LifecycleOwner) {
                    super.onStop(owner)
                    endIcon.callOnClick()
                }
            })
        }
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
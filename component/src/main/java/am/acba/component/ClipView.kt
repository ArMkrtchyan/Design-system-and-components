package am.acba.component

import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.log
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ClipView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var startViewCenterX = 0f
    private var startViewCenterY = 0f
    private var endViewCenterX = 0f
    private var endViewCenterY = 0f
    private var duration = 150

    private val backgroundPaint = Paint()
    private var startClipRect: RectF? = null
    private var clipRect = RectF()
    private var endClipRect = RectF()

    private val clipPath = Path()
    private val cornerRadius = 12.dpToPx().toFloat()
    private val clipPadding = 8.dpToPx()

    init {
        backgroundPaint.color = ContextCompat.getColor(context, R.color.Black_50)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(clipPath)
        } else {
            canvas.clipPath(clipPath)
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
    }

    fun clipForView(view: View) {
        clipRect.top = (view.top - clipPadding).toFloat()
        clipRect.left = (view.left - clipPadding).toFloat()
        clipRect.right = (view.right + clipPadding).toFloat()
        clipRect.bottom = (view.bottom + clipPadding).toFloat()
        clipPath.reset()
        clipPath.addRoundRect(clipRect, cornerRadius, cornerRadius, Path.Direction.CW)
        clipPath.close()
        invalidate()
        if (startClipRect == null) {
            startViewCenterX = view.x + view.width / 2
            startViewCenterY = view.y + view.height / 2
            startClipRect = RectF()
            startClipRect?.top = (view.top - clipPadding).toFloat()
            startClipRect?.left = (view.left - clipPadding).toFloat()
            startClipRect?.right = (view.right + clipPadding).toFloat()
            startClipRect?.bottom = (view.bottom + clipPadding).toFloat()
            clipPath.reset()
            clipPath.addRoundRect(startClipRect!!, cornerRadius, cornerRadius, Path.Direction.CW)
            clipPath.close()
            invalidate()
        } else {
            endViewCenterX = view.x + view.width / 2
            endViewCenterY = view.y + view.height / 2
            endClipRect.top = (view.top - clipPadding).toFloat()
            endClipRect.left = (view.left - clipPadding).toFloat()
            endClipRect.right = (view.right + clipPadding).toFloat()
            endClipRect.bottom = (view.bottom + clipPadding).toFloat()
            animateTranslation()
        }
    }

    fun clipForView(view: View, clipWidth: Float, clipHeight: Float) {
        if (startClipRect == null) {
            startViewCenterX = view.x + view.width / 2
            startViewCenterY = view.y + view.height / 2
            startClipRect = RectF()
            startClipRect?.top = (startViewCenterY.log(prefix = "Start Y ->") - clipHeight / 2) - clipPadding
            startClipRect?.left = (startViewCenterX.log(prefix = "Start X ->") - clipWidth / 2) - clipPadding
            startClipRect?.right = (startViewCenterX + clipWidth / 2) + clipPadding
            startClipRect?.bottom = (startViewCenterY + clipHeight / 2) + clipPadding
            clipPath.reset()
            clipPath.addRoundRect(startClipRect!!, cornerRadius, cornerRadius, Path.Direction.CW)
            clipPath.close()
            invalidate()
        } else {
            endViewCenterX = view.x + view.width / 2
            endViewCenterY = view.y + view.height / 2
            endClipRect.top = (endViewCenterY.log(prefix = "End Y ->") - clipHeight / 2) - clipPadding
            endClipRect.left = (endViewCenterX.log(prefix = "End X ->") - clipWidth / 2) - clipPadding
            endClipRect.right = (endViewCenterX + clipWidth / 2) + clipPadding
            endClipRect.bottom = (endViewCenterY + clipHeight / 2) + clipPadding
            animateTranslation()
        }
    }

    fun clipByXAndY(viewX: Float, viewY: Float, clipWidth: Float, clipHeight: Float) {
        if (startClipRect == null) {
            startViewCenterX = viewX
            startViewCenterY = viewY
            startClipRect = RectF()
            startClipRect?.top = (startViewCenterY.log(prefix = "Start Y ->") - clipHeight / 2) - clipPadding
            startClipRect?.left = (startViewCenterX.log(prefix = "Start X ->") - clipWidth / 2) - clipPadding
            startClipRect?.right = (startViewCenterX + clipWidth / 2) + clipPadding
            startClipRect?.bottom = (startViewCenterY + clipHeight / 2) + clipPadding
            clipPath.reset()
            clipPath.addRoundRect(startClipRect!!, cornerRadius, cornerRadius, Path.Direction.CW)
            clipPath.close()
            invalidate()
        } else {
            endViewCenterX = viewX
            endViewCenterY = viewY
            endClipRect.top = (endViewCenterY.log(prefix = "End Y ->") - clipHeight / 2) - clipPadding
            endClipRect.left = (endViewCenterX.log(prefix = "End X ->") - clipWidth / 2) - clipPadding
            endClipRect.right = (endViewCenterX + clipWidth / 2) + clipPadding
            endClipRect.bottom = (endViewCenterY + clipHeight / 2) + clipPadding
            animateTranslation()
        }
    }

    private fun animateTranslation() {
        val differentX = endViewCenterX - startViewCenterX
        val differentY = endViewCenterY - startViewCenterY
        if (differentY.log(prefix = "DifferentY ->") == 0f && differentX.log(prefix = "DifferentX ->") == 0f) {
            return
        }
        clipRect.top = startClipRect?.top ?: 0f
        clipRect.left = startClipRect?.left ?: 0f
        clipRect.right = startClipRect?.right ?: 0f
        clipRect.bottom = startClipRect?.bottom ?: 0f
        val leftIncrementStep = (endClipRect.left - (startClipRect?.left ?: 0f)) / duration
        val rightIncrementStep = (endClipRect.right - (startClipRect?.right ?: 0f)) / duration
        val topIncrementStep = (endClipRect.top - (startClipRect?.top ?: 0f)) / duration
        val bottomIncrementStep = (endClipRect.bottom - (startClipRect?.bottom ?: 0f)) / duration
        leftIncrementStep.log(prefix = "leftIncrementStep ->")
        rightIncrementStep.log(prefix = "rightIncrementStep ->")
        topIncrementStep.log(prefix = "topIncrementStep ->")
        bottomIncrementStep.log(prefix = "bottomIncrementStep ->")
        (context as FragmentActivity).lifecycleScope.launch {
            repeat((0..duration).count()) {
                delay(1)
                if (differentX != 0f) {
                    clipRect.left += leftIncrementStep
                    clipRect.right += rightIncrementStep
                }
                if (differentY != 0f) {
                    clipRect.top += topIncrementStep
                    clipRect.bottom += bottomIncrementStep
                }
                clipPath.reset()
                clipPath.addRoundRect(clipRect, cornerRadius, cornerRadius, Path.Direction.CW)
                clipPath.close()
                invalidate()
            }
            startViewCenterX = endViewCenterX
            startViewCenterY = endViewCenterY
            startClipRect?.top = endClipRect.top
            startClipRect?.left = endClipRect.left
            startClipRect?.right = endClipRect.right
            startClipRect?.bottom = endClipRect.bottom
        }
    }
}
package am.acba.component.imageView

import am.acba.component.R
import am.acba.component.extensions.dpToPx
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.os.Looper
import android.text.TextPaint
import android.widget.ImageView
import androidx.core.content.ContextCompat
import java.util.Locale

/**
 * Created by Aditya Awasthi on 28/09/20.
 * @author github.com/adwardstark
 */

class MaterialTextDrawable private constructor(builder: Builder) {

    companion object {
        fun with(context: Context): Builder = Builder().with(context)
        private fun isOnMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper()
    }


    private var context: Context
    private var textColor: Int
    private var backgroundColor: Int
    private var size: Int
    private var text: String

    init {
        this.context = builder.context
        this.size = builder.size
        this.text = builder.text
        this.textColor = builder.textColor ?: ContextCompat.getColor(builder.context, R.color.BrandGreen_650)
        this.backgroundColor = builder.backgroundColor ?: ContextCompat.getColor(builder.context, R.color.BrandGreen_200)
    }

    class Builder {

        internal lateinit var context: Context
        internal var size = 56.dpToPx()
        internal var textColor: Int? = null
        internal var backgroundColor: Int? = null
        internal var text: String = ""

        fun with(context: Context): Builder {
            this.context = context
            return this
        }

        fun textSize(size: Int): Builder {
            this.size = size
            return this
        }

        fun textColor(textColor: Int): Builder {
            this.textColor = textColor
            return this
        }

        fun textBackgroundColor(backgroundColor: Int): Builder {
            this.backgroundColor = backgroundColor
            return this
        }

        fun text(text: String): Builder {
            this.text = text
            return this
        }

        fun get(): BitmapDrawable {
            if (text == "") {
                throw NullPointerException(
                    "No text provided, " +
                            "call text(<your_text>) before calling this method"
                )
            }
            return MaterialTextDrawable(this).getTextDrawable()
        }

        fun into(view: ImageView) {
            if (!isOnMainThread()) {
                throw IllegalArgumentException("You must call this method on the main thread")
            }
            // Set text-drawable
            view.setImageDrawable(get())
        }

        fun into(view: ImageView, scale: ImageView.ScaleType) {
            view.scaleType = scale
            into(view)
        }

    }

    private fun getTextDrawable(): BitmapDrawable {
        val initials = getFirstChars(text)
        val textPaint = textPainter()
        val painter = Paint()
        painter.isAntiAlias = true

        val areaRectangle = Rect(0, 0, size, size)
        val bitmap = Bitmap.createBitmap(size, size, ARGB_8888)
        val canvas = Canvas(bitmap)

        painter.color = ContextCompat.getColor(context, R.color.Transparent)
        canvas.drawRect(areaRectangle, painter)

        val bounds = RectF(areaRectangle)
        bounds.right = textPaint.measureText(initials, 0, initials.length)
        bounds.bottom = textPaint.descent() - textPaint.ascent()

        bounds.left += (areaRectangle.width() - bounds.right) / 2.0f
        bounds.top += (areaRectangle.height() - bounds.bottom) / 2.0f

        painter.color = backgroundColor
        canvas.drawCircle(size.toFloat() / 2, size.toFloat() / 2, size.toFloat() / 2, painter)
        canvas.drawText(initials, bounds.left, bounds.top - textPaint.ascent(), textPaint)
        return BitmapDrawable(context.resources, bitmap)
    }

    private fun getFirstChars(text: String): String {
        var finalText = ""
        val textArray = text.split(" ")
        if (textArray.size == 1) {
            finalText = textArray[0].first().toString()
        } else if (textArray.size > 1) {
            finalText = textArray[0].first().toString() + textArray[1].first().toString()
        }
        return finalText.uppercase(Locale.ROOT)
    }

    private fun textPainter(): TextPaint {
        val textPaint = TextPaint()
        textPaint.isAntiAlias = true
        textPaint.textSize = 20.dpToPx().toFloat()
        textPaint.color = textColor
        return textPaint
    }
}
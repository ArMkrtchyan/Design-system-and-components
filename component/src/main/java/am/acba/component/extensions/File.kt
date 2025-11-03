package am.acba.component.extensions

import am.acba.utils.extensions.orEmpty
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.graphics.createBitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

fun File.createBase64Component(): String {
    var inputStream: InputStream? = null

    try {
        inputStream = FileInputStream(this.absolutePath)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    val bytes: ByteArray
    val buffer = ByteArray(this.length().toInt())
    var bytesRead: Int
    val output = ByteArrayOutputStream()
    try {
        if (inputStream != null) {
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                output.write(buffer, 0, bytesRead)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    bytes = output.toByteArray()
    return Base64.encodeToString(bytes, Base64.NO_WRAP)
}

fun Uri.createBase64Component(context: Context, imageMaxSize: Int): String {
    val file = getFileComponent(context, imageMaxSize)
    val fileBase64 = (file?.createBase64Component().orEmpty()).log()
    file?.delete()
    return fileBase64
}

fun Bitmap.createFileComponent(context: Context, imageMaxSize: Int): File {
    val file = File(
        context.cacheDir,
        this.hashCode().toString() + ""
    )
    file.createNewFile()
    try {
        Log.d("ImageSize", "Before compress -> " + getFileSizeComponent(this.byteCount.toLong()))
        val imageSizeInKB: Double = byteCount / 1024.0
        val bos = ByteArrayOutputStream()
        if (imageSizeInKB > imageMaxSize) {
            var quality = 100
            this.compress(Bitmap.CompressFormat.JPEG, quality, bos) // ignored for PNG
            while (bos.toByteArray().size / 1024 > imageMaxSize) {
                bos.reset()
                compress(Bitmap.CompressFormat.JPEG, quality, bos)
                if (quality == 0) {
                    break
                } else if (quality > 15) {
                    quality -= 15
                } else {
                    break
                }
            }
            Log.d("ImageSize", "After compress -> " + getFileSizeComponent(bos.toByteArray().size.toLong()))
        } else {
            this.compress(Bitmap.CompressFormat.JPEG, 100, bos) // ignored for PNG
        }
        val bitmapData = bos.toByteArray()
        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
    } catch (e: Exception) {
        Log.d("ImageSize", "Base64 encode error -> " + e.localizedMessage)
    }

    return file
}

fun Uri.getFileComponent(context: Context, imageMaxSize: Int): File? {
    var bitmap: Bitmap? = null
    val contentResolver: ContentResolver = context.contentResolver
    try {
        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(contentResolver, this)
        } else {
            val source = ImageDecoder.createSource(contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return bitmap?.createFileComponent(context, imageMaxSize)
}

fun Uri.getFileExtensionComponent(context: Context): String? {
    return let {
        context.contentResolver.getType(it)?.let { mimeType ->
            MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        } ?: path?.substringAfterLast('.', missingDelimiterValue = "")
    }
}

fun getFileSizeComponent(size: Long): String {
    if (size <= 0) return "0"

    val units: Array<String> = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()

    return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
}

fun Uri.getFileSizeComponent(context: Context): Long {
    val resolver = context.contentResolver
    return resolver?.query(this, arrayOf(OpenableColumns.SIZE), null, null, null)?.use { cursor ->
        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
        if (cursor.moveToFirst() && sizeIndex != -1) cursor.getLong(sizeIndex) else 0L
    }.orEmpty()
}

fun renderPdfPageAsBitmapComponent(context: Context, uri: Uri, pageIndex: Int = 0, backgroundColor: Int): Bitmap? {
    return try {
        val fileDescriptor = context.contentResolver.openFileDescriptor(uri, "r") ?: return null
        val renderer = PdfRenderer(fileDescriptor)
        val page = renderer.openPage(pageIndex)

        val width = page.width
        val height = page.height
        val bitmap = createBitmap(width, height)

        val canvas = Canvas(bitmap)
        canvas.drawColor(backgroundColor)

        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        page.close()
        renderer.close()
        fileDescriptor.close()

        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Uri.deleteFileComponent(): Boolean = File(path.orEmpty()).delete()

fun Uri.createBase64ForPdfComponent(context: Context): String? {
    return try {
        context.contentResolver.openInputStream(this)?.use { inputStream ->
            val outputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            val pdfBytes = outputStream.toByteArray()
            Base64.encodeToString(pdfBytes, Base64.NO_WRAP)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

package am.acba.component.extensions

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
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

fun File.createBase64(): String {
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

fun Bitmap.createFile(context: Context, ext: String?): File {
    val file = File(
        context.cacheDir,
        this.hashCode().toString() + ".$ext"
    )
    file.createNewFile()
    try {
        Log.d("ImageSize", "Before compress -> " + getFileSize(this.getByteCount().toLong()))
        val imageSizeInKB: Double = getByteCount() / 1024.0
        val bos = ByteArrayOutputStream()
        if (imageSizeInKB > 600) {
            var quality = 100
            this.compress(Bitmap.CompressFormat.JPEG, quality, bos) // ignored for PNG
            while (bos.toByteArray().size / 1024 > 600) {
                bos.reset()
                compress(Bitmap.CompressFormat.JPEG, quality, bos)
                when {
                    quality == 0 -> break
                    quality > 15 -> quality -= 15
                    else -> break
                }
            }
            Log.d("ImageSize", "After compress -> " + getFileSize(bos.toByteArray().size.toLong()))
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

fun Uri.createBase64(context: Context): String {
    val file = getFile(context)
    val fileBase64 = (file?.createBase64() ?: "").log()
    file?.delete()
    return fileBase64
}

fun Uri.getFile(context: Context): File? {
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
    val fileName = getFileName(context, this)
    val ext = getExtensionFromFileName(fileName)
    return bitmap?.createFile(context, ext)
}

fun Uri.getBitmap(context: Context): Bitmap? {
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
    return bitmap
}

fun getFileName(context: Context, uri: Uri): String? {
    if (uri.scheme == "content") {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) {
                return cursor.getString(nameIndex)
            }
        }
    }
    return uri.path?.substringAfterLast('/')
}

fun Context.getFileExtension(uri: Uri?): String? {
    return uri?.let {
        contentResolver.getType(it)?.let { mimeType ->
            MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        } ?: uri.path?.substringAfterLast('.', missingDelimiterValue = "")
    }
}

fun getFileSize(size: Long): String {
    if (size <= 0) return "0"

    val units: Array<String> = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()

    return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
}

fun getExtensionFromFileName(name: String?): String? {
    return name?.substringAfterLast('.', "")
}

fun getExtensionFromMimeType(context: Context, uri: Uri): String? {
    val mime = context.contentResolver.getType(uri)
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(mime)
}

fun renderPdfPageAsBitmap(context: Context, uri: Uri, pageIndex: Int = 0): Bitmap? {
    return try {
        val fileDescriptor = context.contentResolver.openFileDescriptor(uri, "r") ?: return null
        val renderer = PdfRenderer(fileDescriptor)
        val page = renderer.openPage(pageIndex)

        val width = page.width
        val height = page.height
        val bitmap = createBitmap(width, height)
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
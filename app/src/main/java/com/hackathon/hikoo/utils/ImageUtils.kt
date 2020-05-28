package com.hackathon.hikoo.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.ThumbnailUtils
import com.orhanobut.logger.Logger
import java.io.IOException
import kotlin.math.max
import kotlin.math.min

object ImageUtils {
    @Throws(IOException::class)
    fun getOrientationFromExif(path: String): Int {
        return ExifInterface(path).getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
        )
    }

    fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap? {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }

        try {
            val rotatedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
            )
            bitmap.recycle()
            return rotatedBitmap
        } catch (oomException: OutOfMemoryError) {
            Logger.e(oomException, "rotateBitmap")
            return null
        }
    }

    fun getScaledBitmapFromFile(path: String, maxWidth: Int, maxHeight: Int, fitInside: Boolean): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)

        val height = options.outHeight
        val width = options.outWidth

        // Correct the mapping of the long side and the short side
        var reqHeight = maxHeight
        var reqWidth = maxWidth
        if ((width < height && maxWidth > maxHeight) || (width > height && maxWidth < maxHeight)) {
            reqWidth = maxHeight
            reqHeight = maxWidth
        }

        val widthFactor = width.toFloat() / reqWidth.toFloat()
        val heightFactor = height.toFloat() / reqHeight.toFloat()
        val outFactor = if (fitInside) {
            max(widthFactor, heightFactor)
        } else {
            min(widthFactor, heightFactor)
        }

        options.inSampleSize = outFactor.toInt()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        options.inJustDecodeBounds = false

        val stepOneBitmap = BitmapFactory.decodeFile(path, options)
        if (stepOneBitmap.width <= reqWidth && stepOneBitmap.height <= reqHeight) {
            return stepOneBitmap
        }

        val outWidth = (width.toFloat() / outFactor).toInt()
        val outHeight = (height.toFloat() / outFactor).toInt()
        return ThumbnailUtils.extractThumbnail(stepOneBitmap, outWidth, outHeight, ThumbnailUtils.OPTIONS_RECYCLE_INPUT)
    }
}
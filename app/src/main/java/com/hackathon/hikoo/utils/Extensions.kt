package com.hackathon.hikoo.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun <T : View> Activity.bindView(@IdRes resourceId: Int): Lazy<T> = lazy {
    findViewById<T>(resourceId)
}

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

fun createJPGImageFileMultipart(key: String, path: String): MultipartBody.Part? {
    if (path.isEmpty()) {
        return null
    }

    val file = File(path)
    val requestBody = file.asRequestBody("image/jpg".toMediaType())
    return MultipartBody.Part.createFormData(key, file.name, requestBody)
}
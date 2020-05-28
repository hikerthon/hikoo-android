package com.hackathon.hikoo.utils

import android.graphics.Bitmap
import androidx.collection.LruCache
import com.orhanobut.logger.Logger

class BitmapCacheHandler(cacheSizeInMb: Int = 0) {
    private val DEFAULT_SIZE = 8 * 1024 * 1024 // 8MB
    private val caches: LruCache<String, Bitmap>

    var logPostfixText: String = ""
        set(value) {
            field = "_$value"
        }

    init {
        val cacheMemoryAllocation = if (cacheSizeInMb > 0) {
            cacheSizeInMb * 1024 * 1024
        } else {
            DEFAULT_SIZE
        }

        caches = object : LruCache<String, Bitmap>(cacheMemoryAllocation) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.byteCount
            }
        }
    }

    fun addBitmap(key: String, bitmap: Bitmap) {
        Logger.i("BitmapCache$logPostfixText", "Add Bitmap with key $key")
        synchronized(caches) {
            if (containReference(key)) {
                caches.remove(key)
            }
            caches.put(key, bitmap)
        }
    }

    fun getBitmap(key: String): Bitmap? {
        synchronized(caches) {
            val result = caches.get(key) ?: return null
            if (result.isRecycled) {
                return null
            }
            return result
        }
    }

    fun contains(key: String): Boolean {
        synchronized(caches) {
            val bitmap = caches.get(key)
            return (bitmap != null && !bitmap.isRecycled)
        }
    }

    private fun containReference(key: String): Boolean {
        val bitmap = caches.get(key)
        return (bitmap != null)
    }
}
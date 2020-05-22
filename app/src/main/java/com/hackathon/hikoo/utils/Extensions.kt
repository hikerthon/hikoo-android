package com.hackathon.hikoo.utils

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

fun <T : View> Activity.bindView(@IdRes resourceId: Int): Lazy<T> = lazy {
    findViewById<T>(resourceId)
}
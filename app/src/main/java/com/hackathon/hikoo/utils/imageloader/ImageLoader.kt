package com.hackathon.hikoo.utils.imageloader

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoader {
    fun loadImage(context: Context, url: String?, targetView: ImageView, @DrawableRes placeHolder: Int = -1)

    fun loadCircleImage(context: Context, url: String?, targetView: ImageView, @DrawableRes placeHolder: Int = -1)

    fun loadCircleImage(url: String?, targetView: ImageView, @DrawableRes placeHolder: Int)
}
package com.hackathon.hikoo.utils.imageloader

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class ImageLoadToolImpl : AppGlideModule(), ImageLoadTool {

    lateinit var context: Context

    @SuppressLint("CheckResult")
    override fun loadImage(context: Context, url: String?, targetView: ImageView, placeHolder: Int) {
        val glideRequest = GlideApp.with(context)
            .load(url)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transition(DrawableTransitionOptions.withCrossFade())

        if (placeHolder != -1) {
            glideRequest.thumbnail(Glide.with(context).load(placeHolder).centerCrop())
        }

        glideRequest.into(targetView)
    }

    @SuppressLint("CheckResult")
    override fun loadCircleImage(context: Context, url: String?, targetView: ImageView, placeHolder: Int) {
        val glideRequest = GlideApp.with(context)
            .load(url)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .transition(DrawableTransitionOptions.withCrossFade())

        if (placeHolder != -1) {
            glideRequest.thumbnail(Glide.with(context).load(placeHolder).circleCrop())
        }

        glideRequest.circleCrop()
        glideRequest.into(targetView)
    }

    @SuppressLint("CheckResult") // ignore placeholder result
    override fun loadCircleImage(
        url: String?,
        targetView: ImageView,
        placeHolder: Int
    ) {
        val glideRequest = GlideApp.with(context)
            .load(url)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

        if (placeHolder != -1) {
            glideRequest.thumbnail(Glide.with(context).load(placeHolder).circleCrop())
        }

        glideRequest.transition(DrawableTransitionOptions.withCrossFade())
        glideRequest.circleCrop()
        glideRequest.into(targetView)
    }
}
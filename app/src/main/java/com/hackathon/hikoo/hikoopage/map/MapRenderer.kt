package com.hackathon.hikoo.hikoopage.map

import android.content.Context
import com.google.android.gms.maps.model.Circle

class MapRenderer(
    context: Context
) {

    private val DEFAULT_RADIUS: Int = 1000
    private val context = context.applicationContext

    private var lastVisibleRadius: Int = -1
    private var visibleCircle: Circle? = null

}
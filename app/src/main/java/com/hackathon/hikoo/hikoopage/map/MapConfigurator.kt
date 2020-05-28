package com.hackathon.hikoo.hikoopage.map

import android.content.Context
import androidx.annotation.RawRes
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.MapStyleOptions

class MapConfigurator constructor(
    val map: GoogleMap
) {

    var logPrefixText: String = ""
        set(value) {
            field = "_$value"
        }

//    init {
//        this.setupMapUi(map.uiSettings)
//    }

    fun applyCustomStyle(context: Context, @RawRes rawId: Int) {
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, rawId))
    }

//    abstract fun setupMapUi(mapUiSettings: UiSettings)
}
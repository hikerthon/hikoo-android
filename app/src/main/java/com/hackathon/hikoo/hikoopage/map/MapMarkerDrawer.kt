package com.hackathon.hikoo.hikoopage.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hackathon.hikoo.hikoopage.map.delegate.ShelterStyleDelegate
import com.hackathon.hikoo.utils.BitmapCacheHandler

class MapMarkerDrawer(private val mapConfigurator: MapConfigurator, cacheSize: Int = 0) {

    private val mMap: GoogleMap = mapConfigurator.map
    private val caches: BitmapCacheHandler = BitmapCacheHandler(cacheSize)
        .also {
            it.logPostfixText = javaClass.simpleName + mapConfigurator.logPrefixText
        }

//    @JvmOverloads
//    fun drawerMarker(
//        context: Context,
//        markerItem: MarkerItem,
//        shelterStyleDelegate: ShelterStyleDelegate? = null,
//        attachText: String? = null
//    ): Marker {
//        return drawer(context, markerItem, shelterStyleDelegate, attachText)
//    }

//    private fun drawer(
//        context: Context,
//        markerItem: MarkerItem,
//        shelterStyleDelegate: ShelterStyleDelegate? = null,
//        attachText: String? = null
//    ) : Marker {
//        val icon = drawIcon(context, markerItem, shelterStyleDelegate, attachText)
//        val markerOptions = MarkerOptions()
//            .position(markerItem.getLatLng())
//            .title(markerItem.getTitle())
//            .icon(icon)
//        val addedMarker = mMap.addMarker(markerOptions)
//        addedMarker.tag = markerItem
//
//        return addedMarker
//    }

//    private fun drawIcon(
//        context: Context,
//        markerItem: MarkerItem,
//        shelterStyleDelegate: ShelterStyleDelegate?,
//        attachText: String? = null
//    ): BitmapDescriptor? {
//        val iconBitmap = buildScooterIconBitmap(context, markerItem, shelterStyleDelegate, attachText)
//        return BitmapDescriptorFactory.fromBitmap(iconBitmap)
//    }


}
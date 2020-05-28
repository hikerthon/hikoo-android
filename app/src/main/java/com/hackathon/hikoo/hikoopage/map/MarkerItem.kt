package com.hackathon.hikoo.hikoopage.map

import com.google.android.gms.maps.model.LatLng

interface MarkerItem {
    fun getTitle(): String
    fun getLatLng(): LatLng
    fun getIcon(): Int
}
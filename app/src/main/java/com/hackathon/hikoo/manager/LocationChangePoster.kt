package com.hackathon.hikoo.manager

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import org.greenrobot.eventbus.EventBus

class LocationChangePoster {

    fun post(location: Location) {
        EventBus.getDefault().post(UserLocationChangeEvent(location, location.toLatLng()))
    }

    private fun Location.toLatLng(): LatLng {
        return LatLng(this.latitude, this.longitude)
    }

    class UserLocationChangeEvent(val location: Location, val latLng: LatLng)
}
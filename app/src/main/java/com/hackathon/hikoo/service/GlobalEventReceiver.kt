package com.hackathon.hikoo.service

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.hackathon.hikoo.manager.LocationChangePoster
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class GlobalEventReceiver(private var globalEventListener: GlobalEventListener?) {

    var isListening: Boolean = false

    init {
        EventBus.getDefault().register(this)
    }

    fun removeListener() {
        globalEventListener = null
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = -1)
    fun onUserLocationChanged(event: LocationChangePoster.UserLocationChangeEvent) {
        if (isListening) {
            globalEventListener?.onUserLocationChanged(event.location, event.latlng)
        }
    }

    interface GlobalEventListener {
        fun onUserLocationChanged(location: Location, latlng: LatLng)
    }
}
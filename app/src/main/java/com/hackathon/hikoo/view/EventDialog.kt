package com.hackathon.hikoo.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.R
import com.hackathon.hikoo.utils.bitmapDescriptorFromVector
import com.orhanobut.logger.Logger
import kotlin.math.ln

class EventDialog(context: Context): AppCompatDialog(context), OnMapReadyCallback {

    companion object {
        private const val DANGER = 3
        private const val CAUTION = 2
        private const val INFORMATION = 1
    }

    private lateinit var dialogAlertLevel: MaterialTextView
    private lateinit var dialogDescription: MaterialTextView
    private lateinit var dialogButton: MaterialButton

    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private var hikerLocation: Location? = null
    private var lat: Double = 0.0
    private var lng: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_dialog)

        dialogAlertLevel = findViewById(R.id.dialog_alert_level)!!
        mapView = findViewById(R.id.dialog_map_view)
        MapsInitializer.initialize(context)
        mapView?.onCreate(savedInstanceState)
        mapView?.onResume()
        mapView?.getMapAsync(this)
        dialogDescription = findViewById(R.id.dialog_description)!!
        dialogButton = findViewById(R.id.dialog_dismiss_button)!!


        dialogButton.setOnClickListener {
            dismiss()
            mapView = null
            hikerLocation = null
            lat = 0.0
            lng = 0.0
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun show() {
        super.show()
        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        setCancelable(false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        try {
            Logger.d("onMapReady = ${hikerLocation!!.latitude}, ${hikerLocation!!.longitude}")
            val userLocation = LatLng(hikerLocation!!.latitude, hikerLocation!!.longitude)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
            moveCameraToEachOther(hikerLocation!!, lat, lng)
        }  catch (e: NullPointerException) {
            e.printStackTrace()
        }

        googleMap.addMarker(MarkerOptions()
            .position(LatLng(hikerLocation!!.latitude, hikerLocation!!.longitude))
            .icon(bitmapDescriptorFromVector(context, R.drawable.ic_current_location)))
        googleMap.addMarker(MarkerOptions()
            .position(LatLng(lat, lng))
            .icon(bitmapDescriptorFromVector(context, R.drawable.ic_event_icon)))
    }

    private fun moveCameraToEachOther(hikerLocation: Location, lat: Double, lng: Double) {
        val boundsBuilder = LatLngBounds.builder()
        boundsBuilder.include(LatLng(hikerLocation.latitude, hikerLocation.longitude))
        boundsBuilder.include(LatLng(lat, lng))
        val bounds = boundsBuilder.build()
        val width = context.resources.displayMetrics.widthPixels
        val height = context.resources.displayMetrics.widthPixels - 300
        try {
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width - 100, height - 100, 0))
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun setMapMarker(hikerLocation: Location, lat: Double, lng: Double) {
        this.hikerLocation = hikerLocation
        this.lat = lat
        this.lng = lng
    }

    fun setAlertLevel(alertLevel: Int) {
        when (alertLevel) {
            INFORMATION -> {
                dialogAlertLevel.setTextColor(ContextCompat.getColor(context, R.color.eclipse_black))
                dialogAlertLevel.text = "Alert Level：Normal"
            }
            CAUTION -> {
                dialogAlertLevel.setTextColor(ContextCompat.getColor(context, R.color.eclipse_black))
                dialogAlertLevel.text = "Alert Level：Normal"
            }
            DANGER -> {
                dialogAlertLevel.setTextColor(ContextCompat.getColor(context, R.color.free_speech_red))
                dialogAlertLevel.text = "Alert Level：High"
            }
        }
    }

    fun setDescription(description: String) {
        dialogDescription.text = description
    }
}
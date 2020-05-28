package com.hackathon.hikoo.hikoopage

import android.content.Context
import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hackathon.hikoo.BasePresenter
import com.hackathon.hikoo.R
import com.hackathon.hikoo.manager.SharePreferenceManager
import com.hackathon.hikoo.manager.ShelterManager
import com.hackathon.hikoo.manager.UserLocationManager
import com.hackathon.hikoo.model.domain.Shelter
import com.hackathon.hikoo.network.APIManager
import com.hackathon.hikoo.network.APIManagerImpl
import com.hackathon.hikoo.utils.bitmapDescriptorFromVector
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
import com.orhanobut.logger.Logger
import io.reactivex.rxkotlin.subscribeBy

class HikooPresenter(
    private val userLocationManager: UserLocationManager,
    private val shelterManager: ShelterManager,
    private val imageLoadTool: ImageLoadTool,
    private val apiManager: APIManager,
    private val sharePreferenceManager: SharePreferenceManager
) : BasePresenter<HikooFragment>() {

    private var myLocation: Location? = null
    private var myGoogleMap: GoogleMap? = null
    private var myPosition: LatLng? = null

    private fun updateLocation(currentLocation: Location) {
        this.myLocation = currentLocation
    }

    fun getShelter(context: Context, googleMap: GoogleMap) {
        this.myGoogleMap = googleMap
        shelterManager.fetchShelter(object : ShelterManager.OnShelterListener {
            override fun onFetchShelterSuccess(shelter: List<Shelter>) {
                setMapMarker(shelter, context)
                view?.setupShelter(shelter, imageLoadTool)
            }

            override fun onFetchShelterFailed() {
            }
        })
    }

    private fun setMapMarker(shelter: List<Shelter>, context: Context) {
        view?.setupMyLocation(shelter[0])
        shelter.forEach {
            when (it.shelterType) {
                Shelter.ShelterType.MY_LOCATION -> {
                    myGoogleMap?.addMarker(MarkerOptions()
                        .position(LatLng(it.latpt, it.lngpt))
                        .icon(bitmapDescriptorFromVector(context, R.drawable.ic_current_location)))
                }
                Shelter.ShelterType.SHELTER -> {
                    myGoogleMap?.addMarker(MarkerOptions()
                        .title(it.shelterName)
                        .position(LatLng(it.latpt, it.lngpt))
                        .icon(bitmapDescriptorFromVector(context, R.drawable.ic_shelter_location)))?.showInfoWindow()
                }
                else -> {}
            }
        }
    }

    fun sendSOS() {
        val location = userLocationManager.currentLocation ?: run {
            sharePreferenceManager.getLastLocation()
        }
        apiManager.postSOS(location?.latitude, location?.longitude).subscribeBy(
            onNext = { response ->
                if (response.isSuccessful) {

                }
            },
            onError = {

            }
        ).autoClear()
    }
}
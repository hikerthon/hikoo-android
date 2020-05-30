package com.hackathon.hikoo.hikoopage

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

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
    private var isMapInit: Boolean = false



    fun initGoogleMap(googleMap: GoogleMap) {
        this.myGoogleMap = googleMap
        this.myGoogleMap?.isMyLocationEnabled = false
        isMapInit = true
    }

    private fun updateLocation(currentLocation: Location) {
        this.myLocation = currentLocation
        if (isMapInit && this.myLocation != null) {
            view?.setupMyLocation(currentLocation)
            getShelter()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun receiveLocation() {
        userLocationManager.locationObserver
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    updateLocation(it)
                },
                onError = {
                    Logger.e(Log.getStackTraceString(it))
                }
            ).autoClear()
    }

    private fun getShelter() {
        shelterManager.fetchShelter(object : ShelterManager.OnShelterListener {
            override fun onFetchShelterSuccess(shelter: List<Shelter>) {
                setMapMarker(shelter)
                view?.setupShelter(shelter, imageLoadTool)
            }

            override fun onFetchShelterFailed() {
            }
        })
    }

    private fun setMapMarker(shelter: List<Shelter>) {
        shelter.forEach {
            when (it.shelterType) {
                Shelter.ShelterType.MY_LOCATION -> {
                    view?.setupMyLocationMarker(it)
                }
                Shelter.ShelterType.SHELTER -> {
                    view?.setupShelterMarker(it)
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
                if (response.code() == 403) {
                    Logger.d("showSOSFailedReason = ${response.errorBody()}")
                    view?.showSOSFailedReason("User doesnt have active hiking permit")
                    return@subscribeBy
                }

                if (response.isSuccessful) {
                    view?.showSOSSuccess()
                }
            },
            onError = {
                view?.showSOSFailed()
                Logger.d(Log.getStackTraceString(it))
            }
        ).autoClear()
    }


}
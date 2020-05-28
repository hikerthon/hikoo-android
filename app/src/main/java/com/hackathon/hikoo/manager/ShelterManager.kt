package com.hackathon.hikoo.manager

import android.annotation.SuppressLint
import com.hackathon.hikoo.model.domain.Shelter
import com.hackathon.hikoo.network.APIManager
import io.reactivex.rxkotlin.subscribeBy

class ShelterManager(
    private val userLocationManager: UserLocationManager,
    private val apiManager: APIManager,
    private val sharePreferenceManager: SharePreferenceManager
) {

    var shelter: List<Shelter>? = null
        private set

    @SuppressLint("CheckResult")
    fun fetchShelter(listener: OnShelterListener?) {
        val location = userLocationManager.currentLocation ?: run {
            sharePreferenceManager.getLastLocation()
        }

        apiManager.getShelter(location?.latitude, location?.longitude).subscribeBy(
            onNext = { response ->
                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        val shelterList = mutableListOf<Shelter>()
                        shelterList.addAll(list)
                        shelterList.add(0 , Shelter().createUserShelter(location?.latitude, location?.longitude))
                        this.shelter = shelterList
                        listener?.onFetchShelterSuccess(shelterList)
                    }
                }
            },
            onError = {
                listener?.onFetchShelterFailed()
            }
        )
    }

    interface OnShelterListener {
        fun onFetchShelterSuccess(shelter: List<Shelter>)
        fun onFetchShelterFailed()
    }
}
package com.hackathon.hikoo.manager

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import androidx.core.content.edit
import com.hackathon.hikoo.model.domain.User
import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi

class SharePreferenceManager(
    context: Context,
    private val moshi: Moshi
) {

    private val PREFS_NAME = "com.hackathon.hikoo.preferences"
    private val FCM_TOKEN_KEY = "fcm-token"
    private val ACCESS_TOKEN_KEY = "access-token"
    private val REFRESH_TOKEN_KEY = "refresh-token"
    private val LOCATION_LATITUDE = "location-latitude"
    private val LOCATION_LONGITUDE = "location-longitude"
    private val HIKER_INFO_KEY = "hiker-info-key"

    private val sharedpreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveFcmToken(fcmToken: String) {
        sharedpreferences.edit {
            putString(FCM_TOKEN_KEY, fcmToken)
        }
    }

    fun getFcmToken(): String {
        return sharedpreferences.getString(FCM_TOKEN_KEY, "") ?: ""
    }

    fun saveAccessToken(token: String?) {
        sharedpreferences.edit().also {
            it.putString(ACCESS_TOKEN_KEY, token ?: "")
        }.apply()
    }

    fun getAccessToken(): String {
        return sharedpreferences.getString(ACCESS_TOKEN_KEY, "") ?: ""
    }

    fun saveRefreshToken(token: String?) {
        sharedpreferences.edit().also {
            it.putString(REFRESH_TOKEN_KEY, token ?: "")
        }.apply()
    }

    fun getRefreshToken(): String {
        return sharedpreferences.getString(REFRESH_TOKEN_KEY, "") ?: ""
    }



    private fun SharedPreferences.remove(key: String): Boolean {
        if (sharedpreferences.contains(key)) {
            val editor = sharedpreferences.edit()
            editor.remove(key)
            return editor.commit()
        }
        return false
    }

    fun updateLocation(location: Location) {
        sharedpreferences.edit {
            putFloat(LOCATION_LATITUDE, location.latitude.toFloat())
            putFloat(LOCATION_LONGITUDE, location.longitude.toFloat())
        }
    }

    fun getLastLocation(): Location? {
        val latitude = sharedpreferences.getFloat(LOCATION_LATITUDE, 0.0f)
        val longitude = sharedpreferences.getFloat(LOCATION_LONGITUDE, 0.0f)
        return if (latitude == 0.0f && longitude == 0.0f) {
            null
        } else {
            Location(LocationManager.GPS_PROVIDER).also {
                it.latitude = latitude.toDouble()
                it.longitude = longitude.toDouble()
            }
        }
    }

    fun saveHiker(hiker: User?) {
        val jsonAdapter = moshi.adapter(User::class.java)
        val jsonString = jsonAdapter.toJson(hiker) ?: ""

        sharedpreferences.edit {
            putString(HIKER_INFO_KEY, jsonString)
        }
    }

    fun getHiker(): User? {
        val jsonString = sharedpreferences.getString(HIKER_INFO_KEY, "") ?: ""
        if (jsonString.isNotBlank()) {
            val jsonAdapter = moshi.adapter(User::class.java)
            return jsonAdapter.fromJson(jsonString)
        }
        return null
    }
}
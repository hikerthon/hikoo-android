package com.hackathon.hikoo.manager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.*
import android.provider.Settings
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class UserLocationManager(
    private val context: Context,
    private val sharePreferenceManager: SharePreferenceManager
) {

    enum class LocationManagerState {
        INITIALIZED,
        PREPARE,
        CHECK_LAST_LOCATION,
        PREPARE_TO_LISTEN_NEW_LOCATION,
        LISTENING_NEW_LOCATION,
        PAUSED,
        STOP,
        SETTING_RESOLUTION,
        SETTING_UNAVAILABLE,
        PERMISSION_ERROR
    }

    private enum class ProvideType(val rawValue: String) {
        GPS("gps"),
        NETWORK("network"),
        PASSIVE("passive"),
        LOW_POWER_GPS("low_power_gps"),
        UNKNOWN("")
    }

    private enum class LocationMode {
        OFF,
        HIGH_ACCURACY,
        BATTERY_SAVING,
        SENSORS_ONLY
    }

    var state: LocationManagerState = LocationManagerState.INITIALIZED
        private set

    private val UPDATE_INTERVAL = 15
    private val FASTEST_UPDATE_INTERVAL = 10
    private val CELLULAR_UPDATE_INTERVAL = 30
    private val CELLULAR_FASTEST_UPDATE_INTERVAL = 20
    private val LOW_POWER_UPDATE_INTERVAL = 5
    private var backgroundThread: HandlerThread? = null
    private var uiHandler: Handler? = null

    private val highAccuracyRequest by lazy {
        LocationRequest().apply {
            interval = TimeUnit.SECONDS.toMillis(UPDATE_INTERVAL.toLong())
            fastestInterval = TimeUnit.SECONDS.toMillis(FASTEST_UPDATE_INTERVAL.toLong())
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val balancedRequest by lazy {
        LocationRequest().apply {
            interval = TimeUnit.SECONDS.toMillis(CELLULAR_UPDATE_INTERVAL.toLong())
            fastestInterval = TimeUnit.SECONDS.toMillis(CELLULAR_FASTEST_UPDATE_INTERVAL.toLong())
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }

    private val lowPowerRequest by lazy {
        LocationRequest().apply {
            interval = TimeUnit.SECONDS.toMillis(LOW_POWER_UPDATE_INTERVAL.toLong())
            smallestDisplacement = 10f
            priority = LocationRequest.PRIORITY_LOW_POWER
        }
    }

    private val passiveRequest by lazy {
        LocationRequest().apply {
            interval = TimeUnit.SECONDS.toMillis(LOW_POWER_UPDATE_INTERVAL.toLong())
            priority = LocationRequest.PRIORITY_NO_POWER
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            result?.run {
                val latestLocation = locations.last()
                this@UserLocationManager.currentLocation = latestLocation
            }
        }
    }

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationChangePoster = LocationChangePoster()

    val locationObserver: BehaviorSubject<Location> = BehaviorSubject.create()
    var currentLocation: Location? = null
        private set(value) {
            field = value
            if (value != null) {
                sharePreferenceManager.updateLocation(value)
            }
        }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun startLocationUpdate(failedCallback: OnLocationSettingFailedCallback) {
        state = LocationManagerState.PREPARE
        backgroundThread = HandlerThread("BackgroundThread").apply { start() }
        uiHandler = Handler(Looper.getMainLooper())

        val locationMode = getLocationMode()
        if (locationMode == LocationMode.OFF) {
            failedCallback.onResolutionManually()
            return
        }

        val providerType = getBestProvider()
        getLastKnownLocation(providerType, failedCallback)
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun getLastKnownLocation(providerType: ProvideType, failedCallback: OnLocationSettingFailedCallback) {
        if (isLocationPermissionGranted()) {
            state = LocationManagerState.CHECK_LAST_LOCATION
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val lastLocation = location ?: run {
                    sharePreferenceManager.getLastLocation()
                }

                lastLocation?.let {
                    this@UserLocationManager.currentLocation = it
                    locationObserver.onNext(it)
                    locationChangePoster.post(it)
                }
                prepareListenAccurateLocation(providerType, failedCallback)
            }.addOnFailureListener { exception ->
                sharePreferenceManager.getLastLocation()?.let {
                    currentLocation = it
                    locationObserver.onNext(it)
                }
                prepareListenAccurateLocation(providerType, failedCallback)
            }
        } else {
            state = LocationManagerState.PERMISSION_ERROR
            failedCallback.onLocationPermissionRequired()
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun prepareListenAccurateLocation(providerType: ProvideType, failedCallback: OnLocationSettingFailedCallback) {
        if (isLocationPermissionGranted()) {
            state = LocationManagerState.PREPARE_TO_LISTEN_NEW_LOCATION
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(providerType.getLocationRequest() ?: highAccuracyRequest)
                .setAlwaysShow(false)

            val result = LocationServices.getSettingsClient(context)
                .checkLocationSettings(builder.build())

            result.addOnCompleteListener { task ->
                try {
                    task.getResult<ApiException>(ApiException::class.java)
                    startListenLocationUpdate(providerType, failedCallback)
                } catch (apiException: ApiException) {
                    when (apiException.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            state = LocationManagerState.SETTING_RESOLUTION
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                val resolvable = apiException as ResolvableApiException
                                failedCallback.onResolutionRequired(resolvable)
                            } else {
                                failedCallback.onResolutionManually()
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            state = LocationManagerState.SETTING_UNAVAILABLE
                            failedCallback.onSettingChangeUnavailable()
                        }
                    }
                }

            }
        } else {
            state = LocationManagerState.PERMISSION_ERROR
            failedCallback.onLocationPermissionRequired()
        }
    }

    fun pauseLocationUpdate() {
        state = LocationManagerState.PAUSED
        backgroundThread?.quitSafely()
        backgroundThread?.join()
        backgroundThread = null
        uiHandler = null
        stopLocationUpdate()
    }

    private fun stopLocationUpdate() {
        state = LocationManagerState.STOP
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun startListenLocationUpdate(providerType: ProvideType, failedCallback: OnLocationSettingFailedCallback) {
        if (isLocationPermissionGranted()) {
            state = LocationManagerState.LISTENING_NEW_LOCATION
            if (providerType != ProvideType.UNKNOWN) {
                fusedLocationClient.requestLocationUpdates(providerType.getLocationRequest(), locationCallback, backgroundThread?.looper)
            } else {
                fusedLocationClient.requestLocationUpdates(highAccuracyRequest, locationCallback, backgroundThread?.looper)
            }
        } else {
            state = LocationManagerState.PERMISSION_ERROR
            failedCallback.onLocationPermissionRequired()
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun requestLocationUpdateWithAvailableProvider(failedCallback: OnLocationSettingFailedCallback) {
        val locationMode = getLocationMode()
        if (locationMode == LocationMode.OFF) {
            failedCallback.onResolutionManually()
            return
        }
        prepareListenAccurateLocation(getBestProvider(), failedCallback)
    }

    fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun isPreferNetworkProvider(): Boolean {
        val locationMode = getLocationMode()
        return  locationMode == LocationMode.BATTERY_SAVING || isBatterySaveModeEnable()
    }

    private fun isSensorsOnlyLocationMode(): Boolean {
        return getLocationMode() == LocationMode.SENSORS_ONLY
    }

    fun isRunning(): Boolean {
        return when (state) {
            LocationManagerState.INITIALIZED,
            LocationManagerState.PAUSED,
            LocationManagerState.STOP,
            LocationManagerState.SETTING_UNAVAILABLE,
            LocationManagerState.PERMISSION_ERROR -> false
            else -> true
        }
    }

    private fun getBestProvider(): ProvideType {
        if (isSensorsOnlyLocationMode()) {
            return ProvideType.PASSIVE
        }

        if (isPreferNetworkProvider()) {
            return ProvideType.NETWORK
        }

        val criteria = Criteria()
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return when (locationManager.getBestProvider(criteria, true)) {
            "passive" -> ProvideType.PASSIVE
            "gps" -> ProvideType.GPS
            "network" -> ProvideType.NETWORK
            else -> ProvideType.UNKNOWN
        }
    }

    private fun getLocationMode(): LocationMode {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!locationManager.isLocationEnabled) {
                return LocationMode.OFF
            } else {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER)) {
                    return LocationMode.HIGH_ACCURACY
                }

                if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    return LocationMode.BATTERY_SAVING
                }

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    return LocationMode.SENSORS_ONLY
                }
            }
        } else {
            val locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
            return when (locationMode) {
                0 -> LocationMode.OFF
                1 -> LocationMode.SENSORS_ONLY
                2 -> LocationMode.BATTERY_SAVING
                3 -> LocationMode.HIGH_ACCURACY
                else -> LocationMode.OFF
            }
        }

        return LocationMode.OFF
    }

    private fun isBatterySaveModeEnable(): Boolean {
        return (context.getSystemService(Context.POWER_SERVICE) as? PowerManager)?.isPowerSaveMode ?: false
    }

    private fun ProvideType.getLocationRequest(): LocationRequest? {
        return when (this) {
            ProvideType.GPS -> highAccuracyRequest
            ProvideType.NETWORK -> balancedRequest
            ProvideType.PASSIVE -> passiveRequest
            ProvideType.LOW_POWER_GPS -> lowPowerRequest
            ProvideType.UNKNOWN -> null
        }
    }

    interface OnLocationSettingFailedCallback {
        fun onLocationPermissionRequired()
        fun onResolutionRequired(resolvable: ResolvableApiException)
        fun onResolutionManually()
        fun onSettingChangeUnavailable()
    }
}
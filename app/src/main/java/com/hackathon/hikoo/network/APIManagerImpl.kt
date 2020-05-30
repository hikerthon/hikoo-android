package com.hackathon.hikoo.network

import android.location.Location
import android.os.SystemClock
import com.hackathon.hikoo.model.domain.*
import com.hackathon.hikoo.model.entity.RequestResult
import com.hackathon.hikoo.utils.createJPGImageFileMultipart
import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response
import java.util.*

class APIManagerImpl(
    httpClient: HttpClient,
    moshi: Moshi
) : APIManager(httpClient, moshi) {

    override fun getUserProfile(): Observable<Response<User>> {
        return hikooService.getUserProfile().observeOn(AndroidSchedulers.mainThread())
    }

    override fun putUser(user: User): Observable<Response<RequestResult>> {
        return hikooService.putUser(user).observeOn(AndroidSchedulers.mainThread())
    }

    override fun postUser(user: User): Observable<Response<RequestResult>> {
        return hikooService.putUser(user).observeOn(AndroidSchedulers.mainThread())
    }

    override fun postLocation(
        mountainPermit: MountainPermit,
        location: Location
    ): Observable<Triple<Boolean, Locations, Error?>> {
        val c = Calendar.getInstance()
        val body = JSONObject().apply {
            put("hikerId", mountainPermit.hikerId)
            put("hikeId", mountainPermit.id)
            put("latpt", location.latitude)
            put("lngpt", location.longitude)
            put("recordTime", c.timeInMillis)
            put("elevation", 2000)
            put("battery", 80)
            put("network", -60)
            put("elapsedTime", c.timeInMillis)
        }.toString().run {
            this.toRequestBody(HttpClient.JSON_MEDIA_TYPE)
        }

        return hikooService.postLocation(body).flatMap { responseBody ->
            val jsonString = responseBody.string()
            val jsonAdapter = moshi.adapter(Locations::class.java)
            val locations = jsonAdapter.fromJson(jsonString)!!
            Observable.just(Triple<Boolean, Locations, Error?>(true, locations, null))
        }
    }

    override fun postSOS(lat: Double?, lng: Double?): Observable<Response<RequestResult>> {
        val body = JSONObject().apply {
            put("latpt", lat ?: 23.466305)
            put("lngpt", lng ?: 120.949836)
        }.toString().run {
            this.toRequestBody(HttpClient.JSON_MEDIA_TYPE)
        }
        return hikooService.postSOS(body).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getShelter(lat: Double?, lng: Double?): Observable<Response<List<Shelter>>> {
        return hikooService.getShelter(lat ?: 23.466305, lng ?: 120.949836).observeOn(AndroidSchedulers.mainThread())
    }

    override fun postUploadImage(image: String): Observable<Response<RequestResult>> {
        val body = createJPGImageFileMultipart("file", image)
        return hikooService.postUploadImage(body!!).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getEvent(): Observable<Response<List<Event>>> {
        return hikooService.getEvent().observeOn(AndroidSchedulers.mainThread())
    }

    override fun postEvent(event: EventReport): Observable<Response<RequestResult>> {
        return hikooService.postEvent(event).observeOn(AndroidSchedulers.mainThread())
    }

    override fun postLogin(email: String, password: String): Observable<Response<LoginInfo>> {
        val body = JSONObject().apply {
            put("email", email)
            put("password", password)
        }.toString().run {
            this.toRequestBody(HttpClient.JSON_MEDIA_TYPE)
        }

        return hikooService.postLogin(body).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAlert(): Observable<Response<List<Alert>>> {
        return hikooService.getAlert().observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPermitById(permitId: Int, type: String): Observable<Response<MountainPermit>> {
        return hikooService.getPermitById(permitId, type).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPermit(type: String): Observable<Response<List<MountainPermit>>> {
        return hikooService.getPermit(type).observeOn(AndroidSchedulers.mainThread())
    }

    override fun postCheckOut(userId: Int): Observable<Response<RequestResult>> {
        return hikooService.postCheckOut(userId).observeOn(AndroidSchedulers.mainThread())
    }
}
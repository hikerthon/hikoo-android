package com.hackathon.hikoo.network

import com.hackathon.hikoo.model.domain.*
import com.hackathon.hikoo.model.entity.Location
import com.hackathon.hikoo.model.entity.RequestResult
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response

class APIManagerImpl(
    httpClient: HttpClient,
    moshi: Moshi
) : APIManager(httpClient, moshi) {

    override fun getPermitByUser(userId: Int, type: String): Observable<Response<Permit>> {
        return hikooService.getPermitByUser(userId, type).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPermit(userId: Int, permitId: Int, type: String): Observable<Response<Permit>> {
        return hikooService.getPermit(userId, permitId, type).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAlert(): Observable<Response<List<Alert>>> {
        return hikooService.getAlert().observeOn(AndroidSchedulers.mainThread())
    }

    override fun getEvent(userId: Int): Observable<Response<List<Event>>> {
        return hikooService.getEvent(userId).observeOn(AndroidSchedulers.mainThread())
    }

    override fun postEvent(userId: Int, event: Event): Observable<Response<RequestResult>> {
        return hikooService.postEvent(userId, event).observeOn(AndroidSchedulers.mainThread())
    }

    override fun postLocation(userId: Int, lat: Double, lng: Double): Observable<Response<Location>> {
        val body = JSONObject().apply {
            put("userId", userId)
            put("lat", lat)
            put("lng", lng)
        }.toString().run {
            this.toRequestBody(HttpClient.JSON_MEDIA_TYPE)
        }

        return hikooService.postLocation(body).observeOn(AndroidSchedulers.mainThread())
    }

    override fun postSOS(
        userId: Int,
        lat: Double,
        lng: Double
    ): Observable<Response<RequestResult>> {
        val body = JSONObject().apply {
            put("userId", userId)
            put("lat", lat)
            put("lng", lng)
        }.toString().run {
            this.toRequestBody(HttpClient.JSON_MEDIA_TYPE)
        }
        return hikooService.postSOS(body).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUser(userId: Int): Observable<Response<User>> {
        return hikooService.getUser(userId).observeOn(AndroidSchedulers.mainThread())
    }

    override fun putUser(userId: Int, user: User): Observable<Response<User>> {
        return hikooService.putUser(userId, user).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getShelter(userId: Int): Observable<Response<List<Shelter>>> {
        return hikooService.getShelter(userId).observeOn(AndroidSchedulers.mainThread())
    }
}
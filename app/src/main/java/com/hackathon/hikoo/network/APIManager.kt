package com.hackathon.hikoo.network

import android.location.Location
import com.hackathon.hikoo.model.domain.*
import com.hackathon.hikoo.model.entity.RequestResult
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class APIManager(
    private val httpClient: HttpClient,
    val moshi: Moshi
) {
    companion object {
        private const val BASE_URL = "http://18.177.114.248:3001"
//        private const val BASE_URL = "http://192.168.11.59:3001"
//        private const val BASE_URL = "http://192.168.11.41:3001"
    }

    protected lateinit var hikooService: HikooService

    fun setHeaderToken(token: String?, refreshToken: String?) {
        val accessToken = token ?: ""
        val userRefreshToken = refreshToken ?: ""
        httpClient.setWeMoAccessToken(accessToken, userRefreshToken)
    }

    fun createArdoVenationService(): HikooService {
        val client = httpClient.createHttpClient()


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

        val serviceCreate = retrofit.create(HikooService::class.java)

        this.hikooService = serviceCreate

        return serviceCreate
    }

    abstract fun getAlert(): Observable<Response<List<Alert>>>
    abstract fun postLogin(email: String, password: String): Observable<Response<LoginInfo>>
    abstract fun getUserProfile(): Observable<Response<User>>
    abstract fun putUser(user: User): Observable<Response<RequestResult>>
    abstract fun postUser(user: User): Observable<Response<RequestResult>>
    abstract fun getEvent(): Observable<Response<List<Event>>>
    abstract fun postEvent(event: EventReport): Observable<Response<RequestResult>>
    abstract fun postUploadImage(image: String): Observable<Response<RequestResult>>
    abstract fun postLocation(mountainPermit: MountainPermit, location: Location): Observable<Triple<Boolean, Locations, Error?>>
    abstract fun postSOS(lat: Double?, lng: Double?): Observable<Response<RequestResult>>
    abstract fun getShelter(lat: Double?, lng: Double?): Observable<Response<List<Shelter>>>
    abstract fun getPermitById(permitId: Int, type: String): Observable<Response<MountainPermit>>
    abstract fun getPermit(type: String = "All"): Observable<Response<List<MountainPermit>>>
    abstract fun postCheckOut(hikeId: Int): Observable<Response<RequestResult>>

}
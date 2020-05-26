package com.hackathon.hikoo.network

import com.hackathon.hikoo.model.domain.*
import com.hackathon.hikoo.model.entity.Location
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
    private val moshi: Moshi
) {
    companion object {
        private const val BASE_URL = "http://18.177.114.248:3001"
    }

    protected lateinit var hikooService: HikooService

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

    abstract fun getPermitByUser(userId: Int, type: String): Observable<Response<Permit>>
    abstract fun getPermit(userId: Int, permitId: Int, type: String): Observable<Response<Permit>>
    abstract fun getAlert(): Observable<Response<List<Alert>>>
    abstract fun getEvent(userId: Int): Observable<Response<List<Event>>>
    abstract fun postEvent(userId: Int, event: Event): Observable<Response<RequestResult>>
    abstract fun postLocation(userId: Int, lat: Double, lng: Double): Observable<Response<Location>>
    abstract fun postSOS(userId: Int, lat: Double, lng: Double): Observable<Response<RequestResult>>
    abstract fun getUser(userId: Int): Observable<Response<User>>
    abstract fun putUser(userId: Int, user: User): Observable<Response<User>>
    abstract fun getShelter(userId: Int): Observable<Response<List<Shelter>>>

}
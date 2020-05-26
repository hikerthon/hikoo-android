package com.hackathon.hikoo.network

import com.hackathon.hikoo.model.domain.*
import com.hackathon.hikoo.model.entity.Location
import com.hackathon.hikoo.model.entity.RequestResult
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface HikooService {

    @GET("/user/{userId}/permit")
    fun getPermitByUser(@Path("userId") userId: Int, @Query("type") type: String): Observable<Response<Permit>>

    @GET("/user/{userId}/permit/{permitId}")
    fun getPermit(@Path("userId") userId: Int, @Path("permitId") permitId: Int, @Query("type") type: String): Observable<Response<Permit>>

    @GET("/alert")
    fun getAlert(): Observable<Response<List<Alert>>>

    @GET("/user/{userId}/event")
    fun getEvent(@Path("userId") userId: Int): Observable<Response<List<Event>>>

    @POST("/user/{userId}/event")
    fun postEvent(@Path("userId") userId: Int, @Body event: Event): Observable<Response<RequestResult>>

    @POST("/location")
    fun postLocation(@Body requestBody: RequestBody): Observable<Response<Location>>

    @POST("/sos")
    fun postSOS(@Body requestBody: RequestBody): Observable<Response<RequestResult>>

    @GET("/user/{userId}")
    fun getUser(@Path("userId") userId: Int): Observable<Response<User>>

    @PUT("/user/{userId}")
    fun putUser(@Path("userId") userId: Int, @Body user: User): Observable<Response<User>>

    @GET("/shelter")
    fun getShelter(@Query("userId") userId: Int): Observable<Response<List<Shelter>>>

}
package com.hackathon.hikoo.network

import com.hackathon.hikoo.model.domain.*
import com.hackathon.hikoo.model.entity.RequestResult
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface HikooService {

    @GET("/alert")
    fun getAlert(): Observable<Response<List<Alert>>>

    @POST("/auth/login")
    fun postLogin(@Body requestBody: RequestBody): Observable<Response<LoginInfo>>

    @GET("/user/profile")
    fun getUserProfile(): Observable<Response<User>>

    @PUT("/user")
    fun putUser(@Body user: User): Observable<Response<RequestResult>>

    @POST("/user")
    fun postUser(@Body user: User): Observable<Response<RequestResult>>

    @GET("/event")
    fun getEvent(): Observable<Response<List<Event>>>

    @POST("/event")
    fun postEvent(@Body event: EventReport): Observable<Response<RequestResult>>

    @Multipart
    @POST("/event/uploadImage")
    fun postUploadImage(@Part images: MultipartBody.Part): Observable<Response<RequestResult>>

    @POST("/location")
    fun postLocation(@Body requestBody: RequestBody): Observable<ResponseBody>

    @POST("/sos")
    fun postSOS(@Body requestBody: RequestBody): Observable<Response<RequestResult>>

    @GET("/shelter")
    fun getShelter(@Query("latpt") lat: Double, @Query("lngpt") lng: Double): Observable<Response<List<Shelter>>>

    @GET("/permit")
    fun getPermit(@Query("type") type: String): Observable<Response<List<MountainPermit>>>

    @GET("/permit/{permitId}")
    fun getPermitById(@Query("permitId") permitId: Int, @Query("type") type: String): Observable<Response<MountainPermit>>

}
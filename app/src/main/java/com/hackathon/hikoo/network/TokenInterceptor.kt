package com.hackathon.hikoo.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {

    private var accessToken: String = ""

    fun setAccessToken(token: String?) {
        this.accessToken = token?.let {
            if (it.isNotBlank()) {
                "Bearer $it"
            } else {
                ""
            }
        } ?: ""
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val host = originalRequest.url.host

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", accessToken)
            .build()
        return chain.proceed(newRequest)
    }
}
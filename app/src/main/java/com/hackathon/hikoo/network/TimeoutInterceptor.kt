package com.hackathon.hikoo.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class TimeoutInterceptor : Interceptor {

    companion object {
        @JvmField
        val KEY_CONNECT_TIMEOUT = "CONNECT_TIMEOUT"
        @JvmField
        val KEY_READ_TIMEOUT = "READ_TIMEOUT"
        @JvmField
        val KEY_WRITE_TIMEOUT = "WRITE_TIMEOUT"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var connectTimeout = chain.connectTimeoutMillis()
        var readTimeout = chain.readTimeoutMillis()
        var writeTimeout = chain.writeTimeoutMillis()

        val connectTimeoutNewValue = request.header(KEY_CONNECT_TIMEOUT)
        if (connectTimeoutNewValue != null && connectTimeoutNewValue.isNotBlank()) {
            connectTimeout = connectTimeoutNewValue.toInt()
        }

        val readTimeoutNewValue = request.header(KEY_READ_TIMEOUT)
        if (readTimeoutNewValue != null && readTimeoutNewValue.isNotBlank()) {
            readTimeout = readTimeoutNewValue.toInt()
        }

        val writeTimeoutNewValue = request.header(KEY_WRITE_TIMEOUT)
        if (writeTimeoutNewValue != null && writeTimeoutNewValue.isNotBlank()) {
            writeTimeout = writeTimeoutNewValue.toInt()
        }

        return chain
            .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
            .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
            .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
            .proceed(request)
    }
}
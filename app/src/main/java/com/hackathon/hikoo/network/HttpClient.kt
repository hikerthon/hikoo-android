package com.hackathon.hikoo.network

import com.hackathon.hikoo.BuildConfig
import com.hackathon.hikoo.model.domain.LoginInfo
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class HttpClient(
    private val tokenInterceptor: TokenInterceptor
) {

    companion object {
        @JvmField
        val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaTypeOrNull()
    }

    private val cacheSizeByte = 1024 * 1024 * 5 // 5MB
    private val timeout: Long = 30
    private val maxRetryAuthenticateTimes = 3

    private val client: OkHttpClient = createHttpClient()

    var accessToken: String? = null
        private set
    var refreshToken: String? = null
        private set

    fun createHttpClient(): OkHttpClient {

        val httpClientBuilder = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .callTimeout(timeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        httpClientBuilder.addNetworkInterceptor(tokenInterceptor)

        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(OkHttpProfilerInterceptor())
            httpClientBuilder.addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        return httpClientBuilder.build()
    }

    fun setWeMoAccessToken(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        tokenInterceptor.setAccessToken(accessToken)
    }

    fun cancelConnections() {
        client.dispatcher.cancelAll()
    }

    class TokenRefreshedEvent(val loginInfo: LoginInfo)
    class TokenRefreshFailedEvent
}
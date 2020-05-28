package com.hackathon.hikoo.network

import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object NetworkCheck {

    private const val TEST_URL = "https://www.google.com"

    fun checkInternetConnectivity(): Single<Boolean> {
        return Single.create(SingleOnSubscribe<Boolean> { emitter ->
            try {
                val isNetworkConnected = hasNetworkConnection()
                emitter.onSuccess(isNetworkConnected)
            } catch (exception: SocketTimeoutException) {
                emitter.onSuccess(false)
            } catch (exception: IOException) {
                emitter.onSuccess(false)
            } catch (exception: IllegalStateException) {
                emitter.onSuccess(false)
            } catch (exception: Exception) {
                emitter.onError(exception)
            }
        })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    @Throws(SocketTimeoutException::class, IOException:: class, IllegalStateException:: class)
    private fun hasNetworkConnection(): Boolean {
        try {
            val url = URL(TEST_URL)
            val urlConnection = url.openConnection() as HttpsURLConnection
            with(urlConnection) {
                setRequestProperty("User-Agent", "Android Application:1")
                setRequestProperty("Connection", "close")
                connectTimeout = 1000 * 30 // 30 Sec
            }
            urlConnection.connect()

            val code = urlConnection.responseCode
            if ((code in 200..299) || (code > 400)) {
                return true
            }
        } catch (exception: SocketTimeoutException) {
            throw exception
        } catch (exception: IOException) {
            throw exception
        } catch (exception: IllegalStateException) {
            throw exception
        }
        return false
    }
}
package com.hackathon.hikoo.manager

import android.annotation.SuppressLint
import android.util.Log
import com.hackathon.hikoo.model.domain.MountainPermit
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.network.APIManager
import com.hackathon.hikoo.network.HttpClient
import com.orhanobut.logger.Logger
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import okhttp3.internal.applyConnectionSpec
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class UserManager(
    private val apiManager: APIManager,
    private val sharePreferenceManager: SharePreferenceManager
) {
    var accessToken: String = ""
        private set(value) {
            field = value
            sharePreferenceManager.saveAccessToken(value)
        }
        get() {
            return if (field.isNotBlank()) {
                field
            } else {
                sharePreferenceManager.getAccessToken()
            }
        }

    private var refreshToken: String = ""
        set(value) {
            field = value
            sharePreferenceManager.saveRefreshToken(value)
        }
        get() {
            return if (field.isNotBlank()) {
                field
            } else {
                sharePreferenceManager.getRefreshToken()
            }
        }

    var hikerUser: User? = null
        private set(value) {
            field = value
            sharePreferenceManager.saveHiker(value)
        }

    var mountainPermit: MountainPermit? = null

    init {
        EventBus.getDefault().register(this)
        if (isUserLogin()) {
            apiManager.setHeaderToken(accessToken, refreshToken)
            accessToken = sharePreferenceManager.getAccessToken()
            refreshToken = sharePreferenceManager.getRefreshToken()
            hikerUser = sharePreferenceManager.getHiker()
        }
    }

    @SuppressLint("CheckResult")
    fun login(email: String, password: String, listener: OnUserLoginListener?) {
        apiManager.postLogin(email, password).subscribeBy(
            onNext = { response ->
                if (response.isSuccessful) {
                    response.body()?.let { loginInfo ->
                        this.accessToken = loginInfo.accessToken
                        this.refreshToken = loginInfo.refreshToken
                        apiManager.setHeaderToken(loginInfo.accessToken, loginInfo.refreshToken)
                        fetchUserProfile(null)
                        listener?.onUserLoginSuccess(loginInfo.accessToken)
                        return@let
                    }
                } else {
                    listener?.onUserLoginFailed()
                }
            },
            onError = {
                listener?.onUserLoginFailed()
            }
        )
    }

    @SuppressLint("CheckResult")
    fun fetchUserProfile(listener: OnUserProfileListener?) {
        apiManager.getUserProfile().subscribeBy(
            onNext = { response ->
                if (response.isSuccessful) {
                    response.body()?.let { user ->
                        this.hikerUser = user
                        fetchPermit()
                        updateFcmToken(sharePreferenceManager.getFcmToken())
                        listener?.onFetchUserProfileSuccess(user)
                    }
                }
            },
            onError = {
                listener?.onFetchUserProfileFailed()
            }
        )
    }

    @SuppressLint("CheckResult")
    fun updateUserProfile(user: User, listener: OnUserProfileUpdateListener?) {
        apiManager.putUser(user).subscribeBy(
            onNext = { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.isSuccess) {
                            this.hikerUser = user
                            listener?.onUpdateUserProfileSuccess()
                        } else {
                            listener?.onUpdateUserProfileFailed()
                        }
                    }
                }
            },
            onError = {
                listener?.onUpdateUserProfileFailed()
            }
        )
    }

    @SuppressLint("CheckResult")
    fun fetchPermit() {
        apiManager.getPermit().subscribeBy(
            onNext = { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        this.mountainPermit = it.last()
                    }
                }
            },
            onError = {
                Logger.d(Log.getStackTraceString(it))
            }
        )
    }

    private fun updateFcmToken(token: String) {
        hikerUser?.fcmToken = token
        hikerUser?.emergencyMobile = hikerUser?.mobileNumber
        hikerUser?.let {
            apiManager.putUser(it).subscribeBy(
                onNext = { response ->
                    if (response.isSuccessful) {
                        Logger.d("updateFcmToken is successful")
                    }
                },
                onError = {
                    Logger.d(Log.getStackTraceString(it))
                }
            )
        }
    }

    fun logoutUser() {
        accessToken = ""
        refreshToken = ""
        hikerUser = null
        apiManager.setHeaderToken(null, null)
    }

    fun isUserLogin(): Boolean = sharePreferenceManager.getAccessToken().isNotBlank()

    interface OnUserLoginListener {
        fun onUserLoginSuccess(token: String)
        fun onUserLoginFailed()
    }

    interface OnUserProfileListener {
        fun onFetchUserProfileSuccess(user: User)
        fun onFetchUserProfileFailed()
    }

    interface OnUserProfileUpdateListener {
        fun onUpdateUserProfileSuccess()
        fun onUpdateUserProfileFailed()
    }

    @Subscribe
    fun onTokenRefreshed(event: HttpClient.TokenRefreshedEvent) {
        event.loginInfo.let {
            accessToken = it.accessToken
            refreshToken = it.refreshToken
        }
    }

    @Subscribe
    fun onTokenRefreshFailed(event: HttpClient.TokenRefreshFailedEvent) {
//        logoutUser()
    }
}
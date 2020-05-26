package com.hackathon.hikoo.manager

import com.google.firebase.iid.FirebaseInstanceId
import com.hackathon.hikoo.service.HikooFirebaseMessagingService
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FcmTokenManager(
    private val sharePreferenceManager: SharePreferenceManager
) {

    private var fcmToken: String = ""
        set(value) {
            sharePreferenceManager.saveFcmToken(value)
            field = value
        }

    init {
        EventBus.getDefault().register(this)
    }

    fun fetchFcmToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            fcmToken = it.token
            Logger.i("FCM Token = ${it.token}")
        }
    }

    fun getFcmToken(): String {
        return if (fcmToken.isNotBlank()) {
            fcmToken
        } else {
            sharePreferenceManager.getFcmToken()
        }
    }

    @Subscribe
    fun onFcmTokenRefreshed(event: HikooFirebaseMessagingService.FcmTokenRefreshEvent) {
        fcmToken = event.fcmToken
        Logger.i("FCM token has refresh to = ${event.fcmToken}")
    }
}
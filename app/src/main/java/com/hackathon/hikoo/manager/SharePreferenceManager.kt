package com.hackathon.hikoo.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.Moshi

class SharePreferenceManager(
    context: Context,
    private val moshi: Moshi
) {

    private val PREFS_NAME = "com.hackathon.hikoo.preferences"
    private val FCM_TOKEN_KEY = "fcm-token-key"

    private val sharedpreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveFcmToken(fcmToken: String) {
        sharedpreferences.edit {
            putString(FCM_TOKEN_KEY, fcmToken)
        }
    }

    fun getFcmToken(): String {
        return sharedpreferences.getString(FCM_TOKEN_KEY, "") ?: ""
    }

    private fun SharedPreferences.remove(key: String): Boolean {
        if (sharedpreferences.contains(key)) {
            val editor = sharedpreferences.edit()
            editor.remove(key)
            return editor.commit()
        }
        return false
    }
}
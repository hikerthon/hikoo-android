package com.hackathon.hikoo.utils

import android.os.SystemClock

class ActionLock(private val customThreshold: Int = 0) {
    private val DEFAULT_CLICK_THRESHOLD = 1000
    private var lastClickTime = 0L

    private fun getThresholds(): Int {
        if (customThreshold > 0) {
            return customThreshold
        }
        return DEFAULT_CLICK_THRESHOLD
    }

    fun canPerformAction(): Boolean {
        if ((SystemClock.elapsedRealtime() - lastClickTime) < getThresholds()) {
            return false
        }
        lastClickTime = SystemClock.elapsedRealtime()
        return true
    }

    inline fun doTimerLockAction(block: () -> Unit) {
        if (canPerformAction()) {
            block()
        }
    }
}
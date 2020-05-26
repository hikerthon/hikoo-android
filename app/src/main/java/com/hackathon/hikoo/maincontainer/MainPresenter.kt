package com.hackathon.hikoo.maincontainer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.hackathon.hikoo.BasePresenter
import com.hackathon.hikoo.manager.DrawerManager
import com.hackathon.hikoo.network.APIManager
import com.hackathon.hikoo.service.HikooFirebaseMessagingService
import com.orhanobut.logger.Logger
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.processNextEventInCurrentThread

class MainPresenter (
    private val apiManager: APIManager,
    private val drawerManager: DrawerManager
) : BasePresenter<MainActivity>() {

    fun launchView() {
        view?.setupDrawer(drawerManager)
        view?.requireLocationPermission()
        view?.launchHikooPage()
        view?.startListeningUserStateChange()
    }

}
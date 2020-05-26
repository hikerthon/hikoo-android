package com.hackathon.hikoo.maincontainer

import android.content.BroadcastReceiver
import com.hackathon.hikoo.BaseView
import com.hackathon.hikoo.manager.DrawerManager

interface MainView: BaseView {
    fun setupDrawer(drawerManager: DrawerManager)
    fun requireLocationPermission()
    fun launchHikooPage()
    fun startListeningUserStateChange()
}
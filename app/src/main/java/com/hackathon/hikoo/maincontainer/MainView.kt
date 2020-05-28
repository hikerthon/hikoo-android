package com.hackathon.hikoo.maincontainer

import android.content.BroadcastReceiver
import com.hackathon.hikoo.BaseView
import com.hackathon.hikoo.manager.DrawerManager
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

interface MainView: BaseView {
    fun setupDrawer(drawerManager: DrawerManager)
    fun requireLocationPermission()
    fun launchHikooPage()
    fun startListeningUserStateChange()
    fun backToLogin()
    fun updateDrawer(user: User, drawerManager: DrawerManager, imageLoadTool: ImageLoadTool)
}
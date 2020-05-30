package com.hackathon.hikoo.maincontainer

import com.hackathon.hikoo.IBase
import com.hackathon.hikoo.manager.DrawerManager
import com.hackathon.hikoo.model.domain.MountainPermit
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

interface IMain: IBase {
    fun setupDrawer(drawerManager: DrawerManager)
    fun requireLocationPermission()
    fun launchHikooPage()
    fun startListeningUserStateChange()
    fun backToLogin()
    fun updateDrawer(user: User?, drawerManager: DrawerManager, imageLoadTool: ImageLoadTool)
    fun showCheckOutDialog(mountainPermit: MountainPermit)
    fun showCheckOutSuccess()
    fun showCheckOutFailed()
}
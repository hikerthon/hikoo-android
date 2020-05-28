package com.hackathon.hikoo.account

import com.hackathon.hikoo.BasePresenter
import com.hackathon.hikoo.manager.UserManager
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.network.APIManager
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

class AccountPresenter(
    private val imageLoadTool: ImageLoadTool,
    private val userManager: UserManager,
    private val apiManager: APIManager
) : BasePresenter<AccountFragment>() {

    fun fetchHikerInfo() {
        userManager.fetchUserProfile(object : UserManager.OnUserProfileListener {
            override fun onFetchUserProfileSuccess(user: User) {
                view?.setupHikerInfo(user, imageLoadTool)
            }

            override fun onFetchUserProfileFailed() {
            }
        })
    }
}
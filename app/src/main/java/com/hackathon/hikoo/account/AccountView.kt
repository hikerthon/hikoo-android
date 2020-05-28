package com.hackathon.hikoo.account

import com.hackathon.hikoo.BaseView
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

interface AccountView: BaseView {
    fun setupHikerInfo(user: User, imageLoadTool: ImageLoadTool)
}
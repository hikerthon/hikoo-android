package com.hackathon.hikoo.account

import com.hackathon.hikoo.IBase
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

interface AccountView: IBase {
    fun setupHikerInfo(user: User, imageLoadTool: ImageLoadTool)
}
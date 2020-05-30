package com.hackathon.hikoo.account

import com.hackathon.hikoo.IBase
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

interface IEditAccount: IBase {

    fun setupHikerInfo(user: User?, imageLoadTool: ImageLoadTool)
    fun showUploadImage(imageLoadTool: ImageLoadTool, path: String)
    fun showUpdateFailed()
    fun showUpdateSuccess()
}
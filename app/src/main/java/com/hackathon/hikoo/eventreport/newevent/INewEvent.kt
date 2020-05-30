package com.hackathon.hikoo.eventreport.newevent

import com.hackathon.hikoo.IBase
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

interface INewEvent: IBase {
    fun showUploadImage(imageLoader: ImageLoadTool, path: String)
    fun showSendReportSuccess()
    fun showSendReportFailed()
}
package com.hackathon.hikoo.eventreport

import com.hackathon.hikoo.BaseView
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

interface NewEventView: BaseView {
    fun showUploadImage(imageLoader: ImageLoadTool, path: String)
    fun showSendReportSuccess() {}
}
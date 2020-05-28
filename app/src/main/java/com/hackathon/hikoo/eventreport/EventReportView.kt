package com.hackathon.hikoo.eventreport

import com.hackathon.hikoo.BaseView
import com.hackathon.hikoo.model.domain.Event
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

interface EventReportView: BaseView {
    fun setupEventList(list: List<Event>,  imageLoadTool: ImageLoadTool)


}
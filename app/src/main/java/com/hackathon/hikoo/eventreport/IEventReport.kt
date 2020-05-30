package com.hackathon.hikoo.eventreport

import com.hackathon.hikoo.IBase
import com.hackathon.hikoo.model.domain.Event
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

interface IEventReport: IBase {
    fun setupEventList(list: List<Event>,  imageLoadTool: ImageLoadTool)


}
package com.hackathon.hikoo.hikoopage

import com.hackathon.hikoo.BaseView
import com.hackathon.hikoo.model.domain.Shelter
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

interface HikooView: BaseView {

    fun setupShelter(list: List<Shelter>, imageLoadTool: ImageLoadTool)
    fun setupMyLocation(shelter: Shelter)
}
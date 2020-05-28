package com.hackathon.hikoo.mountainpermit

import com.hackathon.hikoo.BaseView
import com.hackathon.hikoo.model.domain.MountainPermit

interface MountainPermitView: BaseView {
    fun setupPermitList(list: List<MountainPermit>)
}
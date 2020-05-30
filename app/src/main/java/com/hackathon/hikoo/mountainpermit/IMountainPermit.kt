package com.hackathon.hikoo.mountainpermit

import com.hackathon.hikoo.IBase
import com.hackathon.hikoo.model.domain.MountainPermit

interface IMountainPermit: IBase {
    fun setupPermitList(list: List<MountainPermit>)
}
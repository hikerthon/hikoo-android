package com.hackathon.hikoo.alert

import com.hackathon.hikoo.IBase
import com.hackathon.hikoo.model.domain.Alert

interface IAlert: IBase {
    fun setupAlertList(list: List<Alert>)
}
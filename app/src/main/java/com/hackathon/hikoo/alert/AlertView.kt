package com.hackathon.hikoo.alert

import com.hackathon.hikoo.BaseView
import com.hackathon.hikoo.model.domain.Alert

interface AlertView: BaseView {
    fun setupAlertList(list: List<Alert>)
}
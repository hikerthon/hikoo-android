package com.hackathon.hikoo.alert

import com.hackathon.hikoo.BasePresenter
import com.hackathon.hikoo.network.APIManager
import com.orhanobut.logger.Logger
import io.reactivex.rxkotlin.subscribeBy

class AlertPresenter(
    private val apiManager: APIManager
) : BasePresenter<AlertFragment>() {

    fun fetchAlert() {
        apiManager.getAlert().subscribeBy(
            onNext = { response ->
                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        view?.setupAlertList(list.sortedByDescending { it.eventTime })
                    }
                }
            },
            onError = {
                Logger.d("$it")
            }
        ).autoClear()
    }
}
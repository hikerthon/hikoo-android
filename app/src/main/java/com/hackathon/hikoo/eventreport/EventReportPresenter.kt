package com.hackathon.hikoo.eventreport

import com.hackathon.hikoo.BasePresenter
import com.hackathon.hikoo.network.APIManager
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
import io.reactivex.rxkotlin.subscribeBy

class EventReportPresenter(
    private val apiManager: APIManager,
    private val imageLoadTool: ImageLoadTool
) : BasePresenter<EventReportFragment>() {
    fun fetchEvent() {
        apiManager.getEvent().subscribeBy(
            onNext = { response ->
                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        view?.setupEventList(list, imageLoadTool)
                    }
                }
            },
            onError = {

            }
        ).autoClear()
    }
}
package com.hackathon.hikoo.mountainpermit

import com.hackathon.hikoo.BasePresenter
import com.hackathon.hikoo.network.APIManager
import io.reactivex.rxkotlin.subscribeBy

class MountainPermitPresenter(
    private val apiManager: APIManager
) : BasePresenter<MountainPermitFragment>() {

    fun fetchPermit() {
        apiManager.getPermit().subscribeBy(
            onNext = { response ->
                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        view?.setupPermitList(list)
                    }
                }
            },
            onError = {

            }
        ).autoClear()
    }
}
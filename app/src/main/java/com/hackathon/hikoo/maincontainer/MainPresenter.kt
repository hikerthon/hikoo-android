package com.hackathon.hikoo.maincontainer

import com.hackathon.hikoo.BasePresenter
import com.hackathon.hikoo.manager.DrawerManager
import com.hackathon.hikoo.manager.SharePreferenceManager
import com.hackathon.hikoo.manager.UserLocationManager
import com.hackathon.hikoo.manager.UserManager
import com.hackathon.hikoo.model.domain.Locations
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.network.APIManager
import com.hackathon.hikoo.network.NetworkCheck
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
import com.orhanobut.logger.Logger
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit

class MainPresenter (
    private val apiManager: APIManager,
    private val drawerManager: DrawerManager,
    private val userLocationManager: UserLocationManager,
    private val sharePreferenceManager: SharePreferenceManager,
    private val userManager: UserManager,
    private val imageLoadTool: ImageLoadTool
) : BasePresenter<MainActivity>() {

    fun launchView() {
        userManager.fetchUserProfile(object : UserManager.OnUserProfileListener {
            override fun onFetchUserProfileSuccess(user: User) {
                view?.updateDrawer(user, drawerManager, imageLoadTool)
            }

            override fun onFetchUserProfileFailed() {
            }
        })
        view?.setupDrawer(drawerManager)
        view?.requireLocationPermission()
        view?.launchHikooPage()
        view?.startListeningUserStateChange()
    }

    fun uploadUserLocation() {
        val repeatInterval = 30L
        NetworkCheck.checkInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .repeatWhen { flowable ->
                flowable.delay(repeatInterval, TimeUnit.SECONDS)
            }
            .flatMap(object :
                Function<Boolean, Publisher<Triple<Boolean, Locations, Error?>>> {
                @Throws(Exception::class)
                override fun apply(isConnected: Boolean): Publisher<Triple<Boolean, Locations, Error?>>? {
                    if (isConnected) {
                        return apiManager.postLocation(1, userLocationManager.currentLocation!!).toFlowable(BackpressureStrategy.DROP)
                    } else {
                        return Flowable.empty()
                    }
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen { throwableFlowable ->
                throwableFlowable.flatMap {
                    Flowable.timer(repeatInterval, TimeUnit.SECONDS)
                }
            }
            .subscribeBy(
                onNext = {

                },
                onError = {

                }
            ).autoClear()
    }

    fun logout() {
        userManager.logoutUser()
        view?.backToLogin()
    }

}
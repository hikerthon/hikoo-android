package com.hackathon.hikoo

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.hackathon.hikoo.account.AccountPresenter
import com.hackathon.hikoo.alert.AlertPresenter
import com.hackathon.hikoo.eventreport.EventReportPresenter
import com.hackathon.hikoo.eventreport.NewEventPresenter
import com.hackathon.hikoo.hikoopage.HikooPresenter
import com.hackathon.hikoo.login.LoginPresenter
import com.hackathon.hikoo.maincontainer.MainPresenter
import com.hackathon.hikoo.manager.*
import com.hackathon.hikoo.mountainpermit.MountainPermitPresenter
import com.hackathon.hikoo.network.APIManagerImpl
import com.hackathon.hikoo.network.HttpClient
import com.hackathon.hikoo.network.TokenInterceptor
import com.hackathon.hikoo.utils.imageloader.ImageLoadToolImpl
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.ext.android.get
import org.koin.core.context.startKoin
import org.koin.dsl.module

class HikooApplication: Application() {

    private val networkModule = module {
        val moshi = Moshi.Builder().build()
        single { moshi }
        single { HttpClient(get<TokenInterceptor>()) }
        single { APIManagerImpl(get(), get()) }
        factory { TokenInterceptor() }
    }

    private val presenterModule = module {
        factory { MainPresenter(get<APIManagerImpl>(), get<DrawerManager>(), get<UserLocationManager>(),
            get<SharePreferenceManager>(), get<UserManager>(), get<ImageLoadToolImpl>()) }
        factory { HikooPresenter(get<UserLocationManager>(), get<ShelterManager>(),
            get<ImageLoadToolImpl>(), get<APIManagerImpl>(), get<SharePreferenceManager>()) }
        factory { LoginPresenter(get<APIManagerImpl>(), get<UserManager>()) }
        factory { NewEventPresenter(get<APIManagerImpl>(), get<UserLocationManager>(), get<ImageLoadToolImpl>()) }
        factory { AlertPresenter(get<APIManagerImpl>()) }
        factory { MountainPermitPresenter(get<APIManagerImpl>()) }
        factory { EventReportPresenter(get<APIManagerImpl>(), get<ImageLoadToolImpl>()) }
        factory { AccountPresenter(get<ImageLoadToolImpl>(), get<UserManager>(), get<APIManagerImpl>()) }
    }

    private val managerModule = module {
        single { UserLocationManager(applicationContext, get<SharePreferenceManager>()) }
        single { DrawerManager(get<ImageLoadToolImpl>()) }
        single { ImageLoadToolImpl().also { it.context = applicationContext } }
        single { SharePreferenceManager(applicationContext, get()) }
        single { FcmTokenManager(get<SharePreferenceManager>()) }
        single { UserManager(get<APIManagerImpl>(), get<SharePreferenceManager>()) }
        single { ShelterManager(get<UserLocationManager>(), get<APIManagerImpl>(), get<SharePreferenceManager>()) }
    }

    private val appModule = listOf(networkModule, presenterModule, managerModule)
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        startKoin {
            androidContext(this@HikooApplication)
            androidLogger()
            modules(appModule)
        }

        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(AndroidLogAdapter())
        }

        val apiManager = get<APIManagerImpl>()
        apiManager.createArdoVenationService()

        val fcmTokenManager = get<FcmTokenManager>()
        fcmTokenManager.fetchFcmToken()
    }
}
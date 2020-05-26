package com.hackathon.hikoo

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.hackathon.hikoo.hikoopage.HikooPresenter
import com.hackathon.hikoo.maincontainer.MainPresenter
import com.hackathon.hikoo.manager.DrawerManager
import com.hackathon.hikoo.manager.FcmTokenManager
import com.hackathon.hikoo.manager.SharePreferenceManager
import com.hackathon.hikoo.manager.UserLocationManager
import com.hackathon.hikoo.network.APIManagerImpl
import com.hackathon.hikoo.network.HttpClient
import com.hackathon.hikoo.utils.imageloader.ImageLoaderImpl
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
        single { HttpClient() }
        single { APIManagerImpl(get(), get()) }
    }

    private val presenterModule = module {
        factory { MainPresenter(get<APIManagerImpl>(), get<DrawerManager>()) }
        factory { HikooPresenter(get<UserLocationManager>()) }
    }

    private val managerModule = module {
        single { UserLocationManager(applicationContext) }
        single { DrawerManager(get<ImageLoaderImpl>()) }
        single { ImageLoaderImpl().also { it.context = applicationContext } }
        single { SharePreferenceManager(applicationContext, get()) }
        single { FcmTokenManager(get<SharePreferenceManager>()) }
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
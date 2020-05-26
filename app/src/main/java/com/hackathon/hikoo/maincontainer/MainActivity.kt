package com.hackathon.hikoo.maincontainer

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.material.appbar.AppBarLayout
import com.hackathon.hikoo.BaseActivity
import com.hackathon.hikoo.R
import com.hackathon.hikoo.manager.DrawerManager
import com.hackathon.hikoo.manager.UserLocationManager
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.ActionLock
import com.hackathon.hikoo.utils.bindView
import com.hackathon.hikoo.view.AdvanceDrawerLayout
import com.hackathon.hikoo.view.router.MainRouter
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity(),
    DrawerManager.onDrawerMenuClickListener,
    UserLocationManager.OnLocationSettingFailedCallback,
    MainView {

    companion object {
        const val REQUEST_PERMISSION = 1000
        private const val LOCATION_RESOLUTION_REQUEST = 1001
    }

    private lateinit var navigationToggle: ActionBarDrawerToggle
    private val appBarLayout: AppBarLayout by bindView(R.id.app_bar_layout)
    private val toolbar: Toolbar by bindView(R.id.toolbar)

    private val router: MainRouter = MainRouter(supportFragmentManager, this)
    private val drawerItemClickLock = ActionLock()

    private val userLocationManager: UserLocationManager by inject()
    private val presenter: MainPresenter by inject()

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val intentAction = intent.action
            Logger.d("broadcastReceiver = $intentAction")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()
        presenter.attachView(this)
        presenter.launchView()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

    override fun onRequestPermissionsResult(
        permsRequestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grantResults)
        when (permsRequestCode) {
            REQUEST_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.launchView()
                } else {
                    finish()
                }
            }
        }
    }

    fun startLocationListener() {
        if (!userLocationManager.isRunning() && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            userLocationManager.startLocationUpdate(this)
        }
    }

    fun stopLocationListener() {
        if (userLocationManager.isRunning()) {
            userLocationManager.pauseLocationUpdate()
        }
    }

    private fun setupActionBar() {
        appBarLayout.outlineProvider = null
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayShowTitleEnabled(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hikoo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.hikoo_alert -> {
                router.navToAlertPage()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDrawerMenuClickMountainPermitPage() {
        drawerItemClickLock.doTimerLockAction {
            router.navToMountainPermitPage()
        }
    }

    override fun onDrawerMenuClickEventReportPage() {
        drawerItemClickLock.doTimerLockAction {
            router.navToEventReportPage()
        }
    }

    override fun onDrawerHeaderUserClicked(user: User?) {
        drawerItemClickLock.doTimerLockAction {
            router.navToAccountPage()
        }
    }

    override fun onLocationPermissionRequired() {
        checkPermissions(mutableListOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)
    }

    override fun onResolutionRequired(resolvable: ResolvableApiException) {
        try {
            resolvable.startResolutionForResult(this@MainActivity, LOCATION_RESOLUTION_REQUEST)
        } catch (e: IntentSender.SendIntentException) {
            Logger.e(Log.getStackTraceString(e))
        }
    }

    override fun onResolutionManually() {
    }

    override fun onSettingChangeUnavailable() {
    }

    //region MainView
    override fun provideLifecycleOwner(): LifecycleOwner {
        return this
    }

    override fun requireLocationPermission() {
        if (!checkPermissions(mutableListOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)) {
            return
        }
    }

    override fun launchHikooPage() {
        router.navToHikooPage()
    }

    override fun setupDrawer(drawerManager: DrawerManager) {
        with(drawerManager) {
            val drawerLayout: AdvanceDrawerLayout = findViewById(R.id.drawer_layout)
            setupDrawer(drawerLayout, R.id.nav_view, null)
            callback = this@MainActivity
            lockDrawer(true)
        }
        setupNavigationMenu(drawerManager)
    }

    private fun setupNavigationMenu(drawerManager: DrawerManager) {
        val drawerLayout = drawerManager.drawer
        navigationToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                navigationToggle.setHomeAsUpIndicator(R.drawable.circle_border_shape)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                navigationToggle.setHomeAsUpIndicator(R.drawable.drawer_close_icon)
            }
        }
        navigationToggle.syncState()
        drawerManager.setupCustomIndicator(navigationToggle)
        drawerManager.lockDrawer(false)
        drawerLayout.addDrawerListener(navigationToggle)
        drawerManager.navigationView.itemIconTintList = null
    }

    override fun startListeningUserStateChange() {
        val intentFilter = IntentFilter()
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
    }
    //endregion
}

package com.hackathon.hikoo.maincontainer

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.BaseActivity
import com.hackathon.hikoo.R
import com.hackathon.hikoo.login.LoginActivity
import com.hackathon.hikoo.manager.DrawerManager
import com.hackathon.hikoo.manager.UserLocationManager
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.ActionLock
import com.hackathon.hikoo.utils.bindView
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
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
    private val toolbarTitle: MaterialTextView by bindView(R.id.toolbar_title)
    private val toolbarAvatar: AppCompatImageView by bindView(R.id.toolbar_avatar)
    private val backToHomeButton: MaterialTextView by bindView(R.id.back_to_home)

    private val router: MainRouter = MainRouter(supportFragmentManager, this)
    private val drawerItemClickLock = ActionLock()

    private val userLocationManager: UserLocationManager by inject()
    private val drawerManager: DrawerManager by inject()
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

        toolbarAvatar.setOnClickListener {
            drawerManager.openDrawer(true)
        }

        backToHomeButton.setOnClickListener {
            showBackButton(false)
            router.navToHikooPage()
        }
    }

    override fun onDestroy() {
        drawerManager.release()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
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

    fun setActionBarTitle(@StringRes string: Int) {
        toolbarTitle.setText(string)
    }

    fun launchAlertPage() {
        showBackButton(true)
        router.navToAlertPage()
    }

    fun showBackButton(show: Boolean) {
        if (show) {
            backToHomeButton.visibility = View.VISIBLE
            toolbarAvatar.visibility = View.GONE
        } else {
            backToHomeButton.visibility = View.GONE
            toolbarAvatar.visibility = View.VISIBLE
        }
    }

    override fun onDrawerMenuClickMountainPermitPage() {
        drawerItemClickLock.doTimerLockAction {
            router.navToMountainPermitPage()
            showBackButton(true)
        }
    }

    override fun onDrawerMenuClickEventReportPage() {
        drawerItemClickLock.doTimerLockAction {
            router.navToEventReportPage()
            showBackButton(true)
        }
    }

    override fun onDrawerHeaderUserClicked() {
        drawerItemClickLock.doTimerLockAction {
            router.navToAccountPage()
            showBackButton(true)
        }
    }

    override fun onDrawerMenuClickLogout() {
        drawerItemClickLock.doTimerLockAction {
//            presenter.logout()
        }
    }

    override fun onDrawerMenuClickHikooPage() {
        drawerItemClickLock.doTimerLockAction {
            router.navToHikooPage()
            showBackButton(false)
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
        showBackButton(false)
    }

    override fun setupDrawer(drawerManager: DrawerManager) {
        with(drawerManager) {
            val drawerLayout: AdvanceDrawerLayout = findViewById(R.id.drawer_layout)
            setupDrawer(drawerLayout, R.id.nav_view)
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
        ){}
        navigationToggle.syncState()
        drawerManager.lockDrawer(false)
        drawerLayout.addDrawerListener(navigationToggle)
        drawerManager.navigationView.itemIconTintList = null
        toolbar.navigationIcon = null
    }

    fun openDrawer() {
        drawerManager.openDrawer(true)
    }

    override fun startListeningUserStateChange() {
        val intentFilter = IntentFilter()
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun backToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
    }

    override fun updateDrawer(user: User, drawerManager: DrawerManager, imageLoadTool: ImageLoadTool) {
        imageLoadTool.loadCircleImage(this, user.image, toolbarAvatar, R.drawable.ic_profile_icon)
        drawerManager.updateUserData(user)
    }
    //endregion
}

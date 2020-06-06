package com.hackathon.hikoo.maincontainer

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.BaseActivity
import com.hackathon.hikoo.R
import com.hackathon.hikoo.login.LoginActivity
import com.hackathon.hikoo.manager.DrawerManager
import com.hackathon.hikoo.manager.UserLocationManager
import com.hackathon.hikoo.model.domain.MountainPermit
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.ActionLock
import com.hackathon.hikoo.utils.bindView
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
import com.hackathon.hikoo.view.AdvanceDrawerLayout
import com.hackathon.hikoo.view.EventDialog
import com.hackathon.hikoo.view.router.MainRouter
import com.orhanobut.logger.Logger
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity(),
    DrawerManager.onDrawerMenuClickListener,
    UserLocationManager.OnLocationSettingFailedCallback,
    IMain {

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

    private var eventDialog: EventDialog? = null

    private val userLocationManager: UserLocationManager by inject()
    private val drawerManager: DrawerManager by inject()
    private val presenter: MainPresenter by inject()

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val title = intent.getStringExtra("Title")
            val body = intent.getStringExtra("Body")
            val alertLevel = intent.getIntExtra("AlertLevel", -1)
            val lat = intent.getDoubleExtra("Lat", 0.0)
            val lng = intent.getDoubleExtra("Lng", 0.0)

            if (title == "check-in") {
                showNotificationDialog("Check-In", body)
                presenter.updatePermitInfo()
            } else if (title == "Hikoo"){
                showEventDialog(body, alertLevel, lat, lng)
            } else {
                showNotificationDialog(title, body)
            }
        }
    }

    private fun showEventDialog(description: String?, alertLevel: Int, lat: Double, lng: Double) {
        this.eventDialog?.takeIf { it.isShowing }?.dismiss()

        val eventDialog = EventDialog(this)

        eventDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        eventDialog.show()

        eventDialog.setAlertLevel(alertLevel)
        eventDialog.setMapMarker(userLocationManager.currentLocation!!, lat, lng)
        eventDialog.setDescription(description!!)

        this.eventDialog = eventDialog
    }

    private fun showNotificationDialog(title: String?, body: String?) {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(title)
            .setMessage(body)
            .setPositiveButton("Got it") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()
        presenter.attachView(this)

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
        router.release()
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
            presenter.uploadUserLocation()
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

    override fun onDrawerMenuClickCheckOut() {
        drawerItemClickLock.doTimerLockAction {
            presenter.startCheckOut()
        }
    }

    override fun onDrawerMenuClickLogout() {
        drawerItemClickLock.doTimerLockAction {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure want to logout?")
                .setPositiveButton("Logout") { dialog, which ->
                    dialog.dismiss()
                    presenter.logout()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
                .show()
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
        drawerLayout?.addDrawerListener(navigationToggle)
        drawerManager.navigationView?.itemIconTintList = null
        toolbar.navigationIcon = null
    }

    override fun startListeningUserStateChange() {
        val intentFilter = IntentFilter().apply {
            addAction("Hikoo")
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun backToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    override fun updateDrawer(user: User?, drawerManager: DrawerManager, imageLoadTool: ImageLoadTool) {
        imageLoadTool.loadCircleImage(this, user?.image, toolbarAvatar, R.drawable.ic_profile_icon)
        drawerManager.updateUserData(user)
    }

    override fun showCheckOutDialog(mountainPermit: MountainPermit) {
        AlertDialog.Builder(this)
            .setTitle("Check Out")
            .setMessage("Are you sure want to ${mountainPermit.permitName} check out?")
            .setPositiveButton("CheckOut") { dialog, which ->
                dialog.dismiss()
                presenter.checkOutPermit()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun showCheckOutSuccess() {
        AlertDialog.Builder(this)
            .setTitle("Result")
            .setMessage("Check out is successful")
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun showCheckOutFailed() {
        AlertDialog.Builder(this)
            .setTitle("Result")
            .setMessage("Check out is failed")
            .setPositiveButton("Retry") { dialog, which ->
                dialog.dismiss()
                presenter.checkOutPermit()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    //endregion
}

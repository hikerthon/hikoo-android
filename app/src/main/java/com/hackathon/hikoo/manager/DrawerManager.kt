package com.hackathon.hikoo.manager

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.R
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.imageloader.ImageLoaderImpl
import com.hackathon.hikoo.view.AdvanceDrawerLayout
import org.greenrobot.eventbus.EventBus

class DrawerManager(private val imageLoader: ImageLoaderImpl) : NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    private var accountImageView: AppCompatImageView? = null
    private var accountNameTextView: MaterialTextView? = null
    private var accountLinearLayout: LinearLayout? = null

    private lateinit var headerView: View

    private var user: User? = null

    var callback: onDrawerMenuClickListener? = null

    lateinit var drawer: AdvanceDrawerLayout
        private set

    lateinit var navigationView: NavigationView
        private set

    fun setupDrawer(
        drawer: AdvanceDrawerLayout,
        @IdRes navigationViewId: Int,
        user: User?
    ) {
        this.user = user

        this.drawer = drawer
        navigationView = drawer.findViewById(navigationViewId)
        navigationView.setNavigationItemSelectedListener(this)

        drawer.userCustomBehavior(Gravity.START)
        drawer.userCustomBehavior(Gravity.END)

        setupHeaderView()
        updateUserData()
    }

    fun setupCustomIndicator(toggle: ActionBarDrawerToggle) {
        val imageView = AppCompatImageView(imageLoader.context)
        imageView.setBackgroundResource(R.drawable.circle_border_shape)
        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(R.drawable.circle_border_shape)
        toggle.setToolbarNavigationClickListener {
            if (drawer.isDrawerVisible(GravityCompat.START)) {
                openDrawer(false)
            } else {
                openDrawer(true)
            }
        }
    }

    private fun setupHeaderView() {
        navigationView.let {
            headerView = it.inflateHeaderView(R.layout.drawer_header)
            headerView.apply {
                accountImageView = findViewById(R.id.header_avatar)
                accountNameTextView = findViewById(R.id.header_name)
                accountLinearLayout = findViewById(R.id.header_linear_layout)
                accountLinearLayout?.setOnClickListener(this@DrawerManager)
//                EventBus.getDefault().register(this@DrawerManager)
            }
        }
    }

    private fun updateUserData() {
        user?.let {
            accountNameTextView?.text = ""
            accountImageView?.let {
                imageLoader.loadCircleImage("", it, R.drawable.ic_profile_icon)
            }
        }
    }


    fun release() {
        callback = null
        EventBus.getDefault().unregister(this@DrawerManager)
    }

    fun isDrawerOpened(): Boolean {
        return drawer.run { isDrawerOpen(GravityCompat.START) }
    }

    fun isDrawerLocked(): Boolean {
        return drawer.run {
            getDrawerLockMode(GravityCompat.START) != DrawerLayout.LOCK_MODE_UNLOCKED
        }
    }

    fun lockDrawer(lock: Boolean) {
        drawer.run {
            if (lock && !isDrawerLocked()) {
                setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                return
            }

            if (!lock && isDrawerLocked()) {
                setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }

    fun openDrawer(open: Boolean) {
        drawer.run {
            if (open && !isDrawerOpened()) {
                openDrawer(GravityCompat.START)
                return
            }

            if (!open && isDrawerOpened()) {
                closeDrawer(GravityCompat.START)
            }
        }
    }

    fun checkMenuOption(@IdRes menuResId: Int) {
        navigationView.menu.findItem(menuResId)?.isChecked = true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        openDrawer(false)
        when (item.itemId) {
            R.id.nav_permit -> {
                callback?.onDrawerMenuClickMountainPermitPage()
            }
            R.id.nav_report -> {
                callback?.onDrawerMenuClickEventReportPage()
            }
            else -> return false
        }
        return item.isChecked
    }

    override fun onClick(view: View) {
        openDrawer(false)
        if (view.id == R.id.header_linear_layout) {
            callback?.onDrawerHeaderUserClicked(user)
//            user?.let {
//                callback?.onDrawerHeaderUserClicked(it)
//            }
        }
    }

    interface onDrawerMenuClickListener {
        fun onDrawerMenuClickMountainPermitPage()
        fun onDrawerMenuClickEventReportPage()
        fun onDrawerHeaderUserClicked(user: User?)
    }
}
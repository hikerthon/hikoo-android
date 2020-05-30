package com.hackathon.hikoo.manager

import android.content.Context
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.R
import com.hackathon.hikoo.model.domain.User
import com.hackathon.hikoo.utils.imageloader.ImageLoadToolImpl
import com.hackathon.hikoo.view.AdvanceDrawerLayout
import org.greenrobot.eventbus.EventBus

class DrawerManager(private val imageLoadTool: ImageLoadToolImpl) : NavigationView.OnNavigationItemSelectedListener,
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
        @IdRes navigationViewId: Int
    ) {
        this.drawer = drawer
        navigationView = drawer.findViewById(navigationViewId)
        navigationView.setNavigationItemSelectedListener(this)

        drawer.setRadius(Gravity.START, 40f)
        drawer.setViewElevation(Gravity.START, 40f)
        setupHeaderView()
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

    fun updateUserData(user: User?) {
        this.user = user
        user?.let {
            accountNameTextView?.text = "${it.firstName} ${it.lastName}"
            accountImageView?.let { view ->
                imageLoadTool.loadCircleImage(it.image, view, R.drawable.ic_profile_icon)
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
            R.id.nav_logout -> {
                callback?.onDrawerMenuClickLogout()
            }
            R.id.nav_check_out -> {
                callback?.onDrawerMenuClickCheckOut()
            }
            else -> return false
        }
        return item.isChecked
    }

    override fun onClick(view: View) {
        openDrawer(false)
        if (view.id == R.id.header_linear_layout) {
            callback?.onDrawerHeaderUserClicked()
        }
    }

    interface onDrawerMenuClickListener {
        fun onDrawerMenuClickMountainPermitPage()
        fun onDrawerMenuClickEventReportPage()
        fun onDrawerHeaderUserClicked()
        fun onDrawerMenuClickCheckOut()
        fun onDrawerMenuClickLogout()
    }
}
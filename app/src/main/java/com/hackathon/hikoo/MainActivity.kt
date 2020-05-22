package com.hackathon.hikoo

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.hackathon.hikoo.utils.bindView
import com.hackathon.hikoo.view.AdvanceDrawerLayout

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val drawerLayout: AdvanceDrawerLayout by bindView(R.id.drawer_layout)
    private val navView: NavigationView by bindView(R.id.nav_view)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        changeStatusBarColor(R.color.iris_blue)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        drawerLayout.userCustomBehavior(Gravity.START)
        drawerLayout.userCustomBehavior(Gravity.END)
        drawerLayout.setRadius(Gravity.START, 50f)
        drawerLayout.setViewElevation(Gravity.START, 20f)
        drawerLayout.setViewScale(Gravity.START, 0.95f)

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}

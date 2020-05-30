package com.hackathon.hikoo

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.hackathon.hikoo.view.router.Router

abstract class BaseActivity: AppCompatActivity(), Router.OnTransactionReadyCallback {

    private var isReadyTransaction = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isReadyTransaction = true
    }

    override fun onStart() {
        super.onStart()
        isReadyTransaction = true
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        isReadyTransaction = true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        isReadyTransaction = false
        super.onSaveInstanceState(outState)
    }

    fun changeStatusBarColor(@ColorRes color: Int) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, color)
    }

    fun checkPermissions(permissions: MutableList<String>, requestCode: Int): Boolean {
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED) {
                permissions.remove(it)
            }
        }

        if (permissions.isNotEmpty()) {
            val permissionArray = permissions.toTypedArray()
            ActivityCompat.requestPermissions(this, permissionArray, requestCode)
            return false
        }

        return true
    }

    override fun isReadyForTransaction(): Boolean {
        return isReadyTransaction
    }
}
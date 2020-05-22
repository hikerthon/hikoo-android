package com.hackathon.hikoo

import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

public abstract class BaseActivity: AppCompatActivity() {

    private var isReadyTransaction = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isReadyTransaction = true
    }

    override fun onStart() {
        super.onStart()
        isReadyTransaction = true
    }

    override fun onResume() {
        super.onResume()
        isReadyTransaction = true
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
}
package com.hackathon.hikoo.view

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.graphics.ColorUtils
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.shape.MaterialShapeDrawable
import com.hackathon.hikoo.R
import kotlin.collections.set


class AdvanceDrawerLayout : DrawerLayout {

    private val TAG = AdvanceDrawerLayout::class.java.simpleName
    var settings: HashMap<Int, Setting> = HashMap()
    private var defaultScrimColor = 0x99000000
    private var defaultDrawerElevation = 0f
    private var frameLayout: FrameLayout? = null
    private var drawerView: View? = null
    private var statusBarColor = 0
    private var defaultFitsSystemWindows = false
    private var contrastThreshold = 3f


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        init()
    }

    private fun init() {
        defaultDrawerElevation = Setting().getDrawerElevation()
        defaultFitsSystemWindows = fitsSystemWindows

        if (!isInEditMode) {
            statusBarColor = (context as Activity).window.statusBarColor
        }

        addDrawerListener(object : DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                this@AdvanceDrawerLayout.drawerView = drawerView
                updateSlideOffset(drawerView, slideOffset)
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }
        })

        frameLayout = FrameLayout(context)
        frameLayout?.setPadding(0, 0, 0, 0)
        super.addView(frameLayout)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        child?.layoutParams = params
        addView(child)
    }

    override fun addView(child: View?) {
        if (child is NavigationView) {
            super.addView(child)
        } else {
            val cardView = MaterialCardView(context)
            cardView.radius = 0f
            cardView.addView(child)
            cardView.cardElevation = 0f
            frameLayout?.addView(cardView)
        }
    }

    fun setViewScale(gravity: Int, percentage: Float) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        val setting = checkSettings(absGravity)
        if (percentage < 1) {
            setStatusBarBackground(null)
            systemUiVisibility = 0
        }
        setting.setPercentage(percentage)
        setting.setScrimColor(Color.TRANSPARENT.toLong())
        setting.setDrawerElevation(0f)
    }

    fun setViewElevation(gravity: Int, elevation: Float) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        val setting = checkSettings(absGravity)
        setting.setScrimColor(Color.TRANSPARENT.toLong())
        setting.setDrawerElevation(0f)
        setting.setElevation(elevation)
    }

    fun setViewScrimColor(gravity: Int, scrimColor: Long) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        val setting = checkSettings(absGravity)
        setting.setScrimColor(scrimColor)
    }

    fun setDrawerElevation(gravity: Int, elevation: Float) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        val setting = checkSettings(absGravity)
        setting.setElevation(0f)
        setting.setDrawerElevation(elevation)
    }

    fun setRadius(gravity: Int, radius: Float) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        val setting = checkSettings(absGravity)
        setting.setRadius(radius)
    }

    fun getSetting(gravity: Int): Setting {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        return settings[absGravity]!!
    }

    override fun setDrawerElevation(elevation: Float) {
        defaultDrawerElevation = elevation
        super.setDrawerElevation(elevation)
    }

    override fun setScrimColor(color: Int) {
        defaultScrimColor = color.toLong()
        super.setScrimColor(color)
    }

    fun userCustomBehavior(gravity: Int) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        if (!settings.containsKey(absGravity)) {
            val setting = createSetting()
            settings[absGravity] = setting
        }
    }

    fun removeCustomBehavior(gravity: Int) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        if (settings.containsKey(absGravity)) {
            settings.remove(absGravity)
        }
    }

    override fun openDrawer(drawerView: View, animate: Boolean) {
        super.openDrawer(drawerView, animate)

        post(Runnable {
            if (isDrawerOpen(drawerView)) {
                updateSlideOffset(drawerView, 1f)
            } else {
                updateSlideOffset(drawerView, 0f)
            }
        })
    }

    private fun checkSettings(absGravity: Int): Setting {
        return if (!settings.containsKey(absGravity)) {
            val setting = createSetting()
            settings[absGravity] = setting
            setting
        } else {
            settings[absGravity]!!
        }
    }

    private fun updateSlideOffset(drawerView: View, slideOffset: Float) {
        val absHorizGravity = getDrawerViewAbsoluteGravity(GravityCompat.START)
        val childAbsGravity = getDrawerViewAbsoluteGravity(drawerView)

        val window = (context as? Activity)?.window
        var isRtl = false

        if (frameLayout == null || window == null) {
            return
        }

        isRtl = layoutDirection == View.LAYOUT_DIRECTION_RTL || window.decorView.layoutDirection == View.LAYOUT_DIRECTION_RTL ||
                resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL

        for (i in 0 until frameLayout!!.childCount) {
            var child = frameLayout!!.getChildAt(i) as MaterialCardView
            val setting = settings[childAbsGravity]
            var adjust = 0f

            if (setting != null) {
                if (setting.getPercentage() < 1.0) {
                    if (drawerView.background is ColorDrawable) {
                        val color = ColorUtils.setAlphaComponent(statusBarColor, (255 - 255 * slideOffset).toInt())
                        window.statusBarColor = color

                        val bgColor = (drawerView.background as ColorDrawable).color
                        window.decorView.setBackgroundColor(bgColor)
                        systemUiVisibility = if (ColorUtils.calculateContrast(Color.WHITE, bgColor) < contrastThreshold && slideOffset > 0.4) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
                    } else if (drawerView.background is MaterialShapeDrawable && (drawerView.background as MaterialShapeDrawable).fillColor != null) {
                        val color = ColorUtils.setAlphaComponent(statusBarColor, (255 - 255 * slideOffset).toInt())
                        window.statusBarColor = color

                        val bgColor = (drawerView.background as MaterialShapeDrawable).fillColor?.defaultColor
                        window.decorView.setBackgroundColor(bgColor ?: 0)
                        systemUiVisibility = if (ColorUtils.calculateContrast(Color.WHITE, bgColor!!) < contrastThreshold && slideOffset > 0.4) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
                    }
                }

                child.radius = setting.getRadius() * slideOffset
                super.setScrimColor(setting.getScrimColor().toInt())
                super.setDrawerElevation(setting.getDrawerElevation())
                val percentage = 1f - setting.getPercentage()
                ViewCompat.setScaleY(child, 1f - percentage * slideOffset)
                child.cardElevation = setting.getElevation() * slideOffset
                adjust = setting.getElevation()
                val isLeftDrawer: Boolean = if (isRtl) childAbsGravity != absHorizGravity else childAbsGravity == absHorizGravity
                val width = if (isLeftDrawer) drawerView.width + adjust else -drawerView.width - adjust
                updateSlideOffset(child, setting, width, slideOffset, isLeftDrawer)
            } else {
                super.setScrimColor(defaultScrimColor.toInt());
                super.setDrawerElevation(defaultDrawerElevation);
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (drawerView != null) {
            if (isDrawerOpen(drawerView!!)) {
                updateSlideOffset(drawerView!!, 1f)
            } else {
                updateSlideOffset(drawerView!!, 0f)
            }
        }
    }

    private fun updateSlideOffset(child: CardView, setting: Setting?, width: Float, slideOffset: Float, isLeftDrawer: Boolean) {
        child.x = width * slideOffset
    }

    private fun getDrawerViewAbsoluteGravity(gravity: Int): Int {
        return  GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this)) and GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK
    }

    private fun getDrawerViewAbsoluteGravity(drawerView: View): Int {
        val gravity = (drawerView.layoutParams as LayoutParams).gravity
        return getDrawerViewAbsoluteGravity(gravity)
    }

    private fun createSetting(): Setting {
        return Setting()
    }

    inner class Setting {
        private var percentage = 1f
        private var scrimColor = defaultScrimColor
        private var elevation = 0f
        private var drawerElevation = defaultDrawerElevation
        private var radius = 0f

        fun getDrawerElevation(): Float {
            return this.drawerElevation
        }

        fun setDrawerElevation(drawerElevation: Float) {
            this.drawerElevation = drawerElevation
        }

        fun getPercentage(): Float {
            return this.percentage
        }

        fun setPercentage(percentage: Float) {
            this.percentage = percentage
        }

        fun getScrimColor(): Long {
            return this.scrimColor
        }

        fun setScrimColor(scrimColor: Long) {
            this.scrimColor = scrimColor
        }

        fun getElevation(): Float {
            return this.elevation
        }

        fun setElevation(elevation: Float) {
            this.elevation = elevation
        }

        fun getRadius(): Float {
            return this.radius
        }

        fun setRadius(radius: Float) {
            this.radius = radius
        }

    }

}
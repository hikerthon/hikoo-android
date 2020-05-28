package com.hackathon.hikoo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackathon.hikoo.R

class ListDivider: RecyclerView.ItemDecoration {

    companion object {
        const val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        const val VERTICAL_LIST = LinearLayoutManager.VERTICAL
    }

    private val ATTR = intArrayOf(R.attr.listDivider)
    private var divider: Drawable? = null
    private var orientation: Int = VERTICAL_LIST
    private var showFirstDivider = false
    private var showLastDivider = false

    constructor(context: Context, orientation: Int) {
        init(context, orientation)
    }

    constructor(
        context: Context,
        orientation: Int,
        showFirstDivider: Boolean,
        showLastDivider: Boolean
    ) {
        init(context, orientation)
        this.showFirstDivider = showFirstDivider
        this.showLastDivider = showLastDivider
    }

    constructor(context: Context, @DrawableRes drawableId: Int, orientation: Int) {
        init(context, drawableId, orientation)
    }

    constructor(
        context: Context,
        @DrawableRes drawableId: Int,
        orientation: Int,
        showFirstDivider: Boolean,
        showLastDivider: Boolean
    ) {
        init(context, drawableId, orientation)
        this.showFirstDivider = showFirstDivider
        this.showLastDivider = showLastDivider
    }

    private fun init(context: Context, orientation: Int) {
        val typedArray = context.obtainStyledAttributes(ATTR)
        divider = typedArray.getDrawable(0)
        typedArray.recycle()
        setOrientation(orientation)
    }

    private fun init(context: Context, @DrawableRes resId: Int, orientation: Int) {
        divider = ContextCompat.getDrawable(context, resId)
        setOrientation(orientation)
    }

    private fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw IllegalArgumentException("ListDivider must be attach to a vertical or horizontal RecyclerView.")
        }
        this.orientation = orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (divider == null) {
            super.onDraw(c, parent, state)
            return
        }

        if (orientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        val position = if (showFirstDivider) {
            0
        } else {
            1
        }

        for (i in position until childCount) {
            divider?.let {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.top - params.topMargin
                val bottom = top + it.intrinsicHeight
                it.setBounds(left, top, right, bottom)
                it.draw(canvas)
            }
        }

        if (showLastDivider && childCount > 0) {
            drawLastVertical(canvas, parent, left, right)
        }
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        val position = if (showFirstDivider) {
            0
        } else {
            1
        }

        for (i in position until childCount) {
            divider?.let {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val left = child.left - params.leftMargin
                val right = left + it.intrinsicWidth
                it.setBounds(left, top, right, bottom)
                it.draw(canvas)
            }
        }

        if (showLastDivider && childCount > 0) {
            drawLastHorizontal(canvas, parent, top, bottom)
        }
    }

    private fun drawLastVertical(canvas: Canvas, parent: RecyclerView, left: Int, right: Int) {
        val childCount = parent.childCount
        val child = parent.getChildAt(childCount - 1)
        val params = child.layoutParams as RecyclerView.LayoutParams
        divider?.run {
            val top = child.bottom + params.bottomMargin
            val bottom = top + intrinsicHeight
            setBounds(left, top, right, bottom)
            draw(canvas)
        }
    }

    private fun drawLastHorizontal(canvas: Canvas, parent: RecyclerView, top: Int, bottom: Int) {
        val childCount = parent.childCount
        val child = parent.getChildAt(childCount - 1)
        val params = child.layoutParams as RecyclerView.LayoutParams
        val left = child.right + params.rightMargin
        val right = left + divider!!.intrinsicWidth
        divider?.run {
            setBounds(left, top, right, bottom)
            draw(canvas)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (divider == null) {
            return
        }

        if (parent.getChildAdapterPosition(view) < 0) {
            return
        }

        divider?.let {
            if (orientation == VERTICAL_LIST) {
                outRect.top = it.intrinsicHeight
            } else {
                outRect.left = it.intrinsicWidth
            }
        }
    }

    fun setColor(@ColorInt color: Int) {
        divider?.let {
            it.setColorFilter(color, PorterDuff.Mode.SRC)
        }
    }
}
package com.stormers.storm.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MiddleDividerItemDecoration(context: Context, orientation: Int) : RecyclerView.ItemDecoration() {

    companion object {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
        const val ALL = 2
    }

    private var divider: Drawable? = null

    private var orientation: Int = 0

    private val rect = Rect()

    init {
        val typedArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = typedArray.getDrawable(0)
        typedArray.recycle()
        setOrientation(orientation)
    }

    fun setDividerColor(color: Int) {
        divider!!.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    private fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL && orientation != VERTICAL && orientation != ALL) {
            throw IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL")
        }
        this.orientation = orientation
    }

    fun setDrawable(drawable: Drawable) {
        divider = drawable
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null || divider == null) {
            return
        }

        when (orientation) {
            //Fixme: All 하면 세로 구분선만 생김
            ALL -> {
                drawHorizontal(canvas, parent)
                drawVertical(canvas, parent)
            }
            VERTICAL -> drawVertical(canvas, parent)
            else -> drawHorizontal(canvas, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        var childCount = parent.childCount
        if (parent.layoutManager is GridLayoutManager) {
            var leftItems = childCount % (parent.layoutManager as GridLayoutManager).spanCount

            if (leftItems == 0) {
                leftItems = (parent.layoutManager as GridLayoutManager).spanCount
            }
            childCount -= leftItems
        }

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i) ?: return
            parent.getDecoratedBoundsWithMargins(child, rect)
            val bottom = rect.bottom + Math.round(child.translationY)
            val top = bottom - divider!!.intrinsicHeight
            divider!!.setBounds(left, top, right, bottom)
            divider!!.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, top, parent.width - parent.paddingRight, bottom)
        } else {
            top = 0
            bottom = parent.height
        }

        var childCount = parent.childCount
        if (parent.layoutManager is GridLayoutManager) {
            childCount = (parent.layoutManager as GridLayoutManager).spanCount
        }

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i) ?: return
            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, rect)
            val right = rect.right + Math.round(child.translationX)
            val left = right - divider!!.intrinsicWidth
            divider!!.setBounds(left, top, right, bottom)
            divider!!.draw(canvas)
        }
        canvas.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (divider == null) {
            outRect.set(0, 0, 0, 0)
            return
        }
        if (orientation == VERTICAL) {
            outRect.set(0, 0, 0, divider!!.intrinsicHeight)
        } else {
            outRect.set(0, 0, divider!!.intrinsicWidth, 0)
        }
    }
}
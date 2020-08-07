package com.stormers.storm.customview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView

class ClickableRecyclerView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val recyclerView = RecyclerView(context)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    init {
        recyclerView.id = View.generateViewId()
        this.addView(recyclerView)
        ConstraintSet().also { cs ->
            cs.clone(this)
            cs.connect(
                recyclerView.id,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT,
                0
            )
            cs.connect(
                recyclerView.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                0
            )
            cs.applyTo(this)
        }

        recyclerView.layoutParams.apply {
            width = LayoutParams.MATCH_PARENT
            height = LayoutParams.MATCH_PARENT
        }
    }

}
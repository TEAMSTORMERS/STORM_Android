package com.stormers.storm.customview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.stormers.storm.R
import com.stormers.storm.ui.MypageActivity
import kotlinx.android.synthetic.main.view_storm_toolbar.view.*

class StormToolbar : ConstraintLayout {

    companion object {
        private const val layoutRes = R.layout.view_storm_toolbar
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(layoutRes, this, true)
    }

    fun setBackButton(listener: OnClickListener?) {
        this.imagebutton_stormtoolbar_left.run {
            visibility = View.VISIBLE
            setImageResource(R.drawable.mypage_ic_back)
            setOnClickListener {
                if (listener != null) {
                    listener.onClick(this@StormToolbar)
                } else {
                    (context as Activity).finish()
                }
            }
        }
    }

    fun setBackButton() {
        setBackButton(null)
    }

    fun setMyPageButton() {
        this.imagebutton_stormtoolbar_right.run {
            visibility = View.VISIBLE
            setImageResource(R.drawable.host_a_1_btn_mypage)
            setOnClickListener {
                context.startActivity(Intent(context as Activity, MypageActivity::class.java))
            }
        }
    }

    fun setExitButton(listener: OnClickListener) {
        this.imagebutton_stormtoolbar_right.run {
            visibility = View.VISIBLE
            setImageResource(R.drawable.h_roundstart_ic_exit)
            setOnClickListener {
                listener.onClick(this@StormToolbar)
            }
        }
    }

    fun clearButton() {
        this.imagebutton_stormtoolbar_right.run {
            visibility = View.INVISIBLE
            setOnClickListener(null)
        }

        this.imagebutton_stormtoolbar_left.run {
            visibility = View.INVISIBLE
            setOnClickListener(null)
        }
    }
}
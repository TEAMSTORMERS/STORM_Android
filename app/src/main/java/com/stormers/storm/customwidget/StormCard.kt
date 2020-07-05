package com.stormers.storm.customwidget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.stormers.storm.R
import com.stormers.storm.util.MetricsUtil
import kotlinx.android.synthetic.main.view_button_custom.view.*
import kotlinx.android.synthetic.main.view_card_custom.view.*

class StormCard : CardView {
    companion object {
        private const val layoutRes = R.layout.view_card_custom
        private val styleableRes = R.styleable.StormCard

        private const val RADIUS = 15f
    }

    var heartState = true

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context) {
        init()
        if (attrs != null) {
            getAttrs(attrs)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context) {
        init()
        if (attrs != null) {
            getAttrs(attrs, defStyleAttr)
        }
    }

    private fun init() {
        LayoutInflater.from(context).inflate(layoutRes, this, true)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, styleableRes)

        setTypedArray(typedArray)

        typedArray.recycle()
    }

    private fun getAttrs(attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, styleableRes, defStyleAttr, 0) as TypedArray

        setTypedArray(typedArray)

        typedArray.recycle()
    }

    private fun setTypedArray(typedArray: TypedArray) {
        val showHeartButton = typedArray.getBoolean(R.styleable.StormCard_showHeartButton, false)

        if (!showHeartButton) {
            imagebutton_customcard_heart.visibility = View.GONE
        } else {
            switchHeartState()
        }

        val isTouchable = typedArray.getBoolean(R.styleable.StormCard_isTouchable, false)

        if (isTouchable) {
            //Todo: 더블 클릭 시 하트 채우기
        }

        val elevation = typedArray.getDimension(R.styleable.StormButton_android_elevation, 1f)
        this.elevation = elevation

        radius = MetricsUtil.convertDpToPixel(RADIUS, context)
        cardview_customcard_root.setCardBackgroundColor(context.getColor(R.color.storm_white))
    }

    private fun switchHeartState() {
        imagebutton_customcard_heart.setOnClickListener {
            heartState = if (heartState) {
                imagebutton_customcard_heart.setImageResource(R.drawable.scrapcard_btn_heart_1)
                false
            } else {
                imagebutton_customcard_heart.setImageResource(R.drawable.scrapview_heart)
                true
            }
        }
    }

}
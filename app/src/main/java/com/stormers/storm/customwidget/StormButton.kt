package com.stormers.storm.customwidget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.stormers.storm.R
import kotlinx.android.synthetic.main.view_button_custom.view.*


class StormButton : CardView {

    companion object {
        private const val layoutRes = R.layout.view_button_custom
        private val styleableRes = R.styleable.StormButton
    }

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
        val strText = typedArray.getString(R.styleable.StormButton_android_text)

        if (strText != null) {
            textview_custombutton.text = strText
        } else {
            textview_custombutton.text = null
        }

//        val layoutParams = cardview_custombutton_root.layoutParams as ConstraintLayout.LayoutParams
//        layoutParams.matchConstraintPercentHeight = 0.4.toFloat()
//        mConstrainLayout.layoutParams = lp
    }
}
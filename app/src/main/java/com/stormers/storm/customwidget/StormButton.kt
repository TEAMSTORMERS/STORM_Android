package com.stormers.storm.customwidget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.base.BaseConstraintLayout
import kotlinx.android.synthetic.main.view_button_custom.view.*

class StormButton : BaseConstraintLayout {

    companion object {
        private const val layoutRes = R.layout.view_button_custom
        private val styleableRes = R.styleable.StormButton
    }

    constructor(context: Context?) : super(context, layoutRes, styleableRes)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, layoutRes, styleableRes)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr, layoutRes, styleableRes)

    override fun setTypedArray(typedArray: TypedArray) {
        val showArrow = typedArray.getBoolean(R.styleable.StormButton_showArrow, false)

        if (showArrow) {
            imageview_custombutton_arrow.visibility = View.VISIBLE
        } else {
            imageview_custombutton_arrow.visibility = View.GONE
        }

        val strText = typedArray.getString(R.styleable.StormButton_android_text)

        if (strText != null) {
            textview_custombutton.text = strText
        } else {
            textview_custombutton.text = null
        }

        //Fixme: 작동하지 않음 현재 padding을 하드코딩 한 상태
//        val paddingVertical = typedArray.getDimension(R.styleable.StormButton_paddingVertical, 0f).toInt()
//        val paddingHorizontal = typedArray.getDimension(R.styleable.StormButton_paddingHorizontal, 0f).toInt()
//        this.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)

        val textSize = typedArray.getDimension(R.styleable.StormButton_android_textSize, 12f)
        textview_custombutton.textSize = textSize
    }
}
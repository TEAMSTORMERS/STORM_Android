package com.stormers.storm.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.stormers.storm.R
import com.stormers.storm.util.MetricsUtil
import kotlinx.android.synthetic.main.view_button_custom.view.*


class StormButton : CardView {

    companion object {
        private const val layoutRes = R.layout.view_button_custom
        private val styleableRes = R.styleable.StormButton

        private const val MARGIN_VERTICAL = 13f
        private const val MARGIN_HORIZONTAL = 28f
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        if (attrs != null) {
            getAttrs(attrs)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
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

        val marginVertical = MetricsUtil.convertDpToPixel(MARGIN_VERTICAL, context).toInt()
        val marginHorizontal = MetricsUtil.convertDpToPixel(MARGIN_HORIZONTAL, context).toInt()
        (textview_custombutton.layoutParams as MarginLayoutParams).setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical)

        val elevation = typedArray.getDimension(R.styleable.StormButton_android_elevation, 1f)

        this.elevation = elevation

        radius = MetricsUtil.convertDpToPixel(7f, context)
        cardview_custombutton_root.setCardBackgroundColor(context.getColor(R.color.storm_yellow))
    }

    override fun setOnClickListener(l: OnClickListener?) {
        this.cardview_custombutton_root.setOnClickListener(l)
    }

}
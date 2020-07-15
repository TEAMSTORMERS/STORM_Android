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


    private var activation = true


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
        setText(typedArray)

        setPadding()

        setElevation(typedArray)

        setRadius()
    }

    private fun setText(typedArray: TypedArray) {
        val strText = typedArray.getString(R.styleable.StormButton_android_text)

        if (strText != null) {
            textview_custombutton.text = strText
        } else {
            textview_custombutton.text = null
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        this.cardview_custombutton_root.setOnClickListener(l)
    }

    private fun setPadding() {
        val marginVertical = MetricsUtil.convertDpToPixel(MARGIN_VERTICAL, context).toInt()
        val marginHorizontal = MetricsUtil.convertDpToPixel(MARGIN_HORIZONTAL, context).toInt()
        (textview_custombutton.layoutParams as MarginLayoutParams).setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical)
    }

    private fun setElevation(typedArray: TypedArray) {
        val elevation = typedArray.getDimension(R.styleable.StormButton_android_elevation, 1f)
        this.elevation = elevation

        useCompatPadding = true
    }

    private fun setRadius() {
        radius = MetricsUtil.convertDpToPixel(7f, context)
        cardview_custombutton_root.setCardBackgroundColor(context.getColor(R.color.storm_yellow))
    }

    fun getActivation() : Boolean {
        return this.activation
    }

    fun setActivation(activation: Boolean) {
        this.activation = activation
        this.cardview_custombutton_root.isEnabled = activation

        cardview_custombutton_root.run {
            if (activation) {
                setCardBackgroundColor(context.getColor(R.color.storm_yellow))
            } else {
                setCardBackgroundColor(context.getColor(R.color.storm_popup_gray))
            }
        }
    }

    fun setText(text: String) {
        textview_custombutton.text = text
    }
}
package com.stormers.storm.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.annotation.StyleableRes
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_edittext_custom.view.*

abstract class BaseConstraintLayout : ConstraintLayout {

    constructor(context: Context?, @LayoutRes layoutRes: Int, @StyleableRes styleableRes: IntArray) : super(context) {
        init(layoutRes)
    }

    constructor(context: Context?, attrs: AttributeSet?, @LayoutRes layoutRes: Int, @StyleableRes styleableRes: IntArray) : super(context, attrs) {
        init(layoutRes)
        if (attrs != null) {
            getAttrs(attrs, styleableRes)
        }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, @LayoutRes layoutRes: Int, @StyleableRes styleableRes: IntArray) : super(context, attrs, defStyleAttr) {
        init(layoutRes)
        if (attrs != null) {
            getAttrs(attrs,defStyleAttr, styleableRes)
        }
    }

    private fun init(@LayoutRes layoutRes: Int) {
        LayoutInflater.from(context).inflate(layoutRes, this, true)
    }

    private fun getAttrs(attrs: AttributeSet, @StyleableRes styleableRes: IntArray) {
        val typedArray = context.obtainStyledAttributes(attrs, styleableRes)

        setTypedArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyleAttr: Int, @StyleableRes styleableRes: IntArray) {
        val typedArray = context.obtainStyledAttributes(attrs, styleableRes, defStyleAttr, 0) as TypedArray

        setTypedArray(typedArray)
    }

    abstract fun setTypedArray(typedArray: TypedArray)
}
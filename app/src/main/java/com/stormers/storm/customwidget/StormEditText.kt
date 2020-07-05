package com.stormers.storm.customwidget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.stormers.storm.R
import kotlinx.android.synthetic.main.view_edittext_custom.view.*


class StormEditText : ConstraintLayout {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
        if (attrs != null) {
            getAttrs(attrs)
        }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
        if (attrs != null) {
            getAttrs(attrs,defStyleAttr)
        }
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_edittext_custom, this, true)

        imagebutton_customedittext.setOnClickListener {
            edittext_customedittext.text = null
        }
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StormEditText)

        setTypedArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StormEditText, defStyleAttr, 0) as TypedArray

        setTypedArray(typedArray)
    }

    fun setTypedArray(typedArray: TypedArray) {
        val showRemoveAll = typedArray.getBoolean(R.styleable.StormEditText_showRemoveAll, false)

        //전체 삭제 버튼
        if (showRemoveAll) {
            this.imagebutton_customedittext.visibility = View.VISIBLE
        } else {
            this.imagebutton_customedittext.visibility = View.GONE
        }

        val strHint = typedArray.getString(R.styleable.StormEditText_android_hint)

        //힌트
        if (strHint != null) {
            edittext_customedittext.hint = strHint
        } else {
            edittext_customedittext.hint = null
        }

        typedArray.recycle()
    }
}
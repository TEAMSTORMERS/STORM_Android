package com.stormers.storm.customview

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.stormers.storm.R
import kotlinx.android.synthetic.main.view_edittext_custom.view.*
import java.util.*


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

    var text: Editable? = null
    get() {
        field = this.edittext_customedittext.text
        return field
    }
    set(value) {
        field = value
        this.edittext_customedittext.text = field
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

        val lines = typedArray.getInteger(R.styleable.StormEditText_android_lines, -1)

        if (lines != -1) {
            edittext_customedittext.setLines(lines)
        }

        val inputType = typedArray.getInt(R.styleable.StormEditText_android_inputType, -1)

        if (inputType != -1) {
             edittext_customedittext.inputType = inputType
        }

        typedArray.recycle()
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        this.edittext_customedittext.addTextChangedListener(watcher)
    }

    fun removeTextChangedListener(watcher: TextWatcher) {
        this.edittext_customedittext.removeTextChangedListener(watcher)
    }
}
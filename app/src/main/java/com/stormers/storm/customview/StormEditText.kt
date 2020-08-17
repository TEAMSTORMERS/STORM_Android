package com.stormers.storm.customview

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginTop
import com.stormers.storm.R
import com.stormers.storm.util.MetricsUtil
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

        //최대 라인 수
        val lines = typedArray.getInteger(R.styleable.StormEditText_android_lines, -1)

        if (lines != -1) {
            edittext_customedittext.setLines(lines)
        }

        //입력 타입
        val inputType = typedArray.getInteger(R.styleable.StormEditText_android_inputType, -1)

        if (inputType != -1) {
             edittext_customedittext.inputType = inputType

            if (inputType == InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_CLASS_TEXT) {
                edittext_customedittext.run {
                    gravity = Gravity.TOP or Gravity.START
                    id = View.generateViewId()
                }

                imageview_customedittext_left.id = View.generateViewId()
                imagebutton_customedittext.id = View.generateViewId()
                (edittext_customedittext.layoutParams as LayoutParams).run {
                    height = 0
                    setMargins(MetricsUtil.convertDpToPixel(10f, context).toInt(), MetricsUtil.convertDpToPixel(10f, context).toInt(),
                        MetricsUtil.convertDpToPixel(10f, context).toInt(), MetricsUtil.convertDpToPixel(10f, context).toInt())
                }

                val constraints = ConstraintSet()
                constraints.clone(constraintlayout_stormedittext_root)
                constraints.constrainDefaultHeight(edittext_customedittext.id, ConstraintSet.MATCH_CONSTRAINT_SPREAD)
                constraints.applyTo(constraintlayout_stormedittext_root)
            }
        }

        val srcDrawable = typedArray.getDrawable(R.styleable.StormEditText_android_drawableStart)

        if (srcDrawable != null) {
            imageview_customedittext_left.run {
                visibility = View.VISIBLE
                setImageDrawable(srcDrawable)
            }
        }

        typedArray.recycle()
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        this.edittext_customedittext.addTextChangedListener(watcher)
    }

    fun removeTextChangedListener(watcher: TextWatcher) {
        this.edittext_customedittext.removeTextChangedListener(watcher)
    }

    companion object {
        private const val TAG = "StormEditText"
    }
}
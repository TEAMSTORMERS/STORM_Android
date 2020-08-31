package com.stormers.storm.canvas.base

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.stormers.storm.R
import com.stormers.storm.card.fragment.AddCardFragment
import com.stormers.storm.round.base.BaseRoundFragment
import com.stormers.storm.ui.RoundProgressActivity
import kotlinx.android.synthetic.main.activity_round_progress.*
import kotlinx.android.synthetic.main.fragment_round_canvas.*

abstract class BaseCanvasFragment(private val mode: Int, @LayoutRes private val canvasLayout: Int) :
    BaseRoundFragment(R.layout.fragment_round_canvas) {

    companion object {
        const val DRAWING_MODE = 100
        const val TEXT_MODE = 200
    }

    private lateinit var targetModeStr: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LayoutInflater.from(context).inflate(canvasLayout, cardview_roundcanvas_canvas)

        (mActivity as RoundProgressActivity).run {
            stormtoolbar_roundprogress.setBackButton(View.OnClickListener {
                goToFragment(AddCardFragment::class.java, null)
            })
        }

        initCanvas()

        when (mode) {
            DRAWING_MODE -> {
                targetModeStr = "글 "

                imagebutton_change_text.alpha = 0.5f

                imagebutton_change_text.setOnClickListener {
                    (mActivity as RoundProgressActivity).run {
                        showFragment(canvasTextFragment!!)
                        hideFragment(this@BaseCanvasFragment)
                    }
                }
            }

            else -> {
                targetModeStr = "그림 "

                var pretext = ""

                val editText = view.findViewById<EditText>(R.id.edittext_addcard)


                editText.addTextChangedListener(object: TextWatcher {
                    override fun afterTextChanged(p0: Editable?) { }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        if (editText.layout != null && editText.lineCount > 10) {
                            editText.text.delete(editText.text.length - 1, editText.text.length)
                        }
                    }
                })

                imagebutton_change_draw.alpha = 0.5f

                imagebutton_change_draw.setOnClickListener {
                    (mActivity as RoundProgressActivity).run {
                        showFragment(canvasDrawingFragment!!)
                        hideFragment(this@BaseCanvasFragment)
                    }
                }
                group_canvas_unredo.visibility = View.INVISIBLE
            }
        }

        stormbutton_roundcanvas_apply.setOnClickListener {
            onApplied()
        }

        imagebutton_canvas_trash.setOnClickListener {
            onTrashed()
        }
    }

    protected abstract fun onTrashed()

    protected abstract fun onApplied()

    protected open fun initCanvas() {
        return
    }
}
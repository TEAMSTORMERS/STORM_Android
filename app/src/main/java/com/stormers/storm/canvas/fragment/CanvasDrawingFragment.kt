package com.stormers.storm.canvas.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.stormers.storm.R
import com.stormers.storm.canvas.base.BaseCanvasFragment
import kotlinx.android.synthetic.main.fragment_round_canvas.*
import kotlinx.android.synthetic.main.view_signaturepad.*

class CanvasDrawingFragment : BaseCanvasFragment(DRAWING_MODE) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //뷰 초기화
        initView()
        //그림 그릴 수 있도록 초기화
        initDrawCanvas()
    }

    private fun initView() {
        imagebutton_change_text.alpha = 0.5f

        imagebutton_change_text.setOnClickListener {
            showChangeDialog()
        }
    }

    private fun initDrawCanvas() {
        LayoutInflater.from(context).inflate(R.layout.view_signaturepad, cardview_roundcanvas_canvas)

        imagebutton_canvas_trash.setOnClickListener{
            signaturepad.clear()
        }
    }
}